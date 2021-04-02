package edu.study.module.repeat_submit_intercept.entity;

import java.io.Serializable;

/**
 * @author zl
 * @create 2021-04-02 17:42
 */
public class User implements Serializable {
    private Long userId;
    private String userName;

    public User(){}

    public User(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
