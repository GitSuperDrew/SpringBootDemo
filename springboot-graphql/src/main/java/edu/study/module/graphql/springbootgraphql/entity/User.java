package edu.study.module.graphql.springbootgraphql.entity;

import lombok.Data;

import java.util.List;

/**
 * @author zl
 * @create 2021-04-26 17:38
 */
@Data
public class User {

    private int userId;
    private String userName;
    private String realName;
    private String email;
    private List<Post> posts;

    public User() {
    }

    public User(int userId, String userName, String realName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.realName = realName;
        this.email = email;
    }
}
