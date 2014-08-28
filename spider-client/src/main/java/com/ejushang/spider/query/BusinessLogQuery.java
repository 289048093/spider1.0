package com.ejushang.spider.query;

import org.apache.commons.lang3.StringUtils;

/**
 * User: tin
 * Date: 14-1-16
 * Time: 下午2:13
 */
public class BusinessLogQuery extends BaseQuery {

    private String userName;
    private String searchType;
    private String searchValue;
    private String operationName;
    private String resourceName;
    private String createTimeStart;
    private String createTimeEnd;
    private String executionTimeStart;
    private String executionTimeEnd;
    private String createTimeCondition;
    private String executionTimeCondition;
    private String searchCondition;

    public String getSearchCondition() {

        return searchConditionUtil();
    }


    public String getExecutionTimeCondition() {

        return executionDateConditionUtil();
    }

    public void setCreateTimeCondition(String createTimeCondition) {
        this.createTimeCondition = createTimeCondition;
    }

    public void setExecutionTimeCondition(String executionTimeCondition) {
        this.executionTimeCondition = executionTimeCondition;
    }

    public String getCreateTimeCondition() {

        return createDateConditionUtil();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getExecutionTimeStart() {
        return executionTimeStart;
    }

    public void setExecutionTimeStart(String executionTimeStart) {
        this.executionTimeStart = executionTimeStart;
    }

    public String getExecutionTimeEnd() {
        return executionTimeEnd;
    }

    public void setExecutionTimeEnd(String executionTimeEnd) {
        this.executionTimeEnd = executionTimeEnd;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public String createDateConditionUtil() {
        StringBuilder stringBuilder = new StringBuilder();

        if (!StringUtils.isBlank(createTimeStart)) {
            stringBuilder.append(" and create_time >= '").append(createTimeStart).append("'");
        }
        if (!StringUtils.isBlank(createTimeEnd)) {
            stringBuilder.append(" and create_time <= '").append(createTimeEnd).append("'");
        }



        return stringBuilder.toString();
    }

    public String executionDateConditionUtil() {
        StringBuilder stringBuilder = new StringBuilder();

        if (!StringUtils.isBlank(executionTimeStart)) {
            stringBuilder.append(" and execution_time >= '").append(executionTimeStart).append("'");
        }
        if (!StringUtils.isBlank(executionTimeEnd)) {
            stringBuilder.append(" and execution_time <= '").append(executionTimeEnd).append("'");
        }

        return stringBuilder.toString();
    }

    public String getSearchType() {

        return searchType;
    }


    public void setSearchType(String searchType) {

        this.searchType = searchType;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String searchConditionUtil() {
        String result = null;
        if (searchType.equals("userName")) {
            result = "username";
        }
        if (searchType.equals("operationName")) {
            result = "operation_name";
        }
        if (searchType.equals("resourceName")) {
            result = "resource_name";
        }


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" and ").append(result).append(" like '").append("%").append(searchValue).append("%").append("'");

        return stringBuilder.toString();

    }

    @Override
    public String toString() {
        return "BusinessLogQuery{" +
                "userName='" + userName + '\'' +
                ", searchType='" + searchType + '\'' +
                ", searchValue='" + searchValue + '\'' +
                ", operationName='" + operationName + '\'' +
                ", resourceName='" + resourceName + '\'' +
                ", createTimeStart='" + createTimeStart + '\'' +
                ", createTimeEnd='" + createTimeEnd + '\'' +
                ", executionTimeStart='" + executionTimeStart + '\'' +
                ", executionTimeEnd='" + executionTimeEnd + '\'' +
                ", createTimeCondition='" + createTimeCondition + '\'' +
                ", executionTimeCondition='" + executionTimeCondition + '\'' +
                ", searchCondition='" + searchCondition + '\'' +
                '}';
    }
}
