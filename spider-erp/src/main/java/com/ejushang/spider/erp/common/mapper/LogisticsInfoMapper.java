package com.ejushang.spider.erp.common.mapper;

import com.ejushang.spider.domain.LogisticsInfo;

import java.util.List;
import java.util.Map;

/**
 * create by Athens on 14-1-8
 */
public interface LogisticsInfoMapper {

    /** write */
    int saveLogistics(LogisticsInfo logisticsInfo);

    /** find by example */
    List<LogisticsInfo> findLogisticsByNoes(LogisticsInfo logisticsInfo);

    /** find all not success compare with latestTime */
    List<Map<String, Object>> findNotSuccessLogisticsByLaTestTime(int hourCount);

    /** find all not success compare with firstTime */
    List<Map<String, Object>> findNotSuccessLogisticsByFirstTime(int hourCount);

    /** update */
    int updateLogisticsInfo(LogisticsInfo logisticsInfo);

    // ignore delete

    int deleteLogisticsInfo(int id);

    /**
     * 批量查询物流信息 create by Baron.Zhang on 14-03-05
     * @param expressNos
     * @return
     */
    List<LogisticsInfo> findLogisticsByExpressNos(List<String> expressNos);

}
