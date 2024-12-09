package com.kaolee.hotel.repository;

import com.kaolee.hotel.pojo.entity.UserPO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserPO,String> {

    Optional<UserPO> findByEmail(String email);
}
