package com.kaolee.hotel.controller.user;

import com.kaolee.hotel.constant.JwtClaimsConstant;
import com.kaolee.hotel.context.BaseContext;
import com.kaolee.hotel.pojo.dto.ForgotDTO;
import com.kaolee.hotel.pojo.dto.LoginInfo;
import com.kaolee.hotel.pojo.dto.SignupDTO;
import com.kaolee.hotel.pojo.dto.UserUpdateDTO;
import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.pojo.vo.LoginVO;
import com.kaolee.hotel.pojo.vo.UserVO;
import com.kaolee.hotel.service.UserService;
import com.kaolee.hotel.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtService;


    /**
     * 使用者登入，其實可以不需要，因為在過濾器已經做掉，可是為了讓swagger生成自動測試，所以一樣加入。
     *
     * @param loginInfo
     * @return
     */
    @Operation(tags = {"User-使用者"}, summary = "使用者登入", security = {})
    @PostMapping("/login")
    public Response<LoginVO> login(@RequestBody LoginInfo loginInfo) {
        return null;
    }


    /**
     * 使用者註冊
     *
     * @param signupDTO
     * @return
     */
    @Operation(tags = {"User-使用者"}, summary = "使用者註冊", security = {})
    @PostMapping("/signup")
    public Response<LoginVO> signup(@RequestBody SignupDTO signupDTO) {
        log.info("用戶註冊：{}", signupDTO);
        return userService.signup(signupDTO);
    }

    /**
     * 檢查使用者登入
     * @param request
     * @param response
     * @return
     */
    @Operation(tags = {"User-使用者"}, summary = "檢查使用者登入")
    @GetMapping("/check")
    public Response check(HttpServletRequest request, HttpServletResponse response) {

        //1、從請求頭中獲取令牌
        String token = request.getHeader(jwtService.getUserTokenName());

        //2、校驗令牌
        try {
            log.info("jwt校驗:{}", token);

            Claims claims = jwtService.extractAllClaims(token);
            String userId = claims.get(JwtClaimsConstant.USER_ID).toString();
            log.info("目前用戶id：{}", userId);
            //3、驗證成功，回傳token
            return Response.success(token);
        } catch (Exception ex) {
            //4、不通過，響應401狀態
            response.setStatus(401);
            log.info("解析錯誤：{}", ex);
            return Response.failure("請重新登入");
        }
    }


    /**
     * 取得使用者資訊
     *
     * @return Response<UserVO>
     */
    //TODO:不確定功能，先當作確認session
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = {"User-使用者"}, summary = "取得使用者資訊")
    @GetMapping
    public Response<UserVO> getUser() {
        String currentId = BaseContext.getCurrentId();
        UserVO userVO = userService.getUser(currentId);
        return Response.success(userVO);
    }


    /**
     * 更新使用者資訊
     *
     * @param userUpdateDTO
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = {"User-使用者"}, summary = "更新使用者資訊")
    @PutMapping
    public void userUpdate(@RequestBody UserUpdateDTO userUpdateDTO) {
        userService.update(userUpdateDTO);
    }


    @Operation(tags = {"User-使用者"}, summary = "忘記密碼")
    @PostMapping("/forgot")
    public Response forgot(@RequestBody ForgotDTO forgotDTO){
        userService.forgot(forgotDTO);
        return Response.success();
    }


}
