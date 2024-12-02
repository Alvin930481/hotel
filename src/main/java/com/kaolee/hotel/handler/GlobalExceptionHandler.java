package com.kaolee.hotel.handler;

import com.kaolee.hotel.constant.MessageConstant;
import com.kaolee.hotel.exception.BaseException;
import com.kaolee.hotel.pojo.response.Response;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 處理資料庫異常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Response exceptionHandler(MongoException ex){
        //Write operation error on server cluster0-shard-00-02.qwysd.mongodb.net:27017. Write error: WriteError{code=11000, message='E11000 duplicate key error collection: hotel_sys.users index: email_1 dup key: { email: "kao@kao.com" }', details={}}.
        //Duplicate entry 'zhangsan' for key 'employee.idx_username'
        log.info("捕捉到資料庫異常：{}",ex);
        String message = ex.getMessage();
        if(message.contains("duplicate key")){
            String[] split = message.split(" ");
            String email = split[21].replace("\"","");
            String msg = email + MessageConstant.ALREADY_EXISTS;
            return Response.failure(msg);
        }else{
            return Response.failure(MessageConstant.UNKNOWN_ERROR);
        }
    }

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
     * 捕獲所有異常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Response exceptionHandler(Exception ex){
        log.error("未知異常訊息：{}", ex);
        return Response.failure(MessageConstant.UNKNOWN_ERROR);
    }

}
