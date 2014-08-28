
package com.ejushang.spider.erp.service.test;

import com.ejushang.spider.SpiderApplication;
import com.ejushang.spider.constant.ProductType;
import com.ejushang.spider.domain.*;
import com.ejushang.spider.service.product.IBrandService;
import com.ejushang.spider.service.product.IMealSetService;
import com.ejushang.spider.service.product.IProdCategoryService;
import com.ejushang.spider.service.product.IProductService;
import com.ejushang.spider.service.repository.IRepositoryService;
import com.ejushang.spider.service.repository.IStorageService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: amos.zhou
 * Date: 13-12-23
 * Time: 上午9:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-*.xml")
public class ErpTest {

    @Autowired
    private IProductService productService;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private IProdCategoryService prodCategoryService;
    @Autowired
    private IMealSetService mealsetService;
    @Autowired
    private IRepositoryService repositoryService;
    @Autowired
    private IStorageService storageService;
    @Autowired
    protected DataSource dataSource;
    @Autowired
    protected ApplicationContext applicationContext;
    private boolean initialized;

    public static final int INIT_STORAGE_NUMBER = 1000;

    @Before
    public void init() {
        if(initialized) return;
        SpiderApplication.getInstance().init(applicationContext, null);
        initialized = true;
    }


    /**
     * 初始化产品,品牌,产品库存以及仓库
     * 每2个产品属于同一个品牌,以及同一个仓库.库存数量初始化为1000
     * @param productType 产品类型,商品/套餐商品/赠品
     * @param size 产品数量
     * @return
     */
    protected List<Product> initProducts(String productType, int size) {

        List<Product> products = new ArrayList<Product>(size);

        ProdCategory prodCategory = new ProdCategory();
        prodCategory.setName("测试分类" + RandomStringUtils.randomAlphabetic(6));
        prodCategory.setCreateTime(new Date());
        prodCategoryService.saveProdCategory(prodCategory);

        Brand brand = null;
        Repository repository = null;
        for (int i = 0; i < size; i++) {
            if(i % 2 == 0) {
                brand = new Brand();
                brand.setName(RandomStringUtils.randomAlphabetic(6));
                brand.setCreateTime(new Date());
                brand.setDescription("");
                brandService.saveBrand(brand);

                repository = new Repository();
                repository.setName("测试仓库" + RandomStringUtils.randomAlphabetic(6));
                repository.setAddress("");
                repository.setChargeMobile("18682223322");
                repository.setChargePerson("张三");
                repository.setRepoCode(RandomStringUtils.randomAlphanumeric(8));
                repository.setShippingComp("shunfeng");
                repositoryService.saveRepository(repository);
            }

            Product product = new Product();
            product.setBrandId(brand.getId());
            product.setDescription("");
            product.setCreateTime(new Date());
            product.setCid(prodCategory.getId());
            product.setPicUrl("http://www.baidu.com/img/bdlogo.gif");
            product.setProdCode(RandomStringUtils.randomAlphanumeric(16));
            product.setProdName(RandomStringUtils.randomAlphabetic(10));
            product.setProdNo(RandomStringUtils.randomAlphanumeric(8));
            Long tempPrice1 = Long.parseLong(RandomStringUtils.randomNumeric(5));
            Long tempPrice2 = Long.parseLong(RandomStringUtils.randomNumeric(5));
            product.setShopPrice(Math.min(tempPrice1, tempPrice2));
            product.setStandardPrice(Math.max(tempPrice1, tempPrice2));
            product.setBuyPrice(product.getShopPrice());
            product.setType(productType);


            Storage storage = new Storage();
            storage.setActuallyNumber(INIT_STORAGE_NUMBER);
            storage.setRepoId(repository.getId());
            productService.saveProduct(product,storage);
            products.add(product);

        }

        return products;
    }

    /**
     * 根据产品初始化套餐以及套餐明细
     * 如果产品数量为奇数,初始化的套餐数量为 产品数量/2 + 1,否则为 产品数量/2
     * 每个套餐有2个套餐明细(如果产品数量为奇数,则最后一个套餐只有一个套餐明细)
     * @param mealsetProducts
     * @return
     */
    protected List<Mealset> initMealsets(List<Product> mealsetProducts) {
        int size = (mealsetProducts.size() % 2 == 0) ? (mealsetProducts.size() / 2) : (mealsetProducts.size() / 2 + 1);
        List<Mealset> mealsets = new ArrayList<Mealset>();

        int productCount = 0;
        for (int i = 0; i < size; i++) {

            Mealset mealset = new Mealset();
            mealset.setCreateTime(new Date());
            mealset.setName(RandomStringUtils.randomAlphabetic(6));
            mealset.setCode(RandomStringUtils.randomAlphanumeric(16));

            List<MealsetItem> mealsetItems = new ArrayList<MealsetItem>();
            for (int j = 0; j < 2 && productCount < mealsetProducts.size(); j++, productCount++) {
                Product product = mealsetProducts.get(productCount);
                MealsetItem mealsetItem = new MealsetItem();
                mealsetItem.setCreateTime(new Date());
                mealsetItem.setProdId(product.getId());
                mealsetItem.setMealPrice(product.getShopPrice());
                mealsetItem.setMealCount(1);
                mealsetItems.add(mealsetItem);
            }

            mealset.setMealsetItemList(mealsetItems);
            mealsets.add(mealset);
            mealsetService.saveMealset(mealset);
        }

        return mealsets;
    }


}
