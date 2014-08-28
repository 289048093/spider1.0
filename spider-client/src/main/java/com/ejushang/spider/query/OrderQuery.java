package com.ejushang.spider.query;

import com.ejushang.spider.constant.OrderStatus;
import com.ejushang.spider.constant.OrderType;
import com.ejushang.spider.util.Money;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Json.zhu
 * Date: 13-12-26
 * Time: 上午10:30
 * 普通javaBean，封装数据发送到前台
 */
public class OrderQuery extends BaseQuery {
    private Integer id;//订单ID
    private String dateType;//日期类型
    private String startDate;//开始日期
    private String endDate;//结束日期
    private String storageName;//库房
    private String orderStatus;//订单状态
    private String shippingComp;//快递
    private String conditionQuery;//右边条件
    private String conditionType;//右边条件类型
    private String conditionValue; //右边值
    private String conditionQuery2;//右边条件2
    private String conditionType2;//右边条件类型2
    private String conditionValue2; //右边值2
    private String orderType;//订单类型
    private Integer shopId;//店铺ID
    private Integer repoId;//仓库ID
    private String lastCondition;//最后的查询条件
    private String newCondition;  //新的查询条件
    private String dateCondition; //日期查询条件
    private Integer start = 0;    //数据下标
    private Integer limitNum = 100;   //限制条数

    public String getNewCondition() {
        return lastConditionUtil(conditionType2,conditionValue2,conditionQuery2);
    }

    public String getLastCondition() {

        return lastConditionUtil(conditionType,conditionValue,conditionQuery);
    }

    public String getDateCondition() {

        return dateConditionUtil();
    }
    /**
     * 日期类型查找
     *
     * @return 返回特定的字段
     */
    public String dateConditionQueryUtil() {
        String result = null;
        StringBuilder stringBuilder = null;
        if (StringUtils.isBlank(dateType)||dateType.equals("all")) {
            result = null;
        } else {
            stringBuilder = new StringBuilder();
            String cloumn = null;
            if (dateType.trim().equalsIgnoreCase("payDate")) {
                cloumn = "o.pay_time";
            }
            if (dateType.trim().equals("printDate")) {
                cloumn = "o.print_time";
            }
            if (dateType.trim().equals("deliveryDate")) {
                cloumn = "o.delivery_time";
            }
            if (dateType.trim().equals("orderDate")) {
                cloumn = "o.buy_time";
            }
            if (dateType.trim().equals("receiptDate")) {
                cloumn = "o.receipt_time";
            }
            stringBuilder.append(cloumn);
            result = stringBuilder.toString();
        }

        return result;
    }

    /**
     * 对日期进行条件判断
     *
     * @return 一个拼接的字符串
     */
    public String dateConditionUtil() {
        StringBuilder stringBuilder = new StringBuilder();
        if(!StringUtils.isBlank(startDate))
        {
            stringBuilder.append(" and ").append(dateConditionQueryUtil()).append(" >= '").append(startDate).append("'");
        }
        if(!StringUtils.isBlank(endDate))
        {
            stringBuilder.append(" and ").append(dateConditionQueryUtil()).append("  <= '").append(endDate).append("'");
        }


        return stringBuilder.toString();
    }
    /**
     * 对condition进行条件拼接
     *
     * @return String
     */
    public String lastConditionUtil(String conType,String conValue,String conQuery) {
        String result = null;
        StringBuilder stringBuilder = null;

        if (StringUtils.isBlank(conValue)) {
            result = null;
        } else {

            stringBuilder = new StringBuilder();
             if(conQuery.equals("totalFee"))
             {
                 conValue= Money.YuanToCent(conValue)+"";
             }
            if (conType.equals("=")) {
                stringBuilder.append(conditionQueryUtil(conQuery)).append(" = '").append(conValue).append("'");
            }
            if (conType.equals("!=")) {
                stringBuilder.append(conditionQueryUtil(conQuery)).append(" <> ").append(conValue);
            }
            if (conType.equals("<=")) {
                stringBuilder.append(conditionQueryUtil(conQuery)).append(" <= ").append(conValue);
            }
            if (conType.equals(">")) {
                stringBuilder.append(conditionQueryUtil(conQuery)).append(" > ").append(conValue);
            }
            if (conType.equals(">=")) {
                stringBuilder.append(conditionQueryUtil(conQuery)).append(" >= ").append(conValue);
            }
            if (conType.equals("<")) {
                stringBuilder.append(conditionQueryUtil(conQuery)).append(" < ").append(conValue);
            }
            if (conType.equals("!")) {
                stringBuilder.append(conditionQueryUtil(conQuery)).append(" not like '").append("%").append(conValue).append("%'");
            }

            if (conType.equals("has")) {
                stringBuilder.append(conditionQueryUtil(conQuery)).append(" like '").append("%").append(conValue).append("%'");
            }

            if (conType.equals("^")) {
                stringBuilder.append(conditionQueryUtil(conQuery)).append(" like '").append(conValue).append("%'");
            }
            if (conType.equals("$")) {
                stringBuilder.append(conditionQueryUtil(conQuery)).append(" like '").append("%").append(conValue).append("'");
            }
            result = stringBuilder.toString();
        }
        return result;

    }

