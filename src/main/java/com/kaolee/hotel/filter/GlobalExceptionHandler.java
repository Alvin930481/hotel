package com.kaolee.hotel.filter;

import com.kaolee.hotel.exception.BaseException;
import com.kaolee.hotel.pojo.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
