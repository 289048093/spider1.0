package com.ejushang.spider.query;

/**
 * User:moon
 * Date: 14-1-10
 * Time: 下午1:31
 */
public class ProdNoQuery {

    private  String prodNo;

    private  String type;

    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ProdNoQuery{" +
                "prodNo='" + prodNo + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
