package com.ejushang.spider.web.controller;

import com.ejushang.spider.domain.Delivery;
import com.ejushang.spider.exception.ErpBusinessException;
import com.ejushang.spider.query.DeliveryQuery;
import com.ejushang.spider.service.delivery.IDeliveryService;
import com.ejushang.spider.util.AppConfigUtil;
import com.ejushang.spider.util.WebUtil;
import com.ejushang.spider.vo.DeliveryVo;
import com.ejushang.spider.web.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

/**
 * User: 龙清华
 * Date: 13-12-23
 * Time: 上午10:46
 * 物流设计Controller
 */
@RequestMapping("/delivery")
@Controller
public class DeliveryController {
    private static final Logger log = LoggerFactory.getLogger(DeliveryController.class);

    @Autowired
    IDeliveryService deliveryService;

    /**
     * 保存物流信息
     *
     * @param delivery 物流信息实体
     * @return 插入的条数
     */

    @RequestMapping("/add")
    public void saveDelivery(MultipartFile file,
                             Delivery delivery,
                             HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("物流保存的delivery" + delivery);
        }

        if (file == null) {
            new JsonResult(JsonResult.FAILURE).setMsg("没有发现上传图片！").toJson(response);
            return;
        }
        if (!file.getOriginalFilename().endsWith(".jpg")) {
            new JsonResult(JsonResult.FAILURE).setMsg("暂时只支持jpg格式图片").toJson(response);
            return;
        }

        this.upload(file, delivery);
        int count = deliveryService.saveDelivery(delivery);
        if (count == -1) {
            if (log.isInfoEnabled()) {
                log.info("此物流公司已经存在！" + delivery);
            }
            new JsonResult(JsonResult.FAILURE).setMsg("此物流公司已经存在！").toJson(response);
            return;
        }
        if (log.isInfoEnabled()) {
            log.info("保存是否OK" + count);
        }
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
    }


    /**
     * 生成图片,图片名
     */
    public Delivery upload(MultipartFile file, Delivery delivery) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("图片名 ：" + file.getOriginalFilename());
        }

        String paths = (String) AppConfigUtil.getInstance().get("paths");
        if (log.isInfoEnabled()) {
            log.info("保存的目录 paths：" + paths);
        }
        String name = UUID.randomUUID() + ".jpg";
        /**服务器所在的路径**/
        String path = WebUtil.getWebAppPath();
        if (log.isInfoEnabled()) {
            log.info("路径 path：" + path + paths + name);
        }
        File targetFile = new File(path + paths + name);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        /**保存*/
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String printHtml = "LODOP.PRINT_INITA(0,0,1000,600,\"初始化打印控件\");\n" +
                "LODOP.ADD_PRINT_SETUP_BKIMG(\"<img border='0' src='" + paths + name + "'>\");";
        delivery.setLogisticsPicturePath(name);//保存图片名
        delivery.setPrintHtml(printHtml); //保存loop代码信息
        if (log.isInfoEnabled()) {
            log.info("物流的图片存档信息" + delivery);
        }
        return delivery;
    }


    /**
     * 获得所有物流技术信息
     */
    @RequestMapping("/list")
    public void findDeliveryAll(DeliveryQuery deliveryQuery, HttpServletResponse response) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (log.isInfoEnabled()) {
            log.info("用于查询所有的物流信息的deliveryQuery:" + deliveryQuery);
        }
        List<DeliveryVo> list = deliveryService.findDeliveryAll(deliveryQuery);
        if (log.isInfoEnabled()) {
            log.info("查询出来的所有物流信息" + list);
        }
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_LIST, list).toJson(response);
    }

    /**
     * 根据快递公司名获取物流单打印格式
     * @param response
     * @param name
     * @throws IOException
     */
    @RequestMapping("/print_html")
    public void findDeliveryByName(HttpServletResponse response, String name) throws IOException {
        if (log.isInfoEnabled()) {
            log.info(String.format("用于查找指定物流公司的打印格式的,参数name:[%s]", name));
        }
        if(name == null || name == ""){
            new JsonResult(JsonResult.SUCCESS, "没有指定物流公司").toJson(response);
            return;
        }
        try {
            Delivery delivery = deliveryService.findByName(name);
            String printHtml = delivery.getPrintHtml();
            new JsonResult(JsonResult.SUCCESS, "打印成功").addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT, printHtml).toJson(response);
        } catch (ErpBusinessException e) {
            log.error(e.getMessage());
            new JsonResult(JsonResult.FAILURE, e.getMessage()).toJson(response);
        }

    }


    /**
     * 修改物流信息
     *
     * @param delivery 物流信息实体
     * @return 修改的条数
     */
    @RequestMapping("/update")

    public void updateDelivery(MultipartFile file, Delivery delivery, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("file 是否为null:" + delivery);
        }
        if (file != null) {
            delivery = this.upload(file, delivery);
        }
        if (log.isInfoEnabled()) {
            log.info("修改的物流信息delivery:" + delivery);
        }
        int count = deliveryService.updateDelivery(delivery);
        if (count == -1) {
            new JsonResult(JsonResult.FAILURE).setMsg("物流公司已经存在！").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
            return;
        }
        new JsonResult(JsonResult.SUCCESS).setMsg("修改成功！").addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
    }

    /**
     * 用于保存loop代码
     *
     * @param delivery
     * @param response
     */
    @RequestMapping("/updates")
    public void updateDelivery(Delivery delivery, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("保存loop代码的物流信息delivery:" + delivery);
        }
        int count = deliveryService.updateDelivery(delivery);
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);

    }

    /**
     * 删除物流信息 通过ID
     *
     * @param idArray 物流id字符串
     * @return 删除的条数
     */
    @RequestMapping("/delete")
    public void deleteDeliveryQuery(int[] idArray, HttpServletResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("需要删除的idArray:" + idArray);
        }
        int count = deliveryService.deleteDelivery(idArray);
        if (log.isInfoEnabled()) {
            log.info("删除是否成功 " + count);
        }
        new JsonResult(JsonResult.SUCCESS).addData(JsonResult.RESULT_TYPE_TOTAL_COUNT, count).toJson(response);
    }

}
