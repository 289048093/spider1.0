package com.ejushang.spider.domain;

import java.util.Date;

/**
 * SQL日志
 * User: liubin
 * Date: 14-1-13
 */
public class SqlLog {


    private Integer id;
    /**
     * 业务日志ID
     */
    private Integer businessLogId;
    /**
     * SQL内容
     */
    private String content;
    /**
     * mybatis sql mapper id
     */
    private String sqlMapper;
    /**
     * SQL类型:select/insert/update/delete
     */
    private String operationType;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * SQL执行时间,单位毫秒
     */
    private Long executionTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessLogId() {
        return businessLogId;
    }

    public void setBusinessLogId(Integer businessLogId) {
        this.businessLogId = businessLogId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSqlMapper() {
        return sqlMapper;
    }

    public void setSqlMapper(String sqlMapper) {
        this.sqlMapper = sqlMapper;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
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
}
