package com.gz.vhr.exception;

import com.gz.vhr.bean.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @ClassName GlobalExceptionHandler
 * @Description TODO
 * @Author 孔明灯
 * @Data 2021/9/13 10:23
 * @Version 1.0
 */
//全局捕获spring mvc抛的异常
@RestControllerAdvice
public class GlobalExceptionHandler {

    //捕获指定的异常
    @ExceptionHandler(SQLException.class)
    public RespBean sqlException(SQLException e){
        if (e instanceof SQLIntegrityConstraintViolationException){
            return RespBean.fail("该数据有关联数据，操作失败！");
        }
        return RespBean.fail("数据库异常，操作失败！");
    }
}
