package com.kaolee.hotel.service.Impl;

import com.kaolee.hotel.constant.DateTimeFormatConstant;
import com.kaolee.hotel.constant.MessageConstant;
import com.kaolee.hotel.exception.DateTimeFormatException;
import com.kaolee.hotel.exception.RoomNotFoundException;
import com.kaolee.hotel.pojo.dto.OrderDTO;
import com.kaolee.hotel.pojo.po.OrdersPO;
import com.kaolee.hotel.pojo.po.RoomsPO;
import com.kaolee.hotel.pojo.vo.OrderVO;
import com.kaolee.hotel.repository.OrdersRepository;
import com.kaolee.hotel.repository.RoomsRepository;
import com.kaolee.hotel.service.OrderService;
import com.kaolee.hotel.utils.DateTimeFormatCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private RoomsRepository roomsRepository;

    /**
     * save new order
     *
     * @param orderDTO
     * @return
     */
    @Override
    public OrderVO save(OrderDTO orderDTO) {
        //確認check in date格式
        boolean validDate = DateTimeFormatCheck
                .isValidDate(String.valueOf(orderDTO.getCheckInDate()), DateTimeFormatConstant.CHECK_IN_DATE_FORMAT);
        if (!validDate) {
            throw new DateTimeFormatException(MessageConstant.DATE_OR_TIME_FORMAT_EXCEPTION);
        }
        //確認房型存在
        String roomId = orderDTO.getRoomId();
        Optional<RoomsPO> optionalRoomsPO = roomsRepository.findById(roomId);
        RoomsPO roomsPO = optionalRoomsPO.orElseThrow(() -> new RoomNotFoundException(MessageConstant.ROOM_NOT_FOUND));

        //存入訂單
        OrdersPO ordersPO = new OrdersPO();
        BeanUtils.copyProperties(orderDTO, ordersPO);
        ordersRepository.save(ordersPO);
        LocalDate checkInDate = ordersPO.getCheckInDate();
        ordersPO = ordersRepository.findByRoomIdAndCheckInDate(roomId, checkInDate);
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(ordersPO,orderVO);
        orderVO.setRoomId(roomsPO);

        return orderVO;
    }


}
