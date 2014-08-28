package com.ejushang.spider.query;

/**
 * User: Blomer
 * Date: 14-1-7
 * Time: 下午1:20
 */
public class GiftProdQuery extends BaseQuery{

    /**
     * id
     */
    private Integer id;
    /**
     * 销售商品id
     */
    private Integer sellProdId;

    /**
     * 赠品商品id
     */
    private Integer giftProdId;

    /**
     * 赠品商品数量
     */
    private Integer giftProdCount;
    /**
     * 是否在使用
     */
    private Boolean inUse;

    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 商品名称
     */
    private String prodName;
    /**
     * 商品编码
     */
    private String prodNo;

    /**
     * 搜索 类型
     */
    private String searchType;
    /**
     * 搜索 类型
     */
    private String searchValue;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSellProdId() {
        return sellProdId;
    }

    public void setSellProdId(Integer sellProdId) {
        this.sellProdId = sellProdId;
    }

    public Integer getGiftProdId() {
        return giftProdId;
    }

    public void setGiftProdId(Integer giftProdId) {
        this.giftProdId = giftProdId;
    }

    public Integer getGiftProdCount() {
        return giftProdCount;
    }

    public void setGiftProdCount(Integer giftProdCount) {
        this.giftProdCount = giftProdCount;
    }

    public Boolean getInUse() {
        return inUse;
    }

    public void setInUse(Boolean inUse) {
        this.inUse = inUse;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    @Override
    public String toString() {
        return "GiftProdQuery{" +
                "id=" + id +
                ", sellProdId=" + sellProdId +
                ", giftProdId=" + giftProdId +
                ", giftProdCount=" + giftProdCount +
                ", inUse=" + inUse +
                ", brandName='" + brandName + '\'' +
                ", prodName='" + prodName + '\'' +
                ", prodNo='" + prodNo + '\'' +
                ", searchType='" + searchType + '\'' +
                ", searchValue='" + searchValue + '\'' +
                '}';
    }
}
