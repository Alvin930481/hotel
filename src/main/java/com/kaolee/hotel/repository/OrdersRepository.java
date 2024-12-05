package com.kaolee.hotel.repository;

import com.kaolee.hotel.pojo.po.OrdersPO;
import com.kaolee.hotel.pojo.po.UserPO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface OrdersRepository extends MongoRepository<OrdersPO,String> {

    OrdersPO findByRoomIdAndCheckInDate(String roomId, LocalDate checkInDate);
}
