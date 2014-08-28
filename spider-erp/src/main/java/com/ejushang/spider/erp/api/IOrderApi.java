package com.ejushang.spider.erp.api;

import com.ejushang.spider.bean.ShopBean;
import com.ejushang.spider.domain.OriginalOrder;

import java.util.List;
/**
 * User: Baron.Zhang
 * Date: 14-1-9
 * Time: 下午2:21
 */
public interface IOrderApi {

    /** 查询间隔时间，单位：分钟 */
    Integer timeInterval = 5;
    /** 查询间隔时间增量 */
    Integer timeIntervalIncrement = 30;
    /** 当前最后抓取时间往后推的时间，单位：秒 */
    Integer timeDelay = 1;
    /** 查询每页记录数 */
    Long pageSize = 100L;

    /**
     * 根据sessionKey从外部平台抓取订单
     * @param shopBean
     * @return
     */
    public List<OriginalOrder> fetchOrder(ShopBean shopBean) throws Exception;

    /**
     * 从淘宝聚石塔抓取订单
     * @param shopBean
     * @return
     * @throws Exception
     */
    public List<OriginalOrder> fetchOrderByJst(ShopBean shopBean) throws Exception;

}
