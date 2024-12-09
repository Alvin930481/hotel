package com.kaolee.hotel.service;

import com.kaolee.hotel.pojo.dto.LoginInfo;
import com.kaolee.hotel.pojo.dto.SignupDTO;
import com.kaolee.hotel.pojo.dto.UserUpdateDTO;
import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.pojo.vo.LoginVO;
import com.kaolee.hotel.pojo.vo.UserVO;

public interface UserService {
/*

    */
/**
     * 登入操作
     * @param loginInfo
     *//*

    Response<LoginVO> login(LoginInfo loginInfo);
*/

    /**
     * 用戶註冊
     *
     * @param signupDTO
     * @return
     */
    Response<LoginVO> signup(SignupDTO signupDTO);

    /**
     * 查詢使用者
     * @return
     */
    UserVO getUser(String userId);

    /**
     * 用戶更新
     * @param userUpdateDTO
     */
    void update(UserUpdateDTO userUpdateDTO);
}
