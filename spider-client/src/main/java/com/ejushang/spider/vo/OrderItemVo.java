package com.ejushang.spider.vo;

/**
 * User: tin
 * Date: 14-1-8
 * Time: 上午11:43
 */
public class OrderItemVo {
    /**
     * 订单详细ID
     */
  private Integer id;
    /**
     * 商品编码
     */
    private String prodNo;
    /**
     * 商品ID
     */
    private Integer prodId;
    /**
     * 商品名
     */
    private String prodName;
    /**
     * 商品条形码
     */
    private String skuCode;
    /**
     * 商品分类id
     */
    private Integer cid;
    /**
     * 商品分类名称
     */
    private String prodCateName ;
    /**
     * 品牌ID
     */
    private Integer brandId;
    /**
     * 品牌名称
     */
    private String  brandName;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 订单Id
     */
    private Integer orderId;
    /**
     * 商品数量
     */
    private Integer prodCount =0;
    /**
     * 库存数量
     */
    private Integer actuallyNumber =0;
    /**
     * 库存ID
     */
    private Integer storageId;
    /**
     * 单价
     */
    private  String  prodPrice;
    /**
     *应付总金额
     */
    private String  totalFee;
    /**
     * 实付总金额
     */
    private String   actualFee;
    /**
     * 订单价格描述
     */
    private String priceDescription;
    /**
     * 产品描述
     */


    private String description;

    /**
     * 外部订单编号
     */
    private String outOrderNo;
    /**
     * 订单项类型(商品, 套餐, 赠品...)
     */
    private String itemType;
    /**
     * 邮费
     */
    private String postFee;

    public String getPostFee() {
        return postFee;
    }

    public void setPostFee(String postFee) {
        this.postFee = postFee;
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

    public String getPriceDescription() {
        return priceDescription;
    }

    public void setPriceDescription(String priceDescription) {
        this.priceDescription = priceDescription;
    }

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }



    /**
     *订单项类型
     */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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



    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getProdCateName() {
        return prodCateName;
    }

    public void setProdCateName(String prodCateName) {
        this.prodCateName = prodCateName;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getProdCount() {
        return prodCount;
    }

    public void setProdCount(Integer prodCount) {
        this.prodCount = prodCount;
    }

    public Integer getActuallyNumber() {
        return actuallyNumber;
    }

    public void setActuallyNumber(Integer actuallyNumber) {
        this.actuallyNumber = actuallyNumber;
    }

    public Integer getStorageId() {
        return storageId;
    }

    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }



    public String getDescription() {
        return description;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "OrderItemVo{" +
                "id=" + id +
                ", prodNo='" + prodNo + '\'' +
                ", prodId=" + prodId +
                ", prodName='" + prodName + '\'' +
                ", skuCode='" + skuCode + '\'' +
                ", cid=" + cid +
                ", prodCateName='" + prodCateName + '\'' +
                ", brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", orderId=" + orderId +
                ", prodCount=" + prodCount +
                ", actuallyNumber=" + actuallyNumber +
                ", storageId=" + storageId +
                ", prodPrice='" + prodPrice + '\'' +
                ", totalFee='" + totalFee + '\'' +
                ", actualFee='" + actualFee + '\'' +
                ", priceDescription='" + priceDescription + '\'' +
                ", description='" + description + '\'' +
                ", outOrderNo='" + outOrderNo + '\'' +
                ", itemType='" + itemType + '\'' +
                ", postFee=" + postFee +
                '}';
    }
}
