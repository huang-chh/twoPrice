package com.tiger.sell.enums;

public enum ExceptionEnum {
    FORM(100,"表单验证异常"),
    ORDER(101,"订单异常"),
    STOCK(102,"库存异常"),
    PRODUCT(103,"商品异常"),
    PARAMS(110,"数据异常"),
    WXMPINFO(104,"公账号方面异常")
    ;

    private Integer code;

    private String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
