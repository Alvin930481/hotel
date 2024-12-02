package com.kaolee.hotel.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewsDTO {
    private String title;
    private String description;
    private String image;
}
