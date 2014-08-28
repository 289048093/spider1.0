package com.ejushang.spider.query;
/**
 * User:Abby
 * Date: 13-12-23
 * Time: 上午11:24
 * 产品分类    query实体类
 */
public class ProdCategoryQuery extends BaseQuery{
    private Integer id;
    /**
     * 分类名
     */
    private String name;
    /**
     * 增加时间
     */
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



    @Override
    public String toString() {
        return "ProdCategoryQuery{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
