package com.ejushang.spider.erp.service.order;

import com.ejushang.spider.constant.OriginalOrderStatus;
import com.ejushang.spider.constant.ProductType;
import com.ejushang.spider.domain.*;
import com.ejushang.spider.erp.common.mapper.GiftProdMapper;
import com.ejushang.spider.erp.service.test.ErpTest;
import com.ejushang.spider.erp.util.SystemConfConstant;
import com.ejushang.spider.service.gift.IGiftBrandService;
import com.ejushang.spider.service.order.IOriginalOrderService;
import com.ejushang.spider.service.product.IProductService;
import com.ejushang.spider.service.repository.IStorageService;
import com.ejushang.spider.service.sysconfig.IConfService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * 订单分析测试类可以继承这个类,可以进行一些原始订单初始化的操作
 * User: liubin
 * Date: 14-1-6
 */
public abstract class OrderAnalyzeBaseTest extends ErpTest {

    @Autowired
    private IOriginalOrderService originalOrderService;
    @Autowired
    private IGiftBrandService giftBrandService;
    @javax.annotation.Resource
    private GiftProdMapper giftProdMapper;
    @Autowired
    private IStorageService storageService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IConfService confService;

    public static final String REMARK_START_WORD = "start_remark_";

    protected Product postageProduct;

    /**
     * 初始化原始订单数据以供测试
     * @param giftParamList 如果需要初始化赠品,就传元素为空的list,否则传null
     * @return
     */
    protected List<OriginalOrder> prepareOriginalOrders(List<Object> giftParamList) {

        List<Product> products = initProducts(ProductType.PRODUCT.toString(), 12);
        List<Product> mealsetProducts = initProducts(ProductType.PRODUCT.toString(), 4);
        List<Mealset> mealsets = initMealsets(mealsetProducts);
        if(giftParamList != null) {
            giftParamList.clear();
            //增加赠品
            //增加一个品牌赠品规则和一个商品赠品规则
            //这个品牌赠品规则会添加2个赠品,商品赠品规则会添加1个赠品,所以一共会有3个赠品
            Integer brandId = products.get(0).getBrandId();
            Integer productId = products.get(1).getId();
            List<Product> giftProducts = initProducts(ProductType.GIFT.toString(), 2);
            initGiftBrandAndGiftProducts(brandId, productId, giftProducts);
            giftParamList.add(giftProducts);
        }


        //不需要拆分的订单
        //1个订单明细,产品数量1
        OriginalOrder originalOrder1 = initOriginalOrder(extractToOriginalOrderItem(products.subList(0, 1), new long[]{1}));

        //不需要拆分的订单
        //1个订单明细,产品数量多个
        OriginalOrder originalOrder2 = initOriginalOrder(extractToOriginalOrderItem(products.subList(1, 2), new long[]{4}));

        //需要拆分的订单
        //4个订单明细,每2个订单明细对应1个仓库,产品数量1~4个
        OriginalOrder originalOrder3 = initOriginalOrder(extractToOriginalOrderItem(products.subList(2, 6), new long[]{1,2,3,4}));

        //需要拆分的订单
        //3个订单明细,2个订单明细对应一个仓库,1个订单明细对应另一个
        OriginalOrder originalOrder4 = initOriginalOrder(extractToOriginalOrderItem(products.subList(6, 9), new long[]{1,2,3}));

        //不需要拆分的订单
        //1个订单明细,产品数量2
        OriginalOrder originalOrder5 = initOriginalOrder(extractToOriginalOrderItem(products.subList(9, 10), new long[]{2}));

        //不需要拆分的订单
        //构造一个sku不存在的产品
        List<Product> productItems = products.subList(10, 11);
        productItems.get(0).setProdCode("notexistyet");
        OriginalOrder originalOrder6 = initOriginalOrder(extractToOriginalOrderItem(productItems, new long[]{1}));

        //不需要拆分的订单
        //订单明细为1个套餐,2个套餐明细
        OriginalOrder originalOrder7 = initOriginalOrder(extractToOriginalOrderItem(mealsets.get(0)));

        //需要拆分的订单
        //订单明细为1个套餐,1个产品,这1个套餐有2个套餐明细,所以最后订单里会有3个产品明细,分属于2个仓库
        List<OriginalOrderItem> originalOrderItems = extractToOriginalOrderItem(mealsets.get(1));
        originalOrderItems.addAll(extractToOriginalOrderItem(products.subList(11,12), new long[]{2}));
        OriginalOrder originalOrder8 = initOriginalOrder(originalOrderItems);

        //初始化邮费虚拟产品,前提是数据库要有配置项记录存在
        if(postageProduct == null) {
            postageProduct = productService.getPostageProduct();
        }
        if(postageProduct == null) {
            Conf conf = confService.findConfByKey(SystemConfConstant.POSTAGE_PRODUCT_SKU);
            if(conf == null) {
                throw new RuntimeException(String.format("配置项[%s]不能为空", SystemConfConstant.POSTAGE_PRODUCT_SKU));
            } else {
                postageProduct = initProducts(ProductType.PRODUCT.toString(), 1).get(0);
                conf.setConfigValue(postageProduct.getProdCode());
                confService.updateConfById(conf);
            }
        }

        //不需要拆分的订单
        //订单明细为1个产品,1个邮费虚拟产品(邮费2元)
        products = initProducts(ProductType.PRODUCT.toString(), 1);
        products.add(postageProduct);
        OriginalOrder originalOrder9 = initOriginalOrder(extractToOriginalOrderItem(products, new long[]{1,2}));

        //不需要拆分的订单
        //订单明细为2个产品,在同1个仓库,然后1个邮费虚拟产品(邮费4元)
        products = initProducts(ProductType.PRODUCT.toString(), 2);
        products.add(postageProduct);
        OriginalOrder originalOrder10 = initOriginalOrder(extractToOriginalOrderItem(products, new long[]{2,3,4}));

        //需要拆分的订单
        //订单明细为3个产品,在2个仓库,然后1个邮费虚拟产品(邮费8元)
        products = initProducts(ProductType.PRODUCT.toString(), 3);
        products.add(postageProduct);
        OriginalOrder originalOrder11 = initOriginalOrder(extractToOriginalOrderItem(products, new long[]{1,2,3,4}));


        return Lists.newArrayList(originalOrder1, originalOrder2, originalOrder3, originalOrder4, originalOrder5, originalOrder6,
                originalOrder7, originalOrder8, originalOrder9, originalOrder10, originalOrder11);
    }

