package com.ejushang.spider.query;

/**
 * User:moon
 * Date: 14-1-10
 * Time: 下午1:32
 */
public class ProdNameQuery {

    private String prodName;

    private  String type;

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ProdNameQuery{" +
                "prodName='" + prodName + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
