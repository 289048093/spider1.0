package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.OriginalOrderItem;

/**
 * User: Baron.Zhang
 * Date: 13-12-25
 * Time: 上午10:57
 */
public interface OriginalOrderItemMapper {

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
