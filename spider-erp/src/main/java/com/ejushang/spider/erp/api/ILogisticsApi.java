package com.ejushang.spider.erp.api;

import com.ejushang.spider.bean.LogisticsBean;

/**
 * User: Baron.Zhang
 * Date: 14-1-16
 * Time: 下午3:34
 */
public interface ILogisticsApi {

    /**
     * 在线订单发货处理
     * @param logisticsBean
     * @return
     */
    public Boolean sendLogisticsOnline(LogisticsBean logisticsBean) throws Exception;

}
