package com.ejushang.spider.service.order;

import com.ejushang.spider.bean.TransformOrderResultBean;
import com.ejushang.spider.constant.OrderItemType;
import com.ejushang.spider.domain.*;
import com.ejushang.spider.exception.OrderAnalyzeException;
import com.ejushang.spider.query.OrderQuery;
import com.ejushang.spider.vo.AddOrderVo;
import com.ejushang.spider.vo.OrderVo;
import com.ejushang.spider.vo.QueryProdVo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

/**
 * User: liubin
 * Date: 14-1-8
 */
public interface IOrderAnalyzeService {

    /**
     * 将源订单转化为系统内的真实订单,该方法不负责持久化
     *
     * @param originalOrders
     * @return
     */
    TransformOrderResultBean transformToOrder(List<OriginalOrder> originalOrders) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    /**
     * 对订单导入前进行准备工作,主要包括加赠品,拆分订单,合并订单,该方法不负责持久化
     * @param orders
     * @return 处理后的结果
     */
    List<Order> prepareImport(List<Order> orders) throws OrderAnalyzeException;

    /**
     * 将处理后的订单持久化到数据库,并且修改原始订单为已处理
     * @param orders
     * @param prcessedOriginalOrderIdSet
     */
    void saveAnalyzeResults(List<Order> orders, Set<Integer> prcessedOriginalOrderIdSet);

    /**
     * 创建一个订单项
     * @param buyCount
     * @param orderItemType
     * @param order
     * @param product
     * @param mealsetItem
     * @param originalOrderItem
     * @return
     */
    public OrderItem createOrderItem(int buyCount, OrderItemType orderItemType,
                                      Order order, Product product, MealsetItem mealsetItem, OriginalOrderItem originalOrderItem);

    /**
     * 手动加赠品
     */
    public void addGiftByHand(QueryProdVo[] queryProdVos1,String[] orderNos);

    /**
     * 手动加订单
     */
    public List<String> addOrderByHand(AddOrderVo addOrderVo,QueryProdVo[] queryProdVos) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, OrderAnalyzeException;

    /**
     * 复制订单
     */
    public AddOrderVo copyOrderByHand(Integer id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    /**
     * 对传入的原始订单列表进行预处理
     * 主要做的操作是
     * 1.根据外部订单号,判断该队列中是否有外部订单号重复的原始订单,有的话保留修改时间最靠后的原始订单,其余的从队列中删除
     * 2.根据外部订单号,查询数据库是否有订单存在,如果有,表明这个原始订单数据只是修改了备注,则更新所有查询到的订单的备注,并且将该原始订单从队列中删除
     *
     * 传入的队列最后剩下的全都是需要生成新订单的原始订单
     *
     * @param originalOrders
     */
    void preHandleOriginalOrders(List<OriginalOrder> originalOrders);

    /**
     * 手动拆单, 返回的是拆出来的订单,目前只会有一个订单
     * 手动拆单的订单要满足下面条件:
     * 1.订单项数量大于等于2
     * 2.传来的ids对应的产品要能是同一仓库的
     *
     * 原订单在拆分之后,如果订单项所对应的仓库依然大于1个的话,标识原订单的拆分状态为需要继续拆分
     * 否则修改原订单的拆分状态为手动拆分
     * @param order
     * @param ids
     * @return
     */
    List<Order> splitOrderByHand(Order order, int[] ids) throws OrderAnalyzeException;
}
