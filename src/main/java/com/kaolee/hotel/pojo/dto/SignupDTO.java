package com.kaolee.hotel.pojo.dto;

import com.kaolee.hotel.pojo.entity.AddressPO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignupDTO {
    private AddressPO address;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthday;
    private String password;
}
