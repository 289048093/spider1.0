package com.ejushang.spider.erp.service.gift;

import com.ejushang.spider.bean.OrderGiftBean;
import com.ejushang.spider.constant.ProductType;
import com.ejushang.spider.domain.*;
import com.ejushang.spider.erp.common.mapper.BrandMapper;
import com.ejushang.spider.erp.common.mapper.GiftProdMapper;
import com.ejushang.spider.erp.common.mapper.ProductMapper;
import com.ejushang.spider.exception.ErpBusinessException;
import com.ejushang.spider.query.GiftBrandQuery;
import com.ejushang.spider.query.GiftProdQuery;
import com.ejushang.spider.query.ProductQuery;
import com.ejushang.spider.service.gift.IGiftProdService;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.vo.GiftProdVO;
import com.ejushang.spider.vo.ProdVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * User: Blomer
 * Date: 13-12-24
 * Time: 上午10:23
 */
@Service
public class GiftProdService implements IGiftProdService {

    private static final Logger log = LoggerFactory.getLogger(GiftProdService.class);


    @Resource
    private GiftProdMapper giftProdMapper;

    @Resource
    private GiftBrandService giftBrandService;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private BrandMapper brandMapper;

    /**
     * 根据对象 查找所有记录
     *
     * @return 赠品规则--商品记录
     */
    @Transactional(readOnly = true)
    @Override
    public List<GiftProd> findAll(GiftProdQuery giftProdQuery, Page page) {
        return giftProdMapper.findAll(giftProdQuery, page);
    }

    @Transactional
    public List<GiftProd> findAllCascades(GiftProdQuery giftProdQuery, Page page) {
        List<GiftProd> giftProdList = findAll(giftProdQuery, page);
        List<Product> productList = null;
        for (GiftProd giftProd : giftProdList) {
            productList = new ArrayList<Product>(2);
            Product sellProd = productMapper.findProductById(giftProd.getSellProdId());
            Product giftProd1 = productMapper.findProductById(giftProd.getGiftProdId());
            if (null != sellProd && null != brandMapper.findBrandById(sellProd.getBrandId())) {
                sellProd.setBrand(brandMapper.findBrandById(sellProd.getBrandId()));
            }
            productList.add(sellProd);
            productList.add(giftProd1);
            giftProd.setProductList(productList);
        }
        return giftProdList;
    }

    /**
     * 查找所有记录 可分页
     *
     * @return 赠品规则--商品记录
     */
    @Transactional
    @Override
    public Page findAllPage(GiftProdQuery giftProdQuery) {
        Page page = new Page();
        page.setPageNo(giftProdQuery.getPage());
        page.setPageSize(giftProdQuery.getLimit());
        List<GiftProd> giftProdList = findAllCascades(giftProdQuery, page);
        if (null != giftProdList) {
            List<GiftProdVO> giftProdVOList = new ArrayList<GiftProdVO>(giftProdList.size());
            for (GiftProd giftProd : giftProdList) {
                if (null != giftProd.getProductList().get(0) && null != giftProd.getProductList().get(1)) {
                    GiftProdVO giftProdVO = new GiftProdVO();
                    giftProdVO.setId(giftProd.getId());
                    if (null != giftProd.getProductList().get(0).getBrand()) {
                        giftProdVO.setBrandName(giftProd.getProductList().get(0).getBrand().getName());
                    } else {  //防止空指针异常
                        giftProdVO.setBrandName(null);
                    }
                    giftProdVO.setSellProdId(giftProd.getSellProdId());
                    giftProdVO.setSellProdName(giftProd.getProductList().get(0).getProdName());
                    giftProdVO.setProdNo(giftProd.getProductList().get(0).getProdNo());

                    giftProdVO.setGiftProdId(giftProd.getGiftProdId());
                    giftProdVO.setGiftProdName(giftProd.getProductList().get(1).getProdName());
                    giftProdVO.setGiftProdCount(giftProd.getGiftProdCount());
                    giftProdVO.setInUse(giftProd.getInUse());
                    giftProdVOList.add(giftProdVO);
                }
            }
            page.setResult(giftProdVOList);
            return page;
        } else {
            log.info("findAllCascades中没有返回值");
            return null;
        }

    }

    /**
     * 根据对象 查找所有记录
     *
     * @param giftProdQuery
     * @return 赠品规则--商品记录
     */
    @Transactional(readOnly = true)
    @Override
    public List<GiftProd> findByQuery(GiftProdQuery giftProdQuery) {
        if (log.isInfoEnabled()) {
            log.info("GiftProdService的findByQuery操作，参数giftProdQuery=" + giftProdQuery.toString());
        }
        return giftProdMapper.findByQuery(giftProdQuery);
    }

