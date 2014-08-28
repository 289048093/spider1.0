package com.ejushang.spider.vo;

/**
 * User: tin
 * Date: 14-2-10
 * Time: 下午5:39
 */
public class RepositoryVo {
    /**
     * 仓库表id
     */
    private Integer id;
    /**
     * 仓库名
     */
    private String name;

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
        return "RepositoryVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
