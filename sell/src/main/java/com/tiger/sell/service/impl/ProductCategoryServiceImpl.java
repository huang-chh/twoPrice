package com.tiger.sell.service.impl;

import com.tiger.sell.dao.ProductCategoryDAO;
import com.tiger.sell.entity.ProductCategory;
import com.tiger.sell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDAO categoryDAO;
    @Override
    public ProductCategory findOne(Integer categoryId) {
        return categoryDAO.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return categoryDAO.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return categoryDAO.save(productCategory);
    }
}
