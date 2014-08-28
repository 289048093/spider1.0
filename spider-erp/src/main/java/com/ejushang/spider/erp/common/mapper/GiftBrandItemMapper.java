package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.GiftBrandItem;

import java.util.List;

/**
 * User: Blomer
 * Date: 13-12-23
 * Time: 上午10:34
 */
public interface GiftBrandItemMapper {

    /**
     * 增加一条赠品记录
     *
     * @param giftBrandItem
     * @return 新增记录的条数
     */
    public Integer saveGiftBrandItem(GiftBrandItem giftBrandItem);

    /**
     * 根据giftBrandId查询记录
     * @param giftBrandId
     * @return 一组赠送商品项
     */
    public List<GiftBrandItem> findByGiftBrandId(Integer giftBrandId);

    /**
     * 跟据giftBrandId删除一组赠品项
     * @param giftBrandId
     * @return删除记录
     */
    public Integer deleteByGiftBrandId(Integer giftBrandId);

}
