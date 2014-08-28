package com.ejushang.spider.erp.service.delivery;

import com.ejushang.spider.constant.DeliveryType;
import com.ejushang.spider.domain.LogisticsInfo;
import com.ejushang.spider.erp.service.test.ErpTest;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * create by Athens on 14-1-8
 */
public class LogisticsServiceTest extends ErpTest {

    @Autowired
    private LogisticsInfoService logisticsInfoService;

    @Test
    @Rollback(false)
    public void logisticsInfo() {
        String orderNo = "201401011234567890";
        String expressNo = "10231451";
        DeliveryType deliveryType = DeliveryType.ems;
        String sendTo = "北京";

        // 添加
        logisticsInfoService.sendLogisticsInfoRequest(orderNo, deliveryType, expressNo, sendTo);

        // 查询
        LogisticsInfo info = logisticsInfoService.findLogisticsInfoByExpressNo(expressNo);
        Assert.assertNotNull(info);

        info.setExpressInfo("{}");
        // 更新
        logisticsInfoService.updateLogisticsInfo(info);

        info = logisticsInfoService.findLogisticsInfoByExpressNo(expressNo);
        Assert.assertEquals("{}", info.getExpressInfo());
        Assert.assertEquals(false, info.getExpressStatus().booleanValue());

        info.setExpressInfo("{success}");
        info.setExpressStatus(true);
        logisticsInfoService.updateLogisticsInfo(info);

        info = logisticsInfoService.findLogisticsInfoByExpressNo(expressNo);
        Assert.assertEquals("{success}", info.getExpressInfo());
        Assert.assertEquals(true, info.getExpressStatus().booleanValue());

        //logisticsInfoService.saveLogisticsInfo(new LogisticsInfo(orderNo, expressNo, deliveryType, sendTo));

        logisticsInfoService.deleteLogisticsInfo(info.getId());
        info = logisticsInfoService.findLogisticsInfoByExpressNo(expressNo);
        Assert.assertNull(info);
    }

    @Test
    public void testExport() throws IllegalAccessException, IOException, InvocationTargetException {
        List<String> expressNos = new ArrayList<String>();
        expressNos.add("11111");
        expressNos.add("22222");
        expressNos.add("33333");
        expressNos.add("44444");
        expressNos.add("55555");
        File file = logisticsInfoService.exportLogisticsFullInfoBeanByExpressNos(expressNos);

        System.out.println(file.getAbsolutePath());
    }

}
