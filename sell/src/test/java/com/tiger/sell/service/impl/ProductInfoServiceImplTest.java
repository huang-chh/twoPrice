package com.tiger.sell.service.impl;

import com.tiger.sell.entity.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(value = SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl productInfoService;
    @Test
    public void findOne() {
        ProductInfo productInfo =productInfoService.findOne("1234");
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void findAllUp() {
        List<ProductInfo> productInfos = productInfoService.findAllUp();
        Assert.assertNotNull("0",productInfos.size());
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<ProductInfo> productInfoPage =productInfoService.findAll(pageRequest);
        System.out.println("totalElement:"+productInfoPage.getTotalElements());
        System.out.println("totalPages:"+productInfoPage.getTotalPages());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(1235);
        productInfo.setProductName("脆皮油条");
        productInfo.setProductPrice(new BigDecimal(3.0));
        productInfo.setCategoryType(1);
        productInfo.setProductStatus(0);
        productInfo.setProductStock(4);
        productInfo.setProductIcon("2.jpg");
        productInfo.setProductDescription("香脆的油条");
        Assert.assertNotNull(productInfoService.save(productInfo));
    }
}