package com.kaolee.hotel.service;

import com.kaolee.hotel.pojo.dto.RoomsDTO;
import com.kaolee.hotel.pojo.entity.RoomsPO;

import java.util.List;

public interface RoomService {

    RoomsPO save(RoomsDTO roomsDTO);

    List<RoomsPO> getAll();

    RoomsPO update(String id,RoomsDTO roomsDTO);

    RoomsPO delete(String id);

    RoomsPO getById(String id);
}
