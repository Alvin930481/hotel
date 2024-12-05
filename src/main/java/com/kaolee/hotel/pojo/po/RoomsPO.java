package com.kaolee.hotel.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "rooms")
public class RoomsPO {
    @Id
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private List<String> imageUrlList;
    private String areaInfo;
    private String bedInfo;
    private Integer maxPeople;
    private Integer price;
    //TODO: change the type to enum
    private Integer status;
    private List<RoomInfoPO> layoutInfo;
    private List<RoomInfoPO> facilityInfo;
    private List<RoomInfoPO> amenityInfo;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
