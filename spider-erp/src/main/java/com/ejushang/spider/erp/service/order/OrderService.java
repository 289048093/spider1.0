package com.ejushang.spider.erp.service.order;

import com.ejushang.spider.constant.DeliveryType;
import com.ejushang.spider.constant.OrderStatus;
import com.ejushang.spider.constant.OrderType;
import com.ejushang.spider.domain.*;
import com.ejushang.spider.erp.common.mapper.*;
import com.ejushang.spider.erp.util.NumGeneratorUtil;
import com.ejushang.spider.erp.util.OrderUtil;
import com.ejushang.spider.erp.util.PoiUtil;
import com.ejushang.spider.erp.util.SequenceGenerator;
import com.ejushang.spider.exception.ErpBusinessException;
import com.ejushang.spider.exception.OrderAnalyzeException;
import com.ejushang.spider.query.OrderQuery;
import com.ejushang.spider.service.delivery.ILogisticsInfoService;
import com.ejushang.spider.service.order.IOrderAnalyzeService;
import com.ejushang.spider.service.order.IOrderFlowService;
import com.ejushang.spider.service.order.IOrderItemService;
import com.ejushang.spider.service.order.IOrderService;
import com.ejushang.spider.service.product.IBrandService;
import com.ejushang.spider.service.product.IProdCategoryService;
import com.ejushang.spider.service.product.IProductService;
import com.ejushang.spider.service.repository.IRepositoryService;
import com.ejushang.spider.service.repository.IStorageService;
import com.ejushang.spider.util.*;
import com.ejushang.spider.vo.*;
import com.ejushang.spider.vo.logistics.BackMsg;
import com.ejushang.spider.vo.logistics.BackResult;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * User: Baron.Zhang
 * Date: 13-12-23
 * Time: 下午3:36
 */
