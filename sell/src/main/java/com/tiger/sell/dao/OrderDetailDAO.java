package com.tiger.sell.dao;

import com.tiger.sell.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailDAO extends JpaRepository<OrderDetail,String> {
    List<OrderDetail> findAllByOrderId(String orderId);


}
