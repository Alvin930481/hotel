package com.kaolee.hotel.repository;

import com.kaolee.hotel.pojo.entity.RoomsPO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomsRepository extends MongoRepository<RoomsPO,String> {

    RoomsPO findByName(String name);
}
