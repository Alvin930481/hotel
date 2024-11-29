package com.kaolee.hotel.service.Impl;

import com.kaolee.hotel.constant.JwtClaimsConstant;
import com.kaolee.hotel.constant.MessageConstant;
import com.kaolee.hotel.exception.EmailNotFoundException;
import com.kaolee.hotel.pojo.dto.LoginInfo;
import com.kaolee.hotel.pojo.dto.SignupDTO;
import com.kaolee.hotel.pojo.po.UserPO;
import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.pojo.vo.LoginVO;
import com.kaolee.hotel.properties.JwtProperties;
import com.kaolee.hotel.repository.UserRepository;
import com.kaolee.hotel.service.UserService;
import com.kaolee.hotel.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 用戶登入操作
     *
     * @param loginInfo
     */
    @Override
    public Response<LoginVO> login(LoginInfo loginInfo) {
        String email = loginInfo.getEmail();
        String password = loginInfo.getPassword();

        //依照email查詢用戶
        UserPO user = userRepository.findByEmail(email);
        //如果查詢不到，表示沒有該用戶，返回“使用者不存在”
        if (user==null){
            throw new EmailNotFoundException(MessageConstant.USER_NOT_FOUND);
        }
        //如果有該用戶
        //密碼不正確，返回“密碼錯誤”
        if (!password.equals(user.getPassword())){
            throw new EmailNotFoundException(MessageConstant.PASSWORD_IS_WRONG);
        }
        //如果密碼正確，產生jwt並返回
        log.info("使用者登錄密碼正確，產生JWT...");
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
        user.getAddress().setVerificationToken(null);

        LoginVO loginVO = new LoginVO();
        BeanUtils.copyProperties(user, loginVO);

        return Response.success(loginVO,token);
    }

    /**
     * 用戶註冊
     *
     * @param signupDTO
     * @return
     */
    @Override
    public Response<LoginVO> signup(SignupDTO signupDTO) {
        UserPO userPO = new UserPO();
        LoginVO loginVO = new LoginVO();
        BeanUtils.copyProperties(signupDTO,userPO);
        userRepository.save(userPO);
        BeanUtils.copyProperties(userPO,loginVO);
        return Response.success(loginVO);
    }
}
