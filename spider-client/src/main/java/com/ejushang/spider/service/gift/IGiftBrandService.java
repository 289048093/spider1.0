package com.ejushang.spider.service.gift;

import com.ejushang.spider.domain.GiftBrand;
import com.ejushang.spider.query.GiftBrandQuery;
import com.ejushang.spider.util.Page;

import java.util.List;

/**
 * User: Blomer
 * Date: 13-12-24
 * Time: 下午6:08
 */
public interface IGiftBrandService {
    /**
     * 查询所有记录
     *
     * @return 返回 赠品规则-品牌记录
     */
    public List<GiftBrand> findAll(GiftBrandQuery giftBrandQuery,Page page);

    /**
     * 查找所有记录 可分页
     *
     * @return 返回 赠品规则-品牌记录
     */
    public Page findByPage(GiftBrandQuery giftBrandQuery);

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

    public List<GiftBrand> findGiftBrandByBrandId(Integer brandId);

    /**
     * 根据giftBrand查找这个id所有的奖励价格范围
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
     * @param ids
     * @return 删除记录的条数
     */
    public Integer deleteGiftBrandByIds(Integer[] ids);

    /**
     * 根据id 修改该记录的 其他任何字段值
     *
     * @param giftBrand
     * @return 更新记录的条数
     */
    public Integer updateGiftBrandByGiftBrand(GiftBrand giftBrand);
}
