package com.kaolee.hotel.pojo.response;

import lombok.Data;

@Data
public class ErrorResponse {
    private boolean status;
    private String message;


    public static  ErrorResponse error(String msg) {
        ErrorResponse response = new ErrorResponse();
        response.message = msg;
        response.status=false;
        return response;
    }
}
