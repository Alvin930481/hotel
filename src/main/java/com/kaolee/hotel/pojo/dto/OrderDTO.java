package com.kaolee.hotel.pojo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderDTO {

    private Integer roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer peopleNum;
    private UserInfoDTO userInfo;

}
