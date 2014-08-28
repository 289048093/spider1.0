package com.ejushang.spider.service.log;

import com.ejushang.spider.common.mapper.BusinessLogMapper;
import com.ejushang.spider.common.mapper.SqlLogMapper;
import com.ejushang.spider.domain.BusinessLog;
import com.ejushang.spider.domain.SqlLog;
import com.ejushang.spider.query.BusinessLogQuery;
import com.ejushang.spider.util.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * User: liubin
 * Date: 14-1-13
 */
@Service("businessLogService")
public class BusinessLogService implements IBusinessLogService {

    @Resource
    BusinessLogMapper businessLogMapper;
    @Resource
    SqlLogMapper sqlLogMapper;

    @Transactional
    @Override
    public void createBusinessLog(BusinessLog businessLog) {
        businessLogMapper.save(businessLog);
        for (SqlLog sqlLog : businessLog.getSqlLogs()) {
            sqlLog.setBusinessLogId(businessLog.getId());
            sqlLogMapper.save(sqlLog);
        }
    }

    @Override
    public List<BusinessLog> findBusinessLog() {





        return businessLogMapper.findBusinessLog();

    }
    @Transactional(readOnly =true)
    @Override
    public Page findBusinessLogByBusinessLog(BusinessLogQuery businessLogQuery) {

        // 构造分页信息
        Page page = new Page();
        // 设置当前页
        page.setPageNo(businessLogQuery.getPage());
        // 设置分页大小
        page.setPageSize(businessLogQuery.getLimit());
        businessLogMapper.findBusinessLogByBusinessLog(businessLogQuery,page);

        return page;

    }
    @Transactional(readOnly =true)
    @Override
    public List<SqlLog> findSqlLogByBusinessLogId(Integer businessLogId) {
        return sqlLogMapper.findSqlLogByBusinessLogId(businessLogId);
    }

}
