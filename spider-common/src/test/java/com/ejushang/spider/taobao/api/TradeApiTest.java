package com.ejushang.spider.taobao.api;

import com.ejushang.spider.taobao.helper.ConstantTaoBao;
import com.ejushang.spider.taobao.helper.ConvertUtils;
import com.ejushang.spider.taobao.helper.TaoBaoUtilsEjs;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;
import com.taobao.api.domain.TradeAmount;
import com.taobao.api.domain.TradeConfirmFee;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Baron.Zhang
 * Date: 13-12-16
 * Time: 下午5:15
 */

public class TradeApiTest {

    private TradeApi tradeApi;

    @Before
    public void init(){
        tradeApi = new TradeApi(TaoBaoUtilsEjs.getSessionKey());
    }

    /**
     * taobao.trade.amount.get 交易订单帐务查询 <br/>
     */
    @Test
    public void testGetTradeAmount() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        String fields = "tid,alipay_no,created,pay_time,end_time,total_fee,payment,post_fee,cod_fee,commission_fee," +
                "buyer_obtain_point_fee,order_amounts.oid,order_amounts.title,order_amounts.num_iid, " +
                "order_amounts.sku_properties_name,order_amounts.sku_id,order_amounts.num,order_amounts.price," +
                "order_amounts.discount_fee,order_amounts.adjust_fee,order_amounts.payment,order_amounts.promotion_name," +
                "order_amounts,promotion_details";
        argsMap.put(ConstantTaoBao.FIELDS,fields);
        argsMap.put(ConstantTaoBao.TID,192292112136010L);
        TradeAmount tradeAmount =  tradeApi.getTradeAmount(argsMap);

