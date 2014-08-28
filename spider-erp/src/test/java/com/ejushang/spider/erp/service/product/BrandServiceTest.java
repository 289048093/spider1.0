package com.ejushang.spider.erp.service.product;

import com.ejushang.spider.domain.Brand;
import com.ejushang.spider.erp.service.test.ErpTest;
import com.ejushang.spider.query.BrandQuery;
import com.ejushang.spider.service.product.IBrandService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.lang.reflect.InvocationTargetException;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 下午2:10
 * 物流service 测试类
 */
public class BrandServiceTest extends ErpTest{
    @Autowired
    private IBrandService brandService;

    @Test
    @Rollback(false)
    public void insertBrandTest() {
        Brand brand = new Brand();
        brand.setName("产品1");
        int count=brandService.saveBrand(brand);
        assertEquals("测试失败", 1, count);
    }

    @Test
    @Rollback(false)
    public void updateBrandTest() {
        Brand brand = new Brand();
        brand.setId(1);
        brand.setDescription("123213");
        int count=brandService.updateBrand(brand);
        assertEquals("测试失败", 1, count);
    }

    @Test
    @Rollback(false)
    public void deleteBrandTest() {
        int [] idArray={1,2,3};
        int count=brandService.deleteBrand(idArray);
        assertEquals("测试失败",-1,count);
    }

    @Test
    @Rollback(false)
    public void findBrandAllTest() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
       BrandQuery brandQuery=new BrandQuery();
       System.out.println( brandService.findBrandForList(brandQuery));
    }
}

