package com.kaolee.hotel.service.Impl;

import com.kaolee.hotel.constant.MessageConstant;
import com.kaolee.hotel.exception.RoomNotFoundException;
import com.kaolee.hotel.exception.RoomPriceEmptyException;
import com.kaolee.hotel.pojo.dto.RoomsDTO;
import com.kaolee.hotel.pojo.po.RoomInfoPO;
import com.kaolee.hotel.pojo.po.RoomsPO;
import com.kaolee.hotel.repository.RoomsRepository;
import com.kaolee.hotel.service.RoomService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomsRepository repository;

    /**
     * 新增房型
     *
     * @param roomsDTO
     * @return
     */
    @Override
    public RoomsPO save(RoomsDTO roomsDTO) {
        if (roomsDTO.getPrice()==null || roomsDTO.getPrice().describeConstable().isEmpty()){
            throw new RoomPriceEmptyException(MessageConstant.ROOM_PRICE_EMPTY);
        }
        RoomsPO roomsPO = new RoomsPO();
        BeanUtils.copyProperties(roomsDTO, roomsPO);
        repository.save(roomsPO);
        roomsPO = repository.findByName(roomsDTO.getName());
        return roomsPO;
    }

    /**
     * 查詢所有房型
     *
     * @return
     */
    @Override
    public List<RoomsPO> getAll() {
        return repository.findAll();
    }

    /**
     * 更新房型
     * @param id
     * @param roomsDTO
     * @return
     */
    @Override
    public RoomsPO update(String id,RoomsDTO roomsDTO) {
        //驗證必填欄位
        if (roomsDTO.getPrice()==null || roomsDTO.getPrice().describeConstable().isEmpty()){
            throw new RoomPriceEmptyException(MessageConstant.ROOM_PRICE_EMPTY);
        }
        //確認有該條資料
        Optional<RoomsPO> optionalRoomsPO = repository.findById(id);
        RoomsPO roomsPO = optionalRoomsPO.orElseThrow(() -> new RoomNotFoundException(MessageConstant.ROOM_NOT_FOUND));
        BeanUtils.copyProperties(roomsDTO,roomsPO);
        repository.save(roomsPO);
        return repository.findById(id).get();
    }

    /**
     * 刪除房型
     * @param id
     * @return
     */
    @Override
    public RoomsPO delete(String id) {
        //確認有該條資料
        Optional<RoomsPO> optionalRoomsPO = repository.findById(id);
        RoomsPO roomsPO = optionalRoomsPO.orElseThrow(() -> new RoomNotFoundException(MessageConstant.ROOM_NOT_FOUND));
        repository.deleteById(id);
        return roomsPO;
    }
}
