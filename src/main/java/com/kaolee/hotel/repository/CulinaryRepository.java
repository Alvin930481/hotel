package com.kaolee.hotel.repository;

import com.kaolee.hotel.pojo.po.CulinaryPO;
import com.kaolee.hotel.pojo.po.UserPO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CulinaryRepository extends MongoRepository<CulinaryPO,String> {

}
