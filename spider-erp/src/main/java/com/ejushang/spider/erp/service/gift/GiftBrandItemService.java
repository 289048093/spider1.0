package com.ejushang.spider.erp.service.gift;

import com.ejushang.spider.domain.Brand;
import com.ejushang.spider.domain.GiftBrandItem;
import com.ejushang.spider.erp.common.mapper.BrandMapper;
import com.ejushang.spider.erp.common.mapper.GiftBrandItemMapper;
import com.ejushang.spider.erp.common.mapper.ProductMapper;
import com.ejushang.spider.service.gift.IGiftBrandItemService;
import com.ejushang.spider.vo.GiftBrandItemVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Blomer
 * Date: 13-12-24
 * Time: 上午10:23
 */
@Service
public class GiftBrandItemService implements IGiftBrandItemService {

    private static final Logger log = LoggerFactory.getLogger(GiftBrandItemService.class);

    @Resource
    GiftBrandItemMapper giftBrandItemMapper;

    @Resource
    BrandMapper brandMapper;

    @Resource
    ProductMapper productMapper;

    @Override
    public Integer saveGiftBrandItem(GiftBrandItem giftBrandItem) {
        if (log.isInfoEnabled()) {
            log.info(String.format("GiftBrandItemService中的saveGiftBrandItem方法，参数giftBrandItem:%s", giftBrandItem.toString()));
        }
        return giftBrandItemMapper.saveGiftBrandItem(giftBrandItem);
    }

    @Override
    public List<GiftBrandItemVO> findByGiftBrandId(Integer giftBrandId) {
        if (log.isInfoEnabled()) {
            log.info(String.format("GiftBrandItemService中的findByGiftBrandId方法，参数giftBrandId:%d", giftBrandId));
        }
        List<GiftBrandItem> giftBrandItemList = giftBrandItemMapper.findByGiftBrandId(giftBrandId);
        List<GiftBrandItemVO> giftBrandItemVOList = new ArrayList<GiftBrandItemVO>(giftBrandItemList.size());
        for (GiftBrandItem giftBrandItem : giftBrandItemList) {
            GiftBrandItemVO giftBrandItemVO = new GiftBrandItemVO();
            giftBrandItem.setProduct(productMapper.findProductById(giftBrandItem.getGiftProdId()));
            giftBrandItemVO.setId(giftBrandItem.getId());
            if (giftBrandItem.getProduct() != null) {
                Brand brand = brandMapper.findBrandById(giftBrandItem.getProduct().getBrandId());
                if (brand != null) {
                    giftBrandItemVO.setBrandName(brand.getName());
                } else { //防止报空异常
                    giftBrandItemVO.setBrandName(null);
                }
                giftBrandItemVO.setProdName(giftBrandItem.getProduct().getProdName());
                giftBrandItemVO.setProdNo(giftBrandItem.getProduct().getProdNo());
                giftBrandItemVO.setProdCode(giftBrandItem.getProduct().getProdCode());
            } else { //防止报空异常
                giftBrandItemVO.setProdName(null);
                giftBrandItemVO.setProdNo(null);
                giftBrandItemVO.setProdCode(null);
            }
            giftBrandItemVO.setProdId(giftBrandItem.getGiftProdId());
            giftBrandItemVO.setGiftProdCount(giftBrandItem.getGiftProdCount());
            giftBrandItemVOList.add(giftBrandItemVO);
        }
        return giftBrandItemVOList;
    }

    @Override
    public Integer deleteByGiftBrandId(Integer giftBrandId) {
        return giftBrandItemMapper.deleteByGiftBrandId(giftBrandId);
    }

}
