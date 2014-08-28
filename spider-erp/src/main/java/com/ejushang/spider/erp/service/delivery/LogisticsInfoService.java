package com.ejushang.spider.erp.service.delivery;

import com.ejushang.spider.bean.LogisticsFullInfoBean;
import com.ejushang.spider.bean.TransferInfoBean;
import com.ejushang.spider.constant.DeliveryType;
import com.ejushang.spider.domain.LogisticsInfo;
import com.ejushang.spider.erp.common.mapper.LogisticsInfoMapper;
import com.ejushang.spider.exception.ErpBusinessException;
import com.ejushang.spider.service.delivery.ILogisticsInfoService;
import com.ejushang.spider.util.DateUtils;
import com.ejushang.spider.util.JsonUtil;
import com.ejushang.spider.util.WebUtil;
import com.ejushang.spider.vo.LogisticsInfoVo;
import com.ejushang.spider.vo.logistics.BackMsg;
import com.ejushang.spider.vo.logistics.BackResult;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 物流信息
 * create by Athens on 14-1-8
 */
@Service
public class LogisticsInfoService implements ILogisticsInfoService {

    private static final Logger LOG = LoggerFactory.getLogger("logisticsLog");

    @Autowired
    private LogisticsInfoMapper logisticsInfoMapper;

    @Autowired
    private RequestLogisticsInfo requestLogisticsInfo;

    @Override
    @Transactional
    public void sendLogisticsInfoRequest(String orderNo, DeliveryType company,
                                 String expressNo, String to) throws ErpBusinessException, DuplicateKeyException {
        LogisticsInfo logisticsInfo = findLogisticsInfoByExpressNo(expressNo);
        if (logisticsInfo != null && logisticsInfo.getWasRequest()) {
            throw new ErpBusinessException(String.format("快递单(%s/%s)已经向第三方发送过请求, 不需要再发送.", company.toDesc(), expressNo));
        }

        // 保存, 物流单号的唯一约束, 若并发请求时, 会抛出 DuplicateKeyException 数据库异常
        saveLogisticsInfo(new LogisticsInfo(orderNo, expressNo, company.toDesc(), to));

        // 向第三方物流请求成功则写入数据库
        if (requestLogisticsInfo.requestThirdLogistics(company.toString(), expressNo, to)) {
            if (LOG.isInfoEnabled())
                LOG.info(String.format("快递单(%s/%s)向第三方物流请求成功.", company.toDesc(), expressNo));

            updateLogisticsInfo(new LogisticsInfo(expressNo, true));
        }
    }

    @Override
    public void handleThirdLogisticsInfo(String logisticsMsg) {
        if (StringUtils.isBlank(logisticsMsg)) return;

        // 将返回的数据转换为 对象
        BackMsg backMsg = null;
        try {
            backMsg = JsonUtil.json2Object(logisticsMsg, BackMsg.class);
        } catch (Exception e) {
            if (LOG.isWarnEnabled())
                LOG.warn(String.format("物流信息(%s)解析错误:", logisticsMsg), e);
        }
        if (backMsg == null) return;

        BackResult backResult = backMsg.getLastResult();
        if (backResult == null) return;

        List<LinkedHashMap<String, String>> detailList = backResult.getData();
        if (detailList == null || detailList.size() == 0) return;

        // 最新的记录时间(索引在第一个)
        Date latestTime = getOperationDate(detailList.get(0));
        // 第一条记录时间(索引在最后一个)
        Date firstTime = getOperationDate(detailList.get(detailList.size() - 1));

        if (updateLogisticsInfo(new LogisticsInfo(backResult.getNu(), firstTime, latestTime, backResult.getIscheck(), logisticsMsg)) != 1) {
            if (LOG.isErrorEnabled())
                LOG.error(String.format("更新物流单(%s)信息(%s)时未成功!", backResult.getNu(), logisticsMsg));
        }
    }

    private Date getOperationDate(Map<String, String> logisticsInfo) {
        Date date = null;
        try {
            date = DateUtils.parseDate(logisticsInfo.get("time"), DateUtils.DateFormatType.DATE_FORMAT_STR);
        } catch (Exception e) {
            if (LOG.isWarnEnabled())
                LOG.warn(String.format("物流记录(%s)时间转换出错:", logisticsInfo), e);
            // ignore time
        }
        return date;
    }

