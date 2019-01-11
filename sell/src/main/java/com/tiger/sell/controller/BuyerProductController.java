package com.tiger.sell.controller;

import com.tiger.sell.dao.ProductCategoryDAO;
import com.tiger.sell.entity.ProductCategory;
import com.tiger.sell.entity.ProductInfo;
import com.tiger.sell.service.ProductCategoryService;
import com.tiger.sell.service.ProductInfoService;
import com.tiger.sell.utils.ResultVOUtil;
import com.tiger.sell.vo.ProductInfoVO;
import com.tiger.sell.vo.ProductVO;
import com.tiger.sell.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductCategoryDAO productCategoryDAO;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService categoryService;
    @GetMapping("/list")
    public ResultVO list(){
        //1、查询所有的上架商品
        List<ProductInfo> productInfos = productInfoService.findAllUp();
        //2、查询类目（一次性查询）
        //（1）传统方式
//        List<Integer> productCategoryList = new ArrayList<Integer>();
//        for (ProductInfo productInfo :productInfos){
//            productCategoryList.add(productInfo.getCategoryType());
//        }
        //(2)java8新特性，lamba
        List<Integer> productCategoryList = productInfos.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(productCategoryList);

        List<ProductVO> voList = new ArrayList<>();
        for (ProductCategory category:categoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(category.getCategoryType());
            productVO.setCategoryName(category.getCategoryName());
            List<ProductInfoVO> infoVOS = new ArrayList<>();
            for (ProductInfo productInfo:productInfos){
                if(productInfo.getCategoryType().equals(category.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    infoVOS.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOS(infoVOS);
            voList.add(productVO);
        }

        return ResultVOUtil.success(voList);
    }

    @GetMapping(value = "/save")
    public ProductCategory save(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("热销榜");
        productCategory.setCategoryType(new Integer(1));
        return productCategoryDAO.save(productCategory);
    }

}
