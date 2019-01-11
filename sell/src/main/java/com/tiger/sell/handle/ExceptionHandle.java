package com.tiger.sell.handle;

import com.tiger.sell.utils.ResultVOUtil;
import com.tiger.sell.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle extends Exception{
    @ExceptionHandler
    @ResponseBody
    public ResultVO handle(Exception e){
        if (e instanceof SellException){
            SellException sellException = (SellException) e;
            return ResultVOUtil.error(sellException.getCode(),sellException.getMessage());
        }else{
            return ResultVOUtil.error(-1,"未知错误");
        }
    }
}
