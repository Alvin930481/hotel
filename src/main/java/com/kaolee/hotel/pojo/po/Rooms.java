package com.kaolee.hotel.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Rooms {
    @Id
    private Integer id;
    private String name;
    private String description;
    private String imageUrl;
    //TODO:檢查型態是否更換
    private String[] imageUrlList;
    private String areaInfo;
    private String bedInfo;
    private Integer maxPeople;
    private Double price;
    //TODO: change the type to enum
    private Integer status;
    //TODO: 不知道是什麼，先打
    private Object[] layoutInfo;
    private Object[] facilityInfo;
    private Object[] amenityInfo;
}
