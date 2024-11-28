package com.kaolee.hotel.pojo.po;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressPO {
    private Integer zipcode;
    private String detail;
    private String city;
    private String country;
    private String verificationToken;
}
