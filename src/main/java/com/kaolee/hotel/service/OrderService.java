package com.kaolee.hotel.service;

import com.kaolee.hotel.pojo.dto.OrderDTO;
import com.kaolee.hotel.pojo.po.OrdersPO;
import com.kaolee.hotel.pojo.vo.OrderVO;

public interface OrderService {

    /**
     * 新增訂單
     * @param orderDTO
     * @return
     */
    OrderVO save(OrderDTO orderDTO);
}
