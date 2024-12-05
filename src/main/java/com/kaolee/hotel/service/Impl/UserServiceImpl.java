package com.kaolee.hotel.service.Impl;

import com.kaolee.hotel.utils.JwtService;
import com.kaolee.hotel.constant.MessageConstant;
import com.kaolee.hotel.exception.EmailNotFoundException;
import com.kaolee.hotel.pojo.dto.LoginInfo;
import com.kaolee.hotel.pojo.dto.SignupDTO;
import com.kaolee.hotel.pojo.dto.UserUpdateDTO;
import com.kaolee.hotel.pojo.po.AddressPO;
import com.kaolee.hotel.pojo.po.UserPO;
import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.pojo.vo.LoginVO;
import com.kaolee.hotel.pojo.vo.UserVO;
import com.kaolee.hotel.repository.UserRepository;
import com.kaolee.hotel.service.UserService;
import com.kaolee.hotel.utils.token.Token;
import com.kaolee.hotel.utils.token.TokenRepository;
import com.kaolee.hotel.utils.token.TokenType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    /**
     * 用戶登入操作
     *
     * @param request
     */
    @Override
    public Response<LoginVO> login(LoginInfo request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        //依照email查詢用戶
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EmailNotFoundException(MessageConstant.USER_NOT_FOUND));
        validatePassword(request, user);
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);

        //如果密碼正確，產生jwt並返回
        log.info("使用者登錄密碼正確，產生JWT...");
        user.getAddress().setVerificationToken(null);

        LoginVO loginVO = new LoginVO();
        BeanUtils.copyProperties(user, loginVO);

        return Response.success(loginVO,jwtToken);
    }

    /**
     * 檢核密碼
     *
     * @param request
     * @param user
     */
    private void validatePassword(LoginInfo request, UserPO user) {
        if (!request.getPassword().equals(user.getPassword())) {
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
        UserPO savedUser = userRepository.save(userPO);
        String jwtToken = jwtService.generateToken(userPO);
        saveUserToken(savedUser, jwtToken);
        BeanUtils.copyProperties(userPO,loginVO);
        return Response.success(loginVO);
    }

    private void saveUserToken(UserPO user, String jwtToken) {
        var token = Token.builder()
                .userId(user.getId())
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(UserPO user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
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
