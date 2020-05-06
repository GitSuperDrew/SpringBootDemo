## 🐅 项目简介
    这个项目是我学习Spring Boot系列的有一个Demo。目的当然也是为了实践。了解并逐渐深入学习相关技术。

## ⚙ 技术架构
1. 编程环境：[`Java8`](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)，`Windows10`，
[`maven`](https://mvnrepository.com/)，
[`InterlliJ IDEA`](https://www.jetbrains.com/)，
2. 数据库：`MySQL5.7+`  【后期准备加入 [`Redis`](https://redis.io/) 】
3. ORM框架：[`Mybatis Plus`](https://mp.baomidou.com/)
4. 模板引擎：[`Velocity`](http://velocity.apache.org/)
5. 安全框架：[`Security`](https://spring.io/projects/spring-security)
6. 其他工具：[`Lombok`](https://projectlombok.org/)，`SpringBoot热部署`

## 🦁 详细说明
1. 新建 `config/CodeGenerator.java` 自动代码生成工具：注意修改数据的相关连接信息
    * 数据库的连接驱动`Driver`
    * 数据库的连接信息`url`
    * 数据库的连接用户和密码（`username` / `password`）
    * 配置生成的文件存放的位置。
2. 直接运行 `config/CodeGenerator.java`，输入提示所需信息即可。
3. 在 `controller/UserController.java` 中书写一个方法 `findAll()` 得到数 没有`用户ID` 的数据集合；所以需要手动在`User.java`中手动添加
`private Integer id; // 如果没有，则出现 有关于 @TableId 相关的错误信息.`

## ⛏ 注意事项：
### 备注
1. 文章参考[来源](https://www.toutiao.com/i6691534609064133132/)。
2. `Spring Boot 热部署：[讲解](https://blog.csdn.net/chachapaofan/article/details/88697452)
3. [`Mybatis Plus`学习辅助](https://www.jianshu.com/p/1bbddc36b63b)。

### Spring Security 部分
1. 学习网址：[来源微信文章](https://mp.weixin.qq.com/s?__biz=MzIxNjA5MTM2MA==&mid=2652437103&idx=1&sn=6b23ebf9f026dd087060ed49febc3d60&chksm=8c6205e0bb158cf6051858c5b6c8de6b049c1b2dc8099e02fcc71849b605ce74255363249b7b&scene=126&sessionid=1588736498&key=1fb1a3c108c4a26c9b39d05af79bfee5b3aab9c0897d4e327a48e943c72fa8b0f3948d956b8da80c8db2d8a7a7eb9019a44fee8b1232381a4bb6f7adca89997cecf0b424663e0f7cfa0d934d87d4e38a&ascene=1&uin=MjEyNzQ5NDYzNw%3D%3D&devicetype=Windows+10+x64&version=62090070&lang=zh_CN&exportkey=A5HTPc3MKfCWoFZcTcsNyQE%3D&pass_ticket=64zAAlv%2FhSW%2BbF9r1X7BWLvqZmYwkDa9yUJ33rXZNYBvnif%2Fi2NWSjeXHX0WNPNs)
2. `SpringSecurity`的默认用户和密码分别为：user/【每次启动控制台的密码】，需要手动设置账号和密码（在配置文件`application.yaml`中指定`user`的`name`和`password`即可）。
3. `SpringSecurity`配置指定密码的方式有两种，第一种在配置文件，即上面所属；第二种在Java类中配置，需要配置比较多的内容。`使用第二种java类配置将覆盖application.yaml中的用户和密码配置`.
4. 代码中的配置 优先级高于 配置文件中的配置。
5. 自定义登录页面
    * 引入相关的静态页面的数据放入到`static`目录下，注意，如果时默认的情况下请将前端的name属性值和后端的`security`的`SecurityConfig`保持一致的参数key
    ```html
     用户名：<input type="text" id="name" name="username">
     密码：<input type="password" id="pass" name="password">
    ```
   否认，将出现错误：**~~“`Creation of SecureRandom instance for session ID generation using [SHA1PRNG] took [188] milliseconds
   .`”~~** 这个错误出现的有些奇怪！！
6. 设置 `session` 的超时时间，当达到超时时间后，自动将用户退出登录。`Session` 超时的配置是 `SpringBoot` 原生支持的，我们只需要在 `application.properties` 配置文件中