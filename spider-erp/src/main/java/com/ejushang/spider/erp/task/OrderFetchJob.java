package com.ejushang.spider.erp.task;

import com.ejushang.spider.constant.OutPlatformType;
import com.ejushang.spider.domain.OrderFetch;
import com.ejushang.spider.domain.OriginalOrder;
import com.ejushang.spider.domain.OriginalOrderItem;
import com.ejushang.spider.service.order.IOrderFetchService;
import com.ejushang.spider.service.order.IOriginalOrderItemService;
import com.ejushang.spider.service.order.IOriginalOrderService;
import com.ejushang.spider.taobao.api.ShopApi;
import com.ejushang.spider.taobao.api.TradeApi;
import com.ejushang.spider.taobao.api.UserApi;
import com.ejushang.spider.taobao.helper.ConstantTaoBao;
import com.ejushang.spider.taobao.helper.ConvertUtils;
import com.ejushang.spider.taobao.helper.TaoBaoUtilsEjs;
import com.ejushang.spider.util.DateUtilHelper;
import com.ejushang.spider.util.Money;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Shop;
import com.taobao.api.domain.Trade;
import com.taobao.api.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;

/**
 * User: Baron.Zhang
 * Date: 13-12-26
 * Time: 下午1:37
 */
@Component("orderFetchJob")
public class OrderFetchJob {

    private Logger log = LoggerFactory.getLogger(OrderFetchJob.class);

    // 查询间隔时间，单位：分钟
    private static final Integer timeInterval = 5;
    // 当前最后抓取时间往后推的时间，单位：秒
    private static final Integer timeDelay = 1;
    // 查询每页记录数
    private static final Long pageSize = 100L;

    /**
     * 原始订单操作service
     */
    @Autowired
    private IOriginalOrderService originalOrderService;

    /**
     * 原始订单项操作service
     */
    @Autowired
    private IOriginalOrderItemService originalOrderItemService;

    /**
     * 订单抓取操作service
     */
    @Autowired
    private IOrderFetchService orderFetchService;

