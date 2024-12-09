package com.kaolee.hotel.service.Impl;


import com.kaolee.hotel.constant.MessageConstant;
import com.kaolee.hotel.exception.EmailNotFoundException;
import com.kaolee.hotel.pojo.dto.LoginInfo;
import com.kaolee.hotel.pojo.dto.SignupDTO;
import com.kaolee.hotel.pojo.dto.UserUpdateDTO;
import com.kaolee.hotel.pojo.entity.AddressPO;
import com.kaolee.hotel.pojo.entity.UserPO;
import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.pojo.vo.LoginVO;
import com.kaolee.hotel.pojo.vo.UserVO;
import com.kaolee.hotel.repository.UserRepository;
import com.kaolee.hotel.service.UserService;

import com.kaolee.hotel.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    /*
    */
/**
     * 用戶登入操作
     *
     * @param loginInfo
     *//*

    @Override
    public Response<LoginVO> login(LoginInfo loginInfo) {
        //依照email查詢用戶
        UserPO user = userRepository.findByEmail(loginInfo.getEmail())
                .orElseThrow(() -> new EmailNotFoundException(MessageConstant.USER_NOT_FOUND));
        validatePassword(loginInfo, user);
        //如果密碼正確，產生jwt並返回
        log.info("使用者登錄密碼正確，產生JWT...");
        //將用戶名放置subject、ＩＤ及ＥＭＡＩＬ放於claim
        HashMap<String, Object> claim = new HashMap<>();
        claim.put("id",user.getId());
        claim.put("email",user.getEmail());
        String jwtToken = jwtUtil.generateToken(claim,user);


        user.getAddress().setVerificationToken(null);

        LoginVO loginVO = new LoginVO();
        BeanUtils.copyProperties(user, loginVO);

        return Response.success(loginVO,jwtToken);
    }
*/

    /**
     * 檢核密碼
     *
     * @param loginInfo
     * @param user
     */
    private void validatePassword(LoginInfo loginInfo, UserPO user) {
        if (!loginInfo.getPassword().equals(user.getPassword())) {
            throw new EmailNotFoundException(MessageConstant.PASSWORD_IS_WRONG);
        }
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
        String encodePassword =
                passwordEncoder.encode(userPO.getPassword());
        userPO.setPassword(encodePassword);
        userRepository.save(userPO);
        BeanUtils.copyProperties(userPO,loginVO);
        return Response.success(loginVO);
    }

    /**
     * 查詢使用者
     * @return
     */
    @Override
    public UserVO getUser(String userId) {
        Optional<UserPO> user = userRepository.findById(userId);
        UserPO userPO = user.get();
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userPO,userVO);
        return userVO;
    }

    /**
     * 用戶更新
     * @param userUpdateDTO
     */
    @Override
    public void update(UserUpdateDTO userUpdateDTO) {
        //查詢舊密碼是否正確
        //依照email查詢用戶
        Optional<UserPO> userPO = userRepository.findByEmail(userUpdateDTO.getEmail());
        //如果查詢不到，表示沒有該用戶，返回“使用者不存在”
        if (userPO.isEmpty()){
            throw new EmailNotFoundException(MessageConstant.USER_NOT_FOUND);
        }
        //如果有該用戶
        //密碼不正確，返回“密碼錯誤”

        String oldPassword = userUpdateDTO.getOldPassword();
        String originPassword = userPO.get().getPassword();
        if (!originPassword.equals(oldPassword)){
            throw new EmailNotFoundException(MessageConstant.PASSWORD_IS_WRONG);
        }
        //TODO:有一個返回請重新登入，不知道作用。
        //有該用戶且舊密碼正確，更新資訊
        AddressPO addressPO = new AddressPO();
        BeanUtils.copyProperties(userUpdateDTO.getAddress(),addressPO);
        BeanUtils.copyProperties(userUpdateDTO, userPO);
        UserPO user = userPO.get();
        user.setAddress(addressPO);
        user.setId(userUpdateDTO.getUserId());
        user.setPassword(userUpdateDTO.getNewPassword());
        userRepository.save(user);
    }


}
