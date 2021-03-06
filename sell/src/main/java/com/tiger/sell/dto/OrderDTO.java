package com.tiger.sell.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tiger.sell.entity.OrderDetail;
import com.tiger.sell.enums.OrderStatusEnum;
import com.tiger.sell.enums.PayStatusEnum;
import com.tiger.sell.utils.DateToLongUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDTO {
    private String orderId;
    /** 买家名称*/
    private String buyerName;
    /** 买家电话号*/
    private String buyerPhone;
    /** 买家地址*/
    private String buyerAddress;
    /** 买家openid*/
    private String buyerOpenid;
    /** 订单总金额*/
    private BigDecimal orderAmount;
    /** 订单状态*/
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    /** 支付状态*/
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    @JsonSerialize(using = DateToLongUtil.class)
    private Date createTime;
    @JsonSerialize(using = DateToLongUtil.class)
    private Date updateTime;

    //private  List<OrderDetail> orderDetailList = new ArrayList<>();设置初始化后，返回json是如果为null会转换为[]
    private  List<OrderDetail> orderDetailList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getBuyerOpenid() {
        return buyerOpenid;
    }

    public void setBuyerOpenid(String buyerOpenid) {
        this.buyerOpenid = buyerOpenid;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}
