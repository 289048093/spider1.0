package com.ejushang.spider.vo;

import java.util.Date;

/**
 * User:Abby
 * Date: 13-12-23
 * Time: 上午11:24
 * 产品分类    query实体类
 */
public class ProdCategoryVo {
    private Integer id;
    /**
     * 分类名
     */
    private String name;
    /**
     * 增加时间
     */
    private Date createTime;

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


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "ProdCategoryVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
