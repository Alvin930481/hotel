package com.kaolee.hotel.handler;

import com.kaolee.hotel.constant.MessageConstant;
import com.kaolee.hotel.exception.BaseException;
import com.kaolee.hotel.pojo.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕獲服務異常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Response exceptionHandler(BaseException ex){
        log.error("異常訊息：{}", ex.getMessage());
        return Response.failure(ex.getMessage());
    }


    /**
     * 處理資料庫異常
     * @param ex
     * @return
     */
//    @ExceptionHandler
    public Response exceptionHandler(Exception ex){
        //"error": "E11000 duplicate key error collection: test.users index: email_1 dup key: { email: \"test@example.com\" }"
        //Duplicate entry 'zhangsan' for key 'employee.idx_username'
        String message = ex.getMessage();
        if(message.contains("Duplicate key")){
            String[] split = message.split(" ");
            String email = split[12];
            String msg = email + MessageConstant.ALREADY_EXISTS;
            return Response.failure(msg);
        }else{
            return Response.failure(MessageConstant.UNKNOWN_ERROR);
        }
    }
}
