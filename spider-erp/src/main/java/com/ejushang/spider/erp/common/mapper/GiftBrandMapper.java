package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.GiftBrand;
import com.ejushang.spider.query.GiftBrandQuery;
import com.ejushang.spider.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: Blomer
 * Date: 13-12-23
 * Time: 上午10:34
 */
public interface GiftBrandMapper {

    /**
     * 查询所有记录
     *
     * @return 返回 赠品规则-品牌记录
     */
    public List<GiftBrand> findAll(@Param("giftBrandQuery")GiftBrandQuery giftBrandQuery,@Param("page")Page page);

    /**
     * 查询所有记录
     *
     * @return 返回 赠品规则-品牌记录
     */
    public List<GiftBrand> findByQuery(GiftBrandQuery giftBrandQuery);

    /**
     * 根据id查询记录
     *
     * @param id
     * @return 赠品规则-品牌记录
     */
    public GiftBrand findGiftBrandById(Integer id);

    /**
     * 根据brandId查找记录
     * @param brandId
     * @return brandId
     */
    public List<GiftBrand> findGiftBrandByBrandId(Integer brandId);

    /**
     * 根据giftBrand查找这个brandId所有的奖励价格范围
     *
     * @param brandId
     * @return GiftBrand
     */
    public List<GiftBrand> findBeginEndByBrandId(Integer brandId);

    /**
     * 增加一条赠品记录
     *
     * @param giftBrand
     * @return 新增记录的条数
     */
    public Integer saveGiftBrand(GiftBrand giftBrand);

    /**
     * 根据id删除一条记录
     *
     * @param id
     * @return 删除记录的条数
     */
    public Integer deleteGiftBrandById(Integer id);

    /**
     * 根据id 修改该记录的 其他任何字段值
     *
     * @param giftBrand
     * @return 更新记录的条数
     */
    public Integer updateGiftBrandById(GiftBrand giftBrand);

}
