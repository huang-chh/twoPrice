package com.tiger.sell.service.impl;

import com.tiger.sell.dao.OrderDetailDAO;
import com.tiger.sell.dao.OrderMasterDAO;
import com.tiger.sell.dao.ProductInfoDAO;
import com.tiger.sell.dto.CartDTO;
import com.tiger.sell.dto.OrderDTO;
import com.tiger.sell.entity.OrderDetail;
import com.tiger.sell.entity.OrderMaster;
import com.tiger.sell.entity.ProductInfo;
import com.tiger.sell.enums.ExceptionEnum;
import com.tiger.sell.enums.OrderStatusEnum;
import com.tiger.sell.enums.PayStatusEnum;
import com.tiger.sell.handle.SellException;
import com.tiger.sell.service.OrderService;
import com.tiger.sell.service.ProductInfoService;
import com.tiger.sell.utils.KeyGenerateUtil;
import com.tiger.sell.utils.OrderMasterToOrderDTO;
import org.hibernate.criterion.Order;
import org.hibernate.type.OrderedSetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private ProductInfoDAO productInfoDAO;
    @Autowired
    private OrderDetailDAO orderDetailDAO;
    @Autowired
    private OrderMasterDAO orderMasterDAO;
    @Autowired
    private ProductInfoService productInfoService;
    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        //订单总金额
        BigDecimal orderAmount =new BigDecimal(0);
        //主订单ID
        String orderId = KeyGenerateUtil.generateOrderMasterID();
        //订单详情列表
        //1、查询商品（价格、数量）
        for (OrderDetail orderDetail :orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productInfoDAO.findOne(orderDetail.getProductId());
            //校验商品库存是否满足订单数量
            if(productInfo==null ||productInfo.getProductStock()<orderDetail.getProductQuantity()){
                throw new SellException(ExceptionEnum.STOCK.getCode(),productInfo.getProductName()+"库存不足");
            }
            //2、计算总价
            //1）订单总额=各商品单价*订单商品数量
            orderAmount= productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //2)订单详情入库
            OrderDetail newOrderDetail1 = new OrderDetail();
            BeanUtils.copyProperties(productInfo,newOrderDetail1);
            newOrderDetail1.setOrderId(orderId);
            newOrderDetail1.setDetailId(KeyGenerateUtil.generateOrderDetailID());
            newOrderDetail1.setProductQuantity(orderDetail.getProductQuantity());//订单数量
            orderDetailDAO.save(newOrderDetail1);
        }
        //3、写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setOrderAmount(orderAmount);
        orderMasterDAO.save(orderMaster);
        //4、扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                 new CartDTO(e.getProductId(),e.getProductQuantity())
                ).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);
        orderDTO.setOrderId(orderId);
        return orderDTO;
    }

    @Override
    public OrderDTO findOneOrder(String orderId) {
        OrderMaster orderMaster =orderMasterDAO.findOne(orderId);
        if(orderMaster==null){
            throw new SellException(ExceptionEnum.ORDER.getCode(),"订单不存在");
        }
        List<OrderDetail> orderDetails = orderDetailDAO.findAllByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetails)){
            throw new SellException(ExceptionEnum.ORDER.getCode(),"订单详情不存在");
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetails);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findOrderList(String openId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage =orderMasterDAO.findByBuyerOpenid(openId,pageable);
        List<OrderMaster> orderMasterList = orderMasterPage.getContent();
        OrderDTO orderDTO = new OrderDTO();
        List<OrderDTO> orderDTOS = OrderMasterToOrderDTO.convert(orderMasterList);
        return new PageImpl<OrderDTO>(orderDTOS,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO finishOrder(OrderDTO orderDTO) {
        //1、查询订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            LOGGER.info("【完成订单】订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ExceptionEnum.ORDER.getCode(),"订单状态异常");
        }
        //2、判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            LOGGER.info("【完结订单】订单未支付，orderId={},payStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
        }
        //3、修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result = orderMasterDAO.save(orderMaster);
        if (result ==null){
            LOGGER.info("【完结订单】更新失败，orderMaster={}",orderMaster);
            throw new SellException(ExceptionEnum.ORDER.getCode(),"完结订单更新失败");
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO cancelOrder(OrderDTO orderDTO) {
        //1、查询订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            LOGGER.info("【取消订单】订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ExceptionEnum.ORDER.getCode(),"订单状态异常");
        }
        //2、修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result = orderMasterDAO.save(orderMaster);
        if (result ==null){
            LOGGER.info("【取消订单】更新失败，orderMaster={}",orderMaster);
            throw new SellException(ExceptionEnum.ORDER.getCode(),"取消订单更新失败");
        }
        //3、返回库存
        List<CartDTO> cartDTOList =orderDTO.getOrderDetailList().stream().map(
                e ->new CartDTO(e.getProductId(),e.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);
        //4、如果已支付，需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //todo
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO payOrder(OrderDTO orderDTO) {
        //1、判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            LOGGER.info("【完成订单】订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ExceptionEnum.ORDER.getCode(),"订单状态异常");
        }
        //2、订单支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            LOGGER.info("【支付订单】订单支付状态不正确，orderId={},payStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ExceptionEnum.ORDER.getCode(),"支付状态异常");
        }
        //3、修改订单支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result = orderMasterDAO.save(orderMaster);
        if (result ==null){
            LOGGER.info("【支付订单】更新失败，orderMaster={}",orderMaster);
            throw new SellException(ExceptionEnum.ORDER.getCode(),"支付订单更新失败");
        }
       return orderDTO;
    }
}
