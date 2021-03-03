package com.hnucm.xinglinonlineschool.utils;

import com.hnucm.xinglinonlineschool.pojo.Result;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class MyControllerAdvice {


    @ResponseBody
    @ExceptionHandler(value = SQLException.class)
    public Result sqlHandler(Exception ex) {
        return Result.error(501,ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MyBatisSystemException.class)
    public Result sqlPHandler(Exception ex) {
        return Result.error(510,"数据库密码失败");
    }

    /**
     * 全局异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result errorHandler(Exception ex) {
        System.out.println("errorsssss"+ex.getClass().getName());
        return Result.error(500,ex.getMessage());
    }

}