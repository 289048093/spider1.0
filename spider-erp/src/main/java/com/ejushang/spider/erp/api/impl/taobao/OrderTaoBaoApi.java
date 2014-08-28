package com.ejushang.spider.erp.api.impl.taobao;

import com.ejushang.spider.bean.ShopBean;
import com.ejushang.spider.constant.OriginalOrderStatus;
import com.ejushang.spider.constant.OutPlatformType;
import com.ejushang.spider.domain.OrderFetch;
import com.ejushang.spider.domain.OriginalOrder;
import com.ejushang.spider.domain.OriginalOrderItem;
import com.ejushang.spider.domain.taobao.JdpTbTrade;
import com.ejushang.spider.erp.api.IOrderApi;
import com.ejushang.spider.exception.TaoBaoApiException;
import com.ejushang.spider.query.taobao.JdpTbTradeQuery;
import com.ejushang.spider.service.order.IOrderFetchService;
import com.ejushang.spider.service.order.IOriginalOrderService;
import com.ejushang.spider.service.taobao.IJdpTbTradeService;
import com.ejushang.spider.taobao.api.ProductApi;
import com.ejushang.spider.taobao.api.ShopApi;
import com.ejushang.spider.taobao.api.TradeApi;
import com.ejushang.spider.taobao.api.UserApi;
import com.ejushang.spider.taobao.helper.ConstantTaoBao;
import com.ejushang.spider.taobao.helper.ConvertUtils;
import com.ejushang.spider.util.DateUtilHelper;
import com.ejushang.spider.util.DateUtils;
import com.taobao.api.domain.*;
import com.taobao.api.internal.util.TaobaoUtils;
import com.taobao.api.response.TradeFullinfoGetResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 关注于从淘宝抓取订单
 * User: Baron.Zhang
 * Date: 14-1-9
 * Time: 下午2:22
 */
@Service
public class OrderTaoBaoApi implements IOrderApi {

    private static final Logger log = LoggerFactory.getLogger(OrderTaoBaoApi.class);

    @Autowired
    private IOrderFetchService orderFetchService;

    @Autowired
    private IOriginalOrderService originalOrderService;

    @Autowired
    private IJdpTbTradeService jdpTbTradeService;



    /**
     * 从淘宝抓取订单
     * @param shopBean
     * @return
     */
    @Transactional
    public List<OriginalOrder> fetchOrder(ShopBean shopBean) throws Exception {

        if(log.isInfoEnabled()){
            log.info("淘宝API抓取订单：开始淘宝API初始化……");
        }
        //淘宝API初始化
        // 淘宝交易API
        TradeApi tradeApi = new TradeApi(shopBean.getSessionKey());

        // 设置查询订单开始时间和结束时间
        Date start = null;
        Date end = null;
        // 查询淘宝平台的最后抓取记录
        OrderFetch orderFetch = orderFetchService.findLastOrderFetch(OutPlatformType.TAO_BAO.toDesc(),shopBean.getShopId());
        if(orderFetch == null){ // 没有抓取记录
            // 当前时间往前推5分钟
            start = DateUtilHelper.addMinute(DateUtils.getCurrentDate(), -timeInterval);
        }
        else{
            // 将抓取订单时间往前推1秒
            start = DateUtilHelper.addSecond(orderFetch.getFetchTime(),timeDelay);
        }
        // 开始时间相对于当前时间超过30Min
        if(DateUtilHelper.addMinute(start,timeIntervalIncrement).compareTo(DateUtils.getCurrentDate()) > 0){
            end = DateUtils.getCurrentDate();
        }
        else{
            end = DateUtilHelper.addMinute(start,timeIntervalIncrement);
        }

        if(log.isInfoEnabled()){
            log.info("淘宝API抓取订单：订单抓取开始时间：" + start);
            log.info("淘宝API抓取订单：订单抓取结束时间：" + end);
        }

        //start = DateUtils.parseDate("2014-01-15 13:35:00", DateUtils.DateFormatType.DATE_FORMAT_STR);
        //end = DateUtils.parseDate("2014-01-15 13:40:00", DateUtils.DateFormatType.DATE_FORMAT_STR);

        // 从淘宝平台获取订单信息
        Map<String, Object> soldTradeMap = getTradesSoldMap(tradeApi,start,end);

        // 订单总数
        Long totalResults = (Long) soldTradeMap.get(ConstantTaoBao.TOTAL_RESULTS);
        // 订单集合
        List<Trade> trades = (List<Trade>) soldTradeMap.get(ConstantTaoBao.TRADES);
        if(log.isInfoEnabled()){
            log.info("淘宝API抓取订单：抓取订单总数为：" + totalResults);
        }

        // 获取店铺信息
        // Shop shop = getShop(userApi, shopApi);

        // 保存原始订单和原始订单项
        List<OriginalOrder> originalOrders = getOriginalOrders(trades, shopBean);
        if(log.isInfoEnabled()){
            log.info("淘宝API抓取订单：转换后的原始订单数为：" + originalOrders.size());
            log.info("淘宝API抓取订单：保存原始订单及其订单项");
        }

        for(OriginalOrder originalOrder : originalOrders){
            // 保存原始订单以及相应的原始订单项（一次事务操作）
            originalOrderService.saveOriginalOrderAndItem(originalOrder,originalOrder.getOriginalOrderItems());
        }

        if(log.isInfoEnabled()){
            log.info("淘宝API抓取订单：保存订单抓取记录");
        }
        // 更新抓取时间(只要过程没有异常，就要更新抓取时间，否则会有重复抓取
        // 所有操作成功完成，添加订单抓取记录
        OrderFetch orderFetch1 = getOrderFetch(shopBean,end,trades.size());
        orderFetchService.saveOrderFetch(orderFetch1);
        return originalOrders;
    }

