package com.tiger.sell.mapper;

import com.tiger.sell.entity.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductInfoMapperTest {

    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Test
    public void insertProductInfoTest() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductStock(3);
        productInfo.setProductName("好吃的豆腐脑");
        productInfo.setProductPrice(new BigDecimal(3));
        productInfo.setCategoryType(1);
        productInfoMapper.insertProductInfo(productInfo);
    }
    @Test
    public void getProductInfoTest(){
        ProductInfo productInfo  = productInfoMapper.getProductInfo(1235);
        Assert.assertNotNull(productInfo);
    }


}