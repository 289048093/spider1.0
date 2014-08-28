package com.ejushang.spider.erp.service.order;

import com.ejushang.spider.domain.OrderFetch;
import com.ejushang.spider.domain.Shop;
import com.ejushang.spider.erp.common.mapper.OrderFetchMapper;
import com.ejushang.spider.erp.common.mapper.ShopMapper;
import com.ejushang.spider.query.OrderFetchQuery;
import com.ejushang.spider.service.order.IOrderFetchService;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.OrderFetchVo;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Baron.Zhang
 * Date: 13-12-24
 * Time: 下午5:14
 */
@Service
public class OrderFetchService implements IOrderFetchService {

    private static final Logger log = LoggerFactory.getLogger(OrderFetchService.class);

    @Resource
    private OrderFetchMapper orderFetchMapper;
    @Resource
    private ShopMapper shopMapper;
    /**
     * 订单抓取查询
     * @param orderFetchQuery 订单抓取查询条件
     * @return
     */
    public Page findOrderFetchs(OrderFetchQuery orderFetchQuery) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (log.isInfoEnabled()) {
            log.info("查询的条件 orderFetchQuery：" + orderFetchQuery);
        }
        // 构造分页信息
        Page page = new Page();
        // 设置当前页
        page.setPageNo(orderFetchQuery.getPage());
        // 设置分页大小
        page.setPageSize(orderFetchQuery.getLimit());
        List<OrderFetch> orderFetchList = orderFetchMapper.findOrderFetchs(orderFetchQuery, page);
        if (log.isInfoEnabled()) {
            log.info("查询的结果 brandList：" + orderFetchList);
        }
        List<OrderFetchVo> orderFetchListVo = new ArrayList<OrderFetchVo>();
        OrderFetchVo orderFetchVo = null;
        for (int i = 0; i < orderFetchList.size(); i++) {
            orderFetchVo = new OrderFetchVo();
            PropertyUtils.copyProperties(orderFetchVo, orderFetchList.get(i));
            if(orderFetchQuery.getTitle()!=null){
            }
            String shopId=orderFetchVo.getShopId().toString();
            Shop shop=shopMapper.findShopByShopId(Integer.parseInt(shopId));
            orderFetchVo.setTitle(shop.getTitle());

            orderFetchListVo.add(orderFetchVo);
        }
        if (log.isInfoEnabled()) {
            log.info("查询的VO结果 orderFetchListVo" + orderFetchListVo);
        }
        page.setResult(orderFetchListVo);
        return page;
    }

    /**
     * 订单抓取新增
     * @param orderFetch 订单抓取
     */
    public int saveOrderFetch(OrderFetch orderFetch) {
        if(log.isInfoEnabled()){
            log.info("订单抓取新增:" + orderFetch);
        }
        return orderFetchMapper.saveOrderFetch(orderFetch);
    }

    /**
     * 订单抓取更新
     * @param orderFetch 订单抓取
     */
    public int updateOrderFetch(OrderFetch orderFetch) {
        if(log.isInfoEnabled()){
            log.info("订单抓取更新:" + orderFetch);
        }
        return orderFetchMapper.updateOrderFetch(orderFetch);
    }

    /**
     * 订单抓取删除
     * @param orderFetch
     */
    public int deleteOrderFetch(OrderFetch orderFetch) {
        if(log.isInfoEnabled()){
            log.info("订单抓取删除:" + orderFetch);
        }
        return orderFetchMapper.deleteOrderFetch(orderFetch);
    }

    /**
     * 获取最后一条订单抓取记录（根据抓取时间）
     * @return
     */
    public OrderFetch findLastOrderFetch(String outPlatformType,String shopId) {
        if(log.isInfoEnabled()){
            log.info("获取最后一条订单抓取记录（根据抓取时间）:outPlatformType=" + outPlatformType+",shopId="+shopId);
        }
        OrderFetch orderFetch = new OrderFetch();
        orderFetch.setShopId(shopId);
        orderFetch.setOutPlatform(outPlatformType);
        return orderFetchMapper.findLastOrderFetch(orderFetch);
    }
}
