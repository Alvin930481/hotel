package com.kaolee.hotel.pojo.vo;

import com.kaolee.hotel.pojo.dto.UserInfoDTO;
import com.kaolee.hotel.pojo.entity.RoomsPO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderVO {

    private String id;
    private RoomsPO roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer peopleNum;
    private String OderUserId;
    private Integer status;
    private UserInfoDTO userInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}