    /**
     * 定时任务：抓取订单<br/>
     * 每5分钟抓取一次订单
     */
    //@Scheduled(cron = "0 0/5 * * * ?")
    public void fetchOriginalOrders() throws Exception {

        // 淘宝交易API
        TradeApi tradeApi = new TradeApi(TaoBaoUtilsEjs.getSessionKey());
        // 保存参数的Map
        Map<String,Object> argsMap = new HashMap<String, Object>();
        // 获取并设置查询的field
        String fields = getFields();
        argsMap.put(ConstantTaoBao.FIELDS,fields);
        // 设置每次查询100条 减少调用API的次数
        argsMap.put(ConstantTaoBao.PAGE_SIZE,pageSize);

        Shop shop = getShop(TaoBaoUtilsEjs.getSessionKey());
        // 获取最后一条订单抓取记录
        OrderFetch orderFetch = orderFetchService.findLastOrderFetch(OutPlatformType.TAO_BAO.toDesc(),String.valueOf(shop.getSid()));
        Date start = com.ejushang.spider.util.DateUtils.getCurrentDate();
        if(orderFetch == null){ // 没有抓取记录
            // 当前时间往前推5分钟
            start = DateUtilHelper.addMinute(start, -timeInterval);
        }
        else{
            // 将抓取订单时间往前推1秒
            start = DateUtilHelper.addSecond(orderFetch.getFetchTime(),timeDelay);
        }
        // 设置订单创建时间的起始
        argsMap.put(ConstantTaoBao.START_CREATED,start);

        // 抓取订单
        if(log.isInfoEnabled()){
            log.info(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+" 开始抓取订单……");
        }
        Map<String,Object> soldTradeMap = tradeApi.getTradesSold(argsMap);
        Long totalResults = (Long) soldTradeMap.get(ConstantTaoBao.TOTAL_RESULTS);
        List<Trade> trades = (List<Trade>) soldTradeMap.get(ConstantTaoBao.TRADES);

        totalResults = totalResults == null ? 0 : totalResults;
        if(log.isInfoEnabled()){
            log.info("抓取订单总数为：" + totalResults);
        }

        // 订单保存
        // 原始订单
        OriginalOrder originalOrder = null;
        OriginalOrderItem originalOrderItem = null;
        // 原始订单项集合
        List<OriginalOrderItem> originalOrderItems = null;


        if(trades != null){
            for (Trade trade : trades){
                // 将外部订单转化为系统原始订单
                originalOrder = convertTrade2OriginalOrder(trade,shop);
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
                // 保存原始订单以及相应的原始订单项（一次事务操作）
                originalOrderService.saveOriginalOrderAndItem(originalOrder,originalOrderItems);
            }
        }
        // 所有操作成功完成，添加订单抓取记录
        OrderFetch orderFetch1 = new OrderFetch();
        orderFetch1.setFetchTime(com.ejushang.spider.util.DateUtils.getCurrentDate());
        orderFetch1.setOutPlatform(ConstantTaoBao.OUT_PLATFORM_TYPE);
        orderFetch1.setShopId(ConvertUtils.convertLong2String(shop.getSid()));

        orderFetchService.saveOrderFetch(orderFetch1);
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
    private OriginalOrderItem convertOrder2OriginalOrderItem(Trade trade, Order order) {
        if(order == null){
            return null;
        }
        OriginalOrderItem originalOrderItem = new OriginalOrderItem();
        // 原始订单号
        originalOrderItem.setOrderNo(ConvertUtils.convertLong2String(trade.getTid()));
        // 商品条形码 (目前获取不到sku_id)
        originalOrderItem.setSkuCode(ConvertUtils.convertLong2String(order.getNumIid()));
        // 商品价格
        originalOrderItem.setPrice(Money.YuanToCent(order.getPrice()));
        // 商品购买数量
        originalOrderItem.setBuyCount(order.getNum());
        // 商品应付金额
        originalOrderItem.setTotalFee(Money.YuanToCent(order.getTotalFee()));
        // 商品实付金额
        originalOrderItem.setActualFee(Money.YuanToCent(order.getPayment()));
        return originalOrderItem;
    }

    /**
     * 将外部交易对象Trade转换为内部原始订单OriginalOrder对象
     * @param trade
     * @return
     */
    private OriginalOrder convertTrade2OriginalOrder(Trade trade,Shop shop) {
        if(trade == null){
            return null;
        }
        OriginalOrder originalOrder = new OriginalOrder();
        // 原始订单号
        originalOrder.setOutOrderNo(ConvertUtils.convertLong2String(trade.getTid()));
        // 交易状态
        originalOrder.setStatus(trade.getStatus());
        // 交易成交金额
        originalOrder.setTotalFee(Money.YuanToCent(trade.getTotalFee()));
        // 买家留言
        originalOrder.setBuyerMessage(trade.getBuyerMessage());
        // 客服备注
        originalOrder.setRemark(trade.getSellerMemo());
        // 买家id
        originalOrder.setBuyerId(trade.getBuyerNick());
        // 收货人姓名
        originalOrder.setReceiverName(trade.getReceiverName());
        // 收货人电话
        originalOrder.setReceiverPhone(trade.getReceiverPhone());
        // 收货人手机号
        originalOrder.setReceiverMobile(trade.getReceiverMobile());
        // 收货人邮编
        originalOrder.setReceiverZip(trade.getReceiverZip());
        // 收货人省份
        originalOrder.setReceiverState(trade.getReceiverState());
        // 收货人城市
        originalOrder.setReceiverCity(trade.getReceiverCity());
        // 收货人地区
        originalOrder.setReceiverDistrict(trade.getReceiverDistrict());
        // 收货人详细地址
        originalOrder.setReceiverAddress(trade.getReceiverAddress());
        // 下单时间（交易创建时间）
        originalOrder.setBuyTime(trade.getCreated());
        // 支付时间
        originalOrder.setPayTime(trade.getPayTime());
        // 外部平台类型
        originalOrder.setOutPlatformType(trade.getTradeFrom());
        // 店铺id
        originalOrder.setShopId(shop.getSid());
        // 店铺名称 （店铺标题）
        originalOrder.setShopName(shop.getTitle());
        // 是否需要发票
        Boolean needInvoice = StringUtils.isBlank(trade.getInvoiceName()) ? false : true;
        originalOrder.setNeedInvoice(needInvoice);
        // 发票抬头
        originalOrder.setInvoiceName(trade.getInvoiceName());
        // 发票内容 : 暂时获取不到
        return originalOrder;
    }

    /**
     * 获取订单查询字段
     * @return
     */
    private String getFields() {
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

}
