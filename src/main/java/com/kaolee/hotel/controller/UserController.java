package com.kaolee.hotel.controller;

import com.kaolee.hotel.pojo.dto.LoginInfo;
import com.kaolee.hotel.pojo.dto.SignupDTO;
import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.pojo.vo.LoginVO;
import com.kaolee.hotel.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 使用者登入
     * @param loginInfo
     * @return
     */
    @Operation( tags = {"User-使用者"},summary = "使用者登入")
    @PostMapping("/login")
    public Response<LoginVO> login(@RequestBody LoginInfo loginInfo){
        log.info("用戶登入操作:{}",loginInfo);

        try {
            //登入成功，返回使用者
            return userService.login(loginInfo);
        } catch (Exception e) {
            //捕捉到異常，返回異常訊息
            log.info("登入異常,{}",e);
            return Response.failure(e.getMessage());
        }
    }

    @Operation( tags = {"User-使用者"},summary = "使用者註冊")
    @PostMapping("/signup")
    public Response<LoginVO> signup(@RequestBody SignupDTO signupDTO){
        log.info("用戶註冊：{}", signupDTO);
        return userService.signup(signupDTO);
    }
}