@Service
@Transactional
public class OrderService implements IOrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private RepositoryMapper repositoryMapper;

    @Resource
    private IStorageService storageService;

    @Resource
    private IOrderAnalyzeService orderAnalyzeService;

    @Resource
    private DeliveryMapper deliveryMapper;

    @Resource
    private ILogisticsInfoService logisticsInfoService;

    @Resource
    private IRepositoryService repositoryService;

    @Resource
    private IProductService productService;

    @Resource
    private IProdCategoryService prodCategoryService;
    @Resource
    private IBrandService brandService;
    @Resource
    private IOrderFlowService orderFlowService;

    @Resource
    private LogisticsInfoMapper logisticsInfoMapper;


    @Autowired
    private IOrderItemService orderItemService;

    /**
     * 订单查询
     *
     * @return 订单记录集合
     */
    @Transactional(readOnly = true)
    public List<Order> findOrders() {
        return orderMapper.findOrders();
    }

    /**
     * 订单查询
     *
     * @param orderQuery 查询条件
     * @return 订单记录集合
     */
    @Override
    @Transactional(readOnly = true)
    public Page findDetailOrders(OrderQuery orderQuery) {
        User user = SessionUtils.getUser();
        Integer checknum = 0;
        //判断用户是否有权限
        if (null != user) {
            if (null != user.getRepoId() && !checknum.equals(user.getRepoId())) {
                orderQuery.setRepoId(user.getRepoId());
                orderQuery.setStorageName(null);
            }
        }
        List<OrderVo> result = new ArrayList<OrderVo>();

        OrderVo orderVo = null;
        // 构造分页信息
        Page page = new Page();
        // 设置当前页
        page.setPageNo(orderQuery.getPage());
        //设置总条数
        page.setTotalCount(orderMapper.findOrderCountByOrderQuery(orderQuery));
        // 设置分页大小
        page.setPageSize(orderQuery.getLimit());
        List<Order> orderList = orderMapper.findOrderByOrderQueryNoPage(orderQuery);
        for (Order or : orderList) {
            orderVo = new OrderVo();
            orderVo.setId(or.getId());
            orderVo.setOrderNo(or.getOrderNo());
            orderVo.setOrderStatus(or.getOrderStatus());
            orderVo.setTotalFee(Money.getMoneyString(or.getTotalFee()));
            orderVo.setBuyerMessage(or.getBuyerMessage());
            orderVo.setRemark(or.getRemark());
            orderVo.setBuyerId(or.getBuyerId());
            orderVo.setReceiverZip(or.getReceiverZip());
            orderVo.setReceiverCity(or.getReceiverCity());
            orderVo.setOutOrderNo(or.getOutOrderNo());
            orderVo.setReceiverDistrict(or.getReceiverDistrict());
            orderVo.setReceiverName(or.getReceiverName());
            orderVo.setReceiverAddress(or.getReceiverAddress());
            orderVo.setReceiverState(or.getReceiverState());
            orderVo.setReceiverMobile(or.getReceiverMobile());
            orderVo.setReceiverPhone(or.getReceiverPhone());
            orderVo.setShippingNo(or.getShippingNo());
            orderVo.setShippingComp(or.getShippingComp());
            orderVo.setRepoName(or.getRepoName());
            orderVo.setBuyTime(or.getBuyTime());
            orderVo.setPayTime(or.getPayTime());
            orderVo.setConfirmTime(or.getConfirmTime());
            orderVo.setConfirmUser(or.getConfirmUser());
            orderVo.setPrintTime(or.getPrintTime());
            orderVo.setPrintUser(or.getPrintUser());
            orderVo.setShopId(or.getShopId());
            orderVo.setShopName(or.getShopName());
            orderVo.setInvoiceName(or.getInvoiceName());
            orderVo.setInvoiceContent(or.getInvoiceContent());
            orderVo.setRepoId(or.getRepoId());
            if (or.getPayment() != null) {
                orderVo.setPayment(Money.CentToYuan(or.getPayment()) + "");
            }
            if (or.getPostFee() != null) {
                orderVo.setPostFee(Money.CentToYuan(or.getPostFee()) + "");
            }
            orderVo.setOrderItem(orderItemMapper.findOrderItemByOrderId(or.getId()));
            Integer num = 0;
            if (null != orderVo.getOrderItem() && orderVo.getOrderItem().size() != 0) {
                OrderItem orderIte = orderVo.getOrderItem().get(0);
                orderVo.setItemName(orderIte.getProdName());
                orderVo.setItemCount(orderVo.getOrderItem().size());
                for (OrderItem orderItem : orderVo.getOrderItem()) {
                    num = num + orderItem.getProdCount();
                }
            }
            orderVo.setItemNumCount(num);
            result.add(orderVo);
        }

        page.setResult(result);
        return page;
    }

    /**
     * 批量合并订单
     *
     * @param orderIds
     * @return
     */
    @Transactional
    @Override
    public Integer mergeOrder(String orderIds) {
        Integer result = 1;
        String[] ids = orderIds.split(",");
        //判断订单数量
        Integer[] realIds = new Integer[ids.length];
        if (orderIds.length() < 2) {
            return 0;
        }
        //把所有要合并的订单ID添加到realIds数组里
        for (int i = 0; i < ids.length; i++) {
            realIds[i] = Integer.parseInt(ids[i].trim());
        }
        List<Order> list = new ArrayList<Order>();
        Order order = null;
        //通过ID数组查询出所有Order添加到List集合里
        for (int i = 0; i < realIds.length; i++) {
            order = new Order();
            order = orderMapper.findOrderById(realIds[i]);
            OrderUtil.checkSplitStatus(order, "合并订单");
            list.add(order);
        }
        //判断是否符合合并订单条件.条件:相同仓库，买家，收货地址
        for (int i = 0; i < list.size() - 1; i++) {
            Order order1 = list.get(i);
            Order order2 = list.get(i + 1);
            if ((!order1.getRepoId().equals(order2.getRepoId())) || (!order1.getBuyerId().equals(order2.getBuyerId())) || (!order1.getReceiverAddress().equals(order2.getReceiverAddress()))) {
                return 2;
            }
        }
        //将所有订单项添加到合并订单里
        Order order2 = orderMapper.findOrderById(realIds[0]);
        for (int i = 0; i < list.size(); i++) {
            Integer id = list.get(i).getId();
            if (id.equals(realIds[0])) {
                continue;
            }
            List<OrderItem> orderItemList = orderItemMapper.findOrderItemByOrderId(id);
            for (OrderItem orderItem : orderItemList) {
                orderItem.setOrderNo(order2.getOrderNo());
                orderItem.setOrderId(order2.getId());
                orderItemMapper.updateOrderItem(orderItem);
            }
        }
        //删除被合并订单
        for (int i = 1; i < realIds.length; i++) {
            orderMapper.deleteOrderById(realIds[i]);
        }

        //计算合并订单价格
        List<OrderItem> orderItemList = orderItemMapper.findOrderItemByOrderId(realIds[0]);
        order2.setOrderItemList(orderItemList);
        order2.calPayment();
        orderMapper.updateOrderByOrder(order2);
        return result;
    }

    /**
     * 订单新增
     *
     * @param order 订单信息
     */
    @Transactional
    @Override
    public int createOrder(Order order) {
        //生成订单号
        order.setOrderNo(SequenceGenerator.getInstance().getNextOrderNo());
        int result = orderMapper.saveOrder(order);
        if (order.getOrderItemList() != null) {
            for (OrderItem orderItem : order.getOrderItemList()) {
                orderItem.setOrderId(order.getId());
                orderItem.setOrderNo(order.getOrderNo());
                orderItemMapper.saveOrderItem(orderItem);
            }
        }
        return result;
    }

    /**
     * 订单删除
     *
     * @param order 订单信息
     */
    @Transactional
    public int deleteOrder(Order order) {
        return orderMapper.deleteOrder(order);
    }

    @Transactional(readOnly = true)
    @Override
    public Order findOrderByOrderNo(String orderNo) {
        return orderMapper.findOrderByOrderNo(orderNo);
    }


    /**
     * 通过orderIds 查询订单
     *
     * @param ids
     * @return 一组订单
     */
    @Transactional(readOnly = true)
    public List<Order> findOrderByIds(Integer[] ids) {
        if (ids == null) {
            return null;
        }
        List<Order> orderList = new ArrayList<Order>(ids.length);
        Integer count = 0;
        for (Integer id : ids) {
            Order order = orderMapper.findOrderById(id);
            count++;
            if (order == null) {
                String msg = "您选择的订单中，第" + count + "条,id为" + id + "的订单不存在，请核对";
                log.info(msg);
                throw new ErpBusinessException(msg);
            }
            orderList.add(order);
        }
        return orderList;
    }

    @Transactional(readOnly = true)
    public List<Order> findOrderByIdsCascades(Integer[] orderIds) {
        List<Order> orderList = findOrderByIds(orderIds);

        //对这些订单按物流单号进行由小到大排序
        Collections.sort(orderList, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                if (Long.parseLong(o1.getShippingNo()) > Long.parseLong(o2.getShippingNo())) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });

        for (Order order : orderList) {
            order.setOrderItemList(orderItemMapper.findOrderItemByOrderNo(order.getOrderNo()));  //获取这个id对应的orderItem
            if (order.getRepoId() != null || order.getRepoId() != 0) {
                order.setRepository(repositoryService.findRepositoryById(order.getRepoId())); //获取这个id对应的库房repository
            }
        }
        return orderList;
    }

    /**
     * 根据orderIds 进行物流单的打印
     *
     * @param orderIds
     * @return 要打印的物流单的Vo
     */
    @Transactional(readOnly = true)
    @Override
    public List<DeliveryPrintVo> deliveryPrint(Integer[] orderIds) {
        List<Order> orderList = findOrderByIdsCascades(orderIds);
        if (orderList.isEmpty()) {
            throw new ErpBusinessException("没有要打印的物流单");
        }

        List<DeliveryPrintVo> deliveryPrintVoList = new ArrayList<DeliveryPrintVo>(orderList.size());

        for (Order order : orderList) {
            DeliveryPrintVo deliveryPrintVo = new DeliveryPrintVo();
            if (order.getRepository() != null) {
                deliveryPrintVo.setChargePerson(order.getRepository().getChargePerson());
                deliveryPrintVo.setRepoAddress(order.getRepository().getAddress());
                deliveryPrintVo.setChargeMobile(order.getRepository().getChargeMobile());
                deliveryPrintVo.setChargePhone(order.getRepository().getChargePhone());
                deliveryPrintVo.setRepoName(order.getRepository().getName());
            }
            //订单项的商品编码  与 数量
            List<OrderItemPrintVo> orderItemPrintVoList = new ArrayList<OrderItemPrintVo>();
            for (OrderItem orderItem : order.getOrderItemList()) {
                OrderItemPrintVo orderItemPrintVo = new OrderItemPrintVo();
                orderItemPrintVo.setProdCode(orderItem.getProdCode());
                orderItemPrintVo.setProdCount(orderItem.getProdCount());
                orderItemPrintVoList.add(orderItemPrintVo);
            }
            deliveryPrintVo.setOrderItemPrintVos(orderItemPrintVoList);
            deliveryPrintVo.setReceiverName(order.getReceiverName());
            deliveryPrintVo.setReceiverMobile(order.getReceiverMobile());
            deliveryPrintVo.setReceiverPhone(order.getReceiverPhone());
            deliveryPrintVo.setReceiverState(order.getReceiverState());
            deliveryPrintVo.setReceiverCity(order.getReceiverCity());
            deliveryPrintVo.setReceiverDistrict(order.getReceiverDistrict());
            deliveryPrintVo.setReceiverAddress(order.getReceiverState() + order.getReceiverCity() + order.getReceiverDistrict() + order.getReceiverAddress());
            deliveryPrintVo.setReceiverZip(order.getReceiverZip());
            deliveryPrintVo.setDeliveryTime(DateUtils.getCurrentDate());//保留意见 现为当前时间
            deliveryPrintVoList.add(deliveryPrintVo);
        }
        return deliveryPrintVoList;
    }

    /**
     * 根据orderIds 进行订单打印
     *
     * @param orderIds
     * @return 要打印的订单的Vo
     */
    @Transactional(readOnly = true)
    @Override
    public List<OrderPrintVo> orderPrint(Integer[] orderIds) {
        List<Order> orderList = findOrderByIdsCascades(orderIds);
        if (orderList.isEmpty()) {
            log.info("orderPrint中没有要打印的发货单的记录");
            throw new ErpBusinessException("没有要打印的发货单");
        }
        List<OrderPrintVo> orderPrintVoList = new ArrayList<OrderPrintVo>(orderList.size());
        for (Order order : orderList) {
            OrderPrintVo orderPrintVo = new OrderPrintVo();
            orderPrintVo.setShippingNo(order.getShippingNo());
            orderPrintVo.setReceiverName(order.getReceiverName());
            orderPrintVo.setReceiverPhone(order.getReceiverPhone());
            orderPrintVo.setReceiverMobile(order.getReceiverMobile());
            orderPrintVo.setReceiverAddress(order.getReceiverState() + order.getReceiverCity() + order.getReceiverDistrict() + order.getReceiverAddress());
            orderPrintVo.setBuyerMessage(order.getBuyerMessage());
            orderPrintVo.setShippingComp(order.getShippingComp());
            orderPrintVo.setPayTime(order.getPayTime());
            orderPrintVo.setOrderNo(order.getOrderNo());
            orderPrintVo.setOutOrderNo(order.getOutOrderNo());
            Long finalTotalFee = 0l;
            if (!order.getOrderItemList().isEmpty()) {   //判断order中的OrderItemList是否为空
                List<OrderItem> orderItemList = order.getOrderItemList();
                List<OrderItemPrintVo> orderItemPrintVoList = new ArrayList<OrderItemPrintVo>(orderItemList.size());
                for (OrderItem orderItem : orderItemList) {
                    OrderItemPrintVo orderItemPrintVo = new OrderItemPrintVo();
                    orderItemPrintVo.setId(orderItem.getId());
                    orderItemPrintVo.setProdCode(orderItem.getProdCode());
                    orderItemPrintVo.setProdPrice(Money.CentToYuan(orderItem.getProdPrice()).toString());
                    orderItemPrintVo.setProdName(orderItem.getProdName());
                    if (productService.findProductById(orderItem.getProdId()) != null) {
                        orderItemPrintVo.setColor(productService.findProductById(orderItem.getProdId()).getColor());
                        orderItemPrintVo.setSpeci(productService.findProductById(orderItem.getProdId()).getSpeci());
                    }
                    orderItemPrintVo.setProdCount(orderItem.getProdCount());
                    orderItemPrintVo.setTotalFee(Money.CentToYuan(orderItem.getTotalFee()).toString());
                    finalTotalFee += orderItem.getTotalFee(); //将各个订单项的金额相加，得出合计
                    orderItemPrintVoList.add(orderItemPrintVo);
                }
                orderPrintVo.setOrderItemPrintVos(orderItemPrintVoList);
            }
            orderPrintVo.setFinalTotalFee(Money.CentToYuan(finalTotalFee).toString());
            if (order.getRepository() != null) {
                orderPrintVo.setChargePerson(order.getRepository().getChargePerson());
                orderPrintVo.setRepoAddress(order.getRepository().getAddress());
            }
            orderPrintVo.setRemark(order.getRemark());
            orderPrintVoList.add(orderPrintVo);
        }
        return orderPrintVoList;
    }

    /**
     * 根据快递单号shoppingNos 查找订单
     *
     * @param shippingNos
     * @return 订单组
     */
    @Transactional(readOnly = true)
    public List<Order> findOrderByShippingNo(String[] shippingNos) {
        List<Order> orderList = new ArrayList<Order>(shippingNos.length);
        Integer count = 0;
        for (String shippingNo : shippingNos) {
            List<Order> orderList1 = orderMapper.findOrderByShoppingNo(shippingNo);
            count++;
            if (orderList1.isEmpty()) {
                String msg = "您选择的订单中，第" + count + "条,shippingNo为" + shippingNo + "的订单不存在，请核对";
                log.info(msg);
                throw new ErpBusinessException(msg);
            }
            orderList.add(orderList1.get(0));
        }
        return orderList;
    }

    @Transactional(readOnly = true)
    public List<Order> findOrderByShippingNoCascades(String[] shippingNos) {
        List<Order> orderList = findOrderByShippingNo(shippingNos);
        for (Order order : orderList) {
            List<OrderItem> orderItemList = orderItemMapper.findOrderItemByOrderNo(order.getOrderNo());
            if (!orderItemList.isEmpty()) {
                for (OrderItem orderItem : orderItemList) {
                    orderItem.setProd(productService.findProductById(orderItem.getProdId()));
                }
            }
            order.setOrderItemList(orderItemList);
        }
        return orderList;
    }

    /**
     * 验货功能
     *
     * @param shippingNos
     * @return 在验货时要显示的Vo
     */
    @Transactional(readOnly = true)
    @Override
    public List<OrderInspectionVo> orderInspection(String[] shippingNos) {
        List<Order> orderList = findOrderByShippingNoCascades(shippingNos);
        if (orderList.isEmpty()) {
            log.info("orderPrint中没有要打印的发货单的记录");
            throw new ErpBusinessException("没有要打印的发货单");
        }
        List<OrderInspectionVo> orderInspectionVoList = new ArrayList<OrderInspectionVo>(orderList.size());
        for (Order order : orderList) {
            OrderInspectionVo orderInspectionVo = new OrderInspectionVo();
            orderInspectionVo.setOrderId(order.getId());
            orderInspectionVo.setOrderNo(order.getOrderNo());
            orderInspectionVo.setShippingNo(order.getShippingNo());
            orderInspectionVo.setOrderStatus(order.getOrderStatus());
            orderInspectionVo.setShippingComp(order.getShippingComp());
            orderInspectionVo.setReceiverName(order.getReceiverName());
            orderInspectionVo.setConfirmUser(order.getConfirmUser());
            orderInspectionVo.setRemark(order.getRemark());
            orderInspectionVo.setBuyerMessage(order.getBuyerMessage());
            if (!order.getOrderItemList().isEmpty()) {
                List<OrderItem> orderItemList = order.getOrderItemList();
                List<OrderItemInspectionVo> orderItemInspectionVoList = new ArrayList<OrderItemInspectionVo>(orderItemList.size());
                for (OrderItem orderItem : orderItemList) {
                    OrderItemInspectionVo orderItemInspectionVo = new OrderItemInspectionVo();
                    orderItemInspectionVo.setSkuCode(orderItem.getSkuCode());
                    orderItemInspectionVo.setProdName(orderItem.getProdName());
                    orderItemInspectionVo.setOrderNo(orderItem.getOrderNo());
                    orderItemInspectionVo.setProdCode(orderItem.getProdCode());
                    if (orderItem.getProd() != null) {
                        if (prodCategoryService.findProdCategoryById(orderItem.getProd().getCid()) != null) {
                            orderItemInspectionVo.setCategoryName(prodCategoryService.findProdCategoryById(orderItem.getProd().getCid()).getName());
                        }
                        if (storageService.findStorageByProdId(orderItem.getProd().getId()) != null) {
                            orderItemInspectionVo.setActuallyNumber(storageService.findStorageByProdId(orderItem.getProd().getId()).getActuallyNumber());
                        }
                        if (brandService.findBrandById(orderItem.getProd().getBrandId()) != null) {
                            orderItemInspectionVo.setBrandName(brandService.findBrandById(orderItem.getProd().getBrandId()).getName());
                        }
                    }
                    orderItemInspectionVo.setProdPrice(orderItem.getProdPrice());
                    orderItemInspectionVo.setProdCount(orderItem.getProdCount());
                    orderItemInspectionVoList.add(orderItemInspectionVo);
                }
                orderInspectionVo.setOrderItemInspectionVos(orderItemInspectionVoList);
            }
            orderInspectionVoList.add(orderInspectionVo);
        }
        return orderInspectionVoList;
    }


    /**
     * 订单作废 待处理->作废
     *
     * @param orderIds
     * @return 作废的记录条数
     */
    @Transactional
    @Override
    public Integer orderCancellation(Integer[] orderIds) {
        Integer count = 0;
        for (Integer orderId : orderIds) {
            String currentState = orderMapper.findOrderStatusById(orderId);
            count++;
            if (currentState.equalsIgnoreCase(OrderStatus.INVOICED.toString())) {
                String msg = String.format("第%d条的订单是'已发货'状态，不能修改为'已作废'状态", count);
                log.info(msg);
                throw new ErpBusinessException(msg);
            }
        }
        for (Integer orderId : orderIds) {

            Order order = orderMapper.findOrderById(orderId);
            OrderStatus from = OrderStatus.valueOf(order.getOrderStatus());
            OrderStatus from2 = OrderStatus.WAIT_PROCESS;
            OrderStatus to = OrderStatus.WAIT_PROCESS;
            OrderStatus to2 = OrderStatus.INVALID;
            orderFlowService.changeStatus(order, from, to, false);
            orderFlowService.changeStatus(order, from2, to2, true);
//            Order order = new Order();
//            order.setId(orderId);
//            order.setOrderStatus(OrderStatus.INVALID.toString());
//            orderMapper.updateOrderStatus(order);
        }
        return 1;
    }

    /**
     * 订单恢复 作废->待处理
     *
     * @param orderIds
     * @return
     */
    @Transactional
    @Override
    public Integer orderRecover(Integer[] orderIds) {
        if (log.isInfoEnabled()) {
            log.info(String.format("OrderService中的orderRecover方法，参数orderIds:%s", orderIds.toString()));
        }
        for (Integer orderId : orderIds) {
            Order order = orderMapper.findOrderById(orderId);
            OrderStatus from = OrderStatus.INVALID;
            OrderStatus to = OrderStatus.WAIT_PROCESS;
            orderFlowService.changeStatus(order, from, to, true);
        }
        return 1;
    }

    /**
     * 导入进销存 待处理->已确认
     *
     * @param orderIds
     * @return
     */
    @Transactional
    @Override
    public Integer orderConfirm(Integer[] orderIds) {
        if (log.isInfoEnabled()) {
            log.info(String.format("OrderService中的orderConfirm方法，参数orderIds:%s", orderIds.toString()));
        }
        for (Integer orderId : orderIds) {
            Order order = orderMapper.findOrderById(orderId);
            OrderStatus from = OrderStatus.WAIT_PROCESS;
            OrderStatus to = OrderStatus.CONFIRMED;
            orderFlowService.changeStatus(order, from, to, true);
        }
        return 1;
    }

    /**
     * 订单确认打印 已确认->已打印
     *
     * @param orderIds
     * @return
     */
    @Transactional
    @Override
    public Integer orderAffirmPrint(Integer[] orderIds) {
        if (log.isInfoEnabled()) {
            log.info(String.format("OrderService中的orderAffirmPrint方法，参数orderIds:%s", orderIds.toString()));
        }
        for (Integer orderId : orderIds) {
            Order order = orderMapper.findOrderById(orderId);
            OrderStatus from = OrderStatus.CONFIRMED;
            OrderStatus to = OrderStatus.PRINTED;
            orderFlowService.changeStatus(order, from, to, true);
        }
        return 1;
    }

    /**
     * 返回已导入 已打印->已确认
     *
     * @param orderIds
     * @return
     */
    @Transactional
    @Override
    public Integer orderBackToConfirm(Integer[] orderIds) {
        if (log.isInfoEnabled()) {
            log.info(String.format("OrderService中的orderBackToConfirm方法,参数orderIds:%s", orderIds.toString()));
        }
        for (Integer orderId : orderIds) {
            Order order = orderMapper.findOrderById(orderId);
            OrderStatus from = OrderStatus.PRINTED;
            OrderStatus to = OrderStatus.CONFIRMED;
            orderFlowService.changeStatus(order, from, to, true);
        }
        return 1;
    }

    /**
     * 订单批量验证 已打印->已验证
     *
     * @param orderIds
     * @return
     */
    @Transactional
    @Override
    public Integer orderBatchExamine(Integer[] orderIds) {
        if (log.isInfoEnabled()) {
            log.info(String.format("OrderService中的orderBatchExamine方法，参数orderIds:%s", orderIds.toString()));
        }
        for (Integer orderId : orderIds) {
            Order order = orderMapper.findOrderById(orderId);
            OrderStatus from = OrderStatus.PRINTED;
            OrderStatus to = OrderStatus.EXAMINED;
            orderFlowService.changeStatus(order, from, to, true);
        }
        return 1;
    }

    /**
     * 返回已打印 已验货->已打印
     *
     * @param orderIds
     * @return
     */
    @Transactional
    @Override
    public Integer orderBackToPrint(Integer[] orderIds) {
        if (log.isInfoEnabled()) {
            log.info(String.format("OrderService中的orderBackToPrint方法，参数orderIds:%s", orderIds.toString()));
        }
        for (Integer orderId : orderIds) {
            Order order = orderMapper.findOrderById(orderId);
            OrderStatus from = OrderStatus.EXAMINED;
            OrderStatus to = OrderStatus.PRINTED;
            orderFlowService.changeStatus(order, from, to, true);
        }
        return 1;
    }

    /**
     * 订单发货 已验证->已发货
     *
     * @param orderIds
     * @return
     */
    @Transactional
    @Override
    public Integer orderInvoice(Integer[] orderIds) {
        if (log.isInfoEnabled()) {
            log.info(String.format("OrderService中的orderInvoice方法，参数orderIds:%s", orderIds.toString()));
        }
        for (Integer orderId : orderIds) {
            Order order = orderMapper.findOrderById(orderId);
            OrderStatus from = OrderStatus.EXAMINED;
            OrderStatus to = OrderStatus.INVOICED;
            orderFlowService.changeStatus(order, from, to, true);
        }
        return 1;
    }

    /**
     * 订单返回客服
     * 已验货||已打印||已确认->待处理
     *
     * @param orderIds
     * @return
     */
    @Transactional
    @Override
    public Integer orderBackToWaitProcess(Integer[] orderIds) {
        if (log.isInfoEnabled()) {
            log.info(String.format("OrderService中的orderBackToWaitProcess方法，参数orderIds:%s", orderIds.toString()));
        }
        for (Integer orderId : orderIds) {
            Order order = orderMapper.findOrderById(orderId);
            OrderStatus from = OrderStatus.valueOf(order.getOrderStatus());
            OrderStatus to = OrderStatus.WAIT_PROCESS;
            orderFlowService.changeStatus(order, from, to, false);
        }
        return 1;
    }

    /**
     * 订单更新:更新物流
     *
     * @param orderNos     订单Id数组
     * @param shippingComp 物流表ID
     * @param intNo        基数
     * @return 返回1成功，0失败
     */
    @Override
    @Transactional
    public Integer updateOrderShipping(int orderNos[], String shippingComp, String intNo, String isCover) throws com.ejushang.spider.exception.GenerateException {
        if (log.isInfoEnabled()) {
            log.info(String.format("OrderService中的updateOrderShipping方法，参数orderNOs:", orderNos.toString()) + "参数ShippingComp:" + shippingComp + "参数intNo:" + intNo + "参数isCover:" + isCover);
        }
        int law = 0;
        int status = 1;
        Order order = null;
        List<String> numList = null;
        Delivery delivery = deliveryMapper.findDeliveryByName(shippingComp);
        if (null != delivery) {
            numList = NumGeneratorUtil.getShippingNums(delivery, intNo, orderNos.length);
            List<Order> orderList = orderMapper.findOrders();
            for (String num : numList) {
                for (Order order1 : orderList) {
                    if (num.equals(order1.getShippingNo())) {
                        status = 2;
                        break;
                    }
                }
            }

            if (status != 2) {
                for (int i = 0; i < orderNos.length; i++) {
                    order = new Order();
                    order.setShippingNo(numList.get(i));
                    order.setId(orderNos[i]);
                    order.setShippingComp(delivery.getName());
                    Order or = orderMapper.findOrderById(orderNos[i]);
                    if (isCover == null) {
                        if (or.getShippingNo() == null) {
                            status = orderMapper.updateOrderShipping(order);
                        }
                    } else {
                        status = orderMapper.updateOrderShipping(order);
                    }
                }
            }
        } else {
            log.info("OrderService中的方法updateOrderShipping中获取Delivery快递信息是空值");
            status = 3;
        }
        return status;
    }

    /**
     * 更新订单物流编号
     *
     * @param order
     * @return
     */
    @Override
    @Transactional
    public Integer updateOrderShippingNo(Order order) {
        if (log.isInfoEnabled()) {
            log.info("OrderService中的updateOrderShippingNo方法，参数Order:" + order.toString() + "");
        }
        return orderMapper.updateOrderStatusByOrder(order);
    }