    /**
     * 查找 赠品的 id与name
     *
     * @return GiftProdVO
     */
    @Transactional
    @Override
    public List<GiftProdVO> findGiftProdIdAndName() {
        ProductQuery productQuery = new ProductQuery();
        productQuery.setType(ProductType.GIFT.toString());

        List<Product> productList = productMapper.findProductAll(productQuery, null);
        List<GiftProdVO> giftProdVOList = new ArrayList<GiftProdVO>(productList.size());
        for (Product product : productList) {
            GiftProdVO giftProdVO = new GiftProdVO();
            giftProdVO.setGiftProdId(product.getId());
            giftProdVO.setGiftProdName(product.getProdName());
            giftProdVOList.add(giftProdVO);
        }
        return giftProdVOList;
    }

    /**
     * 根据Query查找商品记录
     *
     * @param productQuery
     * @param limit
     * @return Product
     */
    @Transactional
    public List<Product> findProductAll(ProductQuery productQuery, Integer limit) {
        Page page = new Page();
        if (null != limit) {
            page.setPageSize(limit);
        } else {
            page.setPageSize(50);
        }
        //productQuery.setStart(0);
        page.setPageNo(1);
        productQuery.setPage(1);
        List<Product> productList = productMapper.findProductAll(productQuery, page);
        return productList;
    }

    @Transactional
    public List<Product> findProductAllCascades(ProductQuery productQuery, Integer limit) {
        List<Product> productList = findProductAll(productQuery, limit);
        for (Product product : productList) {
            Brand brand = brandMapper.findBrandById(product.getBrandId());
            if (null != brand) {
                product.setBrand(brand);
            } else {
                log.info("在表t_product表中品牌编号为" + product.getBrandId() + "在品牌表t_brand中没有对应记录");
            }
        }
        return productList;
    }

    /**
     * 根据limit作为记录数返回ProdVo到界面
     *
     * @param productQuery
     * @param limit
     * @return ProdVo
     */
    @Transactional
    @Override
    public List<ProdVo> findProductAllByPage(ProductQuery productQuery, Integer limit) {
        if (log.isInfoEnabled()) {
            log.info("GiftProdService的saveGiftProdList操作，参数productQuery,limit分别为:" + productQuery + "," + limit);
        }
        List<Product> productList = findProductAllCascades(productQuery, limit);

        if (null != productList) {
            List<ProdVo> prodVoList = new ArrayList<ProdVo>(productList.size());
            for (Product product : productList) {

                ProdVo prodVo = new ProdVo();
                prodVo.setId(product.getId());
                if (null != product.getBrand()) {
                    prodVo.setBrandName(product.getBrand().getName());
                } else {  //防止报空指针异常
                    prodVo.setBrandName(null);
                }
                prodVo.setProdNo(product.getProdNo());
                prodVo.setProdCode(product.getProdCode());
                prodVo.setProdName(product.getProdName());
                prodVo.setShopPrice(product.getShopPrice());
                prodVoList.add(prodVo);

            }
            return prodVoList;
        } else {
            log.info("GiftProdService中findByPage操作没有查询到数据");
            return null;
        }

    }

    /**
     * 根据sellProdId查找记录
     *
     * @param sellProdId
     * @return 一组sellProdId对应的优惠或送记录
     */
    @Transactional(readOnly = true)
    @Override
    public List<GiftProd> findBySellProdId(Integer sellProdId) {
        if(log.isInfoEnabled()){
            log.info(String.format("GiftProdService中的findBySellProdId方法,参数sellProdId:%s",sellProdId.toString()));
        }
        return giftProdMapper.findBySellProdId(sellProdId);
    }

    /**
     * 根据giftProdId查找记录
     *
     * @param giftProdId
     * @return 一组giftProdId对应的优惠或送记录
     */
    @Transactional(readOnly = true)
    @Override
    public List<GiftProd> findByGiftProdId(Integer giftProdId) {
        if(log.isInfoEnabled()){
            log.info(String.format("GiftProdService中的findByGiftProdId方法,参数giftProdId:%s",giftProdId.toString()));
        }
        return giftProdMapper.findByGiftProdId(giftProdId);
    }

    /**
     * 增加一组GiftProd记录
     *
     * @param giftProdList
     * @return 新增GiftProd记录的条数
     */
    @Transactional
    @Override
    public Integer saveGiftProdList(List<GiftProd> giftProdList) {
        if (log.isInfoEnabled()) {
            log.info("GiftProdService的saveGiftProdList操作，参数giftProdList=" + giftProdList);
        }

        Integer count = 0;
        for (GiftProd giftProd : giftProdList) {
            GiftProdQuery giftProdQuery = new GiftProdQuery();
            giftProdQuery.setSellProdId(giftProd.getSellProdId());
            giftProdQuery.setGiftProdId(giftProd.getGiftProdId());
            if (!giftProdMapper.findByQuery(giftProdQuery).isEmpty()) {
                String msg = "根据商品添加活动的时候出错了,您选择的活动中有的商品活动已经存在了,请检查!";
                throw new ErpBusinessException(msg);
            }
            count++;
        }
        for (GiftProd giftProd : giftProdList) {
            giftProdMapper.saveGiftProd(giftProd);
        }
        return count;
    }

