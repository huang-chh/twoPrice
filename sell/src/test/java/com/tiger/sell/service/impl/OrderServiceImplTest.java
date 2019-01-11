package com.tiger.sell.service.impl;

import com.tiger.sell.dao.OrderDetailDAO;
import com.tiger.sell.dao.OrderMasterDAO;
import com.tiger.sell.dto.OrderDTO;
import com.tiger.sell.entity.OrderDetail;
import com.tiger.sell.entity.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private OrderMasterDAO orderMasterDAO;
    @Autowired
    private OrderDetailDAO orderDetailDAO;

    @Test
    public void createOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerOpenid("aaaaaaaaaa");
        orderDTO.setBuyerAddress("国元大厦");
        orderDTO.setBuyerName("国元");
        orderDTO.setBuyerPhone("12345678909");
        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductId("1235");
        orderDetail1.setProductQuantity(1);
        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProductId("1234");
        orderDetail2.setProductQuantity(1);
        List<OrderDetail> orderDetails = Arrays.asList(orderDetail1,orderDetail2);
        orderDTO.setOrderDetailList(orderDetails);
        OrderDTO result = orderService.createOrder(orderDTO);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOneOrder() {
    }

    @Test
    public void findOrderList() {
    }

    @Test
    public void finishOrder() {
        OrderDTO orderDTO = new OrderDTO();
        OrderMaster orderMaster =orderMasterDAO.findOne("a1539432950433827162");
        List<OrderDetail> orderDetails = orderDetailDAO.findAllByOrderId("a1539432950433827162");
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetails);
        OrderDTO result = orderService.finishOrder(orderDTO);
        Assert.assertNotNull(result);
    }

    @Test
    public void cancelOrder() {
        OrderDTO orderDTO = new OrderDTO();
        OrderMaster orderMaster =orderMasterDAO.findOne("a1539432303307638579");
        List<OrderDetail> orderDetails = orderDetailDAO.findAllByOrderId("a1539432303307638579");
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetails);
        OrderDTO result = orderService.cancelOrder(orderDTO);
        Assert.assertNotNull(result);
    }

    @Test
    public void payOrder() {
        OrderDTO orderDTO = new OrderDTO();
        OrderMaster orderMaster =orderMasterDAO.findOne("a1539434174650403782");
        List<OrderDetail> orderDetails = orderDetailDAO.findAllByOrderId("a1539434174650403782");
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetails);
        OrderDTO result = orderService.payOrder(orderDTO);
        Assert.assertNotNull(result);

    }
}