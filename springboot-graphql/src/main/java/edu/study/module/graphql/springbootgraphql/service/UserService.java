package edu.study.module.graphql.springbootgraphql.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.google.common.collect.Lists;
import edu.study.module.graphql.springbootgraphql.entity.Post;
import edu.study.module.graphql.springbootgraphql.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author zl
 * @create 2021-04-26 17:42
 */
@Service
public class UserService implements GraphQLQueryResolver {
    List<User> userList = Lists.newArrayList();

    public User getUserById(int id) {
        return userList.stream().filter(item -> item.getUserId() == id).findAny().orElse(null);
    }


    @PostConstruct
    public void initUsers() {
        Post post1 = new Post(1, "Hello,Graphql1", "Graphql初体验1", "日记");
        Post post2 = new Post(2, "Hello,Graphql2", "Graphql初体验2", "日记");
        Post post3 = new Post(3, "Hello,Graphql3", "Graphql初体验3", "日记");
        List<Post> posts = Lists.newArrayList(post1, post2, post3);

        User user1 = new User(1, "zhangsan", "张三", "zhangsan@qq.com");
        User user2 = new User(2, "lisi", "李四", "lisi@qq.com");

        user1.setPosts(posts);
        user2.setPosts(posts);


        userList.add(user1);
        userList.add(user2);

    }

}
