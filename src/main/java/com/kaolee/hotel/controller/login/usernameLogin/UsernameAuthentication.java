package com.kaolee.hotel.controller.login.usernameLogin;

import com.kaolee.hotel.controller.login.UserLoginInfo;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;


public class UsernameAuthentication extends AbstractAuthenticationToken {

  private String email; //
  private String password; //
  private UserLoginInfo currentUser; // 認證成功後，獲取的使用者資訊

  public UsernameAuthentication() {
    // 權限管理，用不到，回傳null
    super(null);
  }

  @Override
  public Object getCredentials() {
    // 根據SpringSecurity的設計，成功授權後，Credential（比如，登入密碼）訊息需要被清空
    return isAuthenticated() ? null : password;
  }

  @Override
  public Object getPrincipal() {
    // 根據SpringSecurity的設計，授權成功之前，getPrincipal返回的客户端傳遞来的資料。授權成功后，返回當前登錄用户的資訊
    return isAuthenticated() ? currentUser : email;
  }

  public String getUsername() {
    return email;
  }

  public void setUsername(String username) {
    this.email = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserLoginInfo getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(UserLoginInfo currentUser) {
    this.currentUser = currentUser;
  }
}