    /**
     * 从originalOrder复制出count个原始订单,按照正常逻辑,最后生成真实订单的时候应该只会生成一个(因为外部订单号相同),应该是最后一个
     *
     * @param originalOrder
     * @param count
     * @return
     */
    protected List<OriginalOrder> prepareSameOriginalOrdersForRemarkUpdateTest(OriginalOrder originalOrder, int count) {
        List<OriginalOrder> originalOrders = new ArrayList<OriginalOrder>();

        for (int i = 1; i <= count ; i++) {
            OriginalOrder result = copyAndSaveOriginalOrder(originalOrder);
            result.setRemark(REMARK_START_WORD + i);
            originalOrderService.updateOriginalOrder(result);
            originalOrders.add(result);
        }

        return originalOrders;
    }

    /**
     * 拷贝并保存OriginalOrder对象以及对应的OriginalOrderItem对象
     * @param originalOrder
     * @return
     */
    private OriginalOrder copyAndSaveOriginalOrder(OriginalOrder originalOrder) {
        OriginalOrder result = new OriginalOrder();
        result.setProcessed(originalOrder.getProcessed());
        result.setRemark(originalOrder.getRemark());
        result.setStatus(originalOrder.getStatus());
        result.setShopName(originalOrder.getShopName());
        result.setShopId(originalOrder.getShopId());
        result.setBuyerId(originalOrder.getBuyerId());
        result.setBuyerMessage(originalOrder.getBuyerMessage());
        result.setBuyTime(originalOrder.getBuyTime());
        result.setCreateTime(originalOrder.getCreateTime());
        result.setInvoiceContent(originalOrder.getInvoiceContent());
        result.setInvoiceName(originalOrder.getInvoiceName());
        result.setNeedInvoice(originalOrder.getNeedInvoice());
        result.setOutOrderNo(originalOrder.getOutOrderNo());
        result.setOutPlatformType(originalOrder.getOutPlatformType());
        result.setReceiverAddress(originalOrder.getReceiverAddress());
        result.setReceiverCity(originalOrder.getReceiverCity());
        result.setReceiverDistrict(originalOrder.getReceiverDistrict());
        result.setReceiverMobile(originalOrder.getReceiverMobile());
        result.setReceiverName(originalOrder.getReceiverName());
        result.setReceiverPhone(originalOrder.getReceiverPhone());
        result.setReceiverState(originalOrder.getReceiverState());
        result.setTotalFee(originalOrder.getTotalFee());

        List<OriginalOrderItem> resultItems = new ArrayList<OriginalOrderItem>();
        result.setOriginalOrderItems(resultItems);


        if(originalOrder.getOriginalOrderItems() != null) {
            for(OriginalOrderItem originalOrderItem : originalOrder.getOriginalOrderItems()) {
                OriginalOrderItem resultItem = new OriginalOrderItem();
                resultItem.setBuyCount(originalOrderItem.getBuyCount());
                resultItem.setPrice(originalOrderItem.getPrice());
                resultItem.setSkuCode(originalOrderItem.getSkuCode());
                resultItem.setTotalFee(originalOrderItem.getTotalFee());
                resultItem.setActualFee(originalOrderItem.getActualFee());
                resultItem.setOrderNo(originalOrderItem.getOrderNo());
                resultItem.setSubOrderNo(originalOrderItem.getSubOrderNo());
                resultItems.add(resultItem);
            }
        }

        originalOrderService.saveOriginalOrderAndItem(result, resultItems);

        return result;
    }

