package com.ejushang.spider.erp.util;

import com.ejushang.spider.domain.Delivery;
import com.ejushang.spider.erp.service.order.numGenerator.AutoIncreaseLogisticsNumGenerator;
import com.ejushang.spider.erp.service.order.numGenerator.EMSLogisticsNumGenerator;
import com.ejushang.spider.erp.service.order.numGenerator.SFLogisticsNumGenerator;
import com.ejushang.spider.exception.GenerateException;

import java.util.List;
import java.util.regex.Pattern;

/**获取批量物流单号帮助类
 * User: tin
 * Date: 14-2-10
 * Time: 上午10:17
 */
public class NumGeneratorUtil {
    private NumGeneratorUtil() {
    }

    /**
     * 获取物流单号
     * @param delivery
     * @param intNo
     * @param orderNos
     * @return
     * @throws com.ejushang.spider.exception.GenerateException
     */
    public static List<String> getShippingNums(Delivery delivery, String intNo, int orderNos) throws GenerateException {
        List<String> numList = null;
        if (delivery.getName().equals("shunfeng")) {
            SFLogisticsNumGenerator sfLogisticsNumGenerator = new SFLogisticsNumGenerator();
            numList = sfLogisticsNumGenerator.generateNumList(intNo, delivery.getLaw(), orderNos);
        } else if (delivery.getName().equals("ems")) {
            EMSLogisticsNumGenerator emsLogisticsNumGenerator = new EMSLogisticsNumGenerator();
            numList = emsLogisticsNumGenerator.generateNumList(intNo, delivery.getLaw(), orderNos);
        } else {
            AutoIncreaseLogisticsNumGenerator autoIncreaseLogisticsNumGenerator = new AutoIncreaseLogisticsNumGenerator();
            numList = autoIncreaseLogisticsNumGenerator.generateNumList(intNo, delivery.getLaw(), orderNos);
        }
        return numList;
    }

    public static boolean isNumericByAscii(String str){
        for(int i=str.length();--i>=0;){
            int chr=str.charAt(i);
            if(chr<48 || chr>57)
                return false;
        }
        return true;
    }
    public static boolean isNumericByJava(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
    public static boolean isNumericByPattern(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
