package com.kaolee.hotel.pojo.vo;

import com.kaolee.hotel.pojo.po.AddressPO;
import lombok.Data;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserVO {
    private String id;
    private String _id = id;
    private AddressPO address;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthday;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
