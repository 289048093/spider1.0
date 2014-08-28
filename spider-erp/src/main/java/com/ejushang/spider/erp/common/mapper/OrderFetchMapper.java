package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.OrderFetch;
import com.ejushang.spider.query.OrderFetchQuery;
import com.ejushang.spider.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单抓取数据操作类
 * User: Baron.Zhang
 * Date: 13-12-24
 * Time: 下午5:18
 */
public interface OrderFetchMapper {

    /**
     * 订单抓取查询：根据条件查询订单抓取
     * @param
     * @return
     */
    public List<OrderFetch> findOrderFetchs(@Param("orderFetch")OrderFetchQuery orderFetchQuery, @Param("page")Page page  );

    /**
     * 订单抓取新增
     * @param orderFetch
     */
    public int saveOrderFetch(OrderFetch orderFetch);

    /**
     * 订单抓取更新
     * @param orderFetch
     */
    public int updateOrderFetch(OrderFetch orderFetch);

    /**
     * 订单抓取删除
     * @param orderFetch
     */
    public int deleteOrderFetch(OrderFetch orderFetch);

    /**
     * 获取最后一条订单抓取记录
     * @return
     */
    public OrderFetch findLastOrderFetch(OrderFetch orderFetch);
}
