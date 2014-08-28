package com.ejushang.spider.query;

/**
 * User: tin
 * Date: 14-1-14
 * Time: 下午5:31
 */
public class RepositoryQuery extends BaseQuery {



    /**
     * 仓库名
     */
    private String name;
    /**
     * 仓库ID
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        return "RepositoryQuery{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
