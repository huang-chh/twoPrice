package com.tiger.sell.dao;

import com.tiger.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(value = SpringRunner.class)
@SpringBootTest
public class ProductCategoryDAOTest {
    @Autowired
    private ProductCategoryDAO productCategoryDAO;

    @Test
    public void findOneTest(){
        ProductCategory category =productCategoryDAO.findOne(new Integer(3));
        Assert.assertEquals(new Integer(1),category.getCategoryType() );
    }

    @Test
    public void findAllTest(){
        List<ProductCategory> categoryList= productCategoryDAO.findAll();
        Assert.assertEquals(2,categoryList.size());
    }

    @Test
    public void updateTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(new Integer(4));
        productCategory.setCategoryName("男生榜");
        productCategory.setCategoryType(new Integer(3));
        productCategoryDAO.save(productCategory);
    }
    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> categoryTypeList = Arrays.asList(1,2,3);
        List<ProductCategory> result = productCategoryDAO.findByCategoryTypeIn(categoryTypeList);
        Assert.assertNotEquals(0,result.size());
    }
}