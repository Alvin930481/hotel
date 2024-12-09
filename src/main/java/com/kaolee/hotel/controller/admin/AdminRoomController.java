package com.kaolee.hotel.controller.admin;

import com.kaolee.hotel.pojo.dto.RoomsDTO;
import com.kaolee.hotel.pojo.entity.RoomsPO;
import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/rooms")
public class AdminRoomController {
    @Autowired
    private RoomService roomService;

    /**
     * 新增房型
     * @param roomsDTO
     * @return
     */
    @Operation(tags = {"Admin/Rooms - 房型管理"}, summary = "新增房型")
    @PostMapping
    public Response<RoomsPO> saveRoom(@RequestBody RoomsDTO roomsDTO){
        RoomsPO roomsPO = roomService.save(roomsDTO);
        return Response.success(roomsPO);
    }

    /**
     * 查詢房型
     */
    @Operation(tags = {"Admin/Rooms - 房型管理"}, summary = "查詢房型")
    @GetMapping
    public Response<List<RoomsPO>> getAllRooms(){
        List<RoomsPO> roomsPOS = roomService.getAll();
        return Response.success(roomsPOS);
    }

    /**
     * 更新房型
     */
    @Operation(tags = {"Admin/Rooms - 房型管理"}, summary = "更新房型")
    @PutMapping("/{id}")
    public Response<RoomsPO> updateRooms(@PathVariable String id,@RequestBody RoomsDTO roomsDTO){
        RoomsPO roomsPOS = roomService.update(id,roomsDTO);
        return Response.success(roomsPOS);
    }

    /**
     * 刪除房型
     */
    @Operation(tags = {"Admin/Rooms - 房型管理"}, summary = "刪除房型")
    @DeleteMapping("/{id}")
    public Response<RoomsPO> deleteRooms(@PathVariable String id){
        RoomsPO roomsPOS = roomService.delete(id);
        return Response.success(roomsPOS);
    }
}
