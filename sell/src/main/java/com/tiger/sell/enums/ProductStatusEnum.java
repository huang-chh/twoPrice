package com.tiger.sell.enums;

public enum ProductStatusEnum {
    Up(0,"上架"),Down(1,"下架");

    private Integer code;

    private String msg;

    ProductStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
