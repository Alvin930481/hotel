package com.kaolee.hotel.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "orders")
public class OrdersPO {
    @Id
    private ObjectId id;
    private Integer roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer peopleNum;
    private Integer OderUserId;
    private Integer status;
    //TODO
    private UserPO userInfo;
}
