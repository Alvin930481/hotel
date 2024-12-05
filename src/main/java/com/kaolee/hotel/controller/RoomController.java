package com.kaolee.hotel.controller;


import com.kaolee.hotel.pojo.po.RoomsPO;
import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    /**
     * 取得所有房型
     * @return
     */
    @Operation(tags = {"Rooms - 房型"},summary = "取得所有房型")
    @GetMapping
    public Response<List<RoomsPO>> getAllRooms(){
        List<RoomsPO> all = roomService.getAll();
        return Response.success(all);
    }

    /**
     * 依據ＩＤ取得房型
     * @return
     */
    @Operation(tags = {"Rooms - 房型"},summary = "依據ＩＤ取得房型")
    @GetMapping("/{id}")
    public Response<RoomsPO> getAllRoom(@PathVariable String id){
        RoomsPO roomsPO = roomService.getById(id);
        return Response.success(roomsPO);
    }
}