    /**
     * 对ConditionQuery进行筛选
     *
     * @return
     */
    public String conditionQueryUtil(String conQuery) {
        String result = null;
        if (conQuery.equals("buyerId")) {
            result = "o.buyer_id";
        }
        if (conQuery.equals("receiverName")) {
            result = "o.receiver_name";
        }
        if (conQuery.equals("buyerMessage")) {
            result = "o.buyer_message";
        }
        if (conQuery.equals("remark")) {
            result = "o.remark";
        }
        if (conQuery.equals("orderNo")) {
            result = "o.order_no";
        }
        if (conQuery.equals("skuCode")) {
            result = "i.sku_code";
        }
        if (conQuery.equals("shippingNo")) {
            result = "o.shipping_no";
        }
        if (conQuery.equals("totalFee")) {
            result = "o.total_fee";
        }
        if (conQuery.equals("receiverMobile")) {
            result = "o.receiver_mobile";
        }
        if (conQuery.equals("prodName")) {
            result = "i.prod_name";
        }
        if (conQuery.equals("outOrderNo")) {
            result = "o.out_order_no";
        }
        if (conQuery.equals("prodCode")) {
            result = "i.prod_code";
        }
        return result;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNewCondition(String newCondition) {
        this.newCondition = newCondition;
    }

    public void setDateCondition(String dateCondition) {
        this.dateCondition = dateCondition;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public void setLastCondition(String lastCondition) {
        this.lastCondition = lastCondition;
    }

    public String getDateType() {
        return dateType;
    }


    public void setDateType(String dateType) {
        this.dateType = dateType;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }




    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }


    public String getConditionQuery() {
        return conditionQuery;
    }

    public void setConditionQuery(String conditionQuery) {
        this.conditionQuery = conditionQuery;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getConditionQuery2() {
        return conditionQuery2;
    }

    public void setConditionQuery2(String conditionQuery2) {
        this.conditionQuery2 = conditionQuery2;
    }

    public String getConditionType2() {
        return conditionType2;
    }

    public void setConditionType2(String conditionType2) {
        this.conditionType2 = conditionType2;
    }

    public String getConditionValue2() {
        return conditionValue2;
    }

    public void setConditionValue2(String conditionValue2) {
        this.conditionValue2 = conditionValue2;
    }

    public Integer getRepoId() {
        return repoId;
    }

    public void setRepoId(Integer repoId) {
        this.repoId = repoId;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public String getShippingComp() {
        return shippingComp;
    }

    public void setShippingComp(String shippingComp) {
        this.shippingComp = shippingComp;
    }

    @Override
    public String toString() {
        return "OrderQuery{" +
                "id='" + id + '\'' +
                ", dateType='" + dateType + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", storageName='" + storageName + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", shippingComp='" + shippingComp + '\'' +
                ", conditionQuery='" + conditionQuery + '\'' +
                ", conditionType='" + conditionType + '\'' +
                ", conditionValue='" + conditionValue + '\'' +
                ", conditionQuery2='" + conditionQuery2 + '\'' +
                ", conditionType2='" + conditionType2 + '\'' +
                ", conditionValue2='" + conditionValue2 + '\'' +
                ", orderType='" + orderType + '\'' +
                ", shopId=" + shopId +
                ", repoId=" + repoId +
                ", lastCondition='" + lastCondition + '\'' +
                ", newCondition='" + newCondition + '\'' +
                ", dateCondition='" + dateCondition + '\'' +
                ", start=" + start +
                ", limitNum=" + limitNum +
                '}';
    }
}
