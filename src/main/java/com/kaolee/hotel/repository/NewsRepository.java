package com.kaolee.hotel.repository;

import com.kaolee.hotel.pojo.po.NewsPO;
import com.kaolee.hotel.pojo.po.UserPO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends MongoRepository<NewsPO,String> {


    NewsPO findByTitle(String title);
}
