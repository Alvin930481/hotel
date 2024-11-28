package com.kaolee.hotel.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "culinaries")
public class CulinaryPO {
    @Id
    private ObjectId id;
    private String title;
    private String description;
    private String diningTime;
    private String image;
}