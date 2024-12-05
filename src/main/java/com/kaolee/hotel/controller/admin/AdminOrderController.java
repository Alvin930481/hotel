package com.kaolee.hotel.controller.admin;

import com.kaolee.hotel.pojo.dto.OrderDTO;
import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.pojo.vo.OrderVO;
import com.kaolee.hotel.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/orders")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 取得所有訂單
     * @return
     */
    @Operation(tags = {"Admin/Orders - 訂單管理"}, summary = "取得所有訂單")
    @GetMapping
    public Response<List<OrderVO>> getAllOrders(){
        List<OrderVO> orderVOS = orderService.getAll();
        return Response.success(orderVOS);
    }

    /**
     * 依據ＩＤ刪除訂單
     * @param id
     * @return
     */
    @Operation(tags = {"Admin/Orders - 訂單管理"}, summary = "依據ＩＤ刪除訂單")
    @DeleteMapping("/{id}")
    public Response<OrderVO> deleteById(@PathVariable String id){
        OrderVO orderVO = orderService.deleteById(id);
        return Response.success(orderVO);
    }



    @Operation(tags = {"Admin/Orders - 訂單管理"}, summary = "修改訂單")
    @PutMapping("/{id}")
    public Response<OrderVO> updateOrder(@PathVariable String id,@RequestBody OrderDTO orderDTO) {
        OrderVO orderVO = orderService.update(id,orderDTO);
        return Response.success(orderVO);
    }
}
