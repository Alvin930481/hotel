package com.kaolee.hotel.repository;

import com.kaolee.hotel.pojo.po.RoomsPO;
import com.kaolee.hotel.pojo.po.UserPO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomsRepository extends MongoRepository<RoomsPO,String> {

}
