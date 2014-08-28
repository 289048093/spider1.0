package com.ejushang.spider.erp.service.gift;

import com.ejushang.spider.domain.Brand;
import com.ejushang.spider.domain.GiftBrand;
import com.ejushang.spider.domain.GiftBrandItem;
import com.ejushang.spider.erp.common.mapper.BrandMapper;
import com.ejushang.spider.erp.common.mapper.GiftBrandItemMapper;
import com.ejushang.spider.erp.common.mapper.GiftBrandMapper;
import com.ejushang.spider.erp.common.mapper.ProductMapper;
import com.ejushang.spider.query.GiftBrandQuery;
import com.ejushang.spider.service.gift.IGiftBrandService;
import com.ejushang.spider.util.Money;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.GiftBrandVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Blomer
 * Date: 13-12-24
 * Time: 上午10:23
 */
@Service
public class GiftBrandService implements IGiftBrandService {

    private static final Logger log = LoggerFactory.getLogger(GiftBrandService.class);

    @Resource
    private GiftBrandMapper giftBrandMapper;

    @Resource
    private BrandMapper brandMapper;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private GiftBrandItemMapper giftBrandItemMapper;

    /**
     * 查询所有记录
     *
     * @return 返回 赠品规则-品牌记录
     */
    @Transactional
    @Override
    public List<GiftBrand> findAll(GiftBrandQuery giftBrandQuery, Page page) {
        return giftBrandMapper.findAll(giftBrandQuery, page);
    }

    /**
     * findAll()的加强版
     *
     * @return 返回 赠品规则-品牌记录
     */
    @Transactional
    public List<GiftBrand> findAllCascades(GiftBrandQuery giftBrandQuery, Page page) {
        List<GiftBrand> giftBrandList = giftBrandMapper.findAll(giftBrandQuery, page);
        for (GiftBrand giftBrand : giftBrandList) {
            Brand brand = brandMapper.findBrandById(giftBrand.getBrandId());
            if (null != brand) {
                giftBrand.setBrand(brand);
            } else {
                log.info("在表t_gift_brand表中品牌编号为" + giftBrand.getBrandId() + "在品牌表t_brand中没有对应记录");
            }
        }
        return giftBrandList;
    }

    /**
     * 查找所有记录 可分页
     *
     * @return 返回 赠品规则-品牌记录
     */
    @Transactional(readOnly = true)
    @Override
    public Page findByPage(GiftBrandQuery giftBrandQuery) {
        Page page = new Page();
        page.setPageNo(giftBrandQuery.getPage());
        page.setPageSize(giftBrandQuery.getLimit());
        List<GiftBrand> giftBrandList = findAllCascades(giftBrandQuery, page);
        if (null != giftBrandList) {
            List<GiftBrandVO> giftBrandVOList = new ArrayList<GiftBrandVO>(giftBrandList.size());
            for (GiftBrand giftBrand : giftBrandList) {
                GiftBrandVO giftBrandVO = new GiftBrandVO();
                giftBrandVO.setId(giftBrand.getId());
                giftBrandVO.setBrandId(giftBrand.getBrandId());
                giftBrandVO.setPriceBegin(giftBrand.getPriceBegin() / 100);
                giftBrandVO.setPriceEnd(giftBrand.getPriceEnd() / 100);
                giftBrandVO.setInUse(giftBrand.getInUse());
                if (null != giftBrand.getBrand()) {
                    giftBrandVO.setBrandName(giftBrand.getBrand().getName());
                } else {
                    giftBrandVO.setBrandName(null);
                }
                giftBrandVOList.add(giftBrandVO);
            }
            page.setResult(giftBrandVOList);
            return page;
        } else {
            log.info("GiftBrandService中findByPage操作没有查询到数据");
            return null;
        }
    }

