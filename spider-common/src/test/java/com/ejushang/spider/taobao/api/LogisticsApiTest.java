package com.ejushang.spider.taobao.api;

import com.ejushang.spider.constant.DeliveryType;
import com.ejushang.spider.taobao.helper.ConstantTaoBao;
import com.ejushang.spider.taobao.helper.ConvertUtils;
import com.ejushang.spider.taobao.helper.TaoBaoUtilsEjs;
import com.taobao.api.ApiException;
import com.taobao.api.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Baron.Zhang
 * Date: 13-12-17
 * Time: 下午4:48
 */
public class LogisticsApiTest {
    private LogisticsApi logisticsApi;
    private UserApi userApi;

    @Before
    public void init(){
        logisticsApi = new LogisticsApi(TaoBaoUtilsEjs.getSessionKey());
        userApi = new UserApi(TaoBaoUtilsEjs.getSessionKey());
    }

    /**
     * taobao.areas.get 查询地址区域<br/>
     * 查询标准地址区域代码信息 参考：http://www.stats.gov.cn/tjbz/xzqhdm/t20100623_402652267.htm<br/>
     */
    @Test
    public void testGetAreas() throws ApiException {
        String fields = "id,type,name,parent_id,zip";
        List<Area> areas = logisticsApi.getAreas(fields);
        for (Area area : areas){
            if(area.getName().contains("新化县"))
            System.out.println(area.getId()+","+area.getType()+","+area.getName()+","+area.getParentId()+","+area.getZip());
        }
    }

