package com.kaolee.hotel.controller.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登入後的資訊，會序列化到Jwt的payload
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginInfo {

  private String sessionId;
  private String userId;
  private String roleId;//1:admin  2:user
  private String email;

  private Long expiredTime;



  @Override
  public String toString() {
    return "UserLoginInfo{" +
        "sessionId='" + sessionId + '\'' +
        ", userId=" + userId +
        ", roleId='" + roleId + '\'' +
        ", expiredTime=" + expiredTime +
        '}';
  }

  public static final class CurrentUserBuilder {

    private UserLoginInfo currentUser;

    private CurrentUserBuilder() {
      currentUser = new UserLoginInfo();
    }

    public static CurrentUserBuilder aCurrentUser() {
      return new CurrentUserBuilder();
    }

    public CurrentUserBuilder sessionId(String sessionId) {
      currentUser.setSessionId(sessionId);
      return this;
    }

    public CurrentUserBuilder userId(String userId) {
      currentUser.setUserId(userId);
      return this;
    }


    public CurrentUserBuilder roleId(String roleId) {
      currentUser.setRoleId(roleId);
      return this;
    }

    public CurrentUserBuilder expiredTime(Long expiredTime) {
      currentUser.setExpiredTime(expiredTime);
      return this;
    }

    public UserLoginInfo build() {
      return currentUser;
    }
  }
}
