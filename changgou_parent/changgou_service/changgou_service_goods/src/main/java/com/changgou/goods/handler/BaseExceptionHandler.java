package com.changgou.goods.handler;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Program: ChangGou
 * @ClassName: BaseExceptionHandler
 * @Description: 公共异常处理类
 * @Author: KyleSun
 **/
@ControllerAdvice   // 声明该类是个增强类
public class BaseExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)  // 声明对哪个异常进行处理
    public Result error(Exception e) {
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, "当前系统繁忙，请稍后重试！");
    }

}
