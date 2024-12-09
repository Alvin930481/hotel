package com.kaolee.hotel.controller.login.usernameLogin;

import com.kaolee.hotel.constant.MessageConstant;
import com.kaolee.hotel.controller.login.UserLoginInfo;
import com.kaolee.hotel.exception.EmailNotFoundException;
import com.kaolee.hotel.pojo.entity.UserPO;
import com.kaolee.hotel.repository.UserRepository;
import com.kaolee.hotel.service.Impl.UserServiceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 帳號密碼登入認證
 */
@Component
public class UsernameAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public UsernameAuthenticationProvider() {
    super();
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    // 用户提交的用户名 + 密碼：
    String email = (String)authentication.getPrincipal();
    String password = (String) authentication.getCredentials();

    // 查尋資料庫，驗證用戶
    UserPO userPO = userRepository.findByEmail(email).get();
    if (userPO == null
        || !passwordEncoder.matches(password, userPO.getPassword())) {
      // 密碼錯誤，直接抛異常。
      // 根據SpringSecurity框架的程式碼邏輯，認證失敗時，應該拋這個異常：org.springframework.security.core.AuthenticationException
      // BadCredentialsException就是這個異常的子类
      // 拋出異常後，AuthenticationFailureHandler的實現類會處理這個異常。
      throw new BadCredentialsException("用戶名或密碼不正確");
    }

    UsernameAuthentication token = new UsernameAuthentication();
    UserLoginInfo userLoginInfo = UserLoginInfo.builder()
            .userId(userPO.getId())
            .build();
    BeanUtils.copyProperties(userPO, userLoginInfo);
    token.setCurrentUser(userLoginInfo);
    token.setAuthenticated(true); // 認證通過。
    return token;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.isAssignableFrom(UsernameAuthentication.class);
  }
}

