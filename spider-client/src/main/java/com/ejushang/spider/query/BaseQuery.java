package com.ejushang.spider.query;

/**
 * User: Baron.Zhang
 * Date: 14-1-8
 * Time: 下午4:13
 */
public class BaseQuery {
    /** 分页：默认显示 15 条数据 */
    private static final int DEFAULT_PAGE_SIZE = 15;
    /** 分页：当前第几页 */
    private Integer page = 1;
    /** 分页：一页多少条 */
    private Integer limit = DEFAULT_PAGE_SIZE;

    private Integer start;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if(page != null && page > 0){
            this.page = page;
            this.start = (this.page - 1) * this.limit;
        }
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        if(limit != null && limit > 0){
            this.limit = limit;
            this.start = (this.page - 1) * this.limit;
        }
    }

    public Integer getStart() {
        return start;
    }


}