    @Override
    public List<OriginalOrder> fetchOrderByJst(ShopBean shopBean) throws Exception {

        if(log.isInfoEnabled()){
            log.info("聚石塔订单抓取开始：：：：：：：：：：：：：：");
        }

        // 设置查询订单开始时间和结束时间
        Date start = null;
        Date end = null;
        // 查询淘宝平台的最后抓取记录
        OrderFetch orderFetch = orderFetchService.findLastOrderFetch(OutPlatformType.TAO_BAO.toDesc(),shopBean.getShopId());
        if(orderFetch == null){ // 没有抓取记录
            // 当前时间往前推5分钟
            start = DateUtilHelper.addMinute(DateUtils.getCurrentDate(), -timeInterval);
        }
        else{
            // 将抓取订单时间往前推1秒
            start = DateUtilHelper.addSecond(orderFetch.getFetchTime(),timeDelay);
        }
        // 开始时间相对于当前时间超过30Min
        if(DateUtilHelper.addMinute(start,timeIntervalIncrement).compareTo(DateUtils.getCurrentDate()) > 0){
            end = DateUtils.getCurrentDate();
        }
        else{
            end = DateUtilHelper.addMinute(start,timeIntervalIncrement);
        }



        //start = DateUtils.parseDate("2014-01-15 13:35:00", DateUtils.DateFormatType.DATE_FORMAT_STR);
        //end = DateUtils.parseDate("2014-01-15 13:40:00", DateUtils.DateFormatType.DATE_FORMAT_STR);

        if(log.isInfoEnabled()){
            log.info("聚石塔订单抓取：：：：订单抓取开始时间:" + start);
            log.info("聚石塔订单抓取：：：：订单抓取结束时间:" + end);
            log.info("聚石塔订单抓取：：：：店铺参数:" + shopBean);
        }

        // 从聚石塔获取交易信息
        JdpTbTradeQuery jdpTbTradeQuery = new JdpTbTradeQuery();
        jdpTbTradeQuery.setSellerNick(shopBean.getSellerNick());
        jdpTbTradeQuery.setStartJdpModified(start);
        jdpTbTradeQuery.setEndJdpModified(end);
        jdpTbTradeQuery.setStatus(OriginalOrderStatus.WAIT_SELLER_SEND_GOODS.toDesc());
        List<JdpTbTrade> jdpTbTradeList = jdpTbTradeService.findJdpTbTradeByJdpTbTradeQuery(jdpTbTradeQuery);

        if(log.isInfoEnabled()){
            log.info("聚石塔订单抓取：：：：抓取到原始淘宝交易信息：" + jdpTbTradeList.size() + "条");
        }

        // 将json交易信息转化为淘宝交易信息
        List<Trade> trades = new ArrayList<Trade>();
        if(CollectionUtils.isNotEmpty(jdpTbTradeList)){
            for (JdpTbTrade jdpTbTrade : jdpTbTradeList){
                TradeFullinfoGetResponse rsp = TaobaoUtils.parseResponse(
                        jdpTbTrade.getJdpResponse(), TradeFullinfoGetResponse.class);
                if(StringUtils.isNotBlank(rsp.getErrorCode())){
                    throw new TaoBaoApiException(rsp.getBody());
                }
                if(rsp.getTrade() != null){
                    trades.add(rsp.getTrade());
                }
            }
        }

        /*// 从淘宝平台获取订单信息
        Map<String, Object> soldTradeMap = getTradesSoldMap(tradeApi,start,end);
        // 订单总数
        Long totalResults = (Long) soldTradeMap.get(ConstantTaoBao.TOTAL_RESULTS);
        // 订单集合
        List<Trade> trades = (List<Trade>) soldTradeMap.get(ConstantTaoBao.TRADES);*/
        if(log.isInfoEnabled()){
            log.info("聚石塔订单抓取：抓取订单总数为：" + trades.size());
        }

        // 获取店铺信息
        //Shop shop = getShop(userApi, shopApi);

        // 保存原始订单和原始订单项
        List<OriginalOrder> originalOrders = getOriginalOrders(trades, shopBean);

        if(log.isInfoEnabled()){
            log.info("聚石塔订单抓取：转换后的原始订单数为：" + originalOrders.size());
        }

        if(log.isInfoEnabled()){
            log.info("聚石塔订单抓取：保存原始订单及其订单项");
        }
        for(OriginalOrder originalOrder : originalOrders){
            // 保存原始订单以及相应的原始订单项（一次事务操作）
            originalOrderService.saveOriginalOrderAndItem(originalOrder,originalOrder.getOriginalOrderItems());
        }

        // 更新抓取时间(只要过程没有异常，就要更新抓取时间，否则会有重复抓取
        // 所有操作成功完成，添加订单抓取记录
        if(log.isInfoEnabled()){
            log.info("聚石塔订单抓取：保存订单抓取记录");
        }
        OrderFetch orderFetch1 = getOrderFetch(shopBean,end,trades.size());
        orderFetchService.saveOrderFetch(orderFetch1);
        return originalOrders;
    }

