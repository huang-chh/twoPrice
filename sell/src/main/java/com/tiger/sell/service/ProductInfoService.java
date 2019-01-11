package com.tiger.sell.service;

import com.tiger.sell.dto.CartDTO;
import com.tiger.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {
    ProductInfo findOne(String productId);

    List<ProductInfo> findAllUp();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    void increaseStock(List<CartDTO> cartDTOS);

    void decreaseStock(List<CartDTO> cartDTOS);

}
