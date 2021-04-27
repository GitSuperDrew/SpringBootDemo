package edu.study.module.graphql.springbootgraphql.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import edu.study.module.graphql.springbootgraphql.entity.Post;
import edu.study.module.graphql.springbootgraphql.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author zl
 * @create 2021-04-26 17:42
 */
@Service
public class PostService implements GraphQLQueryResolver {
    /**
     * 为了测试，只查询id为1的结果
     */
    public Post getPostById(int id) {
        if (id == 1) {
            User user = new User(1, "javadaily", "JAVA日知录", "zhangsan@qq.com");
            Post post = new Post(1, "Hello,Graphql", "Graphql初体验", "日记");
            post.setUser(user);
            return post;
        } else {
            return null;
        }

    }
}
