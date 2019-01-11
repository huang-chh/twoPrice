package com.tiger.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductVO {
    @JsonProperty(value = "name")
    private String categoryName;
    @JsonProperty(value = "type")
    private Integer categoryType;
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOS;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    public List<ProductInfoVO> getProductInfoVOS() {
        return productInfoVOS;
    }

    public void setProductInfoVOS(List<ProductInfoVO> productInfoVOS) {
        this.productInfoVOS = productInfoVOS;
    }
}
