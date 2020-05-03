# 📕 项目简介
项目简介：本项目式学习SpringBoot系列之一，目的在于实践各个框架的结合使用。虽然GitHub上不乏这些项目，可能也比我的项目不知道好了多少，但我还是想自己动手实践下。
<pre>
jpa
├─ JpaApplication.java
├─ config
│	├─ ShiroConfig.java
│	└─ StudentRealm.java
├─ controller
│	├─ StudentController.java
│	├─ UserController.java
│	└─ thymeleaflearn
│	 	└─ ThymeleafLearnController.java
├─ dao
│	├─ StudentRepository.java
│	└─ UserRepository.java
├─ entity
│	├─ Student.java
│	└─ UserEntity.java
├─ service
│	├─ StudentService.java
│	├─ StudentServiceImpl.java
│	├─ UserService.java
│	└─ UserServiceImpl.java
└─ utils
 	├─ DataTable.java
 	├─ DateUtil.java
 	├─ GenRandomNameUtil.java
 	├─ HutoolUtilTest.java
 	├─ PageBean.java
 	└─ ServletsUtil.java
</pre>

## ⚙项目架构
技术组成部分：[`Spring Boot`](https://spring.io/projects/spring-boot)、
[`JPA`](https://spring.io/projects/spring-data-jpa)、
[`MySQL`](https://www.mysql.com/)、
[`Thymeleaf`](https://www.thymeleaf.org/)、
[`LayUI`](https://www.layui.com/)、
[`JQuery DataTable`](https://datatables.net/)、
[`Shiro`](http://shiro.apache.org/);
    
## ⚠ 注意事项：
1. 项目只供学习交流使用
2. 编辑使用工具：
    - 编辑环境IDE：[`InterlliJ IDEA`](https://www.jetbrains.com/idea/download)
    - 数据库图形化操作软件：[`Navicat 12`](https://www.navicat.com.cn/) / [`DBeaver`](https://dbeaver.io/)
3. 在 `GitHub` 的 `README.md` 上生成项目文档的树状结构图工具：[Dir Tree Noter](http://dir.yardtea.cc/)
4. 建议使用浏览器的一个访问 `GitHub` 的插件：`Orctree` (这样较之 `Dir Tree Noter`更方便查看相关代码)

## ⚙ 功能模块
1. `学生STUDENT` 和 `用户USER` : 基本的 CURD 操作；
2. 导出所有用户生成`Excel`数据；
后续功能待续 ...✈