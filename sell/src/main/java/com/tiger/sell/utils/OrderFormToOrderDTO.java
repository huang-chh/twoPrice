package com.tiger.sell.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tiger.sell.dto.OrderDTO;
import com.tiger.sell.entity.OrderDetail;
import com.tiger.sell.enums.ExceptionEnum;
import com.tiger.sell.form.OrderForm;
import com.tiger.sell.handle.SellException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;

import java.util.ArrayList;
import java.util.List;

public class OrderFormToOrderDTO {
    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderFormToOrderDTO.class);

    public static OrderDTO convert(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();
        Gson gson = new Gson();
      orderDTO.setBuyerName(orderForm.getName());
      orderDTO.setBuyerPhone(orderForm.getPhone());
      orderDTO.setBuyerAddress(orderForm.getAddress());
      orderDTO.setBuyerOpenid(orderForm.getOpenid());
      List<OrderDetail> orderDetails = new ArrayList<>();
      try {
          orderDetails =  gson.fromJson(orderForm.getItems(),
                  new TypeToken<List<OrderDetail>>(){}.getType());
      }catch (Exception e){
          LOGGER.info("数据转换异常 string={}",orderForm.getItems());
          throw new SellException(ExceptionEnum.PARAMS.getCode(),e.getMessage().toString());
      }

        orderDTO.setOrderDetailList(orderDetails);
      return orderDTO;
    }
}
