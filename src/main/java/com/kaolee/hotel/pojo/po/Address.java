package com.kaolee.hotel.pojo.po;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class Address {
    @Id
    private Integer id;
    private Integer zipcode;
    private String detail;
    private String city;
    private String country;
    private String verificationToken;
}