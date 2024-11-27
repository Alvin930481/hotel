package com.kaolee.hotel.pojo.vo;

import com.kaolee.hotel.pojo.po.AddressPO;
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
public class LoginVO {


        private ObjectId id;
        private AddressPO address;
        private String name;
        private String email;
        private String phone;
        private LocalDate birthday;
        private String password;
        private String verificationToken;
    }

