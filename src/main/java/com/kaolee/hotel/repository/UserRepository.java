package com.kaolee.hotel.repository;

import com.kaolee.hotel.pojo.po.UserPO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserPO,String> {

    UserPO findByEmail(String email);
}
