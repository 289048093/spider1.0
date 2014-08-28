package com.ejushang.spider.service.gift;

import com.ejushang.spider.domain.GiftBrandItem;
import com.ejushang.spider.vo.GiftBrandItemVO;

import java.util.List;

/**
 * User: Blomer
 * Date: 13-12-24
 * Time: 下午6:08
 */
public interface IGiftBrandItemService {

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
    public List<GiftBrandItemVO> findByGiftBrandId(Integer giftBrandId);

    /**
     * 跟据id删除一组赠品项
     * @param id
     * @return删除记录
     */
    public Integer deleteByGiftBrandId(Integer id);


}
