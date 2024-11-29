package com.kaolee.hotel.service;

import com.kaolee.hotel.pojo.dto.LoginInfo;
import com.kaolee.hotel.pojo.dto.SignupDTO;
import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.pojo.vo.LoginVO;

public interface UserService {

    /**
     * 登入操作
     * @param loginInfo
     */
    Response<LoginVO> login(LoginInfo loginInfo);

    /**
     * 用戶註冊
     *
     * @param signupDTO
     * @return
     */
    Response<LoginVO> signup(SignupDTO signupDTO);
}
