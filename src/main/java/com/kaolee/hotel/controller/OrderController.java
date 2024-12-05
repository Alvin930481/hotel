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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 新增訂單
     * @param orderDTO
     * @return
     */
    @Operation(tags = {"Orders - 訂單"}, summary = "新增訂單")
    @PostMapping
    public Response<OrderVO> saveOrder(@RequestBody OrderDTO orderDTO) {
        OrderVO orderVO = orderService.save(orderDTO);
        return Response.success(orderVO);
    }

    /**
     * 取得所有訂單
     * @return
     */
    @Operation(tags = {"Orders - 訂單"}, summary = "取得所有訂單")
    @GetMapping
    public Response<List<OrderVO>> getAllOrders(){
        List<OrderVO> orderVOS = orderService.getAll();
        return Response.success(orderVOS);
    }

    /**
     * 依據ＩＤ取得訂單
     * @param id
     * @return
     */
    @Operation(tags = {"Orders - 訂單"}, summary = "依據ＩＤ取得訂單")
    @GetMapping("/{id}")
    public Response<OrderVO> getOrdersById(@PathVariable String id){
        OrderVO orderVO = orderService.getById(id);
        return Response.success(orderVO);
    }

    /**
     * 依據ＩＤ刪除訂單
     * @param id
     * @return
     */
    @Operation(tags = {"Orders - 訂單"}, summary = "依據ＩＤ刪除訂單")
    @DeleteMapping("/{id}")
    public Response<OrderVO> deleteById(@PathVariable String id){
        OrderVO orderVO = orderService.deleteById(id);
        return Response.success(orderVO);
    }


}