    /**
     * 获取订单抓取信息
     * @param shopBean
     * @return
     */
    private OrderFetch getOrderFetch(ShopBean shopBean,Date end,int fetchCount) {
        OrderFetch orderFetch1 = new OrderFetch();
        // 设置抓取时间，为end
        orderFetch1.setFetchTime(end);
        orderFetch1.setOutPlatform(OutPlatformType.TAO_BAO.toDesc());
        orderFetch1.setShopId(shopBean.getShopId());
        orderFetch1.setFetchCount(fetchCount);
        return orderFetch1;
    }

    /**
     * 获取原始订单集合
     * @param trades
     * @param shopBean
     * @return
     */
    private List<OriginalOrder> getOriginalOrders(List<Trade> trades, ShopBean shopBean) throws Exception {
        // 原始订单
        OriginalOrder originalOrder = null;
        // 原始订单集合
        List<OriginalOrder> originalOrders = new ArrayList<OriginalOrder>();
        // 原始订单项
        OriginalOrderItem originalOrderItem = null;
        // 原始订单项集合
        List<OriginalOrderItem> originalOrderItems = null;

        if(trades == null){
            return originalOrders;
        }

        for (Trade trade : trades){
            // 将外部订单转化为系统原始订单
            originalOrder = convertTrade2OriginalOrder(trade,shopBean);
            if(trade.getOrders() != null){
                // 创建保存原始订单项的集合
                originalOrderItems = new ArrayList<OriginalOrderItem>();
                for (Order order : trade.getOrders()){
                    originalOrderItem = convertOrder2OriginalOrderItem(trade, order);
                    originalOrderItems.add(originalOrderItem);
                }
            }
            // 交易记录的status都在Order中
            if(StringUtils.isBlank(originalOrder.getStatus())){
                originalOrder.setStatus(trade.getOrders().get(0).getStatus());
            }
            originalOrder.setOriginalOrderItems(originalOrderItems);
            // 添加原始订单至集合
            originalOrders.add(originalOrder);
        }

        return originalOrders;
    }

