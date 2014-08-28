package com.ejushang.spider.vo;

import com.ejushang.spider.constant.OrderItemType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * User:moon
 * Date: 14-1-9
 * Time: 下午5:26
 */
public class QueryProdVo {


    private Integer autoId;

    /**
     * 商品编号
     */
    private String prodNo;

    /**
     * 商品名
     */
    private String prodName;

    /**
     * 商品条形码
     */
    private String skuCode;

    /**
     * 数量
     */
    private Integer prodCount;

    /**
     * 价格
     *
     * @return
     */
    private String prodPrice;

    /**
     * 应付总金额
     */
    private String totalFee;

    /**
     * 实付总金额
     */
    private String actualFee;

    /**
     * 类型
     *
     * @return
     */
    private String itemType;

    /**
     * 邮费
     * @return
     */
    private String postFee;

    public String getPostFee() {
        return postFee;
    }

    public void setPostFee(String postFee) {
        this.postFee = postFee;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public Integer getProdCount() {
        return prodCount;
    }

    public void setProdCount(Integer prodCount) {
        this.prodCount = prodCount;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getActualFee() {
        return actualFee;
    }

    public void setActualFee(String actualFee) {
        this.actualFee = actualFee;
    }

    @Override
    public String toString() {
        return "QueryProdVo={" +
                "skuCode = " + skuCode + "," +
                "prodCount = " + prodCount + "," +
                "itemType = " + itemType + "," +
                "}";
    }

    public Integer getAutoId() {
        return autoId;
    }

    public void setAutoId(Integer autoId) {
        this.autoId = autoId;
    }

}
