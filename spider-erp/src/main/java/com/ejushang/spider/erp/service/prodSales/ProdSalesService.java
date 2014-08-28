package com.ejushang.spider.erp.service.prodSales;

import com.ejushang.spider.domain.ProdSales;
import com.ejushang.spider.erp.common.mapper.ProdSalesMapper;
import com.ejushang.spider.service.prodSales.IProdSalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * User: Blomer
 * Date: 13-12-24
 * Time: 上午10:23
 */
@Service
public class ProdSalesService implements IProdSalesService {

    private static final Logger log = LoggerFactory.getLogger(ProdSalesService.class);

    @Resource
    private IProdSalesService prodSalesService;

    @Resource
    private ProdSalesMapper prodSalesMapper;

    /**
     * 查询所有记录
     *
     * @return 返回商品销量记录
     */
    @Override
    public List<ProdSales> findAll() {
        return prodSalesMapper.findAll();
    }

    /**
     * 根据商品编号查找该商品有销售记录
     * @param prodId
     * @return 该商品的销售记录
     */
    @Override
    public ProdSales findProdSalesByProdId(Integer prodId) {
        if (log.isInfoEnabled()) {
            log.info(String.format("ProdSalesService的findProdSalesByProdId操作，参数prodId:%s",prodId.toString()));
        }
        return prodSalesMapper.findProdSalesByProdId(prodId);
    }

    /**
     * 根据商品销售实体中的商品id更改 商品销售数量  添加
     *
     * @param productId
     * @param quantity
     * @return 本次该商品销售数量
     */
    public Integer updateProdSalesSaleCount(Integer productId, Integer quantity) {
        if(log.isInfoEnabled()){
            log.info(String.format("ProdSalesService的updateProdSalesSaleCount操作，参数productId:%s",productId.toString()));
        }
        if (quantity == null || quantity.equals(0)) {
            return 0;
        }
        ProdSales prodSales = findProdSalesByProdId(productId);
        if (prodSales == null) {
            log.error("操作商品销量表的时候根据产品ID没有找到产品记录,productId[{}]", productId);
            return 0;
        }
        prodSales.setSaleCount((prodSales.getSaleCount() == null ? 0 : prodSales.getSaleCount()) + quantity);
        prodSalesMapper.updateProdSalesSaleCount(prodSales);
        return 1;
    }
}
