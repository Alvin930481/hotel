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
        String message = ex.getMessage();
        if(message.contains("duplicate key")){
            log.info("捕捉到資料庫資料重複：{}", message);
            String[] split = message.split(" ");
            String email = split[21].replace("\"","");
            String msg = email + MessageConstant.ALREADY_EXISTS;
            return Response.failure(msg);
        }else{
            log.error("未知異常訊息：{}", ex);
            return Response.failure(MessageConstant.UNKNOWN_ERROR);
        }
    }

}
