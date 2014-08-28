
package com.ejushang.spider.common.mapper;

import com.ejushang.spider.domain.BusinessLog;
import com.ejushang.spider.domain.User;
import com.ejushang.spider.query.BusinessLogQuery;
import com.ejushang.spider.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 业务操作mapper
 * User: liubin
 * Date: 13-12-13
 */
public interface BusinessLogMapper {

	void save(BusinessLog businessLog);
    public List<BusinessLog> findBusinessLog();
    public List<BusinessLog> findBusinessLogByBusinessLog(@Param("businessLogQuery")BusinessLogQuery businessLogQuery,@Param("page")Page page);
    public Integer findBusinessLogCountByBusinessLog(BusinessLogQuery businessLogQuery);


}