    /**
     * 查询所有记录
     *
     * @param giftBrandQuery
     * @return 返回 赠品规则-品牌记录
     */
    @Transactional(readOnly = true)
    @Override
    public List<GiftBrand> findByQuery(GiftBrandQuery giftBrandQuery) {
        if (log.isInfoEnabled()) {
            log.info("GiftBrandService中的findGiftBrandByQuery操作，参数giftBrandQuery=" + giftBrandQuery.toString());
        }
        List<GiftBrand> giftBrandList = giftBrandMapper.findByQuery(giftBrandQuery);
        for (GiftBrand giftBrand : giftBrandList) {
            giftBrand.setGiftBrandItemList(giftBrandItemMapper.findByGiftBrandId(giftBrand.getId()));
        }
        return giftBrandList;
    }

    /**
     * 根据id查询记录
     *
     * @param id
     * @return 赠品规则-品牌记录
     */
    @Transactional(readOnly = true)
    @Override
    public GiftBrand findGiftBrandById(Integer id) {
        if (log.isInfoEnabled()) {
            log.info("GiftBrandService中的findGiftBrandById操作，参数id=" + id.toString());
        }
        GiftBrand giftBrand = giftBrandMapper.findGiftBrandById(id);
        List<GiftBrandItem> giftBrandItemList = giftBrandItemMapper.findByGiftBrandId(giftBrand.getId());
        if (!giftBrandItemList.isEmpty()) {
            giftBrand.setGiftBrandItemList(giftBrandItemMapper.findByGiftBrandId(giftBrand.getId()));
        } else {
            giftBrand.setGiftBrandItemList(null);
        }
        return giftBrandMapper.findGiftBrandById(id);
    }

    /**
     * 根据brandId查询记录
     *
     * @param brandId
     * @return giftBrand的集合
     */
    @Transactional(readOnly = true)
    @Override
    public List<GiftBrand> findGiftBrandByBrandId(Integer brandId) {
        if (log.isInfoEnabled()) {
            log.info("GiftBrandService中的findGiftBrandByBrandId操作，参数brandId=" + brandId.toString());
        }
        List<GiftBrand> giftBrandList = giftBrandMapper.findGiftBrandByBrandId(brandId);
        for (GiftBrand giftBrand : giftBrandList) {
            giftBrand.setGiftBrandItemList(giftBrandItemMapper.findByGiftBrandId(giftBrand.getId()));
        }
        return giftBrandList;
    }

    /**
     * 根据brandId查找这个id所有的奖励价格范围
     *
     * @param brandId
     * @return GiftBrand
     */
    @Transactional(readOnly = true)
    @Override
    public List<GiftBrand> findBeginEndByBrandId(Integer brandId) {
        if (log.isInfoEnabled()) {
            log.info("GiftBrandService中的findBeginEndByBrandId操作，参数brandId=" + brandId.toString());
        }
        List<GiftBrand> giftBrandList = giftBrandMapper.findGiftBrandByBrandId(brandId);
        for (GiftBrand giftBrand : giftBrandList) {
            giftBrand.setGiftBrandItemList(giftBrandItemMapper.findByGiftBrandId(giftBrand.getId()));
        }
        return giftBrandList;
    }


    /**
     * 增加一条赠品记录
     *
     * @param giftBrand
     * @return 新增记录的条数
     */
    @Transactional
    @Override
    public Integer saveGiftBrand(GiftBrand giftBrand) {
        if (log.isInfoEnabled()) {
            log.info("GiftBrandService中的saveGiftBrand操作，参数giftBrand=" + giftBrand.toString());
        }
        giftBrand.setPriceBegin(Money.YuanToCent(giftBrand.getPriceBegin().toString()));
        giftBrand.setPriceEnd(Money.YuanToCent(giftBrand.getPriceEnd().toString()));

        if (!saveOrUpdateFunction(giftBrand, 0)) return 0;
        Integer count = giftBrandMapper.saveGiftBrand(giftBrand);
        //级联保存 赠品项
        if (!giftBrand.getGiftBrandItemList().isEmpty()) {
            List<GiftBrandItem> giftBrandItemList = giftBrand.getGiftBrandItemList();
            for (GiftBrandItem giftBrandItem : giftBrandItemList) {
                giftBrandItem.setGiftBrandId(giftBrand.getId());
                giftBrandItemMapper.saveGiftBrandItem(giftBrandItem);
            }
        }
        return count;
    }

