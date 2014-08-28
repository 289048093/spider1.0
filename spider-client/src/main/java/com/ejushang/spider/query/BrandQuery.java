package com.ejushang.spider.query;

import java.util.Date;

/**
 * User:龙清华
 * Date: 13-12-23
 * Time: 上午11:17
 * 品牌管理 query实体类
 */
public class BrandQuery extends BaseQuery {

    private Integer id;
    /**
     * 品牌名
     */
    private String name;
    /**
     * 品牌描述
     */
    private String description;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "BrandQuery{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


}
