package com.tiger.sell.dao;

import com.tiger.sell.entity.ProductInfo;
import com.tiger.sell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(value = SpringRunner.class)
@SpringBootTest
public class ProductInfoDAOTest {

    @Autowired
    private ProductInfoDAO productInfoDAO;
    @Test
    public void save(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(1234);
        productInfo.setProductName("啥汤");
        productInfo.setProductPrice(new BigDecimal(4.5));
        productInfo.setCategoryType(3);
        productInfo.setProductStatus(0);
        productInfo.setProductStock(5);
        productInfo.setProductIcon("1.jpg");
        productInfo.setProductDescription("很好喝的sha汤");
        Assert.assertNotNull(productInfoDAO.save(productInfo));

    }

    @Test
    public void findByProductStatusTest() {
        List<ProductInfo> productInfos = productInfoDAO.findByProductStatus(ProductStatusEnum.Up.getCode());
        Assert.assertNotEquals(0,productInfos.size());
    }
}