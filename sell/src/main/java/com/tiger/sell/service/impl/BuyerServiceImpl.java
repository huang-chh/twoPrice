package com.tiger.sell.service.impl;

import com.tiger.sell.dto.OrderDTO;
import com.tiger.sell.enums.ExceptionEnum;
import com.tiger.sell.handle.SellException;
import com.tiger.sell.service.BuyerService;
import com.tiger.sell.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyerServiceImpl.class);
    @Autowired
    private OrderService orderService;
    @Override
    public OrderDTO findOneOrder(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOneOrder(orderId);
        if (orderDTO==null){
            return null;
        }
        if (!orderDTO.getBuyerOpenid().equals(openid)){
            LOGGER.info("【查询订单】openid 不一致，openid={},orderDTO={}",openid,orderDTO);
            throw new SellException(ExceptionEnum.ORDER.getCode(),"openid不一致");
        }
        return orderDTO;
    }

    @Override
    public OrderDTO cancel(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOneOrder(orderId);
        if (orderDTO==null){
            return null;
        }
        if (!orderDTO.getBuyerOpenid().equals(openid)){
            LOGGER.info("【查询订单】openid 不一致，openid={},orderDTO={}",openid,orderDTO);
            throw new SellException(ExceptionEnum.ORDER.getCode(),"openid不一致");
        }
        OrderDTO result = orderService.cancelOrder(orderDTO);
        return result;
    }
}