    /**
     * 获取店铺信息
     * @param userApi
     * @param shopApi
     * @return
     * @throws Exception
     */
    private Shop getShop(UserApi userApi, ShopApi shopApi) throws Exception {
        if(log.isInfoEnabled()){
            log.info("从淘宝平台获取用户信息，fields："+getUserFields());
        }
        // 淘宝用户API
        User user =  userApi.getSeller(getUserFields());
        // 淘宝店铺API
        Map<String,Object> shopArgsMap = new HashMap<String, Object>();
        shopArgsMap.put(ConstantTaoBao.FIELDS,getShopFields());
        shopArgsMap.put(ConstantTaoBao.NICK,user.getNick());
        if(log.isInfoEnabled()){
            log.info("从淘宝平台获取店铺信息，shopArgsMap："+shopArgsMap);
        }
        // 获取店铺
        Shop shop = shopApi.getShop(shopArgsMap);

        return shop;
    }

    /**
     * 从淘宝平台获取订单信息
     * @param tradeApi
     * @return
     * @throws Exception
     */
    private Map<String, Object> getTradesSoldMap(TradeApi tradeApi,Date start,Date end) throws Exception {
        // 创建保存订单查询的map
        Map<String,Object> tradeArgsMap = new HashMap<String, Object>();
        // 获取并设置查询订单的field
        tradeArgsMap.put(ConstantTaoBao.FIELDS,getTradeFields());
        // 设置每次查询100条 减少调用API的次数
        tradeArgsMap.put(ConstantTaoBao.PAGE_SIZE,pageSize);
        // 设置抓取订单状态
        tradeArgsMap.put(ConstantTaoBao.STATUS, OriginalOrderStatus.WAIT_SELLER_SEND_GOODS.toDesc());
        // 设置订单创建时间的起始
        tradeArgsMap.put(ConstantTaoBao.START_CREATED,start);
        // 设置订单创建时间的结束
        tradeArgsMap.put(ConstantTaoBao.END_CREATED,end);

        if(log.isInfoEnabled()){
            log.info("开始从淘宝平台查询订单信息……，参数argsMap = " + tradeArgsMap);
        }
        // 调用淘宝API查询订单信息
        return tradeApi.getTradesSold(tradeArgsMap);
    }

    /**
     * 获取用户查询字段
     * @return
     */
    private String getUserFields() {
        StringBuffer fieldBuffer = new StringBuffer();
        // 添加User（用户）查询字段
        fieldBuffer.append("nick"); // 用户昵称
        return fieldBuffer.toString();
    }

    /**
     * 获取店铺查询字段
     * @return
     */
    private String getShopFields() {
        StringBuffer fieldBuffer = new StringBuffer();
        // 添加Shop（店铺）查询字段
        fieldBuffer.append("sid,"); // 店铺编号
        fieldBuffer.append("title"); // 店铺标题（名称？）
        return fieldBuffer.toString();
    }

    /**
     * 获取商品查询字段
     * @return
     */
    private String getProductFields() {
        StringBuffer fieldBuffer = new StringBuffer();
        // 添加Product（商品）查询字段
        fieldBuffer.append("outer_id"); // 商家外部编号（即商品条形码）
        return fieldBuffer.toString();
    }