    /**
     * 根据id删除一条记录
     *
     * @param ids
     * @return 删除记录的条数
     */
    @Transactional
    @Override
    public Integer deleteGiftBrandByIds(Integer[] ids) {
        if (log.isInfoEnabled()) {
            log.info("GiftBrandService中的deleteGiftBrandById操作，参数giftBrand=" + ids.toString());
        }
        Integer count = 0;
        for (Integer id : ids) {
            //先删赠品项的记录
            giftBrandItemMapper.deleteByGiftBrandId(id);
            //在删赠品表的记录
            giftBrandMapper.deleteGiftBrandById(id);
            count++;
        }
        return count;
    }

    /**
     * 根据id 修改该记录的 其他任何字段值
     *
     * @param giftBrand
     * @return 更新记录的条数
     */
    @Transactional
    @Override
    public Integer updateGiftBrandByGiftBrand(GiftBrand giftBrand) {
        if (log.isInfoEnabled()) {
            log.info("GiftBrandService中的updateGiftBrandById操作，参数giftBrand=" + giftBrand.toString());
        }
        Integer count = 0;
        giftBrand.setPriceBegin(Money.YuanToCent(giftBrand.getPriceBegin().toString()));
        giftBrand.setPriceEnd(Money.YuanToCent(giftBrand.getPriceEnd().toString()));
        if (!saveOrUpdateFunction(giftBrand, 1)) return 0;
        //先删除原来的赠品项
        giftBrandItemMapper.deleteByGiftBrandId(giftBrand.getId());
        //在插入新的赠品项
        List<GiftBrandItem> giftBrandItemList1 = giftBrand.getGiftBrandItemList();
        for (GiftBrandItem giftBrandItem : giftBrandItemList1) {
            giftBrandItemMapper.saveGiftBrandItem(giftBrandItem);
        }
        //最后更新赠品表
        count = giftBrandMapper.updateGiftBrandById(giftBrand);
        return count;
    }

    /**
     * 作为更新时 判断 满足金额范围 的函数
     *
     * @param giftBrand
     * @return boolean
     */
    private boolean saveOrUpdateFunction(GiftBrand giftBrand, Integer flag) {
        Integer id = giftBrand.getId();
        Integer brandId = 0;
        if (flag.equals(0)) {
            brandId = giftBrand.getBrandId();
        } else {
            brandId = giftBrandMapper.findGiftBrandById(giftBrand.getId()).getBrandId();
        }
        Long newPriceBegin = giftBrand.getPriceBegin();
        Long newPriceEnd = giftBrand.getPriceEnd();

        //防止某个品牌有重叠的 价格范围
        List<GiftBrand> giftBrandList = giftBrandMapper.findGiftBrandByBrandId(brandId);
        if (null != giftBrandList) {
            List<GiftBrand> giftBrandList1 = giftBrandMapper.findGiftBrandByBrandId(brandId);
            for (GiftBrand giftBrand2 : giftBrandList1) {
                if (giftBrand2.getId().equals(id)) {
                    continue;
                }
                Long priceBegin = giftBrand2.getPriceBegin();
                Long priceEnd = giftBrand2.getPriceEnd();
                if (((newPriceBegin >= priceBegin && newPriceBegin <= priceEnd) || (newPriceEnd >= priceBegin && newPriceEnd <= priceEnd)) ||
                        ((priceBegin >= newPriceBegin && priceBegin <= newPriceEnd) || (priceEnd >= newPriceBegin && priceEnd <= newPriceEnd))) {
                    return false;
                }
            }
        }
        return true;
    }


}