    private List<OriginalOrderItem> extractToOriginalOrderItem(Mealset... mealsets) {
        List<OriginalOrderItem> originalOrderItems = new ArrayList<OriginalOrderItem>();
        for(Mealset mealset : mealsets) {
            OriginalOrderItem originalOrderItem = new OriginalOrderItem();
            originalOrderItem.setSkuCode(mealset.getCode());
            originalOrderItem.setPrice(mealset.calTotalPrice());
            originalOrderItem.setBuyCount(2L);
            originalOrderItem.setSubOrderNo(RandomStringUtils.randomAlphanumeric(8));
            originalOrderItems.add(originalOrderItem);
        }
        return originalOrderItems;
    }

    protected List<OriginalOrderItem> extractToOriginalOrderItem(List<Product> products, long[] buyCounts) {
        List<OriginalOrderItem> originalOrderItems = new ArrayList<OriginalOrderItem>();
        int i=0;
        for(Product product : products) {
            OriginalOrderItem originalOrderItem = new OriginalOrderItem();
            originalOrderItem.setSkuCode(product.getProdCode());
            originalOrderItem.setPrice(product.getShopPrice());
            originalOrderItem.setBuyCount(buyCounts[i]);
            originalOrderItem.setSubOrderNo(RandomStringUtils.randomAlphanumeric(8));
            originalOrderItems.add(originalOrderItem);
            i++;
        }

        return originalOrderItems;
    }

    protected OriginalOrder initOriginalOrder(List<OriginalOrderItem> originalOrderItems) {
        OriginalOrder originalOrder = new OriginalOrder();
        originalOrder.setBuyerId(RandomStringUtils.randomAlphanumeric(8));
        originalOrder.setCreateTime(new Date());
        originalOrder.setBuyTime(new Date());
        originalOrder.setInvoiceContent("发票内容");
        originalOrder.setInvoiceName("发票抬头");
        originalOrder.setNeedInvoice(true);
        originalOrder.setOutOrderNo(RandomStringUtils.randomNumeric(12));
        originalOrder.setOutPlatformType("TAOBAO");
        originalOrder.setPayTime(new Date());
        originalOrder.setReceiverAddress("西长安街32号");
        originalOrder.setReceiverCity("北京");
        originalOrder.setReceiverDistrict("西城区");
        originalOrder.setReceiverMobile("13800002211");
        originalOrder.setReceiverName(RandomStringUtils.randomAlphabetic(6));
        originalOrder.setReceiverPhone("010-82223379");
        originalOrder.setReceiverState("北京");
        originalOrder.setReceiverZip("10010");
        originalOrder.setRemark("要大号的啊");
        originalOrder.setShopId(882211L);
        originalOrder.setShopName("淘宝店铺");
        originalOrder.setStatus(OriginalOrderStatus.WAIT_SELLER_SEND_GOODS.toString());
        originalOrder.setProcessed(false);

        for(OriginalOrderItem originalOrderItem : originalOrderItems) {
            originalOrderItem.setOrderNo(originalOrder.getOutOrderNo());
            originalOrderItem.setTotalFee(originalOrderItem.getPrice() * originalOrderItem.getBuyCount());
            originalOrderItem.setActualFee(originalOrderItem.getTotalFee());
        }

        //计算价格
        originalOrder.setOriginalOrderItems(originalOrderItems);
        long totalFee = 0L;
        for(OriginalOrderItem originalOrderItem : originalOrderItems) {
            totalFee += originalOrderItem.getTotalFee();
        }
        originalOrder.setTotalFee(totalFee);

        originalOrderService.saveOriginalOrderAndItem(originalOrder, originalOrderItems);

        return originalOrder;
    }

