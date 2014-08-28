package com.ejushang.spider.erp.service.product;

import com.ejushang.spider.domain.Brand;
import com.ejushang.spider.domain.GiftBrand;
import com.ejushang.spider.domain.Product;
import com.ejushang.spider.erp.common.mapper.BrandMapper;
import com.ejushang.spider.erp.common.mapper.GiftBrandMapper;
import com.ejushang.spider.erp.common.mapper.ProductMapper;
import com.ejushang.spider.query.BrandQuery;
import com.ejushang.spider.query.ProductQuery;
import com.ejushang.spider.service.product.IBrandService;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.BrandVo;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: 龙清华
 * Date: 13-12-24
 * Time: 下午2:38
 */
@Transactional
@Service
public class BrandService implements IBrandService {
    private static final Logger log = LoggerFactory.getLogger(BrandService.class);

    @Resource
    BrandMapper brandMapper;
    @Resource
    ProductMapper productMapper;
    @Resource
    private GiftBrandMapper giftBrandMapper;

    @Override
    public int saveBrand(Brand brand) {
        if (brandMapper.findBrandByName(brand.getName()) != null) {
            return -1;
        }
        return brandMapper.saveBrand(brand);
    }

    @Override
    @Transactional(readOnly = true)
    public Page findBrandAll(BrandQuery brandQuery) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (log.isInfoEnabled()) {
            log.info("查询的条件 brandQuery：" + brandQuery);
        }
        // 构造分页信息
        Page page = new Page();
        // 设置当前页
        page.setPageNo(brandQuery.getPage());
        // 设置分页大小
        page.setPageSize(brandQuery.getLimit());
        List<Brand> brandList = brandMapper.findBrandAll(brandQuery, page);
        if (log.isInfoEnabled()) {
            log.info("查询的结果 brandList：" + brandList);
        }
        List<BrandVo> brandVoList = new ArrayList<BrandVo>();
        BrandVo brandVo = null;
        for (int i = 0; i < brandList.size(); i++) {
            brandVo = new BrandVo();
            PropertyUtils.copyProperties(brandVo, brandList.get(i));
            brandVoList.add(brandVo);
        }
        if (log.isInfoEnabled()) {
            log.info("查询的VO结果 brandVoList" + brandVoList);
        }
        page.setResult(brandVoList);
        return page;
    }

    @Override
    public int updateBrand(Brand brand) {
        if (log.isInfoEnabled()) {
            log.info("修改的条件实体" + brand);
        }
        if (brandMapper.findBrandByName(brand.getName()) != null) {
            return -1;
        }
        return brandMapper.updateBrand(brand);
    }

    @Override
    @Transactional
    public int deleteBrand(int[] idArray) {
        if (log.isInfoEnabled()) {
            log.info("删除的数组" + Arrays.toString(idArray));
        }
        for (int id : idArray) {
            ProductQuery productQuery = new ProductQuery();
            productQuery.setBrandId(id);
            Page page = new Page();
            page.setPageSize(1);
            page.setPageNo(1);
            List<Product> productList = productMapper.findProductAll(productQuery, page);
            List<GiftBrand> list = giftBrandMapper.findGiftBrandByBrandId(id);
            if (productList.size() != 0 || list.size() != 0) {
                return -1;
            }
            brandMapper.deleteBrand(id);
        }

        return idArray.length;
    }

    @Override
    public List<Brand> findBrandForList(BrandQuery brandQuery) {
        return brandMapper.findBrandAll(brandQuery, null);
    }

    @Transactional(readOnly = true)
    @Override
    public Brand findBrandById(Integer id) {
        if (log.isInfoEnabled()) {
            log.info("通过ID获得brand对象" + id);
        }
        return brandMapper.findBrandById(id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Brand findBrandByName(String name) {
        if (log.isInfoEnabled()) {
            log.info("通过name获得brand对象" + name);
        }
        return brandMapper.findBrandByName(name);  //To change body of implemented methods use File | Settings | File Templates.
    }


}
