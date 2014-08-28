package com.ejushang.spider.service.order;

import com.ejushang.spider.domain.OriginalOrder;
import com.ejushang.spider.domain.OriginalOrderItem;

import java.util.List;

/**
 * User: Baron.Zhang
 * Date: 13-12-25
 * Time: 上午11:42
 */
public interface IOriginalOrderService {

    /**
     * 根据ID查询OriginalOrder
     * @param id
     * @return
     */
    public OriginalOrder get(Integer id);

    /**
     * 原始订单查询：根据查询条件查询订单
     * @param processed
     * @return 订单集合
     */
    public List<OriginalOrder> findOriginalOrders(Boolean processed);

    /**
     * 原始订单新增：添加一条订单记录
     * @param originalOrder 订单记录
     */
    public int saveOriginalOrder(OriginalOrder originalOrder);

    /**
     * 原始订单更新：更新一条订单记录
     * @param originalOrder 订单记录
     */
    public int updateOriginalOrder(OriginalOrder originalOrder);

    /**
     * 原始订单删除：删除一条订单记录
     * @param originalOrder 订单记录
     */
    public int deleteOriginalOrder(OriginalOrder originalOrder);

    /**
     * 保存原始订单及其订单项
     * @param originalOrder 原始订单
     * @param originalOrderItems 订单项集合
     */
    public void saveOriginalOrderAndItem(OriginalOrder originalOrder,List<OriginalOrderItem> originalOrderItems);

    /**
     * 批量修改状态processed为true
     * @param ids id数组,不能传空数组
     * @return
     */
    int updateBatchProcessed(Integer[] ids);
}
