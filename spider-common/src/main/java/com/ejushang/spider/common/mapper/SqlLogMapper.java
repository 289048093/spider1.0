
package com.ejushang.spider.common.mapper;

import com.ejushang.spider.domain.SqlLog;

import java.util.List;

/**
 * Sql日志mapper
 * User: liubin
 * Date: 13-12-13
 */
public interface SqlLogMapper {

	void save(SqlLog sqlLog);
  public List<SqlLog> findSqlLog();
    public List<SqlLog> findSqlLogByBusinessLogId(Integer businessLogId);

}