//    /**
//     * 更新订单：批量更新订单状态
//     *
//     * @param order 订单实体
//     * @param ids   订单ID数组
//     * @return 成功返回1，失败返回 0
//     */
//    @Override
//    @Transactional
//    public int updateOrderStatusByOrder(Order order, Integer[] ids) {
//        if (log.isInfoEnabled()) {
//            log.info("OrderService中的updateOrderStatusByOrder方法，参数Order:" + order.toString() + "参数ids:" + ids);
//        }
//        int result = 1;
//        for (int i : ids) {
//            order.setId(i);
//            Repository repository = repositoryMapper.findRepositoryById(order.getRepoId());
//
//            if (null != repository) {
//
//                order.setRepoName(repository.getName());
//            } else {
//                log.info("OrderService中的方法updateOrderStatusByOrder中的repository获取对象是空的");
//            }
//            int check = orderMapper.updateOrderStatusByOrder(order);
//            if (check == 0) {
//                result = 0;
//            }
//        }
//        return result;
//
//
//    }

    /**
     * 订单更新：通过order实体更新订单
     *
     * @param order 订单实体
     * @return
     */
    @Override
    @Transactional
    public int updateOrderByOrder(Order order) {
        if (log.isInfoEnabled()) {
            log.info("OrderService中的方法updateOrderByOrder的参数Order:" + order.toString());
        }
        int result = 1;
        if (order.getShippingNo() != null) {
            if (!NumGeneratorUtil.isNumericByAscii(order.getShippingNo())) {

                return 3;
            }
            Order order1 = orderMapper.findOrderById(order.getId());
            List<Order> orderList = orderMapper.findOrders();
            for (Order or : orderList) {
                if (order.getShippingNo().equals(or.getShippingNo()) && !order.getShippingNo().equals(order1.getShippingNo())) {
                    result = 2;
                }
            }
            if (result != 2) {
                result = orderMapper.updateOrderByOrder(order);
            }
        } else {
            log.info("updateOrderByOrder中的参数order获取的shippingNo是空的");
            result = orderMapper.updateOrderByOrder(order);
        }
        return result;
    }

    /**
     * 逻辑删除订单
     *
     * @param ids 订单ID数组
     * @return 成功返回1，失败返回0
     */
    @Override
    @Transactional
    public int deleteOrderById(Integer[] ids) {
        int result = 1;
        for (int id : ids) {
            orderItemMapper.deleteOrderItemByOrderId(id);
            int check = orderMapper.deleteOrderById(id);
            if (check == 0) {
                result = 0;
            }
        }
        return result;
    }

    /**
     * 拆分订单
     *
     * @param ids
     * @param orderNo
     * @return
     * @throws OrderAnalyzeException
     */
    @Override
    @Transactional
    public String orderByOrderNo(int[] ids, String orderNo) throws OrderAnalyzeException {

        if (log.isInfoEnabled()) {
            log.info("传过来的ids为：" + ids + "传过来的orderNo为：" + orderNo);
        }

        //根据orderNo查询出order
        Order order = findOrderByOrderNo(orderNo);

        if (order == null) {
            if (log.isInfoEnabled()) {
                log.info("根据订单号无法查找到订单，订单号为：" + orderNo);
            }
            throw new IllegalArgumentException("根据订单号无法查找到订单，订单号为：" + orderNo);
        }

        List<Order> splitResults = orderAnalyzeService.splitOrderByHand(order, ids);
        return splitResults.get(0).getOrderNo();

    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> findOrderNoLaterPay(String triggerWarnDate) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orderStatusList", Arrays.asList(OrderStatus.WAIT_PROCESS, OrderStatus.CONFIRMED, OrderStatus.PRINTED, OrderStatus.EXAMINED));
        param.put("triggerWarnDate", triggerWarnDate);
        param.put("orderTypeList", Arrays.asList(OrderType.NORMAL, OrderType.INVOICED));
        List<Map<String, Object>> orderList = orderMapper.findOrderNoByStatusAndWarnDate(param);
        for (Map<String, Object> objectMap : orderList) {
            try {
                objectMap.put("orderStatus", OrderStatus.valueOf(String.valueOf(objectMap.get("orderStatus"))).toDesc());
            } catch (IllegalArgumentException e) {
                // ignore
                if (log.isErrorEnabled())
                    log.error("订单(" + objectMap.get("orderNo") + ")对应的状态(" + objectMap.get("orderStatus") + ")有误");
            }
        }
        return orderList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> findNoLogisticsInfoOrderNoLaterSend(String triggerWarnDate) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orderStatus", OrderStatus.INVOICED);
        param.put("triggerWarnDate", triggerWarnDate);
        param.put("orderTypeList", Arrays.asList(OrderType.NORMAL, OrderType.INVOICED));

        List<Map<String, Object>> orderList = orderMapper.findOrderNoByStatusAndTypeAndWarnDate(param);
        List<Map<String, Object>> orderNoList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> objectMap : orderList) {
            LogisticsInfo logisticsInfo = logisticsInfoService.findLogisticsInfoByExpressNo(String.valueOf(objectMap.get("expressNo")));
            if (logisticsInfo == null || StringUtils.isBlank(logisticsInfo.getExpressInfo())) {
                try {
                    objectMap.put("expressCompany", DeliveryType.valueOf(String.valueOf(objectMap.get("expressCompany"))).toDesc());
                } catch (IllegalArgumentException e) {
                    // ignore
                    if (log.isErrorEnabled())
                        log.error("订单(" + objectMap.get("expressNo") + ")发货时对应的物流公司(" + objectMap.get("expressCompany") + ")未找到!");
                }
                orderNoList.add(objectMap);
            }
        }
        return orderNoList;
    }

    /**
     * 单个订单详情
     *
     * @param orderNo 订单编号
     * @return
     */
    @Override
    public OrderVo findSingleDetailOrder(String orderNo) {
        Order or = orderMapper.findOrderByOrderNo(orderNo);
        if (or == null) return null;
        String expressNo = or.getShippingNo();
        OrderVo orderVo = new OrderVo();
        copyOrderProperties(or, orderVo);
        if (StringUtils.isNotBlank(expressNo)) {
            LogisticsInfo logisticsInfo = logisticsInfoService.findLogisticsInfoByExpressNo(expressNo);
            if (logisticsInfo != null) {
                List<LinkedHashMap<String, String>> expressData = null;
                try {
                    expressData = parseExpressInfo(logisticsInfo.getExpressInfo());
                } catch (Exception e) { //解析错误时
                    if (log.isWarnEnabled())
                        log.warn(e.getMessage());
                }
                orderVo.setExpressInfoData(expressData);
            }
        }
        return orderVo;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> findOrderByOutOrderNo(String outOrderNo) {
        return orderMapper.findOrderByOutOrderNo(outOrderNo);
    }

    /**
     * 拷贝order属性
     *
     * @param src  order
     * @param dest orderVO
     */
    private void copyOrderProperties(Order src, OrderVo dest) {
        dest.setId(src.getId());
        dest.setOrderNo(src.getOrderNo());
        dest.setOrderStatus(src.getOrderStatus());
        dest.setTotalFee(Money.getMoneyString(src.getTotalFee()));
        dest.setBuyerMessage(src.getBuyerMessage());
        dest.setRemark(src.getRemark());
        dest.setBuyerId(src.getBuyerId());
        dest.setReceiverZip(src.getReceiverZip());
        dest.setReceiverCity(src.getReceiverCity());
        dest.setOutOrderNo(src.getOutOrderNo());
        dest.setReceiverDistrict(src.getReceiverDistrict());
        dest.setReceiverName(src.getReceiverName());
        dest.setReceiverAddress(src.getReceiverAddress());
        dest.setReceiverState(src.getReceiverState());
        dest.setReceiverMobile(src.getReceiverMobile());
        dest.setReceiverPhone(src.getReceiverPhone());
        dest.setShippingNo(src.getShippingNo());
        dest.setShippingComp(src.getShippingComp());
        dest.setRepoName(src.getRepoName());
        dest.setBuyTime(src.getBuyTime());
        dest.setPayTime(src.getPayTime());
        dest.setConfirmTime(src.getConfirmTime());
        dest.setConfirmUser(src.getConfirmUser());
        dest.setPrintTime(src.getPrintTime());
        dest.setPrintUser(src.getPrintUser());
        dest.setShopId(src.getShopId());
        dest.setShopName(src.getShopName());
        dest.setInvoiceName(src.getInvoiceName());
        dest.setInvoiceContent(src.getInvoiceContent());
        dest.setRepoId(src.getRepoId());
    }

    /**
     * 解析物流信息
     *
     * @param expressInfo
     * @return
     */
    private List<LinkedHashMap<String, String>> parseExpressInfo(String expressInfo) throws Exception {
        if (StringUtils.isBlank(expressInfo)) return null;
        List<LinkedHashMap<String, String>> detailList = null;
        // 将返回的数据转换为 对象
        BackMsg backMsg = null;
        try {
            backMsg = JsonUtil.json2Object(expressInfo, BackMsg.class);
        } catch (Exception e) {
            throw new Exception(String.format("物流信息(%s)解析错误:", expressInfo), e);
        }
        if (backMsg == null) return null;
        BackResult backResult = backMsg.getLastResult();
        if (backResult != null) {
            detailList = backResult.getData();
        }
        return detailList;
    }

    @Override
    public Order findOrderById(Integer id) {
        return orderMapper.findOrderById(id);
    }


    /**
     * @param orderQuery
     * @return
     */
    @Override
    public Workbook extractOrderAndItem2Excel(OrderQuery orderQuery) {
        User user = SessionUtils.getUser();
        Integer checknum = 0;
        if (null != user) {
            if (null != user.getRepoId() && !checknum.equals(user.getRepoId())) {
                orderQuery.setRepoId(user.getRepoId());
                orderQuery.setStorageName(null);
            }
        }
        List<Order> orderList = orderMapper.findOrderByOrderQuery(orderQuery);

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        int orderCellIndex = createExcelTitle(sheet);

        int rowIndex = 2;//从第三行开始，一二行放title
        for (Order or : orderList) {
            List<OrderItemVo> orderItemVos = orderItemService.findOrderItemsByOrderId(or.getId());

            Row row = sheet.createRow(rowIndex++);
            int cellIndex = 0;

            renderOrder2Excel(row, cellIndex, or);

            //Item
            for (int i = 0; orderItemVos != null && i < orderItemVos.size(); i++) {
                OrderItemVo item = orderItemVos.get(i);
                Row itemRow;
                if (i > 0) {
                    itemRow = sheet.createRow(row.getRowNum() + i);  //如果商品条目>1则新建一行
                    rowIndex++;
                } else {
                    itemRow = row;
                }
                int itemCellIndex = orderCellIndex + 1;
                renderOrderItem2Excel(itemRow, itemCellIndex, item);
            }
            for (int i = 0; orderItemVos != null && i <= orderCellIndex; i++) {
                sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum() + orderItemVos.size() - 1, i, i));  //合并订单的单元格
            }
        }

        return workbook;
    }

    /**
     * 添加订单信息到excel行
     *
     * @param row            row
     * @param startCellIndex 开始位置
     * @param order          order
     * @return 结束的单元格位置
     */
    private int renderOrder2Excel(Row row, int startCellIndex, Order order) {
        PoiUtil.createCell(row, startCellIndex++, order.getOrderNo());
        PoiUtil.createCell(row, startCellIndex++, order.getOrderStatus());
        PoiUtil.createCell(row, startCellIndex++, Money.getMoneyString(order.getTotalFee()));
        PoiUtil.createCell(row, startCellIndex++, order.getBuyerMessage());
        PoiUtil.createCell(row, startCellIndex++, order.getRemark());
        PoiUtil.createCell(row, startCellIndex++, order.getReceiverZip());
        PoiUtil.createCell(row, startCellIndex++, order.getReceiverCity());
        PoiUtil.createCell(row, startCellIndex++, order.getOutOrderNo());
        PoiUtil.createCell(row, startCellIndex++, order.getReceiverDistrict());
        PoiUtil.createCell(row, startCellIndex++, order.getReceiverName());
        PoiUtil.createCell(row, startCellIndex++, order.getReceiverAddress());
        PoiUtil.createCell(row, startCellIndex++, order.getReceiverState());
        PoiUtil.createCell(row, startCellIndex++, order.getReceiverMobile());
        PoiUtil.createCell(row, startCellIndex++, order.getReceiverPhone());
        PoiUtil.createCell(row, startCellIndex++, order.getShippingNo());
        PoiUtil.createCell(row, startCellIndex++, order.getShippingComp());
        PoiUtil.createCell(row, startCellIndex++, order.getRepoName());
        PoiUtil.createCell(row, startCellIndex++, order.getBuyTime());
        PoiUtil.createCell(row, startCellIndex++, order.getPayTime());
        PoiUtil.createCell(row, startCellIndex++, order.getConfirmTime());
        PoiUtil.createCell(row, startCellIndex++, order.getConfirmUser());
        PoiUtil.createCell(row, startCellIndex++, order.getPrintTime());
        PoiUtil.createCell(row, startCellIndex++, order.getPrintUser());
        PoiUtil.createCell(row, startCellIndex++, order.getShopName());
        PoiUtil.createCell(row, startCellIndex++, order.getInvoiceName());
        PoiUtil.createCell(row, startCellIndex++, order.getInvoiceContent());
        PoiUtil.createCell(row, startCellIndex, Money.getMoneyString(order.getPostFee()));
        return startCellIndex;
    }

    /**
     * 添加订单项到excel行
     *
     * @param itemRow        指定行
     * @param startCellIndex 开始位置
     * @param item           orderItem实体
     * @return 结束的单元格位置
     */
    private int renderOrderItem2Excel(Row itemRow, int startCellIndex, OrderItemVo item) {
        PoiUtil.createCell(itemRow, startCellIndex++, item.getProdNo());
        PoiUtil.createCell(itemRow, startCellIndex++, item.getProdName());
        PoiUtil.createCell(itemRow, startCellIndex++, item.getSkuCode());
        PoiUtil.createCell(itemRow, startCellIndex++, item.getProdCateName());
        PoiUtil.createCell(itemRow, startCellIndex++, item.getProdPrice());
        PoiUtil.createCell(itemRow, startCellIndex++, item.getProdCount());
        PoiUtil.createCell(itemRow, startCellIndex++, item.getActualFee());
        PoiUtil.createCell(itemRow, startCellIndex++, item.getPostFee());
        PoiUtil.createCell(itemRow, startCellIndex++, item.getPriceDescription());
        PoiUtil.createCell(itemRow, startCellIndex++, item.getActuallyNumber());
        PoiUtil.createCell(itemRow, startCellIndex++, item.getBrandName());
        PoiUtil.createCell(itemRow, startCellIndex, item.getOutOrderNo());
        return startCellIndex;
    }

    /**
     * 创建excel的标题
     *
     * @param sheet sheet
     * @return 订单结束的单元格位置
     */
    private int createExcelTitle(Sheet sheet) {
        Row titleRow = sheet.createRow(0);
        PoiUtil.createCell(titleRow, 0, "订单信息");
        int cellIndex = 0;
        Row row = sheet.createRow(1);
        PoiUtil.createCell(row, cellIndex++, "订单编号");
        PoiUtil.createCell(row, cellIndex++, "订单状态");
        PoiUtil.createCell(row, cellIndex++, "商品金额");
        PoiUtil.createCell(row, cellIndex++, "买家留言");
        PoiUtil.createCell(row, cellIndex++, "备注说明");
        PoiUtil.createCell(row, cellIndex++, "邮政编码");
        PoiUtil.createCell(row, cellIndex++, "收货市");
        PoiUtil.createCell(row, cellIndex++, "原始订单号");
        PoiUtil.createCell(row, cellIndex++, "收货区");
        PoiUtil.createCell(row, cellIndex++, "收货人姓名");
        PoiUtil.createCell(row, cellIndex++, "收货人地址");
        PoiUtil.createCell(row, cellIndex++, "收货省");
        PoiUtil.createCell(row, cellIndex++, "收货手机");
        PoiUtil.createCell(row, cellIndex++, "收货电话");
        PoiUtil.createCell(row, cellIndex++, "物流编号");
        PoiUtil.createCell(row, cellIndex++, "物流公司");
        PoiUtil.createCell(row, cellIndex++, "库房名称");
        PoiUtil.createCell(row, cellIndex++, "下单时间");
        PoiUtil.createCell(row, cellIndex++, "付款时间");
        PoiUtil.createCell(row, cellIndex++, "审单时间");
        PoiUtil.createCell(row, cellIndex++, "审单员");
        PoiUtil.createCell(row, cellIndex++, "打单时间");
        PoiUtil.createCell(row, cellIndex++, "打单员");
        PoiUtil.createCell(row, cellIndex++, "店铺名称");
        PoiUtil.createCell(row, cellIndex++, "发票抬头");
        PoiUtil.createCell(row, cellIndex++, "发票内容");
        PoiUtil.createCell(row, cellIndex++, "邮费");
        int orderCellIndex = cellIndex - 1;
        PoiUtil.createCell(titleRow, cellIndex, "订单项信息");
        //商品条目
        PoiUtil.createCell(row, cellIndex++, "商品编码");
        PoiUtil.createCell(row, cellIndex++, "商品名称");
        PoiUtil.createCell(row, cellIndex++, "条形码");
        PoiUtil.createCell(row, cellIndex++, "类别");
        PoiUtil.createCell(row, cellIndex++, "单价");
        PoiUtil.createCell(row, cellIndex++, "数量");
        PoiUtil.createCell(row, cellIndex++, "总价");
        PoiUtil.createCell(row, cellIndex++, "实际总价");
        PoiUtil.createCell(row, cellIndex++, "邮费");
        PoiUtil.createCell(row, cellIndex++, "价格描述");
        PoiUtil.createCell(row, cellIndex++, "库存");
        PoiUtil.createCell(row, cellIndex++, "品牌");
        PoiUtil.createCell(row, cellIndex++, "原订单编号");
        sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), 0, orderCellIndex));  //合并订单标题的单元格
        sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), orderCellIndex, cellIndex));  //合并商品条目标题的单元格
        return orderCellIndex;
    }


}
