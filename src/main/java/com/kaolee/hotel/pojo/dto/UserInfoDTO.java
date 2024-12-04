package com.kaolee.hotel.pojo.dto;

import lombok.Data;

@Data
public class UserInfoDTO {
    private UserUpdateAddressDTO address;
    private String name;
    private String phone;
    private String email;
}