        System.out.println();
    }

    /**
     * 未通过（<error_response><code>520</code>
     * <msg>Remote service error</msg>
     * <sub_code>isp.remote-connection-error</sub_code>
     * <sub_msg>API调用远程连接错误</sub_msg></error_response>）
     * taobao.trade.close 卖家关闭一笔交易<br/>
     * 关闭一笔订单，可以是主订单或子订单。当订单从创建到关闭时间小于10s的时候，会报“CLOSE_TRADE_TOO_FAST”错误。<br/>
     */
    @Test
    public void testCloseTrade() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        argsMap.put(ConstantTaoBao.TID,192247966088727L);
        argsMap.put(ConstantTaoBao.CLOSE_REASON,"未及时付款");
        Trade trade = tradeApi.closeTrade(argsMap);

        System.out.println("");
    }

    /**
     * taobao.trade.confirmfee.get 获取交易确认收货费用<br/>
     * 获取交易确认收货费用 可以获取主订单或子订单的确认收货费用<br/>
     */
    @Test
    public void testGetTradeConfirmFee() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        argsMap.put(ConstantTaoBao.TID,192247966088727L);
        argsMap.put(ConstantTaoBao.IS_DETAIL,"IS_FATHER");
        TradeConfirmFee tradeConfirmFee = tradeApi.getTradeConfirmFee(argsMap);

        System.out.println("");
    }

    /**
     * taobao.trade.fullinfo.get 获取单笔交易的详细信息<br/>
     * 获取单笔交易的详细信息<br/>
     * <ul>
     *     <li>1. 只有在交易成功的状态下才能取到交易佣金，其它状态下取到的都是零或空值 </li>
     *     <li>2. 只有单笔订单的情况下Trade数据结构中才包含商品相关的信息</li>
     *     <li>3. 获取到的Order中的payment字段在单笔子订单时包含物流费用，多笔子订单时不包含物流费用</li>
     *     <li>4. 请按需获取字段，减少TOP系统的压力</li>
     * </ul>
     */
    @Test
    public void testGetTradeFullinfo() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        String fields = "seller_nick,buyer_nick,title,type,created,sid,tid,seller_rate,buyer_rate,status,payment," +
                "discount_fee,adjust_fee,post_fee,total_fee,pay_time,end_time,modified,consign_time,buyer_obtain_point_fee," +
                "point_fee,real_point_fee,received_payment,commission_fee,pic_path,num_iid,num_iid,num,price,cod_fee," +
                "cod_status,shipping_type,receiver_name,receiver_state,receiver_city,receiver_district,receiver_address," +
                "receiver_zip,receiver_mobile,receiver_phone,orders.title,orders.pic_path,orders.price,orders.num," +
                "orders.iid,orders.num_iid,orders.sku_id,orders.refund_status,orders.status,orders.oid,orders.total_fee," +
                "orders.payment,orders.discount_fee,orders.adjust_fee,orders.sku_properties_name,orders.item_meal_name," +
                "orders.buyer_rate,orders.seller_rate,orders.outer_iid,orders.outer_sku_id,orders.refund_id,orders.seller_type";
        argsMap.put(ConstantTaoBao.FIELDS,fields);
        argsMap.put(ConstantTaoBao.TID,192328579673574L);
        Trade trade = tradeApi.getTradeFullinfo(argsMap);

        if(trade != null && trade.getOrders() != null){
            for(Order order : trade.getOrders()){
                System.out.println("子订单号：" + order.getOid()+","+order.getTitle());
            }
        }


        System.out.println("");
    }

    /**
     * 未通过（<error_response><code>520</code>
     * <msg>Remote service error</msg>
     * <sub_code>isp.remote-connection-error</sub_code>
     * <sub_msg>API调用远程连接错误</sub_msg></error_response>）
     * taobao.trade.get 获取单笔交易的部分信息(性能高) <br/>
     * 获取单笔交易的部分信息<br/>
     * @throws Exception
     */
    @Test
    public void testGetTrade() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        String fields = "seller_nick, buyer_nick, title, type, created, tid, seller_rate, buyer_rate, status, payment, " +
                "discount_fee, adjust_fee, post_fee, total_fee, pay_time, end_time, modified, consign_time, " +
                "buyer_obtain_point_fee, point_fee, real_point_fee, received_payment, commission_fee, buyer_memo," +
                " seller_memo, alipay_no, buyer_message, pic_path, num_iid, num, price, cod_fee, cod_status, " +
                "shipping_type,is_daixiao,orders.title, orders.pic_path, orders.price, orders.num, orders.num_iid," +
                " orders.sku_id, orders.refund_status, orders.status, orders.oid, orders.total_fee, orders.payment, " +
                "orders.discount_fee, orders.adjust_fee, orders.sku_properties_name, orders.item_meal_name, " +
                "orders.outer_sku_id, orders.outer_iid, orders.buyer_rate, orders.seller_rate,orders.is_daixiao";
        argsMap.put(ConstantTaoBao.FIELDS,fields);
        argsMap.put(ConstantTaoBao.TID,192247966088727L);
        Trade trade = tradeApi.getTrade(argsMap);

        System.out.println("");
    }

    /**
     * 未通过（<error_response><code>520</code>
     * <msg>Remote service error</msg>
     * <sub_code>isp.remote-connection-error</sub_code>
     * <sub_msg>API调用远程连接错误</sub_msg></error_response>）
     * taobao.trade.get 获取单笔交易的部分信息(性能高) <br/>
     * 获取单笔交易的部分信息<br/>
     * @throws Exception
     */
    @Test
    public void testAddTradeMemo() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        argsMap.put(ConstantTaoBao.TID,192247966088727L);
        argsMap.put(ConstantTaoBao.MEMO,"EJS&mdash;&mdash;沙箱测试，添加备注了。。");
        argsMap.put(ConstantTaoBao.FLAG,1L);
        Trade trade = tradeApi.addTradeMemo(argsMap);

        System.out.println("");
    }

    /**
     * 未通过（<error_response><code>520</code>
     * <msg>Remote service error</msg>
     * <sub_code>isp.remote-connection-error</sub_code>
     * <sub_msg>API调用远程连接错误</sub_msg></error_response>）
     * taobao.trade.memo.add 对一笔交易添加备注<br/>
     * 根据登录用户的身份（买家或卖家），自动添加相应的交易备注,不能重复调用些接口添加备注，需要更新备注请用taobao.trade.memo.update<br/>
     * @throws Exception
     */
    @Test
    public void testUpdateTradeMemo() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        argsMap.put(ConstantTaoBao.TID,192247966088727L);
        argsMap.put(ConstantTaoBao.MEMO,"EJS&mdash;&mdash;沙箱测试，修改备注了。。");
        argsMap.put(ConstantTaoBao.FLAG,2L);
        Trade trade = tradeApi.updateTradeMemo(argsMap);

        System.out.println("");
    }

    /**
     * 未完成
     * taobao.trade.ordersku.update 更新交易订单的销售属性<br/>
     * <ul>
     *     <li>只能更新发货前子订单的销售属性 只能更新价格相同的销售属性。</li>
     *     <li>对于拍下减库存的交易会同步更新销售属性的库存量。</li>
     *     <li>对于旺店的交易，要使用商品扩展信息中的SKU价格来比较。</li>
     *     <li> 必须使用sku_id或sku_props中的一个参数来更新，如果两个都传的话，sku_id优先</li>
     * </ul>
     * @throws Exception
     */
    @Test
    public void testUpdateTradeOrdersku() throws Exception {
        // 保存参数的map
        Map<String,Object> argsMap = new HashMap<String, Object>();

        //根据交易id（tid）获取交易详细信息
        argsMap.put(ConstantTaoBao.TID,92074305747533L);
        String fields = "seller_nick, buyer_nick, title, type, created, tid, seller_rate,buyer_flag, buyer_rate," +
                " status, payment, adjust_fee, post_fee, total_fee, pay_time, end_time, modified, consign_time, " +
                "buyer_obtain_point_fee, point_fee, real_point_fee, received_payment, commission_fee, buyer_memo," +
                "orders";
        argsMap.put(ConstantTaoBao.FIELDS,fields);
        Trade trade = tradeApi.getTradeFullinfo(argsMap);
        // 获取交易下的所有子订单
        List<Order> orders = trade.getOrders();
        // 根据子订单获取商品SKU信息

        // Order order = tradeApi.updateTradeOrdersku(argsMap);

        System.out.println("");
    }

    /**
     * 未通过
     * <error_response><code>15</code><msg>Remote service error</msg><sub_code>isp.trade-service-failed</sub_code>
     * <sub_msg>交易服务不可调用</sub_msg></error_response>
     * taobao.trade.postage.update 修改订单邮费价格<br/>
     * 修改订单邮费接口，通过传入订单编号和邮费价格，修改订单的邮费，返回修改时间modified,邮费post_fee,总费用total_fee。<br/>
     * 只有为等待买家付款的状态下，才可以修改邮费<br/>
     * @throws Exception
     */
    @Test
    public void testUpdateTradePostage() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        argsMap.put(ConstantTaoBao.TID,192247818129729L);
        argsMap.put(ConstantTaoBao.POST_FEE,9.52);
        Trade trade = tradeApi.updateTradePostage(argsMap);

        System.out.println("");
    }

    /**
     * taobao.trade.receivetime.delay 延长交易收货时间<br/>
     * @throws Exception
     */
    @Test
    public void testDelayTradeReceivetime() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        argsMap.put(ConstantTaoBao.TID,192322834233255L);
        argsMap.put(ConstantTaoBao.DAYS,3L); // 可选值为3、5、7、10
        Trade trade = tradeApi.delayTradeReceivetime(argsMap);

        System.out.println("");
    }

    /**
     * taobao.trade.shippingaddress.update 更改交易的收货地址<br/>
     * 只能更新一笔交易里面的买家收货地址 只能更新发货前（即买家已付款，等待卖家发货状态）的交易的买家收货地址<br/>
     * 更新后的发货地址可以通过taobao.trade.fullinfo.get查到 参数中所说的字节为GBK编码的（英文和数字占1字节，中文占2字节）
     * @throws Exception
     */
    @Test
    public void testUpdateTradeShippingaddress() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        argsMap.put(ConstantTaoBao.TID,192322754653255L);
        argsMap.put(ConstantTaoBao.RECEIVER_NAME,"ejs_001");
        argsMap.put(ConstantTaoBao.RECEIVER_PHONE,"0755-00000001");
        argsMap.put(ConstantTaoBao.RECEIVER_MOBILE,"15500000001");
        argsMap.put(ConstantTaoBao.RECEIVER_STATE,"广东");
        argsMap.put(ConstantTaoBao.RECEIVER_CITY,"深圳");
        argsMap.put(ConstantTaoBao.RECEIVER_DISTRICT,"宝安区");
        argsMap.put(ConstantTaoBao.RECEIVER_ADDRESS,"008路008号");
        argsMap.put(ConstantTaoBao.RECEIVER_ZIP,"518100");

        Trade trade = tradeApi.updateTradeShippingaddress(argsMap);

        System.out.println("");
    }

    /**
     * 未通过
     * <error_response><code>520</code><msg>Remote service error</msg><sub_code>isv.invalid-permission</sub_code>
     * <sub_msg>支持的NBS交易类型为空或权限不足</sub_msg></error_response>
     * taobao.trade.snapshot.get 交易快照查询<br/>
     * 目前只支持类型为“旺店标准版(600)”或“旺店入门版(610)”的交易<br/>
     * 对于“旺店标准版”类型的交易，返回的snapshot字段为交易快照编号<br/>
     * 对于“旺店入门版”类型的交易，返回的snapshot字段为JSON结构的数据(其中的shopPromotion包含了优惠，积分等信息)<br/>
     * @throws Exception
     */
    @Test
    public void testGetTradeSnapshot() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        String fields = "snapshot";
        argsMap.put(ConstantTaoBao.FIELDS,fields);
        argsMap.put(ConstantTaoBao.TID,192322754653255L);
        Trade trade = tradeApi.getTradeSnapshot(argsMap);

        System.out.println("");
    }

    /**
     * taobao.trades.sold.get 查询卖家已卖出的交易数据（根据创建时间）<br/>
     * 搜索当前会话用户作为卖家已卖出的交易数据（只能获取到三个月以内的交易信息）<br/>
     * 1. 返回的数据结果是以订单的创建时间倒序排列的。<br/>
     * 2. 返回的数据结果只包含了订单的部分数据，可通过taobao.trade.fullinfo.get获取订单详情。<br/>
     */
    @Test
    public void testGetTradesSold() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        String fields = "seller_nick,buyer_nick,title,type,created,sid,tid,seller_rate,buyer_rate,status,payment," +
                "discount_fee,adjust_fee,post_fee,total_fee,pay_time,end_time,modified,consign_time," +
                "buyer_obtain_point_fee,point_fee,real_point_fee,received_payment,commission_fee,pic_path,num_iid," +
                "num_iid,num,price,cod_fee,cod_status,shipping_type,receiver_name,receiver_state,receiver_city," +
                "receiver_district,receiver_address,receiver_zip,receiver_mobile,receiver_phone,orders.title," +
                "orders.pic_path,orders.price,orders.num,orders.iid,orders.num_iid,orders.sku_id,orders.refund_status," +
                "orders.status,orders.oid,orders.total_fee,orders.payment,orders.discount_fee,orders.adjust_fee," +
                "orders.sku_properties_name,orders.item_meal_name,orders.buyer_rate,orders.seller_rate,orders.outer_iid," +
                "orders.outer_sku_id,orders.refund_id,orders.seller_type";
        // 设置返回结果字段
        argsMap.put(ConstantTaoBao.FIELDS,fields);
        // 设置每次查询100条 减少调用API的次数
        argsMap.put(ConstantTaoBao.PAGE_SIZE,100L);
        // 设置订单创建时间的起始
        argsMap.put(ConstantTaoBao.START_CREATED, DateUtils.parseDate("2014-01-02 22:18:00", new String[]{"yyyy-MM-dd HH:mm:ss"}));
        // 设置订单创建时间的结束
        argsMap.put(ConstantTaoBao.END_CREATED, DateUtils.parseDate("2014-01-02 22:20:00",new String[]{"yyyy-MM-dd HH:mm:ss"}));


        Map<String,Object> soldTradeMap = tradeApi.getTradesSold(argsMap);
        Long totalResults = (Long) soldTradeMap.get(ConstantTaoBao.TOTAL_RESULTS);
        Boolean hasNext = (Boolean) soldTradeMap.get(ConstantTaoBao.HAS_NEXT);
        List<Trade> trades = (List<Trade>) soldTradeMap.get(ConstantTaoBao.TRADES);

        if(totalResults != null){
            System.out.println("总记录数：" + totalResults);
        }
        if(trades != null){
            for (Trade trade : trades){
                System.out.print(" 交易编号：" + trade.getTid());
                System.out.print(" 商品购买数量：" + trade.getNum());
                System.out.print(" 商品数字编号：" + trade.getNumIid());
                System.out.print(" 交易状态：" + trade.getStatus());
                System.out.println();
                System.out.print(" 交易标题：" + trade.getTitle());
                System.out.print(" 交易类型列表：" + trade.getType());
                System.out.print(" 商品价格：" + trade.getPrice());
                System.out.print(" 商品金额：" + trade.getTotalFee());
                System.out.println();
                System.out.print(" 交易创建时间：" + DateFormatUtils.format(trade.getCreated(), "yyyy-MM-dd HH:mm:ss"));
                System.out.print(" 付款时间：" + DateFormatUtils.format(trade.getPayTime(), "yyyy-MM-dd HH:mm:ss"));
                System.out.print(" 买家留言：" + trade.getBuyerMessage()+",是否有买家留言："+trade.getHasBuyerMessage());
                System.out.print(" 买家的支付宝id号：" + trade.getAlipayId());
                System.out.println();
                System.out.println("=======================================");
                if(trade.getOrders() != null){
                    for (Order order : trade.getOrders()){
                        System.out.print(" 子订单编号：" + order.getOid());
                        System.out.print(" 订单状态：" + order.getStatus());
                        System.out.print(" 商品的字符串编号：" + order.getIid());
                        System.out.print(" 商品标题：" + order.getTitle());
                        System.out.println();
                        System.out.print(" 商品价格：" + order.getPrice());
                        System.out.print(" 商品数字ID：" + order.getNumIid());
                        System.out.print(" 套餐ID：" + order.getItemMealId());
                        System.out.print(" 商品的最小库存单位Sku的id：" + order.getSkuId());
                        System.out.println();

                        System.out.print(" 购买数量：" + order.getNum());
                        System.out.print(" 外部网店自己定义的Sku编号：" + order.getOuterSkuId());
                        System.out.print(" 子订单来源：" + order.getOrderFrom());
                        System.out.print(" 应付金额：" + order.getTotalFee());
                        System.out.print(" 子订单实付金额：" + order.getPayment());
                        System.out.println();
                        System.out.println("-------------------------------------");
                    }
                }
            }
        }
    }

    /**
     * taobao.trades.sold.increment.get 查询卖家已卖出的增量交易数据（根据修改时间）<br/>
     * 搜索当前会话用户作为卖家已卖出的增量交易数据（只能获取到三个月以内的交易信息）<br/>
     * 1. 一次请求只能查询时间跨度为一天的增量交易记录，即end_modified - start_modified <= 1天。<br/>
     * 2. 返回的数据结果是以订单的修改时间倒序排列的，通过从后往前翻页的方式可以避免漏单问题。<br/>
     * 3. 返回的数据结果只包含了订单的部分数据，可通过taobao.trade.fullinfo.get获取订单详情。<br/>
     * 4. 使用主动通知监听订单变更事件，可以实时获取订单更新数据。<br/>
     * @throws Exception
     */
    @Test
    public void testGetTradesSoldIncrement() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        String fields = "seller_nick,buyer_nick,title,type,created,sid,tid,seller_rate,buyer_rate,status,payment," +
                "discount_fee,adjust_fee,post_fee,total_fee,pay_time,end_time,modified,consign_time," +
                "buyer_obtain_point_fee,point_fee,real_point_fee,received_payment,commission_fee,pic_path,num_iid," +
                "num_iid,num,price,cod_fee,cod_status,shipping_type,receiver_name,receiver_state,receiver_city," +
                "receiver_district,receiver_address,receiver_zip,receiver_mobile,receiver_phone,orders.title," +
                "orders.pic_path,orders.price,orders.num,orders.iid,orders.num_iid,orders.sku_id,orders.refund_status," +
                "orders.status,orders.oid,orders.total_fee,orders.payment,orders.discount_fee,orders.adjust_fee," +
                "orders.sku_properties_name,orders.item_meal_name,orders.buyer_rate,orders.seller_rate," +
                "orders.outer_iid,orders.outer_sku_id,orders.refund_id,orders.seller_type";

        Date startDateTime = SimpleDateFormat.getDateTimeInstance().parse("2013-12-16 00:00:00");
        Date endDateTime = SimpleDateFormat.getDateTimeInstance().parse("2013-12-16 23:59:59");

        argsMap.put(ConstantTaoBao.FIELDS,fields);
        argsMap.put(ConstantTaoBao.START_MODIFIED,startDateTime);
        argsMap.put(ConstantTaoBao.END_MODIFIED,endDateTime);


        Map<String,Object> tradesSoldIncrementMap = tradeApi.getTradesSoldIncrement(argsMap);

        Long totalResults = (Long) tradesSoldIncrementMap.get(ConstantTaoBao.TOTAL_RESULTS);
        Boolean hasNext = (Boolean) tradesSoldIncrementMap.get(ConstantTaoBao.HAS_NEXT);
        List<Trade> trades = (List<Trade>) tradesSoldIncrementMap.get(ConstantTaoBao.TRADES);

        for (Trade trade : trades){
            System.out.println("tid:"+trade.getTid()+",title:"+trade.getTitle()+",buyer.nick:"+trade.getBuyerNick()
                    +",modified:"+trade.getModified());
        }

        System.out.println("");
    }

    /**
     * taobao.trades.sold.incrementv.get 查询卖家已卖出的增量交易数据（根据入库时间）<br/>
     * 搜索当前会话用户作为卖家已卖出的增量交易数据（只能获取到三个月以内的交易信息）<br/>
     * 1. 一次请求只能查询时间跨度为一天的增量交易记录，即end_create - start_create <= 1天。<br/>
     * 2. 返回的数据结果是以订单入库时间的倒序排列的(该时间和订单修改时间不同)，通过从后往前翻页的方式可以避免漏单问题。<br/>
     * 3. 返回的数据结果只包含了订单的部分数据，可通过taobao.trade.fullinfo.get获取订单详情。<br/>
     * 4. 使用主动通知监听订单变更事件，可以实时获取订单更新数据。<br/>
     * @throws Exception
     */
    @Test
    public void testGetTradesSoldIncrementv() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        String fields = "seller_nick, buyer_nick, title, type, created, tid, seller_rate, buyer_rate, status, payment," +
                " discount_fee, adjust_fee, post_fee, total_fee, pay_time, end_time, modified, consign_time," +
                " buyer_obtain_point_fee, point_fee, real_point_fee, received_payment,pic_path, num_iid, num, price, " +
                "cod_fee, cod_status, shipping_type, receiver_name, receiver_state, receiver_city, receiver_district," +
                " receiver_address, receiver_zip, receiver_mobile, receiver_phone,alipay_id,alipay_no,is_lgtype," +
                "is_force_wlb,is_brand_sale,has_buyer_message,credit_card_fee,step_trade_status,step_paid_fee,mark_desc," +
                "is_daixiao,is_part_consign,orders,service_orders";

        Date startDateTime = SimpleDateFormat.getDateTimeInstance().parse("2013-12-16 00:00:00");
        Date endDateTime = SimpleDateFormat.getDateTimeInstance().parse("2013-12-16 23:59:59");

        argsMap.put(ConstantTaoBao.FIELDS,fields);
        argsMap.put(ConstantTaoBao.START_CREATE,startDateTime);
        argsMap.put(ConstantTaoBao.END_CREATE,endDateTime);


        Map<String,Object> tradesSoldIncrementvMap = tradeApi.getTradesSoldIncrementv(argsMap);

        Long totalResults = (Long) tradesSoldIncrementvMap.get(ConstantTaoBao.TOTAL_RESULTS);
        Boolean hasNext = (Boolean) tradesSoldIncrementvMap.get(ConstantTaoBao.HAS_NEXT);
        List<Trade> trades = (List<Trade>) tradesSoldIncrementvMap.get(ConstantTaoBao.TRADES);

        for (Trade trade : trades){
            System.out.println("tid:"+trade.getTid()+",title:"+trade.getTitle()+",buyer.nick:"+trade.getBuyerNick()
                    +",modified:"+trade.getModified());
        }

        System.out.println("");
    }


}
