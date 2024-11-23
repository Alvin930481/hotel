package com.kaolee.hotel.repository;

import com.kaolee.hotel.pojo.po.UserPO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MyFirstRepository extends MongoRepository<UserPO,String> {
    UserPO findByName(String name);
}
