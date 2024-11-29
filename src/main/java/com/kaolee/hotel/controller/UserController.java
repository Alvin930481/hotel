package com.kaolee.hotel.controller;

import com.kaolee.hotel.constant.JwtClaimsConstant;
import com.kaolee.hotel.context.BaseContext;
import com.kaolee.hotel.pojo.dto.LoginInfo;
import com.kaolee.hotel.pojo.dto.SignupDTO;
import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.pojo.vo.LoginVO;
import com.kaolee.hotel.properties.JwtProperties;
import com.kaolee.hotel.service.UserService;
import com.kaolee.hotel.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

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

    /**
     * 使用者註冊
     * @param signupDTO
     * @return
     */
    @Operation( tags = {"User-使用者"},summary = "使用者註冊")
    @PostMapping("/signup")
    public Response<LoginVO> signup(@RequestBody SignupDTO signupDTO){
        log.info("用戶註冊：{}", signupDTO);
        return userService.signup(signupDTO);
    }

    /**
     * 檢查使用者登入
     * @param request
     * @param response
     * @return
     */
    @Operation( tags = {"User-使用者"},summary = "檢查使用者登入")
    @GetMapping("/check")
    public Response check(HttpServletRequest request, HttpServletResponse response){
        //1、從請求頭中獲取令牌
        String token = request.getHeader(jwtProperties.getUserTokenName());

        //2、校驗令牌
        try {
            log.info("jwt校驗:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            String userId = claims.get(JwtClaimsConstant.USER_ID).toString();
            log.info("目前用戶id：{}", userId);
            //3、驗證成功，回傳token
            return Response.success(token);
        } catch (Exception ex) {
            //4、不通過，響應401狀態
            response.setStatus(401);
            log.info("解析錯誤：{}",ex);
            return Response.failure("請重新登入");
        }
    }
}
