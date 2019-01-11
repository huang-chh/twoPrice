package com.tiger.sell.controller;

import com.tiger.sell.dto.OrderDTO;
import com.tiger.sell.entity.OrderDetail;
import com.tiger.sell.entity.OrderMaster;
import com.tiger.sell.enums.ExceptionEnum;
import com.tiger.sell.form.OrderForm;
import com.tiger.sell.handle.SellException;
import com.tiger.sell.service.BuyerService;
import com.tiger.sell.service.OrderService;
import com.tiger.sell.utils.OrderFormToOrderDTO;
import com.tiger.sell.utils.ResultVOUtil;
import com.tiger.sell.vo.ResultVO;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {
    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyerOrderController.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerService buyerService;

    /**
     * 创建订单
     * @param orderForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid  OrderForm orderForm, BindingResult bindingResult){
        //1、表单验证
        if (bindingResult.hasErrors()){
            LOGGER.info("表单验证未通过：orderForm={}",orderForm);
            throw new SellException(ExceptionEnum.FORM.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO= OrderFormToOrderDTO.convert(orderForm);
        OrderDTO result = orderService.createOrder(orderDTO);
        Map<String,String> map =new HashMap<>();
        map.put("items",result.getOrderId());
        return ResultVOUtil.success(map);
    }

    /**
     * 订单列表
     * @param openid
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResultVO<List<OrderMaster>> list(@RequestParam String openid,
                                            @RequestParam(value = "page",defaultValue = "0") Integer page,
                                            @RequestParam(value = "size",defaultValue = "10") Integer size){
        PageRequest pageRequest = new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findOrderList(openid,pageRequest);
        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    /**
     * 查询订单详情
     * @param openid
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    public ResultVO detail(@RequestParam String openid,
                           @RequestParam String orderId){
        OrderDTO orderDTO =buyerService.findOneOrder(openid,orderId);
        return ResultVOUtil.success(orderDTO);
    }

    /**
     * 取消订单
     * @param openid
     * @param orderId
     * @return
     */
    @PostMapping("cancel")
    public ResultVO cancel(@RequestParam String openid,
                           @RequestParam String orderId){
        OrderDTO result =buyerService.cancel(openid,orderId);
        return ResultVOUtil.success(result);

    }

}
