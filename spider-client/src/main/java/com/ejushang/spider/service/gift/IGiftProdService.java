package com.ejushang.spider.service.gift;

import com.ejushang.spider.bean.OrderGiftBean;
import com.ejushang.spider.domain.GiftProd;
import com.ejushang.spider.domain.Order;
import com.ejushang.spider.query.GiftProdQuery;
import com.ejushang.spider.query.ProductQuery;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.GiftProdVO;
import com.ejushang.spider.vo.ProdVo;

import java.util.List;

/**
 * User: Blomer
 * Date: 13-12-24
 * Time: 下午6:13
 */
public interface IGiftProdService {

    /**
     * 根据对象 查找所有记录
     *
     * @return 赠品规则--商品记录
     */
    public List<GiftProd> findAll(GiftProdQuery giftProdQuery, Page page);

    /**
     * 查找所有记录 可分页
     *
     * @return 赠品规则--商品记录
     */
    public Page findAllPage(GiftProdQuery giftProdQuery);

    /**
     * 根据对象 查找所有记录
     *
     * @param giftProdQuery
     * @return 赠品规则--商品记录
     */
    public List<GiftProd> findByQuery(GiftProdQuery giftProdQuery);

    /**
     * 查找 赠品的 id与name
     *
     * @return GiftProdVO
     */
    public List<GiftProdVO> findGiftProdIdAndName();

    /**
     * 根据limit作为记录数返回ProdVo到界面
     *
     * @param productQuery
     * @param limit
     * @return ProdVo
     */
    public List<ProdVo> findProductAllByPage(ProductQuery productQuery, Integer limit);

    /**
     * 根据sellProdId查找记录
     *
     * @param sellProdId
     * @return 一组sellProdId对应的优惠或送记录
     */
    public List<GiftProd> findBySellProdId(Integer sellProdId);

    /**
     * 根据giftProdId查找记录
     *
     * @param giftProdId
     * @return 一组giftProdId对应的优惠或送记录
     */
    public List<GiftProd> findByGiftProdId(Integer giftProdId);

    /**
     * 增加一组GiftProd记录
     *
     * @param giftProdList
     * @return 新增GiftProd记录的条数
     */
    public Integer saveGiftProdList(List<GiftProd> giftProdList);

    /**
     * 根据id删除一条记录
     *
     * @param ids
     * @return 删除GiftProd记录条数
     */
    public Integer deleteGiftProdByIds(Integer[] ids);

    /**
     * 根据id 修改该记录的 其他字段值
     *
     * @param giftProd
     * @return 更新GiftProd记录条数
     */
    public Integer updateGiftProdById(GiftProd giftProd);

    /**
     * 查询订单需要加的赠品
     *
     * @param order
     * @return
     */
    List<OrderGiftBean> queryGiftProductForOrder(Order order);
}