    @Override
    public List<LogisticsInfo> findLogisticsInfoByOrderNo(String orderNo) {
        return findLogisticsInfo(orderNo, StringUtils.EMPTY);
    }

    @Override
    public LogisticsInfo findLogisticsInfoByExpressNo(String expressNo) {
        return findLogisticsInfoByNoAndCompany(StringUtils.EMPTY, expressNo);
    }

    private List<LogisticsInfo> findLogisticsInfo(String orderNo, String expressNo) {
        LogisticsInfo logisticsInfo = new LogisticsInfo();
        if (StringUtils.isNotBlank(orderNo))
            logisticsInfo.setOrderNo(orderNo);
        if (StringUtils.isNotBlank(expressNo))
            logisticsInfo.setExpressNo(expressNo);

        return logisticsInfoMapper.findLogisticsByNoes(logisticsInfo);
    }

    @Override
    public LogisticsInfo findLogisticsInfoByNoAndCompany(String orderNo, String expressNo) {
        List<LogisticsInfo> logisticsInfoList = findLogisticsInfo(orderNo, expressNo);
        if (logisticsInfoList != null && logisticsInfoList.size() == 1)
            return logisticsInfoList.get(0);

        return null;
    }

    @Override
    public List<Map<String, Object>> findNotSuccessLogisticsByLaTestTime(int hourCount) {
        return logisticsInfoMapper.findNotSuccessLogisticsByLaTestTime(hourCount);
    }

    @Override
    public List<Map<String, Object>> findNotSuccessLogisticsByFirstTime(int hourCount) {
        return logisticsInfoMapper.findNotSuccessLogisticsByFirstTime(hourCount);
    }

    @Override
    @Transactional
    public int saveLogisticsInfo(LogisticsInfo logisticsInfo) throws DuplicateKeyException {
        return logisticsInfoMapper.saveLogistics(logisticsInfo);
    }

    @Override
    @Transactional
    public int updateLogisticsInfo(LogisticsInfo logisticsInfo) {
        return logisticsInfoMapper.updateLogisticsInfo(logisticsInfo);
    }

    @Override
    @Transactional
    public int deleteLogisticsInfo(int id) {
        return logisticsInfoMapper.deleteLogisticsInfo(id);
    }

    @Override
    public List<TransferInfoBean> findTransferInfoByExpressNo(String expressNo) {

        if(LOG.isInfoEnabled()){
            LOG.info("物流中转信息查询：：：：：：");
        }

        // 查询物流信息
        LogisticsInfo logisticsInfo = findLogisticsInfoByExpressNo(expressNo);
        if(logisticsInfo == null){
            throw new ErpBusinessException("中转信息查询：查不到对应的物流信息！物流单号：" +expressNo);
        }
        // 创建保存中转信息的集合
        List<TransferInfoBean> transferInfoBeanList = new ArrayList<TransferInfoBean>();
        TransferInfoBean transferInfoBean = null;
        // 解析物流中转信息
        BackMsg backMsg = JsonUtil.json2Object(logisticsInfo.getExpressInfo(),BackMsg.class);
        // 获得中转信息集合
        List<LinkedHashMap<String,String>> dataList = backMsg.getLastResult().getData();
        for(LinkedHashMap<String,String> linkedHashMap : dataList){
            transferInfoBean = new TransferInfoBean();
            transferInfoBean.setExpressNo(logisticsInfo.getExpressNo());
            transferInfoBean.setContext(linkedHashMap.get("context"));
            transferInfoBean.setTransferTime(DateUtils.parseDate(linkedHashMap.get("time"), DateUtils.DateFormatType.DATE_FORMAT_STR));
            transferInfoBeanList.add(transferInfoBean);
        }
        return transferInfoBeanList;
    }

    @Override
    public List<LogisticsInfoVo> findLogisticsInfoByExpressNos(List<String> expressNos) throws InvocationTargetException, IllegalAccessException {

        List<LogisticsInfoVo> logisticsInfoVoList = new ArrayList<LogisticsInfoVo>();
        List<LogisticsInfo> logisticsInfoList = logisticsInfoMapper.findLogisticsByExpressNos(expressNos);

        LogisticsInfoVo logisticsInfoVo;
        for(LogisticsInfo logisticsInfo : logisticsInfoList){
            logisticsInfoVo = new LogisticsInfoVo();
            BeanUtils.copyProperties(logisticsInfoVo,logisticsInfo);
            logisticsInfoVo.setExpressCompanyName(DeliveryType.valueOf(logisticsInfo.getExpressCompany()).toDesc());
            logisticsInfoVo.setWasRequestName(logisticsInfo.getWasRequest() ? "已请求" : "未请求");
            logisticsInfoVo.setExpressStatusName(logisticsInfo.getExpressStatus() ? "配送完成" : "配送中");
            logisticsInfoVoList.add(logisticsInfoVo);
        }
        return logisticsInfoVoList;
    }

