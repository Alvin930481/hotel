package com.kaolee.hotel.controller;

import com.kaolee.hotel.pojo.dto.OrderDTO;
import com.kaolee.hotel.pojo.po.OrdersPO;
import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.pojo.vo.OrderVO;
import com.kaolee.hotel.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Operation(tags = {"Orders - 訂單"}, summary = "新增訂單")
    @PostMapping
    public Response<OrderVO> save(@RequestBody OrderDTO orderDTO) {
        OrderVO orderVO = orderService.save(orderDTO);
        return Response.success(orderVO);
    }
}
