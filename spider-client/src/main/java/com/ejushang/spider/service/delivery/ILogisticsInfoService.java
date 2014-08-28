package com.ejushang.spider.service.delivery;

import com.ejushang.spider.bean.TransferInfoBean;
import com.ejushang.spider.constant.DeliveryType;
import com.ejushang.spider.domain.LogisticsInfo;
import com.ejushang.spider.exception.ErpBusinessException;
import com.ejushang.spider.vo.LogisticsInfoVo;
import org.springframework.dao.DuplicateKeyException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * create by Athens on 14-1-8
 */
public interface ILogisticsInfoService {

    /**
     * 向 kuaidi100 发送物流配送信息请求
     *
     * @param orderNo 订单号
     * @param company 物流公司
     * @param expressNo 物流单号
     * @param to 收货地址(kuaidi100 要求必须提供. 到市一级即可. 如广东省深圳市, 北京市)
     *
     * @throws ErpBusinessException 已经向第三方请求过物流查询
     * @throws DuplicateKeyException 发生并发(两个人同时确认发送请求或网速太慢点击太快)时抛出的异常
     */
    void sendLogisticsInfoRequest(String orderNo, DeliveryType company,
                                  String expressNo, String to) throws ErpBusinessException, DuplicateKeyException;

    /**
     * 处理 kuaidi100 推送过来的物流数据
     *
     * @param logisticsMsg kuaidi100 推送的物流数据
     */
    void handleThirdLogisticsInfo(String logisticsMsg);

    /**
     * 使用订单号查询物流信息(若有发错的情况, 可能会导致一个订单出现多个物流单号的情况)
     *
     * @param orderNo 订单号
     * @return 物流信息
     */
    List<LogisticsInfo> findLogisticsInfoByOrderNo(String orderNo);

    /**
     * 查询指定订单指定物流单号的物流信息
     *
     * @param orderNo 订单号
     * @param expressNo 快递单号
     * @return 物流信息
     */
    LogisticsInfo findLogisticsInfoByNoAndCompany(String orderNo, String expressNo);

    /**
     * 使用物流单号查询物流信息.
     *
     * @param expressNo 快递单号
     * @return 物流信息
     */
    LogisticsInfo findLogisticsInfoByExpressNo(String expressNo);

    /**
     * 查询所有距离当前指定小时却还未签收的物流信息, 使用更新时间来比较
     *
     * @return 物流信息
     * @param hourCount 间隔时间, 单位: 小时
     */
    List<Map<String, Object>> findNotSuccessLogisticsByLaTestTime(int hourCount);

    /**
     * 查询所有距离当前时间指定小时却还未签收的物流信息, 使用第一条记录时间来比较
     *
     * @return 物流信息
     * @param hourCount 间隔时间, 单位: 小时
     */
    List<Map<String, Object>> findNotSuccessLogisticsByFirstTime(int hourCount);

    int saveLogisticsInfo(LogisticsInfo logisticsInfo) throws DuplicateKeyException;
    int updateLogisticsInfo(LogisticsInfo logisticsInfo);
    /** 请不要使用此方法! 此方法仅仅只是为了测试 */
    int deleteLogisticsInfo(int id);

    /**
     * 根据物流单号获得中转信息
     * create by Baron.Zhang on 14-03-05
     * @param expressNo
     * @return
     */
    List<TransferInfoBean> findTransferInfoByExpressNo(String expressNo);

    /**
     * 批量查询物流订单信息
     * create by Baron.Zhang on 14-03-05
     *
     * @param expressNos
     * @return
     */
    List<LogisticsInfoVo> findLogisticsInfoByExpressNos(List<String> expressNos) throws InvocationTargetException, IllegalAccessException;

    /**
     * 将物流中转信息生成Excel文件并导出
     *
     *
     * @param expressNos
     * @return
     */
    File exportLogisticsFullInfoBeanByExpressNos(List<String> expressNos) throws InvocationTargetException, IllegalAccessException, IOException;

}
