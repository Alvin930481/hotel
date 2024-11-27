package com.kaolee.hotel.repository;

import com.kaolee.hotel.pojo.po.OrdersPO;
import com.kaolee.hotel.pojo.po.UserPO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdersRepository extends MongoRepository<OrdersPO,String> {

}
