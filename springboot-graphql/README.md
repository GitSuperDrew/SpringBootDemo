# 工程简介
- Spring Boot 整合 GraphQL(新式API风格)项目搭建案例演示。


# 工程搭建步骤
###（1）创建一个 SpringBoot 项目，并引入 Maven 依赖
```xml
<dependencies>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter</artifactId>
</dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!--graphql start-->
    <dependency>
        <groupId>com.graphql-java</groupId>
        <artifactId>graphql-spring-boot-starter</artifactId>
        <version>5.0.2</version>
    </dependency>
    <dependency>
        <groupId>com.graphql-java</groupId>
        <artifactId>graphql-java-tools</artifactId>
        <version>5.2.4</version>
    </dependency>
    <!--graphql end-->

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>

```

核心引入【graphql-spring-boot-starter】和【graphql-java-tools】。
### （2）创建示例实体类 User 和 Post 
#### ① User.java
```java
import lombok.Data;

import java.util.List;

@Data
public class User {

    private int userId;
    private String userName;
    private String realName;
    private String email;
    private List<Post> posts;

    public User() {}

    public User(int userId, String userName, String realName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.realName = realName;
        this.email = email;
    }
}
```


#### ② Post.java
```java
import lombok.Data;

@Data
public class Post {
private int postId;
private String title ;
private String text;
private String  category;
private User user;

    public Post() {}

    public Post(int postId, String title, String text, String category) {
        this.postId = postId;
        this.title = title;
        this.text = text;
        this.category = category;
    }
}
```
以上定义了两个 Java 实体：User 用户 和 Post 文章。


###（3）编写 Schema 文件
在 resources/schema 目录下创建 schema.graphqls 文件，内容如下：
```yaml
schema {
  query: Query,
}

type Query {
    # 获取具体的用户
    getUserById(id:Int) : User
    # 获取具体的博客
    getPostById(id:Int) : Post
}

type User {
    userId : ID!,
    userName : String,
    realName : String,
    email : String,
    posts : [Post],
}

type Post {
    postId : ID!,
    title : String!,
    text : String,
    category: String
    user: User,
}
```
> 注意：<br>
> - 通过 type 关键字定义了两个对象（User 和 Post），在属性后面添加【！】表明这是一个非空属性，通过 【Post】表明这是一个 Post集合，类似于 Java对象中的 List。<br>
> - 通过 Query 关键字定义了两个查询对象，getUserById,  getPostById，分别返回 User对象 和 Post对象。

graphql 的schema 相关语法见：[https://graphql.org/learn/schema](https://graphql.org/learn/schema)


###（4）编写业务逻辑
#### ① UserService.java
```java
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.google.common.collect.Lists;
import edu.study.module.graphql.springbootgraphql.entity.Post;
import edu.study.module.graphql.springbootgraphql.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

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
```

#### ② PostService.java
```java
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import edu.study.module.graphql.springbootgraphql.entity.Post;
import edu.study.module.graphql.springbootgraphql.entity.User;
import org.springframework.stereotype.Service;

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
```
基于 Graphql 的查询需要实现 GraphQLQueryResolver 接口，由于为了便于演示突出此文章的重点，这里并没有引入数据层(即DAO层）。


###（5）配置 Graphql 断点
```properties
server.port = 8080
graphql.servlet.corsEnabled=true
# 配置端点
graphql.servlet.mapping=/graphql
graphql.servlet.enabled=true
```
> 配置完端口和端口点我们就可以对我们编写的 graphql 接口进行测试了。<br/>
> 启动 Spring Boot 服务，测试书写query语句的接口地址为：[http://localhost:8080/graphql](http://localhost:8080/graphql)


# 延伸阅读

