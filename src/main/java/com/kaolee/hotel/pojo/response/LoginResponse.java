package com.kaolee.hotel.pojo.response;

import com.kaolee.hotel.pojo.vo.LoginVO;
import lombok.Data;

@Data
public class LoginResponse<T> {
    private boolean status;
    private String token;
    private T result;


    public static <T> LoginResponse<T> succeed(String token,T result) {
        LoginResponse<T> response = new LoginResponse<>();
        response.token = token;
        response.status = true;
        return response;
    }
}
