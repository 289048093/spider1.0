package com.ejushang.spider.erp.util;

import com.ejushang.spider.erp.service.test.ErpTest;
import org.junit.Test;

/**
 * User:Amos.zhou
 * Date: 13-12-25
 * Time: 上午11:38
 */

public class TestOrderGenerator extends ErpTest {


    @Test
    public void testGetOrderNo(){
        SequenceGenerator og = SequenceGenerator.getInstance();
        System.out.println(og.getNextOrderNo());
        System.out.println(og.getNextOrderNo());
    }


}

