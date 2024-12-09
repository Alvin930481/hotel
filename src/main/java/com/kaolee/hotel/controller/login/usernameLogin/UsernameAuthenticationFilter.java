package com.kaolee.hotel.controller.login.usernameLogin;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Map;


public class UsernameAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private static final Logger logger = LoggerFactory.getLogger(UsernameAuthenticationFilter.class);

  public UsernameAuthenticationFilter(AntPathRequestMatcher pathRequestMatcher,
      AuthenticationManager authenticationManager,
      AuthenticationSuccessHandler authenticationSuccessHandler,
      AuthenticationFailureHandler authenticationFailureHandler) {
    super(pathRequestMatcher);
    setAuthenticationManager(authenticationManager);
    setAuthenticationSuccessHandler(authenticationSuccessHandler);
    setAuthenticationFailureHandler(authenticationFailureHandler);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    logger.debug("use UsernameAuthenticationFilter");

    // 提取請求資料
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> requestMapData = objectMapper.readValue(request.getInputStream(), Map.class);
    String username = requestMapData.get("email").toString();
    String password = requestMapData.get("password").toString();

    // 封装成Spring Security需要的物件
    UsernameAuthentication authentication = new UsernameAuthentication();
    authentication.setUsername(username);
    authentication.setPassword(password);
    authentication.setAuthenticated(false);

    // 開始登錄驗證。SpringSecurity會利用 Authentication物件去找尋 AuthenticationProvider進行登錄驗證
    return getAuthenticationManager().authenticate(authentication);
  }

}