    /**
     * taobao.delivery.template.add 新增运费模板<br/>
     * @throws Exception
     */
    @Test
    public void testAddDeliveryTemplate() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        argsMap.put(ConstantTaoBao.NAME,"按件运费模板_EJS_9527");
        argsMap.put(ConstantTaoBao.ASSUMER,0L);
        argsMap.put(ConstantTaoBao.VALUATION,0L);
        argsMap.put(ConstantTaoBao.TEMPLATE_TYPES,"express;post;ems");
        argsMap.put(ConstantTaoBao.TEMPLATE_DESTS,"1,110000;1,110000|310000;1|320000,310000");
        argsMap.put(ConstantTaoBao.TEMPLATE_START_STANDARDS,"1,1;1,1;1,2");
        argsMap.put(ConstantTaoBao.TEMPLATE_START_FEES,"10.10,10.10;10,11;8,9");
        argsMap.put(ConstantTaoBao.TEMPLATE_ADD_STANDARDS,"3,3;1,3;1,1");
        argsMap.put(ConstantTaoBao.TEMPLATE_ADD_FEES,"10,10;4,4;3,1");
        argsMap.put(ConstantTaoBao.CONSIGN_AREA_ID,440306L);
        DeliveryTemplate deliveryTemplate = logisticsApi.addDeliveryTemplate(argsMap);
        System.out.println("");
    }

    /**
     * taobao.delivery.template.delete 删除运费模板<br/>
     * 根据用户指定的模板ID删除指定的模板<br/>
     */
    @Test
    public void testDeleteDeliveryTemplate() throws ApiException {
        Long templateId = 900860480L;
        Boolean complete = logisticsApi.deleteDeliveryTemplate(templateId);
        System.out.println("complete："+complete);
    }

    /**
     * taobao.delivery.template.get 获取用户指定运费模板信息<br/>
     * 获取用户指定运费模板信息<br/>
     * @throws Exception
     */
    @Test
    public void testGetDeliveryTemplate() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        argsMap.put(ConstantTaoBao.TEMPLATE_IDS,"900860410,497470030");
        String fields = "template_id,template_name,created,modified,supports,assumer,valuation,query_express,query_ems," +
                "query_cod,query_post,address,consign_area_id,query_furniture,query_bzsd,query_wlb";
        argsMap.put(ConstantTaoBao.FIELDS,fields);
        Map<String,Object> deliveryTemplateMap = logisticsApi.getDeliveryTemplate(argsMap);
        List<DeliveryTemplate> deliveryTemplates = (List<DeliveryTemplate>) deliveryTemplateMap.get(ConstantTaoBao.DELIVERY_TEMPLATES);
        for (DeliveryTemplate deliveryTemplate : deliveryTemplates){
            System.out.println("运费模板名：" + deliveryTemplate.getName());
        }
        System.out.println("");
    }

    /**
     * taobao.delivery.template.update 修改运费模板<br/>
     * @throws Exception
     */
    @Test
    public void testUpdateDeliveryTemplate() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        argsMap.put(ConstantTaoBao.TEMPLATE_ID,900860410L);
        argsMap.put(ConstantTaoBao.NAME,"按件运费模板_EJS_9527");
        argsMap.put(ConstantTaoBao.ASSUMER,0L);
        argsMap.put(ConstantTaoBao.TEMPLATE_TYPES,"express;post;ems");
        argsMap.put(ConstantTaoBao.TEMPLATE_DESTS,"1,110000;1,110000|310000;1|320000,310000");
        argsMap.put(ConstantTaoBao.TEMPLATE_START_STANDARDS,"1,1;1,1;1,2");
        argsMap.put(ConstantTaoBao.TEMPLATE_START_FEES,"10.10,10.10;10,11;8,9");
        argsMap.put(ConstantTaoBao.TEMPLATE_ADD_STANDARDS,"3,3;1,3;1,1");
        argsMap.put(ConstantTaoBao.TEMPLATE_ADD_FEES,"10,10;4,4;3,1");

        Boolean complete = logisticsApi.updateDeliveryTemplate(argsMap);

        System.out.println("complete : " + complete );
    }

    /**
     * taobao.delivery.templates.get 获取用户下所有模板<br/>
     * 根据用户ID获取用户下所有模板<br/>
     */
    @Test
    public void testGetDeliveryTemplates() throws ApiException {
        String fields = "template_id,template_name,created,modified,supports,assumer,valuation,query_express,query_ems," +
                "query_cod,query_post";

        Map<String,Object> deliveryTemplatesMap = logisticsApi.getDeliveryTemplates(fields);
        List<DeliveryTemplate> deliveryTemplates = (List<DeliveryTemplate>) deliveryTemplatesMap.get(ConstantTaoBao.DELIVERY_TEMPLATES);
        for (DeliveryTemplate deliveryTemplate : deliveryTemplates){
            System.out.println("运费模板名：" + deliveryTemplate.getName());
        }
        System.out.println("");
    }

    /**
     * taobao.logistics.address.add 卖家地址库新增接口<br/>
     * 通过此接口新增卖家地址库,卖家最多可添加5条地址库,新增第一条卖家地址，将会自动设为默认地址库<br/>
     * @throws Exception
     */
    @Test
    public void testAddLogisticsAddress() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        argsMap.put(ConstantTaoBao.CONTACT_NAME,"张某某");
        argsMap.put(ConstantTaoBao.PROVINCE,"广东省");
        argsMap.put(ConstantTaoBao.CITY,"深圳市");
        argsMap.put(ConstantTaoBao.COUNTRY,"宝安区");
        argsMap.put(ConstantTaoBao.ADDR,"88路88号");
        argsMap.put(ConstantTaoBao.ZIP_CODE,"518100");
        argsMap.put(ConstantTaoBao.MOBILE_PHONE,"13500000000");
        argsMap.put(ConstantTaoBao.SELLER_COMPANY,"XX公司");
        argsMap.put(ConstantTaoBao.MEMO,"备注123123123");
        argsMap.put(ConstantTaoBao.GET_DEF,true);
        argsMap.put(ConstantTaoBao.CANCEL_DEF,true);

        AddressResult addressResult = logisticsApi.addLogisticsAddress(argsMap);

        System.out.println("");
    }

    /**
     * taobao.logistics.address.modify 卖家地址库修改<br/>
     * @throws Exception
     */
    @Test
    public void testModifyLogisticsAddress() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        // 108984004
        argsMap.put(ConstantTaoBao.CONTACT_ID,108984004L);
        argsMap.put(ConstantTaoBao.CONTACT_NAME,"张某某");
        argsMap.put(ConstantTaoBao.PROVINCE,"广东省");
        argsMap.put(ConstantTaoBao.CITY,"深圳市");
        argsMap.put(ConstantTaoBao.COUNTRY,"宝安区");
        argsMap.put(ConstantTaoBao.ADDR,"88路88号");
        argsMap.put(ConstantTaoBao.ZIP_CODE,"518100");
        argsMap.put(ConstantTaoBao.MOBILE_PHONE,"13588887777");
        argsMap.put(ConstantTaoBao.SELLER_COMPANY,"XX公司");
        argsMap.put(ConstantTaoBao.MEMO,"备注123123123");
        argsMap.put(ConstantTaoBao.GET_DEF,true);
        argsMap.put(ConstantTaoBao.CANCEL_DEF,true);

        AddressResult addressResult = logisticsApi.modifyLogisticsAddress(argsMap);

        System.out.println("");
    }

    /**
     * taobao.logistics.address.remove 删除卖家地址库
     */
    @Test
    public void testRemoveLogisticsAddress() throws ApiException {
        Long contactId = 108984003L;
        AddressResult addressResult = logisticsApi.removeLogisticsAddress(contactId);

        System.out.println("");
    }

    /**
     * taobao.logistics.address.search 查询卖家地址库<br/>
     */
    @Test
    public void testSearchLogisticsAddress() throws ApiException {
        String rdef = "";
        List<AddressResult> addressResults = logisticsApi.searchLogisticsAddress(rdef);
        if(addressResults != null){
            System.out.println("size:" + addressResults.size());
        }
    }

    /**
     * taobao.logistics.companies.get 查询物流公司信息<br/>
     * 查询淘宝网合作的物流公司信息，用于发货接口。<br/>
     * @throws Exception
     */
    @Test
    public void testGetLogisticsCompanies() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        argsMap.put(ConstantTaoBao.FIELDS,"id,code,name,reg_mail_no");
        argsMap.put(ConstantTaoBao.IS_RECOMMENDED,true);
        argsMap.put(ConstantTaoBao.ORDER_MODE,"all");

        List<LogisticsCompany> logisticsCompanies = logisticsApi.getLogisticsCompanies(argsMap);

        if(logisticsCompanies != null){
            for (LogisticsCompany logisticsCompany : logisticsCompanies){
                System.out.print("物流公司ID:" + logisticsCompany.getId());
                System.out.print(";物流公司代码：" + logisticsCompany.getCode());
                System.out.print(";物流公司简称：" + logisticsCompany.getName());
                System.out.println(";物流公司验证运单正则表达式：" + logisticsCompany.getRegMailNo());
            }
        }
    }

    /**
     * 未通过<br/>
     * {"error_response":{"code":15,"msg":"Remote service error","sub_code":"isp.top-mapping-parse-error"}}<br/>
     * 参数转换出错，主要是由于传入参数格式不对;<br/>
     * taobao.logistics.consign.order.createandsend 创建订单并发货<br/>
     * 创建物流订单，并发货。<br/>
     * @throws Exception
     */
    @Test
    public void testCreateandsendLogisticsConsignOrder() throws Exception {

        User user = userApi.getSeller("user_id");

        if(user == null){
            throw new Exception("获取淘宝卖家用户id失败！");
        }

        // 参数信息
        Map<String,Object> argsMap = new HashMap<String, Object>();
        // 订单来源，值选择：30
        argsMap.put(ConstantTaoBao.USER_ID,user.getUserId()); // 是卖家ID，还是买家ID？目前获取不到买家ID。
        // 订单来源
        argsMap.put(ConstantTaoBao.ORDER_SOURCE,30L);
        // 订单类型，值固定选择：30
        argsMap.put(ConstantTaoBao.ORDER_TYPE,30L);
        // 物流订单物流类型，值固定选择：2
        argsMap.put(ConstantTaoBao.LOGIS_TYPE,2L);
        // 物流公司ID
        argsMap.put(ConstantTaoBao.COMPANY_ID, DeliveryType.yuantong.getTmallId());
        // 交易流水号，淘外订单号或者商家内部交易流水号
        argsMap.put(ConstantTaoBao.TRADE_ID,192328579673574L);
        // 运单号
        argsMap.put(ConstantTaoBao.MAIL_NO,"0375223288");
        // 费用承担方式 1买家承担运费 2卖家承担运费
        argsMap.put(ConstantTaoBao.SHIPPING,"1");
        // 发件人名称
        argsMap.put(ConstantTaoBao.S_NAME,"戴德惠州");
        argsMap.put(ConstantTaoBao.S_AREA_ID,440306L);
        argsMap.put(ConstantTaoBao.S_ADDRESS,"88888路8888号");
        argsMap.put(ConstantTaoBao.S_ZIP_CODE,"518100");
        argsMap.put(ConstantTaoBao.S_MOBILE_PHONE,"15800000000");
        argsMap.put(ConstantTaoBao.S_TELEPHONE,"0755-00000000");
        argsMap.put(ConstantTaoBao.R_NAME,"张某某");
        argsMap.put(ConstantTaoBao.R_AREA_ID,440306L);
        argsMap.put(ConstantTaoBao.R_ADDRESS,"99999路9999号");
        argsMap.put(ConstantTaoBao.R_ZIP_CODE,"518100");
        argsMap.put(ConstantTaoBao.R_MOBILE_PHONE,"15899999999");
        argsMap.put(ConstantTaoBao.R_TELEPHONE,"0755-99999999");
        String itemJsonString = "[{\"itemName\":\"ssss\",\"singlePrice\":10,\"itemCount\":12},{\"itemName\":\"xxxxx\",\"singlePrice\":10,\"itemCount\":12}]";
        argsMap.put(ConstantTaoBao.ITEM_JSON_STRING,itemJsonString);
        argsMap.put(ConstantTaoBao.R_PROV_NAME,"广东省");
        argsMap.put(ConstantTaoBao.R_CITY_NAME,"深圳市");
        argsMap.put(ConstantTaoBao.R_DIST_NAME,"宝安区");
        argsMap.put(ConstantTaoBao.S_PROV_NAME,"广东省");
        argsMap.put(ConstantTaoBao.S_CITY_NAME,"深圳市");
        argsMap.put(ConstantTaoBao.S_DIST_NAME,"宝安区");

        Map<String,Object> resultsMap = logisticsApi.createandsendLogisticsConsignOrder(argsMap);

        String resultDesc = (String) resultsMap.get(ConstantTaoBao.RESULT_DESC);
        Long orderId = (Long) resultsMap.get(ConstantTaoBao.ORDER_ID);
        Boolean isSuccess = (Boolean) resultsMap.get(ConstantTaoBao.IS_SUCCESS);

        if(resultDesc != null && "".equals(resultDesc)){
            System.out.println("结果：" + resultDesc);
        }

        if(orderId != null){
            System.out.println("订单ID：" + orderId);
        }
    }

    /**
     * 未通过<br/>
     * {"error_response":{"code":15,"msg":"Remote service error",
     * "sub_code":"isv.logistics-update-company-or-mailno-error:P08","sub_msg":"该订单不支持修改"}}<br/>
     * taobao.logistics.consign.resend 修改物流公司和运单号<br/>
     * 支持卖家发货后修改物流公司和运单号。<br/>
     * 支持订单类型支持在线下单和自己联系。<br/>
     * 自己联系只能切换为自己联系的公司，在线下单也只能切换为在线下单的物流公司。<br/>
     * 调用时订单状态是卖家已发货，自己联系在发货后24小时内在线下单未揽收成功才可使用<br/>
     * @throws Exception
     */
    @Test
    public void testResendLogisticsConsign() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        argsMap.put(ConstantTaoBao.TID,192322676196313L);
        argsMap.put(ConstantTaoBao.SUB_TID,null);
        argsMap.put(ConstantTaoBao.IS_SPLIT,null);
        argsMap.put(ConstantTaoBao.OUT_SID,"988111111111");
        argsMap.put(ConstantTaoBao.COMPANY_CODE,"ZTO");
        argsMap.put(ConstantTaoBao.FEATURE,null);
        argsMap.put(ConstantTaoBao.SELLER_IP,null);
        Shipping shipping = logisticsApi.resendLogisticsConsign(argsMap);

        if(shipping != null){
            System.out.println("是否成功：" + shipping.getIsSuccess());
        }
    }

    /**
     * taobao.logistics.dummy.send 无需物流（虚拟）发货处理<br/>
     * 用户调用该接口可实现无需物流（虚拟）发货,使用该接口发货，交易订单状态会直接变成卖家已发货<br/>
     * @throws Exception
     */
    @Test
    public void testSendLogisticsDummy() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        argsMap.put(ConstantTaoBao.TID,192322676436313L);
        argsMap.put(ConstantTaoBao.FEATURE,null);
        argsMap.put(ConstantTaoBao.SELLER_IP,null);
        Shipping shipping = logisticsApi.sendLogisticsDummy(argsMap);

        if(shipping != null){
            System.out.println("是否成功：" + shipping.getIsSuccess());
        }
    }

    /**
     * taobao.logistics.offline.send 自己联系物流（线下物流）发货<br/>
     * 用户调用该接口可实现自己联系发货（线下物流），使用该接口发货，交易订单状态会直接变成卖家已发货。<br/>
     * 不支持货到付款、在线下单类型的订单。<br/>
     * @throws Exception
     */
    @Test
    public void testSendLogisticsOffline() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        argsMap.put(ConstantTaoBao.TID,192322676476313L);
        argsMap.put(ConstantTaoBao.SUB_TID,null);
        argsMap.put(ConstantTaoBao.IS_SPLIT,null);
        argsMap.put(ConstantTaoBao.OUT_SID,"988111111111");
        argsMap.put(ConstantTaoBao.COMPANY_CODE,"ZTO");
        argsMap.put(ConstantTaoBao.SENDER_ID,null);
        argsMap.put(ConstantTaoBao.CANCEL_ID,null);
        argsMap.put(ConstantTaoBao.FEATURE,null);
        argsMap.put(ConstantTaoBao.SELLER_IP,null);

        Shipping shipping = logisticsApi.sendLogisticsOffline(argsMap);

        if(shipping != null){
            System.out.println("是否成功：" + shipping.getIsSuccess());
        }
    }

    /**
     * taobao.logistics.online.cancel 取消物流订单接口<br/>
     * 调此接口取消发货的订单，重新选择物流公司发货。前提是物流公司未揽收货物。<br/>
     * 对未发货和已经被物流公司揽收的物流订单，是不能取消的。<br/>
     */
    @Test
    public void testCancelLogisticsOnline() throws ApiException {
        Long tid = 192328579673574L;

        Map<String,Object> resultMap = logisticsApi.cancelLogisticsOnline(tid);

        Boolean isSuccess = (Boolean) resultMap.get(ConstantTaoBao.IS_SUCCESS);
        Date modifyTime = (Date) resultMap.get(ConstantTaoBao.MODIFY_TIME);
        Long recreatedOrderId = (Long) resultMap.get(ConstantTaoBao.RECREATED_ORDER_ID);

        if(isSuccess != null){
            System.out.println("是否成功：" + isSuccess);
            if(recreatedOrderId != null){
                System.out.println("重新创建的物流订单编号：" + recreatedOrderId);
            }
        }
    }

    /**
     * taobao.logistics.online.confirm 确认发货通知接口<br/>
     * 确认发货的目的是让交易流程继承走下去，确认发货后交易状态会由【买家已付款】变为【卖家已发货】，
     * 然后买家才可以确认收货，货款打入卖家账号。货到付款的订单除外<br/>
     */
    @Test
    public void testConfirmLogisticsOnline() throws Exception {
        User user = userApi.getSeller("user_id");
        if(user == null){
            throw new Exception("获取当前淘宝用户失败");
        }


        Map<String,Object> argsMap = new HashMap<String, Object>();
        // 交易id
        argsMap.put(ConstantTaoBao.TID,192328579673574L);
        //argsMap.put(ConstantTaoBao.IS_SPLIT,0L);
        //argsMap.put(ConstantTaoBao.SUB_TID,"192328579683574,192328579693574");
        argsMap.put(ConstantTaoBao.OUT_SID,"0837546378");

        Shipping shipping = logisticsApi.confirmLogisticsOnline(argsMap);

        if(shipping == null){
            throw new Exception("确认发货失败，淘宝API调用异常！");
        }

        if(shipping.getIsSuccess()){
            System.out.println("确认发货成功！");
        }
    }

    /**
     * taobao.logistics.online.send 在线订单发货处理（支持货到付款）<br/>
     * 用户调用该接口可实现在线订单发货（支持货到付款） 调用该接口实现在线下单发货，有两种情况：<br/>
     * 如果不输入运单号的情况：交易状态不会改变，需要调用taobao.logistics.online.confirm确认发货后交易状态才会变成卖家已发货。<br/>
     * 如果输入运单号的情况发货：交易订单状态会直接变成卖家已发货 。<br/>
     */
    @Test
    public void sendLogisticsOnline() throws Exception {
        Map<String,Object> argsMap = new HashMap<String, Object>();
        // 淘宝交易ID
        argsMap.put(ConstantTaoBao.TID,192330008623574L);
        // 拆单子订单列表，对应的数据是：子订单号的列表。可以不传，但是如果传了则必须符合传递的规则。子订单必须是操作的物流订单的子订单的真子集！
        // argsMap.put(ConstantTaoBao.SUB_TID,"192328579683574,192328579693574");
        //argsMap.put(ConstantTaoBao.SUB_TID,"192328579703574,192328579713574,192328579723574,192328579733574,192328579743574,192328579753574");
        // 表明是否是拆单，默认值0，1表示拆单
        //argsMap.put(ConstantTaoBao.IS_SPLIT,1L);
        // 运单号.具体一个物流公司的真实运单号码。淘宝官方物流会校验，请谨慎传入；
        argsMap.put(ConstantTaoBao.OUT_SID,"0375223245");
        // 物流公司代码.如"POST"就代表中国邮政,"ZJS"就代表宅急送.调用 taobao.logistics.companies.get 获取。
        // 如果是货到付款订单，选择的物流公司必须支持货到付款发货方式
        argsMap.put(ConstantTaoBao.COMPANY_CODE,DeliveryType.yuantong.getTmallCode());

        Shipping shipping = logisticsApi.sendLogisticsOnline(argsMap);

        System.out.println(shipping);

//        if(shipping == null){
//            throw new Exception("在线订单发货处理失败！");
//        }
//
//        if(shipping.getIsSuccess()){
//            System.out.println("在线订单发货处理成功");
//        }
    }


}
