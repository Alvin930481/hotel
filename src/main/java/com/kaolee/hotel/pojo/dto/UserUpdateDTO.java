package com.kaolee.hotel.pojo.dto;

import lombok.Data;

import java.time.LocalDate;


@Data
public class UserUpdateDTO {

    private String userId;
    private UserUpdateAddressDTO address;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthday;
    private String newPassword;
    private String oldPassword;
}
