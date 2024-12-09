package com.kaolee.hotel.pojo.dto;

import com.kaolee.hotel.pojo.entity.RoomInfoPO;

import lombok.Data;

import java.util.List;

@Data
public class RoomsDTO {

    private String name;
    private String description;
    private String imageUrl;
    private List<String> imageUrlList;
    private String areaInfo;
    private String bedInfo;
    private Integer maxPeople;
    private Integer price;
    private List<RoomInfoPO> layoutInfo;
    private List<RoomInfoPO> facilityInfo;
    private List<RoomInfoPO> amenityInfo;
}
