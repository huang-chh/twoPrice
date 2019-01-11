package com.tiger.sell.service.impl;

import com.tiger.sell.dao.ProductInfoDAO;
import com.tiger.sell.dto.CartDTO;
import com.tiger.sell.entity.ProductInfo;
import com.tiger.sell.enums.ExceptionEnum;
import com.tiger.sell.handle.SellException;
import com.tiger.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoDAO productInfoDAO;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoDAO.findOne(productId);
    }

    @Override
    public List<ProductInfo> findAllUp() {
        return productInfoDAO.findByProductStatus(0);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoDAO.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoDAO.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOS) {
        for (CartDTO cartDTO:cartDTOS){
            ProductInfo productInfo = productInfoDAO.findOne(cartDTO.getProductId());
            if(productInfo==null){
                throw new SellException(ExceptionEnum.PRODUCT.getCode(),"商品不存在");
            }
            productInfo.setProductStock(productInfo.getProductStock()+cartDTO.getProductQuantity());
            productInfoDAO.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOS) {
        for (CartDTO cartDTO:cartDTOS){
            ProductInfo productInfo = productInfoDAO.findOne(cartDTO.getProductId());
            if(productInfo==null){
                throw new SellException(ExceptionEnum.PRODUCT.getCode(),"商品不存在");
            }
            if(cartDTO.getProductQuantity()>productInfo.getProductStock()){
                throw new SellException(ExceptionEnum.STOCK.getCode(),"库存不足");
            }
            productInfo.setProductStock(productInfo.getProductStock()-cartDTO.getProductQuantity());
            productInfoDAO.save(productInfo);
        }

    }
}