    /**
     * 初始化赠品规则
     * 首先清除数据库原来的数据,然后根据品牌ID和产品ID分别在2个表创建一条记录
     * @param brandId
     * @param productId
     */
    protected void initGiftBrandAndGiftProducts(Integer brandId, Integer productId, List<Product> giftProducts) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("delete from t_gift_brand_item");
        jdbcTemplate.execute("delete from t_gift_brand");
        jdbcTemplate.execute("delete from t_gift_prod");

        GiftBrand giftBrand = new GiftBrand();
        giftBrand.setInUse(true);
        giftBrand.setBrandId(brandId);
        giftBrand.setPriceBegin(1L);
        giftBrand.setPriceEnd(10000L);
        List<GiftBrandItem> giftBrandItemList = new ArrayList<GiftBrandItem>();
        GiftBrandItem giftBrandItem = new GiftBrandItem();
        giftBrandItem.setGiftProdId(giftProducts.get(0).getId());
        giftBrandItem.setGiftProdCount(2);
        giftBrandItemList.add(giftBrandItem);
        giftBrand.setGiftBrandItemList(giftBrandItemList);
        giftBrandService.saveGiftBrand(giftBrand);

        GiftProd giftProd = new GiftProd();
        giftProd.setInUse(true);
        giftProd.setSellProdId(productId);
        giftProd.setGiftProdId(giftProducts.get(1).getId());
        giftProd.setGiftProdCount(2);
        giftProdMapper.saveGiftProd(giftProd);

    }

    /**
     * 校验保存原始订单分析结果成功
     * @param originalOrders
     * @param orders
     */
    protected void assertSaveAnalyzeResultSuccess(List<OriginalOrder> originalOrders, List<Order> orders) {
        Map<Integer, Integer> productBuyCountMap = new HashMap<Integer, Integer>();
        Product postageProduct = productService.getPostageProduct();
        Set<String> outOrderNoInOrderSet = new HashSet<String>();
        Set<String> outOrderNoInOriginalOrderSet = new HashSet<String>();
        //校验订单与订单项信息保存正确
        for(Order order : orders) {
            assertThat(order.getId(), notNullValue());
            assertThat(order.getOrderNo(), notNullValue());
            //TODO 校验订单金额
//            assertThat(order.getTotalFee(), is(order.calPayment(false)));
            outOrderNoInOrderSet.add(order.getOutOrderNo());
            for(OrderItem orderItem : order.getOrderItemList()) {
                assertThat(orderItem.getId(), notNullValue());
//                assertThat(orderItem.getTotalFee(), is(orderItem.calPrice()));
                assertThat(orderItem.getOrderId(), is(order.getId()));
                //记录产品与购买数量
                //因为一个订单中同一个产品ID可能会出现多个订单项(每个订单项类型不同,例如商品/赠品/套餐商品)
                //所以不能根据订单项直接查库存
                Integer productBuyCount = productBuyCountMap.get(orderItem.getProdId());
                if(productBuyCount == null) {
                    productBuyCount = 0;
                }
                productBuyCountMap.put(orderItem.getProdId(), productBuyCount + orderItem.getProdCount());
            }
        }
        //校验库存
        //库存在确认订单的时候修改,所以不需要再校验
        assertThat(productBuyCountMap.isEmpty(), is(false));
//        for(Map.Entry<Integer, Integer> entry : productBuyCountMap.entrySet()) {
//            Integer productId = entry.getKey();
//            Integer buyCount = entry.getValue();
//            Storage storage = storageService.findStorageByProdId(productId);
//            assertThat(storage, notNullValue());
//            if(postageProduct != null && productId.equals(postageProduct.getId())) {
//                continue;
//            }
//            assertThat(storage.getActuallyNumber(), is(INIT_STORAGE_NUMBER - buyCount));
//        }
        //校验原始订单状态是否已经设置成被处理
        for(OriginalOrder originalOrder : originalOrders) {
            List<OriginalOrderItem> originalOrderItems = originalOrder.getOriginalOrderItems();
            boolean errorOriginalOrder = false;
            for(OriginalOrderItem originalOrderItem : originalOrderItems) {
                if(originalOrderItem.getSkuCode().equals("notexistyet")) {
                    errorOriginalOrder = true;
                    break;
                }
            }
            if(errorOriginalOrder) continue;
            OriginalOrder newOriginalOrder = originalOrderService.get(originalOrder.getId());
            assertThat(newOriginalOrder.getProcessed(), is(true));
            outOrderNoInOriginalOrderSet.add(originalOrder.getOutOrderNo());
        }
        assertThat(outOrderNoInOrderSet, is(outOrderNoInOriginalOrderSet));
    }

}
