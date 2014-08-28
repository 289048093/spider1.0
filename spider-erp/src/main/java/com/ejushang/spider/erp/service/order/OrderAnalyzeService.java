package com.ejushang.spider.erp.service.order;

import com.ejushang.spider.bean.TransformOrderResultBean;
import com.ejushang.spider.constant.*;
import com.ejushang.spider.domain.*;
import com.ejushang.spider.erp.common.mapper.OrderItemMapper;
import com.ejushang.spider.erp.common.mapper.OrderMapper;
import com.ejushang.spider.erp.service.sysconfig.ConfService;
import com.ejushang.spider.erp.util.OrderUtil;
import com.ejushang.spider.erp.util.SystemConfConstant;
import com.ejushang.spider.exception.ErpBusinessException;
import com.ejushang.spider.exception.OrderAnalyzeException;
import com.ejushang.spider.query.AddOrderItemQuery;
import com.ejushang.spider.service.gift.IGiftProdService;
import com.ejushang.spider.service.order.IOrderAnalyzeService;
import com.ejushang.spider.service.order.IOrderItemService;
import com.ejushang.spider.service.order.IOrderService;
import com.ejushang.spider.service.order.IOriginalOrderService;
import com.ejushang.spider.service.product.IMealSetService;
import com.ejushang.spider.service.product.IProductService;
import com.ejushang.spider.service.repository.IRepositoryService;
import com.ejushang.spider.service.repository.IStorageService;
import com.ejushang.spider.service.shop.IShopService;
import com.ejushang.spider.util.Money;
import com.ejushang.spider.vo.AddOrderVo;
import com.ejushang.spider.vo.QueryProdVo;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class OrderAnalyzeService implements IOrderAnalyzeService {

    private static final Logger log = LoggerFactory.getLogger(OrderAnalyzeService.class);

    @Resource
    private IOrderService orderService;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private IOrderItemService orderItemService;

    @Resource
    private IProductService productService;

    @Resource
    private IMealSetService mealsetService;

    @Resource
    private IGiftProdService giftProdService;

    @Resource
    private IStorageService storageService;

    @Resource
    private IOriginalOrderService originalOrderService;

    @Resource
    private IRepositoryService repositoryService;

    @Resource
    private IShopService shopService;

    @Resource
    private ConfService confService;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Transactional(readOnly = true)
    @Override
    public TransformOrderResultBean transformToOrder(List<OriginalOrder> originalOrders) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<Order> results = new ArrayList<Order>(originalOrders.size());
        List<OriginalOrder> errorOriginalOrders = new ArrayList<OriginalOrder>();
        List<String> errorInfoList = new ArrayList<String>();
        for (OriginalOrder oo : originalOrders) {
            try {
                Order order = extractOrder(oo);
                results.add(order);
            } catch (OrderAnalyzeException e) {
                String errorMsg = "将原始订单转化成正式订单的时候发生错误," + e.getMessage();
                log.error(errorMsg);
                errorOriginalOrders.add(oo);
                errorInfoList.add(errorMsg);
            }

        }
        return new TransformOrderResultBean(results, errorOriginalOrders, errorInfoList);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> prepareImport(List<Order> orders) throws OrderAnalyzeException {
        //加赠品
//        addGiftToOrder(orders);
        //TODO 合并订单

        //拆分订单
        List<Order> results = splitOrder(orders);
        return results;
    }

    @Transactional
    @Override
    public void saveAnalyzeResults(List<Order> orders, Set<Integer> prcessedOriginalOrderIdSet) {
        for (Order order : orders) {
            orderService.createOrder(order);
        }

        originalOrderService.updateBatchProcessed(prcessedOriginalOrderIdSet.toArray(new Integer[]{0}));
    }

    /**
     * 拆分订单
     * 根据订单明细的产品所属仓库的不同,根据仓库拆分成多个订单.
     * 如果产品属于同一个仓库,不需要拆分
     * 如果订单内有邮费,停止拆分
     * 其他情况按照仓库进行订单拆分
     *
     * @param orders
     *
     * @return
     */
    private List<Order> splitOrder(List<Order> orders) throws OrderAnalyzeException {
        List<Order> results = new ArrayList<Order>();
        //仓库缓存map,key为repoId,value为Repository对象
        List<Repository> repositories = repositoryService.findRepository();
        Map<Integer, Repository> repositoryMap = new HashMap<Integer, Repository>();
        for(Repository repository : repositories) {
            repositoryMap.put(repository.getId(), repository);
        }

        //产品对应仓库的缓存map, key为productId, value为repoId,如果产品对应库存不存在,value为-1
        Map<Integer, Integer> productRepoCache = new HashMap<Integer, Integer>();
        for (Order order : orders) {
            results.addAll(splitSingleOrder(order, repositoryMap, productRepoCache));
        }

        return results;
    }

    private List<Order> splitSingleOrder(Order order, Map<Integer, Repository> repositoryMap,
                                   Map<Integer, Integer> productRepoCache) throws OrderAnalyzeException {
        List<Order> results = new ArrayList<Order>();
        List<OrderItem> orderItems = order.getOrderItemList();

        //key为repoId, value为productId列表
        Map<Integer, Set<Integer>> repoProductMap = classifyProductByRepo(productRepoCache, orderItems);

        if (repoProductMap.size() <= 1) {
            //订单明细中的产品对应仓库不多于1个,不需要拆分,单纯的把这个订单加入结果就好
            Integer repoId = repoProductMap.entrySet().iterator().next().getKey();
            handleSplitOrder(order, repositoryMap.get(repoId), OrderSplitStatus.NORMAL);
            results.add(order);
            return results;
        }

        if(order.getPostFee() != null && order.getPostFee() > 0) {
            //订单有邮费,停止自动拆单
            handleSplitOrder(order, null, OrderSplitStatus.WAIT_SPLIT);
            results.add(order);
            return results;
        }

        //根据订单明细所属仓库,对订单进行拆分
        boolean firstOne = true;
        List<OrderItem> waitingForAssignOrderItems = new ArrayList<OrderItem>();
        for (Map.Entry<Integer, Set<Integer>> entry : repoProductMap.entrySet()) {
            Integer repoId = entry.getKey();
            Set<Integer> productIds = entry.getValue();
            if (firstOne) {
                //如果是第一次循环,则查找产品不在该仓库的订单明细,从明细列表中删除并且放入待分配列表.
                for (Iterator<OrderItem> iter = orderItems.iterator(); iter.hasNext(); ) {
                    OrderItem orderItem = iter.next();
                    if (!productIds.contains(orderItem.getProdId())) {
                        waitingForAssignOrderItems.add(orderItem);
                        iter.remove();
                    }
                }
                //将主订单放入结果
                handleSplitOrder(order, repositoryMap.get(repoId), OrderSplitStatus.SPLIT_AUTO);
                results.add(order);
                firstOne = false;

            } else {
                //不是第一次循环,首先从主订单复制订单实体,然后从待分配列表中查找产品属于该仓库的明细,加入复制后的订单
                Order copiedOrder = copyForSplit(order);
                List<OrderItem> copiedOrderItems = new ArrayList<OrderItem>();
                for (Iterator<OrderItem> iter = waitingForAssignOrderItems.iterator(); iter.hasNext(); ) {
                    OrderItem waitingForAssignOrderItem = iter.next();
                    if (productIds.contains(waitingForAssignOrderItem.getProdId())) {
                        copiedOrderItems.add(waitingForAssignOrderItem);
                        iter.remove();
                    }
                }
                if (copiedOrderItems.isEmpty()) {
                    throw new OrderAnalyzeException(String.format("拆分订单时没有找到对应的订单项," +
                            "repoId[%d],prodIdSet[%s],outOrderNo[%s]", repoId, productIds.toString(), order.getOutOrderNo()));
                }
                copiedOrder.setOrderItemList(copiedOrderItems);
                handleSplitOrder(copiedOrder, repositoryMap.get(repoId), OrderSplitStatus.SPLIT_AUTO);
                results.add(copiedOrder);
            }
        }
        return results;
    }

    private Map<Integer, Set<Integer>> classifyProductByRepo(Map<Integer, Integer> productRepoCache, List<OrderItem> orderItems) {
        Map<Integer, Set<Integer>> repoProductMap = new LinkedHashMap<Integer, Set<Integer>>();
        for (OrderItem orderItem : orderItems) {
            Integer productId = orderItem.getProdId();
            Integer repoId = productRepoCache.get(productId);
            if (repoId == null) {
                //查询产品对应的仓库ID
                Storage storage = storageService.findStorageByProdId(productId);
                if (storage == null) {
                    log.warn("在拆分订单的时候,发现产品[sku={},id={}]对应库存为null", orderItem.getProd().getProdCode(), productId);
                    productRepoCache.put(productId, -1);
                    continue;
                }
                repoId = storage.getRepoId();
                productRepoCache.put(productId, repoId);
            } else if (repoId == -1) {
                //这个产品在系统中没有找到库存
                continue;
            }
            //此时repoId肯定存在
            Set<Integer> productIds = repoProductMap.get(repoId);
            if (productIds == null) {
                productIds = new LinkedHashSet<Integer>();
                repoProductMap.put(repoId, productIds);
            }
            productIds.add(productId);
        }
        return repoProductMap;
    }


    @Override
    @Transactional
    public List<Order> splitOrderByHand(Order order, int[] ids) throws OrderAnalyzeException {

        List<OrderItem> orderItemList = orderItemMapper.findOrderItemByOrderId(order.getId());
        if(orderItemList.size() <= 1) {
            throw new ErpBusinessException("手动拆单失败, 订单应该至少有2个订单项才能进行拆单");
        }
        order.setOrderItemList(orderItemList);

        //将选中的订单项移到另一个集合中
        List<OrderItem> targetOrderItemList = new ArrayList<OrderItem>();
        for(int targetOrderItemId : ids) {
            boolean findTargetOrderItem = false;
            for(Iterator<OrderItem> iterator = orderItemList.iterator(); iterator.hasNext();) {
                OrderItem orderItem = iterator.next();
                if(orderItem.getId().equals(targetOrderItemId)) {
                    //将该订单项移到拆分出来的新的订单中
                    targetOrderItemList.add(orderItem);
                    findTargetOrderItem = true;
                    iterator.remove();
                    break;
                }
            }
            if(!findTargetOrderItem) {
                String errorMsg = String.format("手动拆单失败,根据订单项ID[%s]无法找到订单项", targetOrderItemId);
                log.warn(errorMsg);
                throw new ErpBusinessException(errorMsg);
            }
        }

        Map<Integer, Integer> productRepoCache = new HashMap<Integer, Integer>();
        Map<Integer, Set<Integer>> targetRepoProductMap = classifyProductByRepo(productRepoCache, targetOrderItemList);
        if(targetRepoProductMap.size() != 1) {
            log.warn("手动拆单失败, ids[{}], repoSize[{}]", ArrayUtils.toString(ids), targetRepoProductMap.size());
            throw new ErpBusinessException("手动拆单失败,所选择的订单项不能拆分到同一仓库");
        }

        //把选中的订单项一起放入一个订单中
        List<Order> results = new ArrayList<Order>();
        Order copiedOrder = copyForSplit(order);
        copiedOrder.setOrderItemList(targetOrderItemList);
        handleSplitOrder(copiedOrder, repositoryService.findRepositoryById(targetRepoProductMap.keySet().iterator().next()), OrderSplitStatus.SPLIT_BY_HAND);
        results.add(copiedOrder);

        //判断剩下来的订单项是否能进同一个仓库,如果可以,则标识订单为正常的,否则标识为需要继续手动拆单
        Map<Integer, Set<Integer>> repoProductMap = classifyProductByRepo(productRepoCache, orderItemList);
        if(repoProductMap.isEmpty()) {
            log.error("手动拆单失败,拆单之后的订单项中的产品没有找到对应仓库");
            throw new ErpBusinessException("手动拆单失败,拆单之后的订单项中的产品没有找到对应仓库");
        }
        if(repoProductMap.size() == 1) {
            handleSplitOrder(order, repositoryService.findRepositoryById(repoProductMap.keySet().iterator().next()), OrderSplitStatus.SPLIT_BY_HAND);
        } else {
            handleSplitOrder(order, null, OrderSplitStatus.WAIT_SPLIT);
        }

        orderMapper.updateOrderByOrder(order);
        orderService.createOrder(copiedOrder);

        return results;
    }

    /**
     * 对拆分出来的订单的一些属性进行处理
     * @param order
     * @param repository
     * @param splitStatus
     */
    private void handleSplitOrder(Order order, Repository repository, OrderSplitStatus splitStatus) {
        if(repository != null) {
            order.setRepoId(repository.getId());
            order.setRepoName(repository.getName());
            order.setShippingComp(repository.getShippingComp());
        }
        //重新计算订单价格
        order.calPayment();
        //设置订单拆分状态
        order.setSplitStatus(splitStatus.toString());
    }


    /**
     * 复制订单信息
     *
     * @param order
     * @return
     * @throws com.ejushang.spider.exception.OrderAnalyzeException
     */
    private Order copyForSplit(Order order) throws OrderAnalyzeException {
        Order copiedOrder = new Order();
        try {
            PropertyUtils.copyProperties(copiedOrder, order);
            copiedOrder.setOrderItemList(null);
            copiedOrder.setId(null);
            return copiedOrder;
        } catch (IllegalAccessException e) {
            throw new OrderAnalyzeException("拷贝订单主体属性的时候发生错误", e);
        } catch (InvocationTargetException e) {
            throw new OrderAnalyzeException("拷贝订单主体属性的时候发生错误", e);
        } catch (NoSuchMethodException e) {
            throw new OrderAnalyzeException("拷贝订单主体属性的时候发生错误", e);
        }

    }

//    /**
//     * 为订单加赠品
//     * 1)单笔订单买了某个产品,加赠品.
//     * 2)单笔订单某个品牌总额满足一定金额,加赠品.
//     *
//     * @param orders
//     */
//    private void addGiftToOrder(List<Order> orders) {
//        for (Order order : orders) {
//            List<OrderGiftBean> orderGifts = giftProdService.queryGiftProductForOrder(order);
//            if (orderGifts.isEmpty()) continue;
//            List<OrderItem> orderItems = order.getOrderItemList();
//            for (OrderGiftBean orderGift : orderGifts) {
//                orderItems.add(createOrderItem(orderGift.getGiftProductCount(), OrderItemType.GIFT, order,
//                        productService.findProductById(orderGift.getGiftProductId()), null, null));
//            }
//        }
//    }

    /**
     * 从原始订单中分析生成真实订单
     *
     * @param originalOrder
     * @return
     * @throws com.ejushang.spider.exception.OrderAnalyzeException
     */
    private Order extractOrder(OriginalOrder originalOrder) throws OrderAnalyzeException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.WAIT_PROCESS.toString());
        order.setOrderType(OrderType.NORMAL.toString());
        order.setOutPlatformType(originalOrder.getOutPlatformType());
        order.setOutOrderNo(originalOrder.getOutOrderNo());
        order.setOriginalOrderId(originalOrder.getId());

        order.setBuyerId(originalOrder.getBuyerId());
        order.setBuyerMessage(originalOrder.getBuyerMessage());
        order.setBuyTime(originalOrder.getBuyTime());
        order.setPayTime(originalOrder.getPayTime());

        order.setReceiverName(originalOrder.getReceiverName());
        order.setReceiverPhone(originalOrder.getReceiverPhone());
        order.setReceiverMobile(originalOrder.getReceiverMobile());
        order.setReceiverCity(originalOrder.getReceiverCity());
        order.setReceiverDistrict(originalOrder.getReceiverDistrict());
        order.setReceiverAddress(originalOrder.getReceiverAddress());
        order.setReceiverZip(originalOrder.getReceiverZip());
        order.setReceiverState(originalOrder.getReceiverState());

        order.setTotalFee(originalOrder.getTotalFee());
        order.setShopId(originalOrder.getShopId() == null ? null : originalOrder.getShopId().intValue());
        order.setShopName(originalOrder.getShopName());
        order.setNeedInvoice(originalOrder.getNeedInvoice());
        order.setInvoiceName(originalOrder.getInvoiceName());
        order.setInvoiceContent(originalOrder.getInvoiceContent());
        order.setRemark(originalOrder.getRemark());

        order.setHasPostFee(originalOrder.getHasPostFee());
        order.setPostFee(originalOrder.getPostFee());

        List<OriginalOrderItem> originalOrderItems = originalOrder.getOriginalOrderItems();
        List<OrderItem> orderItems = new ArrayList<OrderItem>();

        //邮费虚拟产品
        String postageProductSku = confService.findConfValue(SystemConfConstant.POSTAGE_PRODUCT_SKU);

        if (originalOrderItems != null) {

            for (OriginalOrderItem ooItem : originalOrderItems) {

                //存放该原始订单项生成的订单项
                List<OrderItem> generatedOrderItems = new ArrayList<OrderItem>();

                String skuCode = ooItem.getSkuCode();
                if (StringUtils.isBlank(skuCode)) {
                    throw new OrderAnalyzeException(String.format("原始订单id[%d],sku为空", originalOrder.getId()));
                }

                if(skuCode.equals(postageProductSku)) {
                    //该订单项是虚拟邮费产品,需要将对应邮费加入订单的邮费中,然后跳过该订单项
                    order.setPostFee((order.getPostFee() == null ? 0 : order.getPostFee()) + 1 * ooItem.getBuyCount());
                    continue;
                }

                boolean findSku = false;
                //从产品表查找
                Product product = productService.findProductBySKU(skuCode, ProductType.PRODUCT.toString());
                if (product != null) {
                    int buyCount = ooItem.getBuyCount() == null ? 0 : ooItem.getBuyCount().intValue();
                    OrderItem orderItem = createOrderItem(buyCount, OrderItemType.PRODUCT, order, product, null, ooItem);
                    generatedOrderItems.add(orderItem);
//                    orderItems.add(extractOrderItem(ooItem, OrderItemType.PRODUCT, order, product, null));
                    findSku = true;
                } else {
                    //从套餐查询
                    Mealset mealset = mealsetService.findMealsetBySKU(skuCode);
                    if(mealset != null) {
                        List<MealsetItem> mealsetItems = mealset.getMealsetItemList();
                        if(mealsetItems != null) {
                            for(MealsetItem mealsetItem : mealsetItems) {
                                int buyCount = ooItem.getBuyCount() == null ? 0 : ooItem.getBuyCount().intValue();
                                Product mealsetItemProduct = productService.findProductById(mealsetItem.getProdId());
                                OrderItem orderItem = createOrderItem(buyCount, OrderItemType.MEALSET, order, mealsetItemProduct, mealsetItem, ooItem);
                                generatedOrderItems.add(orderItem);
//                                orderItems.add(extractOrderItem(ooItem, OrderItemType.MEALSET, order, productService.findProductById(mealsetItem.getProdId()), mealsetItem));
                                findSku = true;
                            }

                        }
                    }
                }

                if (!findSku) {
                    throw new OrderAnalyzeException(String.format("根据sku查询不到产品或套餐,原始订单id[%d],sku[%s]",
                            originalOrder.getId(), skuCode));
                }

                //将原始订单项中的折扣金额和手工调整金额按价格比例分配到对应的订单项中
                allocateOriginalOrderItemPayment(ooItem, generatedOrderItems);

                orderItems.addAll(generatedOrderItems);
            }
        }

        order.setOrderItemList(orderItems);

        //订单的邮费和整单优惠平均分配到订单项的邮费中
        allocateOrderPayment(order);

        return order;
    }

    /**
     * 订单的邮费和整单优惠平均分配到订单项的邮费中
     * @param order
     */
    private void allocateOrderPayment(Order order) {
        List<OrderItem> orderItemList = order.getOrderItemList();

        if(orderItemList == null) {
            return;
        }
        Long postFee = order.getPostFee();
        if(postFee == null || postFee <= 0) {
            return;
        }

        int itemCount = orderItemList.size();
        //每个订单项分配的邮费
        long postFeePerItem = Math.round(postFee.doubleValue() / itemCount);
        int index = 0;
        for (OrderItem orderItem : orderItemList) {
            //TODO 没有考虑套餐产品分解出多个产品的情况
            if(++index == itemCount) {
                //最后一个订单项分得剩下的邮费
                orderItem.setPostFee(postFee);

            } else {
                //邮费平均分配
                orderItem.setPostFee(postFeePerItem);
                postFee -= postFeePerItem;

            }

        }

    }

    /**
     * 将原始订单项中的折扣金额和手工调整金额按价格比例分配到对应的订单项中,套餐产品会使用到
     * @param originalOrderItem
     * @param generatedOrderItems
     * @return
     */
    private boolean allocateOriginalOrderItemPayment(OriginalOrderItem originalOrderItem, List<OrderItem> generatedOrderItems) {
        Long discountFee = originalOrderItem.getDiscountFee() == null ? 0 : originalOrderItem.getDiscountFee();
        Long adjustFee = originalOrderItem.getAdjustFee() == null ? 0 : originalOrderItem.getAdjustFee();

        if(generatedOrderItems.size() == 1) {
            OrderItem orderItem = generatedOrderItems.get(0);
            orderItem.setDiscountFee(discountFee);
            orderItem.setAdjustFee(adjustFee);
        } else {
            //外部平台(如天猫)套餐组合产品价格
            Long mealsetProductPrice = originalOrderItem.getPrice();
            //系统设置的套餐项价格总和(套餐项价格*数量)
            Long mealsetItemPriceSum = 0L;

            for(OrderItem orderItem : generatedOrderItems) {
                Long eachMealsetItemAmount = orderItem.getProdPrice() * orderItem.getProdCount();
                mealsetItemPriceSum += eachMealsetItemAmount;
            }

            //外部平台(如天猫)套餐组合产品价格 应该大于等于 系统设置的套餐项价格总和
//            if(mealsetProductPrice < mealsetItemPriceSum) {
//                log.warn("对从外部平台购买了套餐产品,进行金额平摊的时候发现问题: 外部平台套餐组合产品价格[{}]小于系统设置的套餐项价格总和[{}]", mealsetProductPrice, mealsetItemPriceSum);
//                return false;
//            }

            for(OrderItem orderItem : generatedOrderItems) {
                Long eachMealsetItemAmount = orderItem.getProdPrice() * orderItem.getProdCount();
                orderItem.setDiscountFee((long)Math.round(discountFee * eachMealsetItemAmount / mealsetItemPriceSum));
                orderItem.setAdjustFee((long) Math.round(adjustFee * eachMealsetItemAmount / mealsetItemPriceSum));
            }
        }

        return true;
    }


    /**
     * 创建订单明细
     *
     * @param buyCount
     * @param orderItemType
     * @param order
     * @param product
     * @param mealsetItem
     * @param originalOrderItem
     * @return
     */
    @Override
    public OrderItem createOrderItem(int buyCount, OrderItemType orderItemType,
                                      Order order, Product product, MealsetItem mealsetItem, OriginalOrderItem originalOrderItem) {

        OrderItem orderItem = new OrderItem();

        orderItem.setOutOrderNo(order.getOutOrderNo());

        orderItem.setProd(product);
        orderItem.setSkuCode(product.getProdCode());
        orderItem.setProdId(product.getId());
        orderItem.setProdCode(product.getProdNo());
        orderItem.setProdName(product.getProdName());


        if(originalOrderItem == null) {
            //手动下单
            switch (orderItemType) {
                case PRODUCT: {

                    orderItem.setProdCount(buyCount);
                    orderItem.setItemType(OrderItemType.PRODUCT.toString());
                    //如果是手动下单,则使用系统设置的产品价格为订单项价格
                    orderItem.setProdPrice(product.getShopPrice());
                    orderItem.setActualFee(orderItem.getProdPrice() * orderItem.getProdCount());
                    orderItem.setTotalFee(orderItem.getActualFee());
                    break;
                }
                case GIFT: {

                    orderItem.setProdCount(buyCount);
                    orderItem.setItemType(OrderItemType.GIFT.toString());
                    orderItem.setProdPrice(0L);

                    orderItem.setActualFee(0L);
                    orderItem.setTotalFee(0L);
                    break;
                }
                default: {
                    log.warn("手动下单创建了未知的订单项类型[{}], 原始订单项ID[{}]", orderItemType.toString(), originalOrderItem.getId());
                }

            }
        } else {
            //抓取订单
            switch (orderItemType) {
                case PRODUCT: {

                    orderItem.setProdCount(buyCount);
                    orderItem.setItemType(OrderItemType.PRODUCT.toString());
                    //如果是抓取的订单,则以实际抓来的价格为准
                    orderItem.setProdPrice(originalOrderItem.getPrice());

                    orderItem.setActualFee(originalOrderItem.getActualFee());
                    orderItem.setTotalFee(originalOrderItem.getTotalFee());
                    break;
                }
                case MEALSET: {

                    //如果是套餐,则订单明细购买数量=套餐订购数量 * 套餐中该明细数量
                    orderItem.setProdCount(buyCount * mealsetItem.getMealCount());
                    orderItem.setItemType(OrderItemType.MEALSET.toString());
                    orderItem.setProdPrice(mealsetItem.getMealPrice());

                    orderItem.setActualFee(orderItem.getProdPrice() * orderItem.getProdCount());
                    orderItem.setTotalFee(orderItem.getActualFee());
                    break;
                }
                default: {
                    log.warn("自动抓单创建了未知的订单项类型[{}], 原始订单项ID[{}]", orderItemType.toString(), originalOrderItem.getId());
                }
            }

            orderItem.setSubOutOrderNo(originalOrderItem.getSubOrderNo());
        }

        return orderItem;
    }

    @Override
    public void addGiftByHand(QueryProdVo[] queryProdVos1,String[] orderNos) {
        /**
         * 先通过orderNo查询出订单
         */
        for(String orderNo:orderNos){
        Order order=orderService.findOrderByOrderNo(orderNo);

        if(order==null){
            if(log.isInfoEnabled()){
                   log.info("根据订单号无法查询到订单，orderNO为："+orderNo);
            }
            throw new IllegalArgumentException("根据订单号无法查询到订单，orderNO为："+orderNo);
        }

        OrderUtil.checkSplitStatus(order, "加产品");

        List<OrderItem> orderItemList=orderItemService.findOrderItemByOrderId(order.getId());
        List<String> stringList=new ArrayList<String>();
        if(orderItemList.size()>0){
        for(OrderItem orderItem:orderItemList){
            stringList.add(orderItem.getSkuCode());
         }
        }
        if(log.isInfoEnabled()){
                 log.info("接收到的stringList为："+stringList.toString());
              }
        /**
         * 添加订单项
         */
          for(QueryProdVo q:queryProdVos1){
              Product product=productService.findProductBySKU(q.getSkuCode(),q.getItemType());
              product.setShopPrice(Money.YuanToCent(q.getProdPrice()));
              Storage storage=storageService.findStorageByProdId(product.getId());

              if(log.isInfoEnabled()){
                  log.info("接收到的product.getProdCode()为："+product.getProdCode());
              }
              if(!order.getRepoId().equals(storage.getRepoId())){
                 throw new IllegalArgumentException("加入的产品与订单里的商品不在一个仓库，不能添加！");
              }

              if(log.isInfoEnabled()){
                  log.info("是否包含："+stringList.contains(product.getProdCode()));
              }
              //if(stringList!=null && stringList.contains(product.getProdCode())){
//                  AddOrderItemQuery addOrderItemQuery=new AddOrderItemQuery();
//                  addOrderItemQuery.setOrderId(order.getId());
//                  addOrderItemQuery.setSkuCode(product.getProdCode());
//                  if(log.isInfoEnabled()){
//                      log.info("接收到的addOrderItemQuery为："+addOrderItemQuery.getOrderId()+"/"+addOrderItemQuery.getSkuCode());
//                  }
//                  OrderItem orderItem=orderItemService.findOrderItemByProdId(addOrderItemQuery);
//                  if(log.isInfoEnabled()){
//                      log.info("接收到的orderItem为："+orderItem.toString());
//                  }
//
//                  orderItem.setProdCount(orderItem.getProdCount()+q.getProdCount());
//                  orderItem.setActualFee(Money.YuanToCent(q.getActualFee())+orderItem.getActualFee());
//                  orderItem.setTotalFee(Money.YuanToCent(q.getTotalFee())+orderItem.getTotalFee());
//                  if(log.isInfoEnabled()){
//                      log.info("接收到的orderItem.getProdCount为："+orderItem.getProdCount());
//                      log.info("接收到的orderItem.getTotal为："+orderItem.getTotalFee());
//                  }
//                  orderItemService.updateOrderItemAddGift(orderItem);
//                  order.setTotalFee(order.getTotalFee()+Money.YuanToCent(q.getActualFee()));
//                  if(log.isInfoEnabled()){
//                      log.info("接收到的order.getTotalFee()为："+order.getTotalFee());
//                  }
                  OrderItemType orderItemType;
                  if(q.getItemType().equals("PRODUCT")){
                      orderItemType=OrderItemType.PRODUCT;
                  }else{
                      orderItemType=OrderItemType.GIFT;
                  }
                  if(log.isInfoEnabled()){
                      log.info("接收到的queryProdVo.getItemType()为："+q.getItemType());
                      log.info("接收到的queryProdVo.getPostFee()为："+q.getPostFee());
                  }
              OrderItem orderItem= createOrderItem(q.getProdCount(), orderItemType, order,
                                                   product, null, null);
              if(q.getPostFee().equals("")){
                  q.setPostFee("0");
              }
              orderItem.setPostFee(Money.YuanToCent(q.getPostFee()));
              orderItem.setActualFee(Money.YuanToCent(q.getActualFee()));
              orderItem.setOrderId(order.getId());
              orderItem.setOrderNo(order.getOrderNo());

              if(log.isInfoEnabled()){
                  log.info("接收到的order.getTotalFee()为："+order.getTotalFee());
                  log.info("接收到的orderItem的信息为："+orderItem.getProdCount()+"/"+orderItem.getProdPrice()+"/"+orderItem.getPostFee());
              }
              if(log.isInfoEnabled()){
                  log.info("接收到的order.getTotalFee()为："+order.getTotalFee());
              }
             orderItemService.saveOrderItem(orderItem);
              List<OrderItem> orderItems=orderItemService.findOrderItemByOrderId(order.getId());
              order.setOrderItemList(orderItems);
              order.calPayment();
        }
        orderService.updateOrderByOrder(order);
        }
    }

    @Override
    @Transactional
    public List<String> addOrderByHand(AddOrderVo addOrderVo,QueryProdVo[] queryProdVos) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, OrderAnalyzeException {
        if(log.isInfoEnabled()){
            log.info("接收到的addOrderVo为："+addOrderVo.toString());
            log.info("接收到的queryProdVos为："+queryProdVos.toString());
        }
        Order order=new Order();
        PropertyUtils.copyProperties(order,addOrderVo);
        order.setOrderStatus("WAIT_PROCESS");
        order.setOutOrderNo("");
        order.setOutPlatformType(OutPlatformType.SW_SPIDER.toDesc());
        order.setPayTime(new Date());
        if(log.isInfoEnabled()){
            log.info("接收到的addOrderVo为："+addOrderVo.getShopId());
            log.info("接收到的order.getShopId()为：" + order.getShopId());
        }
        order.setShopName(shopService.findShopByShopId(order.getShopId()).getTitle());
        //设置订单的订单项
        List<OrderItem> orderItemList=new ArrayList<OrderItem>();
        for(QueryProdVo queryProdVo:queryProdVos){
            OrderItemType orderItemType;
            if(queryProdVo.getItemType().equals("PRODUCT")){
                orderItemType=OrderItemType.PRODUCT;
            }else{
                orderItemType=OrderItemType.GIFT;
            }
           OrderItem orderItem=createOrderItem(queryProdVo.getProdCount(),orderItemType,order,
                             productService.findProductBySKU(queryProdVo.getSkuCode(),queryProdVo.getItemType()),null, null);
               if(queryProdVo.getPostFee().equals("")){
                   queryProdVo.setPostFee("0");
               }
            orderItem.setActualFee(Money.YuanToCent(queryProdVo.getActualFee()));
            orderItem.setTotalFee(Money.YuanToCent(queryProdVo.getTotalFee()));
            orderItem.setPostFee(Money.YuanToCent(queryProdVo.getPostFee()));
            orderItemList.add(orderItem);
        }
        order.setOrderItemList(orderItemList);
        if(log.isInfoEnabled()){
               log.info("order的详细信息是："+order.toString());
        }
        List<Order> orderList=new ArrayList<Order>();
        orderList.add(order);

       List<Order> orderList1= splitOrder(orderList);
        for(Order order1:orderList1){
            order1.setBuyTime(new Date());
            order1.calPayment();
            orderService.createOrder(order1);
        }

        List<String> stringList=new ArrayList<String>();
        for(Order order1:orderList1){
            stringList.add(order1.getOrderNo());
        }
        return stringList;
    }

    @Override
    public AddOrderVo copyOrderByHand(Integer id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Order order=orderService.findOrderById(id);
        if(log.isInfoEnabled()){
            log.info("order的详情是："+order.toString());
        }
        AddOrderVo addOrderVo=new AddOrderVo();
        PropertyUtils.copyProperties(addOrderVo,order);
        if(log.isInfoEnabled()){
            log.info("addOrderVo的详情是："+addOrderVo.toString());
        }
        return addOrderVo;
    }

    @Override
    public void preHandleOriginalOrders(List<OriginalOrder> originalOrders) {
        Set<String> outOrderNoSet = new HashSet<String>();
        //降序遍历原始订单列表,如果外部平台订单号出现重复,删除时间靠前的原始订单
        for(ListIterator<OriginalOrder> iter = originalOrders.listIterator(originalOrders.size()); iter.hasPrevious();) {
            OriginalOrder originalOrder = iter.previous();
            if(outOrderNoSet.contains(originalOrder.getOutOrderNo())) {
                iter.remove();
            } else {
                outOrderNoSet.add(originalOrder.getOutOrderNo());
            }
        }
        //遍历原始订单,根据外部订单号判断该订单属于更新还是新增
        for(Iterator<OriginalOrder> iter = originalOrders.iterator(); iter.hasNext();) {
            OriginalOrder originalOrder = iter.next();
            List<Order> orders = orderService.findOrderByOutOrderNo(originalOrder.getOutOrderNo());
            if(!orders.isEmpty()) {
                //如果不为空代表需要批量更新订单备注
                List<Integer> orderIds = new ArrayList<Integer>();
                for(Order order : orders) {
                    orderIds.add(order.getId());
                }
                orderMapper.updateOrderRemark(orderIds.toArray(new Integer[0]), originalOrder.getRemark());
                iter.remove();
            }
        }
    }

}
