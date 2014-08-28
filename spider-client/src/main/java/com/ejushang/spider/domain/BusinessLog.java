package com.ejushang.spider.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 业务日志
 * User: liubin
 * Date: 14-1-13
 */
public class BusinessLog {


    private Integer id;
    /**
     * ip地址
     */
    private String ip;
    /**
     * 请求参数
     */
    private String params;
    /**
     * 请求的url
     */
    private String requestUrl;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 请求执行时间,单位毫秒
     */
    private Long executionTime;
    /**
     * 请求的用户ID
     */
    private Integer userId;
    /**
     * 请求的用户名称
     */
    private String userName;
    /**
     * 操作ID
     */
    private Integer operationId;
    /**
     * 操作名称
     */
    private String operationName;
    /**
     * 模块名称
     */
    private String resourceName;
    /**
     * 业务操作所执行的所有sql
     */
    private List<SqlLog> sqlLogs = new ArrayList<SqlLog>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }


    public List<SqlLog> getSqlLogs() {
        return sqlLogs;
    }

    public void setSqlLogs(List<SqlLog> sqlLogs) {
        this.sqlLogs = sqlLogs;
    }

    @Override
    public String toString() {
        return "BusinessLog{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", params='" + params + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", createTime=" + createTime +
                ", executionTime=" + executionTime +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", operationId=" + operationId +
                ", operationName='" + operationName + '\'' +
                ", resourceName='" + resourceName + '\'' +
                ", sqlLogs=" + sqlLogs +
                '}';
    }
}
