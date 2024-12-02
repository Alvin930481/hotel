package com.kaolee.hotel.controller;

import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.pojo.vo.IsMailExistVO;
import com.kaolee.hotel.service.UserService;
import com.kaolee.hotel.service.VerfyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/Verify")
public class VerifyController {
    @Autowired
    private VerfyService verfyService;

    @PostMapping("/email")
    public Response<IsMailExistVO> verifyEmail(@RequestBody String email){
        log.info("驗證信箱：{}",email);
        boolean isMailExist = verfyService.verifyEmail(email);
        IsMailExistVO isMailExistVO = new IsMailExistVO(isMailExist);
        return Response.success(isMailExistVO);
    }
}
