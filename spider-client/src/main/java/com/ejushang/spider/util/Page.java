package com.ejushang.spider.util;

import java.util.ArrayList;
import java.util.List;

/**
 * example:
 * <pre style="color:green;">
 * 前台请求第 1 页, 每页 15 行: /user/list 或 /user/list?pageNo=-1&pageSize=0
 * 前台请求第 2 页, 每页 15 行: /user/list?pageNo=2
 * 前台请求第 5 页, 每页 30 行: /user/list?pageNo=5&pageSize=30
 *
 * controller:
 *   <span style="color:blue;">艾特 RequestMapping(value = "/user/list")
 *   public void userList(Page&lt;User> userPage) throws IOException {
 *      userPage = userService.findUser(userPage);
 *      new JsonResult(true).addPageInfo(userPage).toJson(response);
 *   }</span>
 *
 * service:
 *   <span style="color:blue;">public Page&lt;User> findUser(Page&lt;User> userPage) {
 *     // SQL: select count(1) from user
 *     userPage.setTotalCount(userMapper.findUserCount());
 *     // SQL: select xxx from user limit #{start}, #{limit}
 *     userPage.setResult(userMapper.findUserPage(userPage.getStart(), userPage.getLimit()));
 *     return userPage;
 *   }</span>
 * </pre>
 *
 * create by Athens(刘杰) on 13-12-26
 */
public class Page<T> {

    /** 每页显示 15 条数据 */
    private static final int DEFAULT_PAGE_SIZE = 15;

    /** 页数, 默认显示第 1 页 */
    private int pageNo = 1;

    /** 每页显示条数 */
    private int pageSize = DEFAULT_PAGE_SIZE;


    /** 不包含分页查询的总条数 */
    private int totalCount;

    /** 分页数据 */
    private List<T> result = new ArrayList<T>();

    public Page() {}
    public Page(int pageNo, int pageSize) {
        setPageNo(pageNo);
        setPageSize(pageSize);
    }

    /** 总页数 */
    public int getTotalPage() {
        return (totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize + 1);
    }

    /** 数据库 select oo from xxx limit #start#, #limit# 中需要的 start 参数 */
    public int getStart() {
        return (pageNo -1) * pageSize;
    }

    /** 数据库 select oo from xxx limit #start#, #limit# 中需要的 limit 参数 */
    public int getLimit() {
        return pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo=pageNo;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = (totalCount < 0) ? 0 : totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = (pageSize < 1) ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

}
