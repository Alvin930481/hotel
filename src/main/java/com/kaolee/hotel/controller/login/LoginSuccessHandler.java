package com.kaolee.hotel.controller.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaolee.hotel.exception.AuthenticationException;
import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 認證/登錄成功，事件處理器
 */
@Slf4j
@Component
public class LoginSuccessHandler extends
    AbstractAuthenticationTargetUrlRequestHandler implements AuthenticationSuccessHandler {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;
  @Autowired
  private JwtUtil jwtUtil;


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    Object principal = authentication.getPrincipal();
    if (principal == null || !(principal instanceof UserLoginInfo)) {
      throw new AuthenticationException(
          "登錄驗證成功後，authentication.getPrincipal()返回的Object物件必須是：UserLoginInfo！");
    }
    UserLoginInfo currentUser = (UserLoginInfo) principal;
    currentUser.setSessionId(UUID.randomUUID().toString());

    // 生成jwt token
    String email = currentUser.getEmail();
    Map<String, Object> claim = new LinkedHashMap<>();
    claim.put("userId",currentUser.getUserId());
    String token = jwtUtil.generateToken(claim,email);


    // 雖然APPLICATION_JSON_UTF8_VALUE過時了，但也要用。因為Postman工具不宣告utf-8編碼就會出現亂碼

    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    PrintWriter writer = response.getWriter();
    // 構建成功回應對象
    Response responseObj = Response.success(token);
    // 使用 Jackson 將 Java 物件轉換為 JSON 字串
    String jsonResponse = objectMapper.writeValueAsString(responseObj);
    writer.print(jsonResponse);
    log.info("登錄成功");
    writer.flush();
    writer.close();
  }

}