    /**
     * 根据ids删除一组记录
     *
     * @param ids
     * @return 删除GiftProd记录条数
     */
    @Transactional
    @Override
    public Integer deleteGiftProdByIds(Integer[] ids) {
        if (log.isInfoEnabled()) {
            log.info("GiftProdService的deleteGiftProdById删除操作，参数id=" + ids.toString());
        }
        Integer count = 0;
        for (Integer id : ids) {
            giftProdMapper.deleteGiftProdById(id);
            count++;
        }
        return count;
    }

    /**
     * 根据id 修改该记录的 其他字段值
     *
     * @param giftProd
     * @return 更新GiftProd记录条数
     */
    @Transactional
    @Override
    public Integer updateGiftProdById(GiftProd giftProd) {
        if (log.isInfoEnabled()) {
            log.info("GiftProdService的updateGiftProdById操作，参数giftProd=" + giftProd.toString());
        }
        Integer id = giftProd.getId();
        GiftProdQuery giftProdQuery = new GiftProdQuery();
        giftProdQuery.setSellProdId(giftProd.getSellProdId());
        List<GiftProd> giftProdList = giftProdMapper.findByQuery(giftProdQuery);
        //判断更新的 GiftProd 数据是否已经存在
        if (!giftProdList.isEmpty()) {
            for (GiftProd giftProd1 : giftProdList) {
                if (giftProd1.getId().equals(id)) {
                    continue;
                }
                if (giftProd.getGiftProdId().equals(giftProd1.getGiftProdId())) {  //若已存在，返回 0
                    return 0;
                }
            }
        }
        return giftProdMapper.updateGiftProdById(giftProd);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderGiftBean> queryGiftProductForOrder(Order order) {
        List<OrderGiftBean> orderGifts = new ArrayList<OrderGiftBean>();
        Set<Integer> productIds = new HashSet<Integer>();
        Map<Integer, Long> brandMoneyMap = new HashMap<Integer, Long>();
        List<OrderItem> orderItems = order.getOrderItemList();
        if (orderItems == null || orderItems.isEmpty()) {
            return orderGifts;
        }
        for (OrderItem orderItem : orderItems) {
            productIds.add(orderItem.getProdId());
            Integer brandId = orderItem.getProd().getBrandId();
            if (brandId == null) continue;
            Long money = brandMoneyMap.get(brandId);
            if (money == null) money = 0L;
            brandMoneyMap.put(brandId, money + orderItem.getTotalFee());
        }
        //如果单笔订单买了某个产品,加赠品.
        for (Integer productId : productIds) {
            GiftProdQuery queryGiftProd = new GiftProdQuery();
            queryGiftProd.setSellProdId(productId);
            queryGiftProd.setInUse(true);
            List<GiftProd> giftProds = findByQuery(queryGiftProd);
            for (GiftProd giftProd : giftProds) {
                orderGifts.add(createOrderGiftVO(giftProd));
            }
        }
        //单笔订单某个品牌总额满足一定金额,加赠品.
        for (Map.Entry<Integer, Long> entry : brandMoneyMap.entrySet()) {
            Integer brandId = entry.getKey();
            Long money = entry.getValue();
            GiftBrandQuery queryGiftBrand = new GiftBrandQuery();
            queryGiftBrand.setBrandId(brandId);
            queryGiftBrand.setPriceBegin(money);
            queryGiftBrand.setPriceEnd(money);
            queryGiftBrand.setInUse(true);
            List<GiftBrand> giftBrands = giftBrandService.findByQuery(queryGiftBrand);
            for (GiftBrand giftBrand : giftBrands) {
                orderGifts.addAll(createOrderGiftVO(giftBrand));
            }
        }

        return orderGifts;
    }

    private OrderGiftBean createOrderGiftVO(GiftProd giftProd) {
        OrderGiftBean orderGift = new OrderGiftBean();
        orderGift.setGiftProductId(giftProd.getGiftProdId());
        orderGift.setGiftProductCount(giftProd.getGiftProdCount());
        return orderGift;
    }

    private List<OrderGiftBean> createOrderGiftVO(GiftBrand giftBrand) {
        List<GiftBrandItem> giftBrandItemList = giftBrand.getGiftBrandItemList();
        List<OrderGiftBean> orderGiftBeanList = new ArrayList<OrderGiftBean>();
        if (giftBrandItemList != null) {
            for (GiftBrandItem item : giftBrandItemList) {
                OrderGiftBean orderGift = new OrderGiftBean();
                orderGift.setGiftProductId(item.getGiftProdId());
                orderGift.setGiftProductCount(item.getGiftProdCount());
                orderGiftBeanList.add(orderGift);
            }
        }
        return orderGiftBeanList;
    }

}