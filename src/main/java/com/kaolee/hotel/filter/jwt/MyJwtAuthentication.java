package com.kaolee.hotel.filter.jwt;

import com.kaolee.hotel.controller.login.UserLoginInfo;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class MyJwtAuthentication extends AbstractAuthenticationToken {

  private String jwtToken;
  private UserLoginInfo currentUser;

  public MyJwtAuthentication() {
    // 關於權限，沒用到，先回傳null
    super(null);
  }

  @Override
  public Object getCredentials() {
    return isAuthenticated() ? null : jwtToken;
  }

  @Override
  public Object getPrincipal() {
    return isAuthenticated() ? currentUser : jwtToken;
  }

  public String getJwtToken() {
    return jwtToken;
  }

  public void setJwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
  }

  public UserLoginInfo getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(UserLoginInfo currentUser) {
    this.currentUser = currentUser;
  }
}
