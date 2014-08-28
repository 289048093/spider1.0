package com.ejushang.spider.erp.service.product;

import com.ejushang.spider.constant.OrderItemType;
import com.ejushang.spider.constant.ProductType;
import com.ejushang.spider.domain.*;
import com.ejushang.spider.erp.common.mapper.*;
import com.ejushang.spider.erp.util.SystemConfConstant;
import com.ejushang.spider.query.ProdNameQuery;
import com.ejushang.spider.query.ProdNoQuery;
import com.ejushang.spider.query.ProductQuery;
import com.ejushang.spider.service.product.IProductService;
import com.ejushang.spider.service.repository.IStorageService;
import com.ejushang.spider.service.sysconfig.IConfService;
import com.ejushang.spider.taobao.api.ProductApi;
import com.ejushang.spider.taobao.helper.ConstantTaoBao;
import com.ejushang.spider.taobao.helper.TaoBaoUtilsEjs;
import com.ejushang.spider.util.Money;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.ProductVo;
import com.ejushang.spider.vo.QueryProdVo;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * User: 龙清华
 * Date: 13-12-24
 * Time: 下午2:38
 */
@Transactional
@Service
public class ProductService implements IProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Resource
    private ProductMapper productMapper;
    @Resource
    private StorageMapper storageMapper;
    @Resource
    private IStorageService storageService;
    @Resource
    private BrandMapper brandMapper;
    @Resource
    private ProdCategoryMapper prodCategoryMapper;
    @Resource
    private RepositoryMapper repositoryMapper;
    @Resource
    private MealsetMapper mealsetMapper;
    @Resource
    private MealsetItemMapper mealsetItemMapper;
    @Resource
    private IConfService confService;
    @Resource
    private GiftProdMapper giftProdMapper;
    @Resource
    private ProdSalesMapper prodSalesMapper;

    @Override
    public int saveProduct(Product product, Storage storage) {
        Product temp = productMapper.findProductBySKU(product);
        if (log.isInfoEnabled()) {
            log.info("是否存在 temp：" + temp);
        }
        if (temp != null) {
            return -1;
        }
        int count = productMapper.saveProduct(product);
        ProdSales prodSales = new ProdSales();
        prodSales.setProdId(product.getId());
        /**添加商品销售记录*/
        productMapper.saveProdSales(prodSales);
        /**添加库存信息*/
        storage.setProdId(product.getId());
        storageMapper.saveStorage(storage);
        if (log.isInfoEnabled()) {
            log.info("初始化库存信息：storage" + storage);
        }
        return count;
    }

    @Transactional(readOnly = true)
    @Override
    public Page findProductAll(ProductQuery productQuery) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<ProductVo> productVoList = new ArrayList<ProductVo>();
        ProductVo productVo = null;

        // 构造分页信息
        Page page = new Page();
        // 设置当前页
        page.setPageNo(productQuery.getPage());
        // 设置分页大小
        page.setPageSize(productQuery.getLimit());
        List<Product> productList = productMapper.findProductAll(productQuery, page);
        if (log.isInfoEnabled()) {
            log.info("查询的结果productList:" + productList);
        }
        for (int i = 0; i < productList.size(); i++) {
            productVo = new ProductVo();
            Product product = productList.get(i);
            PropertyUtils.copyProperties(productVo, product);
            /**根据品牌id获得品牌信息*/
            Brand brand = brandMapper.findBrandById(product.getBrandId());
            ProdCategory prodCategory = prodCategoryMapper.findProdCategoryById(product.getCid());
            Storage storage = storageMapper.findStorageByProdId(product.getId());
            if (brand != null) {
                productVo.setBrandName(brandMapper.findBrandById(product.getBrandId()).getName());
            }
            if (prodCategory != null) {
                productVo.setProdCategoryName(prodCategory.getName());
            }
            productVo.setStorage(storage);
            productVo.setProdId(productVo.getId());

            productVoList.add(productVo);
        }
        if (log.isInfoEnabled()) {
            log.info("查询的vo结果productVoList:" + productVoList);
        }
        page.setResult(productVoList);
        return page;
    }

    @Override
    public int updateProduct(Product product, Storage storage) {
        if (log.isInfoEnabled()) {
            log.info("修改：product" + product + "修改库存信息：storage" + storage);
        }
        Integer prodId = product.getId();
        storageMapper.deleteStorageByProdId(prodId);
        storage.setProdId(prodId);
        storageMapper.saveStorage(storage);
        return productMapper.updateProduct(product);
    }

    @Override
    public int deleteProduct(int[] idArray) {
        if (log.isInfoEnabled()) {
            log.info("删除的商品ID" + Arrays.toString(idArray));
        }
        for (int id : idArray) {
            //判断商品销售中是否有此商品正在销售
            if (prodSalesMapper.findProdSalesByIsDelete(id) != 0) {
                throw new IllegalStateException("此商品正在销售,不能删除！");
            }
            //判断优惠活动是否已经使用了此商品
            List<GiftProd> giftProdList = giftProdMapper.findByGiftProdId(id);
            List<GiftProd> giftSellList = giftProdMapper.findBySellProdId(id);
            if (giftProdList.size() != 0 || giftSellList.size() != 0) {
                throw new IllegalStateException("此商品已经在优惠活动中使用,不能删除！");
            }
//            if (prodSalesMapper.findProdSalesByIsDelete(id) != 0) {
//                return -1;
//            }
            productMapper.deleteProductById(id);
            storageMapper.deleteStorageByProdId(id);
            mealsetItemMapper.deleteMealsetItemByProdId(id);
        }
        return idArray.length;
    }

    @Transactional(readOnly = true)
    @Override
    public int findProductCount(ProductQuery productQuery) {
        return productMapper.findProductCount();
    }

    @Transactional(readOnly = true)
    @Override
    public Integer findProductMaxId() {
        return productMapper.findProductMaxId();
    }

    @Transactional(readOnly = true)
    @Override
    public Product findProductById(Integer id) {
        if (log.isInfoEnabled()) {
            log.info("通过name获得产品分类对象!! id" + id);
        }
        return productMapper.findProductById(id);
    }


    @Transactional(readOnly = true)
    @Override
    public Product findProductBySKU(String prodCode, String type) {
        if (log.isInfoEnabled()) {
            log.info("通过prodCode获得产品分类对象!! prodCode" + prodCode + "type:" + type);
        }
        Product product = new Product();
        product.setProdCode(prodCode);
        product.setType(type);
        if (log.isInfoEnabled()) {
            log.info("参数信息 product:" + product);
        }
        return productMapper.findProductBySKU(product);
    }

    @Override
    public void leadInProduct(HSSFSheet sheet) {
        if (log.isInfoEnabled()) {
            log.info("数量==行 " + sheet.getFirstRowNum() + 1);
        }
        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i);
            /** 判断是否为null*/
            for (int k = 0; k < 15; k++) {
                row.getCell(k).setCellType(Cell.CELL_TYPE_STRING);
            }
            for (int j = 0; j <= 6; j++) {
                if (StringUtils.isBlank(row.getCell(j).getStringCellValue())) {
                    throw new IllegalStateException("行数：" + i + " 列：" + (j + 1) + "不能为空");
                }
            }
            /** 判断是否为数字*/
            for (int j = 7; j <= 10; j++) {
                if (row.getCell(j) == null) {
                    throw new IllegalStateException("行数：" + i + " 列：" + (j + 1) + "必须是数字");
                }
                if (!NumberUtils.isNumber(row.getCell(j).toString())) {
                    throw new IllegalStateException("行数：" + i + " 列：" + (j + 1) + "必须是数字");
                }
            }
            this.validateExcel(row, i);
        }
    }

    /**
     * 导单前验证excel表单是否合法
     *
     * @param row
     * @return
     */
    public void validateExcel(HSSFRow row, int index) {
        Product product = new Product();
        Storage storage = new Storage();
        String brandName = row.getCell(0).getStringCellValue();        //品牌名
        String proCatgoryName = row.getCell(1).getStringCellValue();   //分类
        String prodNo = row.getCell(2).getStringCellValue();           //产品编号
        String boxSize = row.getCell(3).getStringCellValue();          //包装尺寸
        String prodCode = row.getCell(4).getStringCellValue();         //条形码
        String repositoryName = row.getCell(5).getStringCellValue();   //所属仓库
        String prodName = row.getCell(6).getStringCellValue();         //产品名称
        String standardPrice = row.getCell(7).getStringCellValue();    //市场价
        String buyPrice = row.getCell(8).getStringCellValue();         //进货价
        String shopPrice = row.getCell(9).getStringCellValue();        //销售价格
        String repositoryCount = row.getCell(10).getStringCellValue(); //库存数量
        String weight = row.getCell(11).getStringCellValue();          //重量
        String color = row.getCell(12).getStringCellValue();           //颜色
        String speci = row.getCell(13).getStringCellValue();           //规格
        String description = row.getCell(14).getStringCellValue();     //产品备注

        Brand brand = brandMapper.findBrandByName(brandName);
        /**excel数据判断*/
        if (log.isInfoEnabled()) {
            log.info("品牌是否存在 brand：" + brand);
        }
        if (brand == null) {
            throw new IllegalStateException("品牌:" + brandName + " 不存在" + "  行数：" + index);
        }
        ProdCategory prodCategory = prodCategoryMapper.findProdCategoryByName(proCatgoryName);
        if (log.isInfoEnabled()) {
            log.info("产品分类是否存在 prodCategory：" + prodCategory);
        }
        if (prodCategory == null) {
            throw new IllegalStateException("分类:" + proCatgoryName + " 不存在" + "  行数：" + index);
        }
        Repository repository = repositoryMapper.findRepositoryByName(repositoryName);
        if (log.isInfoEnabled()) {
            log.info("仓库是否存在 repository：" + repository);
        }
        if (repository == null) {
            throw new IllegalStateException("仓库:" + repositoryName + "  不存在" + "  行数：" + index);
        }
        if (Integer.valueOf(repositoryCount) < 0) {
            throw new IllegalStateException("库存数不能是负数  行数：" + index);
        }
        if (log.isInfoEnabled()) {
            log.info("尺寸是否符合规则 boxSize.matches('(\\d+\\*\\d+)(\\*\\d+)*'):" + boxSize.matches("(\\d+\\*\\d+)(\\*\\d+)*"));
        }
        if (log.isInfoEnabled()) {
            log.info("商品是否存在" + this.findProductBySKU(prodCode, ProductType.PRODUCT.toString()));
        }
        if (this.findProductBySKU(prodCode, ProductType.PRODUCT.toString()) != null) {
            throw new IllegalStateException("商品：" + prodName + "  条形码：" + prodCode + " 已经存在请不要重复导入" + "行数：" + index);
        }
        /**商品数据注入*/
        product.setBrandId(brand.getId());
        product.setCid(prodCategory.getId());
        product.setProdNo(prodNo);
        product.setBoxSize(boxSize);
        product.setProdCode(prodCode);
        product.setProdName(prodName);
        product.setStandardPriceStr(standardPrice);
        product.setBuyPriceStr(buyPrice);
        product.setShopPriceStr(shopPrice);
        product.setWeight(weight);
        product.setColor(color);
        product.setSpeci(speci);
        product.setDescription(description);
        product.setPicUrl("");      //一期没有图片
        product.setType("PRODUCT");
        /**库存数据注入*/
        storage.setRepoId(repository.getId());
        storage.setActuallyNumber(Integer.valueOf(repositoryCount));
        /**存入数据库*/
        if (log.isInfoEnabled()) {
            log.info("入库商品信息：product" + product);
        }
        this.saveProduct(product, storage);
    }

    /**
     * User: 易萌
     * Date: 14-1-10
     * Time: 下午6:20
     */
    @Override
    @Transactional
    public List<QueryProdVo> findProductByProd(String param, String paramType, OrderItemType orderItemType, Integer repoId) {

        List<Product> productList = new ArrayList<Product>();
        List<QueryProdVo> queryProdVoList = new ArrayList<QueryProdVo>();
        QueryProdVo queryProdVo = null;
        ProdNameQuery prodNameQuery = new ProdNameQuery();
        ProdNoQuery prodNoQuery = new ProdNoQuery();

        if (log.isInfoEnabled()) {
            log.info("接受到的参数为：" + param + "/" + paramType + "/" + orderItemType);
        }
        prodNameQuery.setType(orderItemType.toString());
        prodNameQuery.setProdName(param);

        if (log.isInfoEnabled()) {
            log.info("prodNameQuery接受到的参数为：" + prodNameQuery.getProdName() + "/" + prodNameQuery.getType() + "/" + paramType);
        }
        prodNoQuery.setType(orderItemType.toString());
        prodNoQuery.setProdNo(param);

        if (log.isInfoEnabled()) {
            log.info("prodNoQuery接受到的参数为：" + prodNoQuery.getProdNo() + "/" + prodNoQuery.getType());
        }
        //套餐处理
        if (orderItemType == OrderItemType.MEALSET) {
            if (paramType.equals("PRODNO")) {
                int id = Integer.parseInt(param);
                Mealset mealset = mealsetMapper.findMealsetById(id);
                queryProdVo = new QueryProdVo();
                queryProdVo.setProdNo("" + mealset.getId());
                queryProdVo.setProdName(mealset.getName());
                queryProdVo.setSkuCode(mealset.getCode());
                queryProdVoList.add(queryProdVo);
            }
            if (paramType.equals("PRODNAME")) {
                String name = param;
                List<Mealset> mealsetList = mealsetMapper.findMealsetByName(param);
                for (int i = 0; i < mealsetList.size(); i++) {
                    queryProdVo = new QueryProdVo();
                    queryProdVo.setProdNo("" + mealsetList.get(i).getId());
                    queryProdVo.setProdName(mealsetList.get(i).getName());
                    queryProdVo.setSkuCode(mealsetList.get(i).getCode());
                    queryProdVoList.add(queryProdVo);
                }
            }
        } else {
            //处理商品和赠品
            if (log.isInfoEnabled()) {
                log.info("接受到的参数为：" + prodNameQuery.getProdName() + "/" + prodNameQuery.getType() + "/" + paramType);
            }
            if (paramType.equals("PRODNO")) {
                productList = productMapper.findProductByProdNo(prodNoQuery);
            } else if (paramType.equals("PRODNAME")) {
                if (log.isInfoEnabled()) {
                    log.info("接受到的参数为：" + prodNameQuery.getProdName() + "/" + prodNameQuery.getType() + "/" + paramType);
                }
                productList = productMapper.findProductByProdName(prodNameQuery);
            }
            if (productList.size() > 0) {
                queryProdVoList = changeToVo(productList, repoId);
            } else {
                return null;
            }
        }
        return queryProdVoList;
    }

    /**
     * User: 易萌
     * Date: 14-1-10
     * Time: 下午6:20
     */
    public List<QueryProdVo> changeToVo(List<Product> productList, Integer repoId) {

        List<QueryProdVo> queryProdVoList = new ArrayList<QueryProdVo>();

        if (log.isInfoEnabled()) {
            log.info("接受到的repoId为：" + repoId);
        }
        for (int i = 0; i < productList.size(); i++) {
            Storage storage = storageService.findStorageByProdId(productList.get(i).getId());
            if (log.isInfoEnabled()) {
                log.info("productList.get(i).getId()：" + productList.get(i).getId());
                log.info("storage.getRepoId()：" + storage);
            }
            if ((storage != null && storage.getRepoId().equals(repoId)) || repoId == 0) {
                QueryProdVo queryProdVo = new QueryProdVo();
                queryProdVo.setProdNo(productList.get(i).getProdNo());
                queryProdVo.setProdName(productList.get(i).getProdName());
                queryProdVo.setSkuCode(productList.get(i).getProdCode());
                queryProdVo.setProdPrice(Money.CentToYuan(productList.get(i).getShopPrice()).toString());
                queryProdVo.setItemType(productList.get(i).getType());
                queryProdVoList.add(queryProdVo);
            }
        }
        return queryProdVoList;
    }


    @Transactional(readOnly = true)
    @Override
    public Product getPostageProduct() {
        String sku = confService.findConfValue(SystemConfConstant.POSTAGE_PRODUCT_SKU);
        if (StringUtils.isBlank(sku)) return null;
        return this.findProductBySKU(sku, ProductType.PRODUCT.toString());
    }
}
