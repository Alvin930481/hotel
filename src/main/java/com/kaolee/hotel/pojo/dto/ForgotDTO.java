package com.kaolee.hotel.pojo.dto;

import lombok.Data;

@Data
public class ForgotDTO {
    private String email;
    private String code;
    private String newPassword;
}