    /**
     * 获取订单查询字段
     * @return
     */
    private String getTradeFields() {
        StringBuffer fieldBuffer = new StringBuffer();
        // 添加Trade（交易）查询字段
        fieldBuffer.append("tid,"); // 交易编号 (父订单的交易编号)
        fieldBuffer.append("num,"); // 商品购买数量
        fieldBuffer.append("num_iid"); // 商品数字编号
        fieldBuffer.append("status,"); // 交易状态
        fieldBuffer.append("title,"); // 交易标题
        fieldBuffer.append("type,"); // 交易类型列表
        fieldBuffer.append("price,"); // 商品价格
        fieldBuffer.append("discount_fee,"); // 系统优惠金额
        fieldBuffer.append("point_fee,"); // 买家使用积分,下单时生成，且一直不变
        fieldBuffer.append("total_fee,"); // 商品金额（商品价格乘以数量的总金额）
        fieldBuffer.append("created,"); // 交易创建时间
        fieldBuffer.append("pay_time,"); // 付款时间
        fieldBuffer.append("modified,"); // 交易修改时间
        fieldBuffer.append("end_time,"); // 交易结束时间
        fieldBuffer.append("alipay_id,"); // 买家的支付宝id号
        fieldBuffer.append("alipay_no,"); // 支付宝交易号
        fieldBuffer.append("buyer_nick,"); // 买家昵称
        fieldBuffer.append("buyer_area,"); // 买家下单的地区
        fieldBuffer.append("trade_from,"); // 交易内部来源
        fieldBuffer.append("receiver_city,"); // 收货人的所在城市
        fieldBuffer.append("receiver_district,"); // 收货人的所在地区
        fieldBuffer.append("seller_nick,"); // 卖家昵称
        fieldBuffer.append("payment,"); // 实付金额
        fieldBuffer.append("post_fee,"); // 邮费
        fieldBuffer.append("receiver_name,"); // 收货人的姓名
        fieldBuffer.append("receiver_state,"); // 收货人的所在省份
        fieldBuffer.append("receiver_address,"); // 收货人的详细地址
        fieldBuffer.append("receiver_zip,"); // 收货人的邮编
        fieldBuffer.append("receiver_mobile,"); // 收货人的手机号码
        fieldBuffer.append("receiver_phone,"); // 收货人的电话号码
        fieldBuffer.append("received_payment,"); // 卖家实际收到的支付宝打款金额
        // 添加Order（订单）查询字段
        fieldBuffer.append("orders.oid,"); // 子订单编号
        fieldBuffer.append("orders.status,"); // 订单状态
        fieldBuffer.append("orders.title,"); // 商品标题
        fieldBuffer.append("orders.price,"); // 商品价格
        fieldBuffer.append("orders.num_iid,"); // 商品数字ID
        fieldBuffer.append("orders.item_meal_id,"); // 套餐ID
        fieldBuffer.append("orders.sku_id,"); // 商品的最小库存单位Sku的id
        fieldBuffer.append("orders.num,"); // 购买数量
        fieldBuffer.append("orders.total_fee,"); // 应付金额
        fieldBuffer.append("orders.payment,"); // 子订单实付金额
        fieldBuffer.append("orders.discount_fee,"); // 子订单级订单优惠金额
        fieldBuffer.append("orders.modified,"); // 订单修改时间
        fieldBuffer.append("orders.seller_nick,"); // 卖家昵称
        fieldBuffer.append("orders.buyer_nick,"); // 买家昵称
        fieldBuffer.append("orders.outer_iid,"); // 商家外部编码
        fieldBuffer.append("orders.cid"); // 交易商品对应的类目ID
        return fieldBuffer.toString();
    }


    /**
     * 获取店铺信息
     * @return
     * @throws Exception
     */
    private Shop getShop(String sessionKey) throws Exception {
        // 淘宝用户API
        UserApi userApi = new UserApi(sessionKey);
        User user =  userApi.getSeller("nick");
        // 淘宝店铺API
        ShopApi shopApi = new ShopApi(sessionKey);
        Map<String,Object> argsMapShop = new HashMap<String, Object>();
        argsMapShop.put(ConstantTaoBao.FIELDS,"sid,title");
        argsMapShop.put(ConstantTaoBao.NICK,user.getNick());
        return shopApi.getShop(argsMapShop);
    }

