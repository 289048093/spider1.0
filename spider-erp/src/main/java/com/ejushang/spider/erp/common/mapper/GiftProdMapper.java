package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.GiftProd;
import com.ejushang.spider.query.GiftProdQuery;
import com.ejushang.spider.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: Blomer
 * Date: 13-12-23
 * Time: 上午10:34
 */
public interface GiftProdMapper {

    /**
     * 根据对象 查找所有记录
     *
     * @param giftProdQuery
     * @param page
     * @return 赠品规则--商品记录
     */
    public List<GiftProd> findAll(@Param("giftProdQuery") GiftProdQuery giftProdQuery, @Param("page") Page page);

    /**
     * 根据对象 查找所有记录
     *
     * @param giftProdQuery
     * @return 赠品规则--商品记录
     */
    public List<GiftProd> findByQuery(GiftProdQuery giftProdQuery);

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
     * 增加一条GiftProd记录
     *
     * @param giftProd
     * @return 新增GiftProd记录的条数
     */
    public Integer saveGiftProd(GiftProd giftProd);

    /**
     * 根据id删除一条记录
     *
     * @param id
     * @return 删除GiftProd记录条数
     */
    public Integer deleteGiftProdById(Integer id);

    /**
     * 根据id 修改该记录的 其他字段值
     *
     * @param giftProd
     * @return 更新GiftProd记录条数
     */
    public Integer updateGiftProdById(GiftProd giftProd);
}
