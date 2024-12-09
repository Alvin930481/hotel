package com.kaolee.hotel.repository;

import com.kaolee.hotel.pojo.entity.CulinaryPO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CulinaryRepository extends MongoRepository<CulinaryPO,String> {

     CulinaryPO findByTitle(String title);

}