    /**
     * 将外部订单对象Order转换为内部原始订单项OriginalOrderItem对象
     * @param trade
     * @param order
     * @return
     */
    private OriginalOrderItem convertOrder2OriginalOrderItem(Trade trade, Order order) throws Exception {
        if(order == null){
            return null;
        }
        OriginalOrderItem originalOrderItem = new OriginalOrderItem();
        // 原始订单号
        originalOrderItem.setOrderNo(ConvertUtils.convertLong2String(trade.getTid()));
        // 子订单号
        originalOrderItem.setSubOrderNo(ConvertUtils.convertLong2String(order.getOid()));

        // 有的商品可能没有SKU
        // 商品条形码 (从淘宝平台上获取商品条形码)
//        if(!StringUtils.isBlank(order.getSkuId())){
//            Sku sku = getSku(order, productApi);
//            originalOrderItem.setSkuCode(sku.getOuterId());
//        }
        originalOrderItem.setSkuCode(order.getOuterSkuId());
        // 商品价格
        originalOrderItem.setPrice(ConvertUtils.convertYuanToCent(order.getPrice()));
        // 商品购买数量
        originalOrderItem.setBuyCount(order.getNum());
        // 应付金额（商品价格 * 商品数量 + 手工调整金额 - 子订单级订单优惠金额）
        originalOrderItem.setTotalFee(ConvertUtils.convertYuanToCent(order.getTotalFee()));
        // 子订单实付金额。精确到2位小数，单位:元。如:200.07，表示:200元7分。
        // 对于多子订单的交易，计算公式如下：payment = price * num + adjust_fee - discount_fee ；
        // 单子订单交易，payment与主订单的payment一致，对于退款成功的子订单，
        // 由于主订单的优惠分摊金额，会造成该字段可能不为0.00元。
        // 建议使用退款前的实付金额减去退款单中的实际退款金额计算。
        originalOrderItem.setActualFee(ConvertUtils.convertYuanToCent(order.getPayment()));
        // 子订单级订单优惠金额
        originalOrderItem.setDiscountFee(ConvertUtils.convertYuanToCent(order.getDiscountFee()));
        // 手工调整金额
        originalOrderItem.setAdjustFee(ConvertUtils.convertYuanToCent(order.getAdjustFee()));
        // 分摊之后的实付金额
        originalOrderItem.setDivideOrderFee(ConvertUtils.convertYuanToCent(order.getDivideOrderFee()));
        // 优惠分摊
        originalOrderItem.setPartMjzDiscount(ConvertUtils.convertYuanToCent(order.getPartMjzDiscount()));

        return originalOrderItem;
    }

    /**
     * 获取商品的SKU信息
     * @param order
     * @param productApi
     * @return
     * @throws Exception
     */
    private Sku getSku(Order order, ProductApi productApi) throws Exception {
        Map<String,Object> productArgsMap = new HashMap<String, Object>();
        productArgsMap.put(ConstantTaoBao.FIELDS,getProductFields());
        productArgsMap.put(ConstantTaoBao.NUM_IID,order.getNumIid());
        productArgsMap.put(ConstantTaoBao.SKU_ID, Long.valueOf(order.getSkuId()));
        return productApi.getItemSku(productArgsMap);
    }

