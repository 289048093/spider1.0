package com.ejushang.spider.service.log;

import com.ejushang.spider.domain.BusinessLog;
import com.ejushang.spider.domain.SqlLog;
import com.ejushang.spider.query.BusinessLogQuery;
import com.ejushang.spider.util.Page;

import java.util.List;

/**
 * User: liubin
 * Date: 14-1-13
 */
public interface IBusinessLogService {

    /**
     * 记录业务日志
     *
     * @param businessLog
     */
    public void createBusinessLog(BusinessLog businessLog);

    /**
     * 查询所有业务日志
     */
    public List<BusinessLog> findBusinessLog();

    /**
     * 通过对象查询业务日志
     * @return 返回BusinessLog类型的List集合
     */
    public Page findBusinessLogByBusinessLog(BusinessLogQuery businessLogQuery);

    public List<SqlLog> findSqlLogByBusinessLogId(Integer businessLogId);


}
