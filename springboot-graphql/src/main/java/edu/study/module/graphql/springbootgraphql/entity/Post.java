package edu.study.module.graphql.springbootgraphql.entity;

import lombok.Data;

/**
 * @author zl
 * @create 2021-04-26 17:38
 */
@Data
public class Post {
    private int postId;
    private String title ;
    private String text;
    private String  category;
    private UserInfo user;

    public Post() {

    }

    public Post(int postId, String title, String text, String category) {
        this.postId = postId;
        this.title = title;
        this.text = text;
        this.category = category;
    }
}
