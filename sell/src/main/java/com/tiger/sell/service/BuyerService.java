package com.tiger.sell.service;

import com.tiger.sell.dto.OrderDTO;

public interface BuyerService {
    OrderDTO findOneOrder(String openid,String orderId);

    OrderDTO cancel(String openid,String orderId);
}
