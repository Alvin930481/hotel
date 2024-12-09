package com.kaolee.hotel.service;

import com.kaolee.hotel.pojo.dto.OrderDTO;
import com.kaolee.hotel.pojo.vo.OrderVO;

import java.util.List;

public interface OrderService {

    /**
     * 新增訂單
     * @param orderDTO
     * @return
     */
    OrderVO save(OrderDTO orderDTO);

    /**
     * 查詢所有訂單
     * @return
     */
    List<OrderVO> getAll();

    /**
     * get by ID
     * @param id
     * @return
     */
    OrderVO getById(String id);

    /**
     * delete by id
     * @param id
     * @return
     */
    OrderVO deleteById(String id);

    /**
     * update order by id
     * @param id
     * @param orderDTO
     * @return
     */
    OrderVO update(String id, OrderDTO orderDTO);
}
