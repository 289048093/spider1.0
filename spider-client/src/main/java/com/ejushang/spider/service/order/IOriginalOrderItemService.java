package com.ejushang.spider.service.order;

import com.ejushang.spider.domain.OriginalOrderItem;

import java.util.List;

/**
 * User: Baron.Zhang
 * Date: 13-12-25
 * Time: 上午11:42
 */
public interface IOriginalOrderItemService {

    /**
     * 原始订单项新增：添加一条订单项记录
     * @param originalOrderItem 订单项记录
     */
    public int saveOriginalOrderItem(OriginalOrderItem originalOrderItem);

    /**
     * 原始订单项更新：更新一条订单项记录
     * @param originalOrderItem 订单项记录
     */
    public int updateOriginalOrderItem(OriginalOrderItem originalOrderItem);

    /**
     * 原始订单项删除：删除一条订单项记录
     * @param originalOrderItem 订单项记录
     */
    public int deleteOriginalOrderItem(OriginalOrderItem originalOrderItem);
}