    /**
     * 将外部交易对象Trade转换为内部原始订单OriginalOrder对象
     * @param trade
     * @return
     */
    private OriginalOrder convertTrade2OriginalOrder(Trade trade,ShopBean shopBean) {
        if(trade == null){
            return null;
        }
        OriginalOrder originalOrder = new OriginalOrder();
        // 原始订单号
        originalOrder.setOutOrderNo(ConvertUtils.convertLong2String(trade.getTid()));
        // 交易状态
        originalOrder.setStatus(trade.getStatus());
        // 商品金额（商品价格乘以数量的总金额）
        originalOrder.setTotalFee(ConvertUtils.convertYuanToCent(trade.getTotalFee()));
        // 建议使用trade.promotion_details查询系统优惠 系统优惠金额（如打折，VIP，满就送等）
        originalOrder.setDiscountFee(ConvertUtils.convertYuanToCent(trade.getDiscountFee()));
        // 买家使用积分,下单时生成，且一直不变
        originalOrder.setPointFee(trade.getPointFee());
        // 是否包含邮费。与available_confirm_fee同时使用
        originalOrder.setHasPostFee(trade.getHasPostFee());
        // 交易中剩余的确认收货金额（这个金额会随着子订单确认收货而不断减少，交易成功后会变为零）
        originalOrder.setAvailableConfirmFee(ConvertUtils.convertYuanToCent(trade.getAvailableConfirmFee()));
        // 买家实际使用积分（扣除部分退款使用的积分），交易完成后生成（交易成功或关闭），交易未完成时该字段值为0
        originalOrder.setRealPointFee(trade.getRealPointFee());
        // 邮费
        originalOrder.setPostFee(ConvertUtils.convertYuanToCent(trade.getPostFee()));
        // 卖家实际收到的支付宝打款金额（由于子订单可以部分确认收货，这个金额会随着子订单的确认收货而不断增加，交易成功后等于买家实付款减去退款金额）
        originalOrder.setReceivedPayment(ConvertUtils.convertYuanToCent(trade.getReceivedPayment()));

        // 买家留言
        originalOrder.setBuyerMessage(trade.getBuyerMessage());
        // 客服备注
        originalOrder.setRemark(trade.getSellerMemo());
        // 买家id
        originalOrder.setBuyerId(trade.getBuyerNick());
        // 收货人姓名
        //originalOrder.setReceiverName(StringUtils.isBlank(trade.getReceiverName()) ? "sandbox_ejs_test1" : trade.getReceiverName());
        originalOrder.setReceiverName(trade.getReceiverName());
        // 收货人电话
        originalOrder.setReceiverPhone(trade.getReceiverPhone());
        // 收货人手机号
        originalOrder.setReceiverMobile(trade.getReceiverMobile());
        // 收货人邮编
        originalOrder.setReceiverZip(trade.getReceiverZip());
        // 收货人省份
        //originalOrder.setReceiverState(StringUtils.isBlank(trade.getReceiverState()) ? "广东省" : trade.getReceiverState());
        originalOrder.setReceiverState(trade.getReceiverState());
        // 收货人城市
        //originalOrder.setReceiverCity(StringUtils.isBlank(trade.getReceiverCity()) ? "深圳市" : trade.getReceiverCity());
        originalOrder.setReceiverCity(trade.getReceiverCity());
        // 收货人地区
        originalOrder.setReceiverDistrict(trade.getReceiverDistrict());
        // 收货人详细地址
        //originalOrder.setReceiverAddress(StringUtils.isBlank(trade.getReceiverAddress()) ? "宝安区大宝路41号" : trade.getReceiverAddress());
        originalOrder.setReceiverAddress(trade.getReceiverAddress());
        // 下单时间（交易创建时间）
        originalOrder.setBuyTime(trade.getCreated());
        // 支付时间
        originalOrder.setPayTime(trade.getPayTime());
        // 外部平台类型
        originalOrder.setOutPlatformType(OutPlatformType.TAO_BAO.toDesc());
        // 店铺id
        originalOrder.setShopId(Long.parseLong(shopBean.getShopId()));
        // 店铺名称 （店铺标题）

        originalOrder.setShopName(shopBean.getTitle());
        // 是否需要发票
        Boolean needInvoice = StringUtils.isBlank(trade.getInvoiceName()) ? false : true;
        originalOrder.setNeedInvoice(needInvoice);
        // 发票抬头
        originalOrder.setInvoiceName(trade.getInvoiceName());
        // 发票内容 : 暂时获取不到
        // 设置是否处理标识 默认为false
        originalOrder.setProcessed(false);

        return originalOrder;
    }
}