    @Override
    public File exportLogisticsFullInfoBeanByExpressNos(List<String> expressNos) throws InvocationTargetException, IllegalAccessException, IOException {
        if(LOG.isInfoEnabled()){
            LOG.info("导出物流中转信息：：：：：：：");
        }
        // 获得物流信息
        List<LogisticsInfo> logisticsInfoList = logisticsInfoMapper.findLogisticsByExpressNos(expressNos);
//        if(CollectionUtils.isEmpty(logisticsInfoList)){
//            return null;
//        }

        List<LogisticsFullInfoBean> logisticsFullInfoBeanList = new ArrayList<LogisticsFullInfoBean>();
        LogisticsFullInfoBean logisticsFullInfoBean;
        List<TransferInfoBean> transferInfoBeanList;
        TransferInfoBean transferInfoBean;
        BackMsg backMsg;
        List<LinkedHashMap<String,String>> dataList;
        for(LogisticsInfo logisticsInfo : logisticsInfoList){
            logisticsFullInfoBean = new LogisticsFullInfoBean();
            BeanUtils.copyProperties(logisticsFullInfoBean, logisticsInfo);
            // 创建保存中转信息的集合
            transferInfoBeanList = new ArrayList<TransferInfoBean>();
            // 解析物流中转信息
            backMsg = JsonUtil.json2Object(logisticsInfo.getExpressInfo(),BackMsg.class);
            // 获得中转信息集合
            dataList = backMsg.getLastResult().getData();
            for(LinkedHashMap<String,String> linkedHashMap : dataList){
                transferInfoBean = new TransferInfoBean();
                transferInfoBean.setExpressNo(logisticsInfo.getExpressNo());
                transferInfoBean.setContext(linkedHashMap.get("context"));
                transferInfoBean.setTransferTime(DateUtils.parseDate(linkedHashMap.get("time"), DateUtils.DateFormatType.DATE_FORMAT_STR));
                transferInfoBeanList.add(transferInfoBean);
            }
            logisticsFullInfoBean.setTransferInfoBeanList(transferInfoBeanList);
            logisticsFullInfoBeanList.add(logisticsFullInfoBean);
        }
        System.out.println("...");

        // 将物流中转信息保存至Excel文件中
        return convertLogistics2Excel(logisticsFullInfoBeanList);  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 将物流中转信息转化为Excel
     * @param logisticsFullInfoBeanList
     * @return
     */
    private File convertLogistics2Excel(List<LogisticsFullInfoBean> logisticsFullInfoBeanList) throws IOException {

        // 创建Excel文档
        HSSFWorkbook book = new HSSFWorkbook();
        // 创建Excel工作sheet
        HSSFSheet sheet = book.createSheet("物流查询中转记录"+DateUtils.formatDate(new Date(),
                DateUtils.DateFormatType.SIMPLE_DATE_TIME_FORMAT_STR));

        // 创建列头字体样式
        HSSFFont fontForHead = book.createFont();
        fontForHead.setFontHeight((short) 210);
        fontForHead.setBoldweight((short) 210);

        HSSFFont fontForContent1 = book.createFont();
        fontForContent1.setFontHeight((short) 220);
        fontForContent1.setBoldweight((short) 220);

        // 设置单元格样式
        // 设置日期单元格内容格式
        HSSFCellStyle cellStyleForDateContent = book.createCellStyle();
        cellStyleForDateContent.setDataFormat(HSSFDataFormat.getBuiltinFormat(DateUtils.DateFormatType.DATE_FORMAT_STR.getValue()));

        // 设置列头单元格样式
        HSSFCellStyle cellStyleForHead = book.createCellStyle();
        cellStyleForHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyleForHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyleForHead.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        cellStyleForHead.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleForHead.setFont(fontForHead);
        cellStyleForHead.setBottomBorderColor(HSSFColor.RED.index);
        cellStyleForHead.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyleForHead.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyleForHead.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyleForHead.setBorderRight(HSSFCellStyle.BORDER_THIN);

        // 设置内容单元格样式1
        HSSFCellStyle cellStyleForContent1 = book.createCellStyle();
        cellStyleForContent1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyleForContent1.setFont(fontForContent1);
//        cellStyleForContent1.setFillForegroundColor(HSSFColor.SEA_GREEN.index);
//        cellStyleForContent1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        cellStyleForContent1.setBottomBorderColor(HSSFColor.BLUE.index);
//        cellStyleForContent1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        cellStyleForContent1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        cellStyleForContent1.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        cellStyleForContent1.setBorderRight(HSSFCellStyle.BORDER_THIN);

        // 设置内容单元格样式2
        HSSFCellStyle cellStyleForContent2 = book.createCellStyle();
        cellStyleForContent2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        cellStyleForContent2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
//        cellStyleForContent2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        cellStyleForContent2.setBottomBorderColor(HSSFColor.BLUE.index);
//        cellStyleForContent2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        cellStyleForContent2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        cellStyleForContent2.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        cellStyleForContent2.setBorderRight(HSSFCellStyle.BORDER_THIN);

        // 冻结窗格
        sheet.createFreezePane(1,1);


        // 设置Excel每列宽度
        sheet.setColumnWidth(0,4500); // 物流单号
        sheet.setColumnWidth(1,4500); // 订单号
        sheet.setColumnWidth(2,3500); // 物流公司
        sheet.setColumnWidth(3,4500); // 收货地址
        sheet.setColumnWidth(4,2500); // 物流状态
        sheet.setColumnWidth(5,5500); // 第一条物流记录时间
        sheet.setColumnWidth(6,5500); // 最后一条物流记录时间
        sheet.setColumnWidth(7,5500); // 是否已请求第三方物流
        sheet.setColumnWidth(8,5500); // 中转日期
        sheet.setColumnWidth(9,18000); // 中转信息

        // 创建列头行
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 400);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("物流单号");
        cell.setCellStyle(cellStyleForHead);
        cell = row.createCell(1);
        cell.setCellValue("订单编号");
        cell.setCellStyle(cellStyleForHead);
        cell = row.createCell(2);
        cell.setCellValue("物流公司");
        cell.setCellStyle(cellStyleForHead);
        cell = row.createCell(3);
        cell.setCellValue("收货地址");
        cell.setCellStyle(cellStyleForHead);
        cell = row.createCell(4);
        cell.setCellValue("物流状态");
        cell.setCellStyle(cellStyleForHead);
        cell = row.createCell(5);
        cell.setCellValue("第一条物流记录时间");
        cell.setCellStyle(cellStyleForHead);
        cell = row.createCell(6);
        cell.setCellValue("最后一条物流记录时间");
        cell.setCellStyle(cellStyleForHead);
        cell = row.createCell(7);
        cell.setCellValue("是否已请求第三方物流");
        cell.setCellStyle(cellStyleForHead);
        cell = row.createCell(8);
        cell.setCellValue("中转日期");
        cell.setCellStyle(cellStyleForHead);
        cell = row.createCell(9);
        cell.setCellValue("中转信息");
        cell.setCellStyle(cellStyleForHead);


        LogisticsFullInfoBean logisticsFullInfoBean;
        // 一条物流信息有多少条物流中转信息
        int transferInfoCount = 0;
        TransferInfoBean transferInfoBean;
        int start = 1;
        for(int i = 0; i < logisticsFullInfoBeanList.size(); i++){
            logisticsFullInfoBean = logisticsFullInfoBeanList.get(i);
            transferInfoCount = logisticsFullInfoBean.getTransferInfoBeanList().size();
            row = sheet.createRow(i+start);
            cell = row.createCell(0); // 物流单号
            if(StringUtils.isNotEmpty(logisticsFullInfoBean.getExpressNo())){
                cell.setCellValue(logisticsFullInfoBean.getExpressNo());
            }
            cell.setCellStyle(cellStyleForContent1);
            cell = row.createCell(1); // 订单编号
            if(StringUtils.isNotEmpty(logisticsFullInfoBean.getOrderNo())){
                cell.setCellValue(logisticsFullInfoBean.getOrderNo());
            }
            cell.setCellStyle(cellStyleForContent1);
            cell = row.createCell(2); // 物流公司
            if(StringUtils.isNotEmpty(logisticsFullInfoBean.getExpressCompany())){
                cell.setCellValue(DeliveryType.valueOf(logisticsFullInfoBean.getExpressCompany()).toDesc());
            }
            cell.setCellStyle(cellStyleForContent1);
            cell = row.createCell(3); // 收货地址
            if(StringUtils.isNotEmpty(logisticsFullInfoBean.getSendTo())){
                cell.setCellValue(logisticsFullInfoBean.getSendTo());
            }
            cell.setCellStyle(cellStyleForContent1);
            cell = row.createCell(4); // 物流状态
            if(logisticsFullInfoBean.getExpressStatus() != null){
                cell.setCellValue(logisticsFullInfoBean.getExpressStatus() ? "配送完成" : "配送中");
            }
            cell.setCellStyle(cellStyleForContent1);
            cell = row.createCell(5); // 第一条物流记录时间
            if(logisticsFullInfoBean.getFirstTime() != null){
                cell.setCellValue(DateUtils.formatDate(logisticsFullInfoBean.getFirstTime(), DateUtils.DateFormatType.DATE_FORMAT_STR));
            }
            cell.setCellStyle(cellStyleForContent1);
            cell = row.createCell(6); // 最后一条物流记录时间
            if(logisticsFullInfoBean.getLatestTime() != null){
                cell.setCellValue(DateUtils.formatDate(logisticsFullInfoBean.getLatestTime(), DateUtils.DateFormatType.DATE_FORMAT_STR));
            }
            cell.setCellStyle(cellStyleForContent1);
            cell = row.createCell(7); // 是否已请求第三方物流
            if(logisticsFullInfoBean.getWasRequest() != null){
                cell.setCellValue(logisticsFullInfoBean.getWasRequest() ? "已请求" : "未请求");
            }
            cell.setCellStyle(cellStyleForContent1);

            // 单元格跨行
            sheet.addMergedRegion(new CellRangeAddress(i+start,i+start+transferInfoCount,0,0));
            sheet.addMergedRegion(new CellRangeAddress(i+start,i+start+transferInfoCount,1,1));
            sheet.addMergedRegion(new CellRangeAddress(i+start,i+start+transferInfoCount,2,2));
            sheet.addMergedRegion(new CellRangeAddress(i+start,i+start+transferInfoCount,3,3));
            sheet.addMergedRegion(new CellRangeAddress(i+start,i+start+transferInfoCount,4,4));
            sheet.addMergedRegion(new CellRangeAddress(i+start,i+start+transferInfoCount,5,5));
            sheet.addMergedRegion(new CellRangeAddress(i+start,i+start+transferInfoCount,6,6));
            sheet.addMergedRegion(new CellRangeAddress(i+start,i+start+transferInfoCount,7,7));

            for(int j = 0; j < transferInfoCount; j++){
                transferInfoBean = logisticsFullInfoBean.getTransferInfoBeanList().get(j);
                if(j != 0){
                    row = sheet.createRow(i+start+j);
                }
                cell = row.createCell(8); // 中转日期
                if(transferInfoBean.getTransferTime() != null){
                    cell.setCellValue(DateUtils.formatDate(transferInfoBean.getTransferTime(), DateUtils.DateFormatType.DATE_FORMAT_STR));
                }
                cell.setCellStyle(cellStyleForContent2);
                cell = row.createCell(9); // 中转信息
                if(StringUtils.isNotEmpty(transferInfoBean.getContext())){
                    cell.setCellValue(transferInfoBean.getContext());
                }
                cell.setCellStyle(cellStyleForContent2);
            }
            start = start + transferInfoCount;
        }

        //String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String path = WebUtil.getWebAppPath();
        String parent = path +"excels";
        File dir = new File(parent);
        dir.mkdirs();
        File file = new File(parent,"logistics_"+DateUtils.formatDate(new Date(), DateUtils.DateFormatType.SIMPLE_DATE_TIME_FORMAT_STR)+".xls");
        FileOutputStream fos = new FileOutputStream(file);
        try{
            book.write(fos);
        }finally {
            if(fos != null){
                fos.close();
            }
        }
        return file;
    }

}
