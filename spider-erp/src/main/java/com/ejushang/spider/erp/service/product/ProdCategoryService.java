package com.ejushang.spider.erp.service.product;

import com.ejushang.spider.domain.ProdCategory;
import com.ejushang.spider.domain.Product;
import com.ejushang.spider.erp.common.mapper.ProdCategoryMapper;
import com.ejushang.spider.erp.common.mapper.ProductMapper;
import com.ejushang.spider.query.ProdCategoryQuery;
import com.ejushang.spider.query.ProductQuery;
import com.ejushang.spider.service.product.IProdCategoryService;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.ProdCategoryVo;
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
public class ProdCategoryService implements IProdCategoryService {
    private static final Logger log = LoggerFactory.getLogger(ProdCategoryService.class);

    @Resource
    ProdCategoryMapper pordCategoryMapper;
    @Resource
    ProductMapper productMapper;

    @Override
    public int saveProdCategory(ProdCategory prodCategory) {
        if (log.isInfoEnabled()) {
            log.info("保存的实体!! prodCategory" + prodCategory);
        }
        if (pordCategoryMapper.findProdCategoryByName(prodCategory.getName()) != null) {
            return -1;
        }
        return pordCategoryMapper.saveProdCategory(prodCategory);
    }

    @Transactional(readOnly = true)
    @Override
    public Page findProdCategoryAll(ProdCategoryQuery prodCategoryQuery) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<ProdCategoryVo> prodCategoryVoArrayList = new ArrayList<ProdCategoryVo>();
        ProdCategoryVo prodCategoryVo = null;

        // 构造分页信息
        Page page = new Page();
        // 设置当前页
        page.setPageNo(prodCategoryQuery.getPage());
        // 设置分页大小
        page.setPageSize(prodCategoryQuery.getLimit());
        List<ProdCategory> prodCategoryList = pordCategoryMapper.findProdCategoryAll(prodCategoryQuery, page);
        for (int i = 0; i < prodCategoryList.size(); i++) {
            prodCategoryVo = new ProdCategoryVo();
            PropertyUtils.copyProperties(prodCategoryVo, prodCategoryList.get(i));
            prodCategoryVoArrayList.add(prodCategoryVo);
        }
        page.setResult(prodCategoryVoArrayList);
        return page;
    }

    @Override
    public List<ProdCategory> findProdCategoryForList(ProdCategoryQuery prodCategoryQuery) {
        return pordCategoryMapper.findProdCategoryAll(prodCategoryQuery, null);
    }

    @Override
    public int updateProdCategory(ProdCategory prodCategory) {
        if (log.isInfoEnabled()) {
            log.info("修改的实体!! prodCategory" + prodCategory);
        }
        ProdCategory temp = pordCategoryMapper.findProdCategoryByName(prodCategory.getName());
        if (log.isInfoEnabled()) {
            log.info("是否存在!! temp" + temp);
        }
        if (temp != null) {
            return -1;
        }
        return pordCategoryMapper.updateProdCategory(prodCategory);
    }

    @Override
    public int deleteProdCategory(int[] idArray) {
        if (log.isInfoEnabled()) {
            log.info("删除的数组!! logStr" + Arrays.toString(idArray));
        }
        for (int id : idArray) {
            ProductQuery productQuery = new ProductQuery();
            productQuery.setCid(id);
            Page page = new Page();
            page.setPageSize(1);
            page.setPageNo(1);
            List<Product> productList = productMapper.findProductAll(productQuery, page);
            if (productList.size() != 0) {
                return -1;
            }
            pordCategoryMapper.deleteProdCategory(id);
        }
        return idArray.length;
    }

    @Transactional(readOnly = true)
    @Override
    public Integer findProdCategoryMaxId() {
        return pordCategoryMapper.findProdCategoryMaxId();
    }

    @Transactional(readOnly = true)
    @Override
    public ProdCategory findProdCategoryById(Integer id) {
        if (log.isInfoEnabled()) {
            log.info("通过ID获得产品分类对象!! id" + id);
        }
        return pordCategoryMapper.findProdCategoryById(id);
    }

    @Override
    public ProdCategory findProdCategoryByName(String name) {
        if (log.isInfoEnabled()) {
            log.info("通过name获得产品分类对象!! name" + name);
        }
        return pordCategoryMapper.findProdCategoryByName(name);
    }


}
