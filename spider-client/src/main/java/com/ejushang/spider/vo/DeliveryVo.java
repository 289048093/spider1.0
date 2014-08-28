package com.ejushang.spider.vo;

import com.ejushang.spider.util.Page;

/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 上午10:46
 * 物流设计实体类  query实体类
 */


public class DeliveryVo extends Page {
    private Integer id;
    /**
     * 物流公司名
     */

    private String name;
    /**
     * 物流单号递增规律
     */

    private Integer law;
    /**
     * 物流信息打印的lodop代码
     */

    private String printHtml;
    /**
     * 物流设计图片
     */
    private String logisticsPicturePath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLaw() {
        return law;
    }

    public void setLaw(Integer law) {
        this.law = law;
    }

    public String getPrintHtml() {
        return printHtml;
    }

    public void setPrintHtml(String printHtml) {
        this.printHtml = printHtml;
    }

    public String getLogisticsPicturePath() {
        return logisticsPicturePath;
    }

    public void setLogisticsPicturePath(String logisticsPicturePath) {
        this.logisticsPicturePath = logisticsPicturePath;
    }


    @Override
    public String toString() {
        return "DeliveryQuery{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", law=" + law +
                ", printHtml='" + printHtml + '\'' +
                ", logisticsPicturePath='" + logisticsPicturePath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryVo delivery = (DeliveryVo) o;

        if (id != null ? !id.equals(delivery.id) : delivery.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (law != null ? law.hashCode() : 0);
        result = 31 * result + (printHtml != null ? printHtml.hashCode() : 0);
        result = 31 * result + (logisticsPicturePath != null ? logisticsPicturePath.hashCode() : 0);
        return result;
    }
}
