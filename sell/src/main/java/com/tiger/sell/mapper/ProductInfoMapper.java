package com.tiger.sell.mapper;

import com.tiger.sell.entity.ProductInfo;
import org.apache.ibatis.annotations.*;

public interface ProductInfoMapper {
    @Insert("insert into product_info(product_name,product_price,product_stock,category_type) values " +
            "(#{productName},#{productPrice},#{productStock},#{categoryType})")
    void insertProductInfo(ProductInfo productInfo);



    @Select("select * from product_info where product_id =#{id}")
    @Results({
            @Result(column = "product_id" ,property = "productId"),
            @Result(column = "product_name" ,property = "productName"),
            @Result(column = "product_price" ,property = "productPrice"),
            @Result(column = "product_stock" ,property = "productStock"),
            @Result(column = "product_description" ,property = "productDescription"),
            @Result(column = "product_icon" ,property = "productIcon"),
            @Result(column = "category_type" ,property = "categoryType"),
            @Result(column = "product_status" ,property = "productStatus"),
            @Result(column = "create_time" ,property = "createTime"),
            @Result(column = "update_time" ,property = "updateTime")
    })
    ProductInfo getProductInfo(Integer haha);




}
