package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.OriginalOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: Baron.Zhang
 * Date: 13-12-25
 * Time: 上午10:57
 */
public interface OriginalOrderMapper {
    /**
     * 原始订单查询：根据查询条件查询订单
     * @param processed
     * @return 订单集合
     */
    public List<OriginalOrder> findOriginalOrders(@Param("processed") Boolean processed);

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
     * 批量修改状态processed为true
     * @param ids id数组,不能传空数组
     * @return
     */
    int updateBatchProcessed(Integer[] ids);

    OriginalOrder get(Integer id);
}
