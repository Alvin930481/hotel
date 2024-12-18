package com.kaolee.hotel.pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private Boolean status;
    private String token;
    private String message;
    private T result;

    public static Response success(){
        Response response = new Response();
        response.setStatus(true);
        return response;
    }

    public static Response success(String token){
        Response response = new Response();
        response.setStatus(true);
        response.setToken(token);
        return response;
    }


    public static <T> Response<T> success(T result) {
        Response<T> response = new Response<>();
        response.setStatus(true);
        response.setResult(result);
        return response;
    }

    public static <T> Response<T> success(T result, String token) {
        Response<T> response = success(result);
        response.setToken(token);
        return response;
    }

    public static <T> Response<T> failure(String message) {
        Response<T> response = new Response<>();
        response.setStatus(false);
        response.setMessage(message);
        return response;
    }
}