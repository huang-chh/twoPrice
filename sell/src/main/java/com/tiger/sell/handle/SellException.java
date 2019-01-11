package com.tiger.sell.handle;

public class SellException extends RuntimeException {
    private Integer code;

    public SellException(Integer code,String msg) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
