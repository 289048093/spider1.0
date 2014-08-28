package com.ejushang.spider.erp.service.log;

import com.ejushang.spider.erp.service.test.ErpTest;
import com.ejushang.spider.query.BusinessLogQuery;
import com.ejushang.spider.service.log.IBusinessLogService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * User: tin
 * Date: 14-1-16
 * Time: 下午12:56
 */
public class BusinessLogTest extends ErpTest {
       @Resource
       private IBusinessLogService businessLogService;
    @Test
    public void findBusinessLog(){
        BusinessLogQuery businessLogQuery=new BusinessLogQuery();

        businessLogQuery.setLimit(10);
        businessLogQuery.setPage(2);

        businessLogService.findBusinessLogByBusinessLog(businessLogQuery);

    }
    @Test
    public void findBusinessLog1(){
        BusinessLogQuery businessLogQuery=new BusinessLogQuery();

       businessLogService.findBusinessLog();

    }


}
