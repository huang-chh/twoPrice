package com.tiger.sell.service;

import com.tiger.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    //创建订单
    OrderDTO createOrder(OrderDTO orderDTO);
    //查询单个订单
    OrderDTO findOneOrder(String orderId);
    //查询订单列表
    Page<OrderDTO> findOrderList(String  openId, Pageable pageable);
    //完成订单
    OrderDTO finishOrder(OrderDTO orderDTO);
    //取消订单
    OrderDTO cancelOrder(OrderDTO orderDTO);
    //支付订单
    OrderDTO payOrder(OrderDTO orderDTO);

}
