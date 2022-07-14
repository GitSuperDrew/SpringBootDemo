# 数据库 `Neo4j` 整合 `SpringBoot` 使用
> 1. Neo4j 官方地址：[https://neo4j.com/](https://neo4j.com/)
> 2. Spring Boot + Neo4j 官方地址：[https://docs.spring.io/spring-data/neo4j/docs/current/reference/html/#getting-started](https://docs.spring.io/spring-data/neo4j/docs/current/reference/html/#getting-started)

# 一、前提准备
1. 准备简单的`Spring boot`项目；
   > https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.7.1&packaging=jar&jvmVersion=17&groupId=edu.study.module&artifactId=springboot-neo4j&name=springboot-neo4j&description=Demo%20project%20for%20Spring%20Boot&packageName=edu.study.module.springboot-neo4j&dependencies=lombok,web,data-neo4j
2. 准备 `neo4j` 数据库服务；
   ```shell
   # docker 启动服务命令（账号:neo4j、密码：secret）
   docker run --publish=7474:7474 --publish=7687:7687 -e 'NEO4J_AUTH=neo4j/secret' neo4j:4.4.8
   # podman 部署启动服务命令（账号：neo4j、密码：secret）
   podman run --publish=7474:7474 --publish=7687:7687 -e 'NEO4J_AUTH=neo4j/secret' docker.io/library/neo4j:4.4.8
   
   # 操作示例：
   [root@localhost ~]# podman run --publish=7474:7474 --publish=7687:7687 -e 'NEO4J_AUTH=neo4j/secret' docker.io/library/neo4j:4.4.8
   Trying to pull docker.io/library/neo4j:latest...
   Getting image source signatures
   Copying blob 75f2c7b97846 done
   Copying blob 461246efe0a7 done
   Copying blob d294696aee8f done
   Copying blob 53a09311990d done
   Copying blob e092b02b7004 done
   Copying blob e693ef00b582 done
   Copying blob feb9a9dd0fc3 done
   Copying config 7e0519e020 done
   Writing manifest to image destination
   Storing signatures
   Changed password for user 'neo4j'. IMPORTANT: this change will only take effect if performed before the database is started for the first time.
   2022-07-14 02:53:23.724+0000 INFO  Starting...
   2022-07-14 02:53:24.752+0000 INFO  This instance is ServerId{4613b08a} (4613b08a-5229-423b-8052-6597516949cd)
   2022-07-14 02:53:27.283+0000 INFO  ======== Neo4j 4.4.8 ========
   2022-07-14 02:53:31.058+0000 INFO  Initializing system graph model for component 'security-users' with version -1 and status UNINITIALIZED
   2022-07-14 02:53:31.070+0000 INFO  Setting up initial user from `auth.ini` file: neo4j
   2022-07-14 02:53:31.070+0000 INFO  Creating new user 'neo4j' (passwordChangeRequired=false, suspended=false)
   2022-07-14 02:53:31.089+0000 INFO  Setting version for 'security-users' to 3
   2022-07-14 02:53:31.095+0000 INFO  After initialization of system graph model component 'security-users' have version 3 and status CURRENT
   2022-07-14 02:53:31.101+0000 INFO  Performing postInitialization step for component 'security-users' with version 3 and status CURRENT
   2022-07-14 02:53:31.492+0000 INFO  Bolt enabled on [0:0:0:0:0:0:0:0%0]:7687.
   2022-07-14 02:53:33.550+0000 INFO  Remote interface available at http://localhost:7474/
   2022-07-14 02:53:33.562+0000 INFO  id: C592E2927232647D56DCD80C5DA2DFF71D811376198C180AF9026EA42E2D1C7B
   2022-07-14 02:53:33.562+0000 INFO  name: system
   2022-07-14 02:53:33.562+0000 INFO  creationDate: 2022-07-14T02:53:28.416Z
   2022-07-14 02:53:33.563+0000 INFO  Started.
   ```
3. `JDK 9+`（这里使用：`JDK17`）；


# 二、实现步骤
## （1） 引入依赖
### ① maven 方式
```xml
<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-neo4j</artifactId>
</dependency>
```
或者指定neo4j驱动版本
```xml
<dependencies>
   <dependency>
      <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-neo4j</artifactId>
       <exclusions>
           <exclusion>
               <artifactId>neo4j-java-driver</artifactId>
               <groupId>org.neo4j.driver</groupId>
           </exclusion>
       </exclusions>
   </dependency>
   <dependency>
       <groupId>org.neo4j.driver</groupId>
       <artifactId>neo4j-java-driver</artifactId>
       <version>4.4.5</version>
   </dependency>
</dependencies>
```

### ② gradle方式
```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-neo4j'
}
```

## （2） 配置 `application.properties`
```properties
# This is the bare minimum of what you need to connect to a Neo4j instance.
spring.neo4j.uri=bolt://192.168.16.178:7687
spring.neo4j.authentication.username=neo4j
spring.neo4j.authentication.password=secret
```

## （3） 编写实体和实体关系
> 注意：
> 1. @Node ：定义节点
> 2. @Relationship：定义关系
> 3. @Propert：定义节点或关系属性
> 4. @RelationshipProperties：定义关系属性集合
> 5. @Id：定义SDN的主键
> 6. @TargetNode：定义关系的另一端节点对象
如下所示：
### （3.1）实体类：电影信息
```java
package edu.study.module.springbootneo4j.model;

import edu.study.module.springbootneo4j.relation.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

/**
 * 电影实体类
 */
@Data
@NoArgsConstructor
@Node("Movie")
public class MovieEntity {
   @Id
   private String title;

   @Property("description")
   private String description;

   @Relationship(type = "ACTED_IN", direction = Relationship.Direction.OUTGOING)
   private List<Roles> roles;

   @Relationship(type = "DIRECTED", direction = Relationship.Direction.INCOMING)
   private List<PersonEntity> directors;

   public MovieEntity(String title, String description) {
      this.title = title;
      this.description = description;
   }
}
```
### （3.2）实体类：人员信息
```java
package edu.study.module.springbootneo4j.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Data
@NoArgsConstructor
@Node("Person")
public class PersonEntity {
   @Id
   private String name;

   @Property("born")
   private Integer born;

   public PersonEntity(Integer born, String name) {
      this.born = born;
      this.name = name;
   }
}
```
### （3.3）关系类：电影-人员关系
```java
package edu.study.module.springbootneo4j.relation;

import edu.study.module.springbootneo4j.model.PersonEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@Data
@NoArgsConstructor
@RelationshipProperties
public class Roles {
   @Id
   @GeneratedValue(GeneratedValue.InternalIdGenerator.class)
   private Long id;

   @Property("roles")
   private List<String> roleList;

   @TargetNode
   private PersonEntity person;


   public Roles(Long id, PersonEntity person, List<String> roleList) {
      this.id = id;
      this.person = person;
      this.roleList = roleList;
   }
}
```

##（4）编写 `Repository` / `DAO`
> `repository` 优点类似 `jpa`，就是继承的类不同。
```java
package edu.study.module.springbootneo4j.dao;

import edu.study.module.springbootneo4j.model.MovieEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends Neo4jRepository<MovieEntity, String> {

   /**
    * 自定义查询方式：根据电影名称查询
    *
    * @param title 电影名称
    * @return 电影集合
    */
//    @Query("MATCH (n:Movie) where n.title contains $title RETURN n.title,n.description LIMIT 1")
//    @Query("MATCH (n:Movie) where n.title contains $title RETURN n LIMIT 1")
   @Query("MATCH (n:Movie) where n.title contains $title RETURN n")
   List<MovieEntity> findByTitle(@Param("title") String title);

   /**
    * 注意：统计所有的数据库neo4j下的所有的系欸但数据
    * @return 数量
    */
   @Query("MATCH (n) RETURN count(n)")
   Long countAllNodes();
}
```

## （5）编写 `Service` & `ServiceImpl`
### （5.1）电影业务层：MovieService
```java
package edu.study.module.springbootneo4j.service;

import edu.study.module.springbootneo4j.model.MovieEntity;

import java.util.List;

public interface MovieService {
   /**
    * 店家电影名称
    *
    * @param movie 电影信息
    */
   MovieEntity add(MovieEntity movie);

   /**
    * 获取所有的电影信息
    *
    * @return 电影即可
    */
   List<MovieEntity> findAll();

   /**
    * 自定义查询：根据电影标题模糊查询电影
    *
    * @return 电影集合
    */
   List<MovieEntity> findByTitle(String movieTitle);

   /**
    * 根据电影名称查询
    *
    * @param movieTitle 电影名称
    * @return 电影信息
    */
   MovieEntity findMovieByTitle(String movieTitle);

   MovieEntity testNeo4jTemplate(String movieTitle);

   /**
    * 统计电影数量
    *
    * @return 电影数量
    */
   Long count();

   /**
    * 统计neo4j下，所有节点数量
    *
    * @return 节点数量
    */
   Long countAllNodes();

   /**
    * 删除所有的电影信息
    */
   void deleteAll(MovieEntity movie);
}
```
###（5.2）电影业务实现层：MovieServiceImpl
```java
package edu.study.module.springbootneo4j.service.impl;

import edu.study.module.springbootneo4j.dao.MovieRepository;
import edu.study.module.springbootneo4j.model.MovieEntity;
import edu.study.module.springbootneo4j.service.MovieService;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service("MovieService")
public class MovieServiceImpl implements MovieService {
   // 【核心】操作类
   @Resource
   private MovieRepository movieRepository;
   // 【核心】操作类
   @Resource
   private Neo4jTemplate neo4jTemplate;


   @Override
   public MovieEntity add(MovieEntity movie) {
      MovieEntity newEntity = movieRepository.save(movie);
      System.out.println("新的电影信息：" + newEntity);
      return newEntity;
   }

   @Override
   public List<MovieEntity> findAll() {
      return movieRepository.findAll();
   }

   @Override
   public List<MovieEntity> findByTitle(String movieTitle) {
      return movieRepository.findByTitle(movieTitle);
   }


   @Override
   public MovieEntity findMovieByTitle(String movieTitle) {
      return movieRepository.findById(movieTitle).orElse(null);
   }

   @Override
   public MovieEntity testNeo4jTemplate(String movieTitle) {
      return neo4jTemplate.findById(movieTitle, MovieEntity.class).orElse(null);
   }

   @Override
   public Long count() {
      return movieRepository.count();
   }

   @Override
   public Long countAllNodes() {
      return movieRepository.countAllNodes();
   }

   @Override
   public void deleteAll(MovieEntity movie) {
      if (Objects.isNull(movie)) {
         movieRepository.deleteAll();
      } else {
         movieRepository.delete(movie);
      }
   }
}
```

## （6）测试
### （6.1）接口测试
```java
package edu.study.module.springbootneo4j.controller;

import edu.study.module.springbootneo4j.model.MovieEntity;
import edu.study.module.springbootneo4j.model.PersonEntity;
import edu.study.module.springbootneo4j.relation.Roles;
import edu.study.module.springbootneo4j.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/neo4j")
public class Neo4jController {
   @Autowired
   MovieService movieService;

   @GetMapping("/addMovie")
   public void addMovie() {
      MovieEntity movie = new MovieEntity("我爱我的祖国", "该片讲述了新中国成立70年间普通百姓与共和国息息相关的故事");

      List<Roles> rolesList = new ArrayList<>(8);
      rolesList.add(new Roles(1L, new PersonEntity(1974, "黄渤"), List.of("林治远")));
      rolesList.add(new Roles(2L, new PersonEntity(1978, "张译"), List.of("高远")));
      rolesList.add(new Roles(3L, new PersonEntity(1974, "吴京"), List.of("陈冬冬")));
      rolesList.add(new Roles(4L, new PersonEntity(1985, "杜江"), List.of("朱涛")));
      rolesList.add(new Roles(5L, new PersonEntity(1957, "葛优"), List.of("张北京")));
      rolesList.add(new Roles(6L, new PersonEntity(1997, "刘昊然"), List.of("沃德乐")));

      movie.setRoles(rolesList);
      movie.setDirectors(Collections.singletonList(new PersonEntity(1952, "陈凯歌")));
      movieService.add(movie);
   }

   @GetMapping("/")
   public List<MovieEntity> findAll() {
      return movieService.findAll();
   }

   @GetMapping("/findByTitle")
   public List<MovieEntity> findByTitle(@RequestParam("title") String movieTitle) {
      return movieService.findByTitle(movieTitle);
   }

   @GetMapping("/allNodes")
   public Long countAllNodes() {
      return movieService.countAllNodes();
   }

}
```
### （6.2）单元测试
```java
package edu.study.module.springbootneo4j.service.impl;

import edu.study.module.springbootneo4j.model.MovieEntity;
import edu.study.module.springbootneo4j.model.PersonEntity;
import edu.study.module.springbootneo4j.relation.Roles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class MovieServiceImplTest {

   @Resource
   private MovieServiceImpl movieService;

   @Test
   void add() {
      // 1. 电影信息
      MovieEntity movie = new MovieEntity("我爱我的祖国1", "该片讲述了新中国成立70年间普通百姓与共和国息息相关的故事");
      // 1.1 电影相关演员角色
      List<Roles> rolesList = new ArrayList<>(8);
      rolesList.add(new Roles( 1L, new PersonEntity(1974, "黄渤"), List.of("林治远")));
      rolesList.add(new Roles(2L, new PersonEntity(1978, "张译"), List.of("高远")));
      rolesList.add(new Roles(3L, new PersonEntity(1974, "吴京"), List.of("陈冬冬")));
      rolesList.add(new Roles(4L, new PersonEntity(1985, "杜江"), List.of("朱涛")));
      rolesList.add(new Roles(5L, new PersonEntity(1957, "葛优"), List.of("张北京")));
      rolesList.add(new Roles(6L, new PersonEntity(1997, "刘昊然"), List.of("沃德乐")));
      movie.setRoles(rolesList);
      // 1.2 电影导演
      movie.setDirectors(Collections.singletonList(new PersonEntity(1952, "陈凯歌")));
      // 2. 添加电影信息
      movieService.add(movie);
   }

   @Test
   void deleteAllMovies() {
      MovieEntity movieEntity = new MovieEntity();
      movieEntity.setTitle("我爱我的祖国");
      movieService.deleteAll(movieEntity);
   }

   @Test
   void findMovieByTitle() {
      MovieEntity movie = movieService.findMovieByTitle("我爱我的祖国");
      System.out.println("电影信息实体：" + movie);
      Assertions.assertNotNull(movie);

   }

   @Test
   void testNeo4jTemplate() {
      MovieEntity movie = movieService.testNeo4jTemplate("我爱我的祖国");
      System.out.println("电影信息实体：" + movie);
      Assertions.assertNotNull(movie);
   }

   @Test
   void findAllMovies() {
      System.out.println("获取所有的电影信息:");
      movieService.findAll().forEach(System.out::println);
   }

   @Test
   void count() {
      Long count = movieService.count();
      System.out.println("电影数量：count = " + count);
      Assertions.assertTrue(count > 0);
   }
}
```
### （6.3）进入neo4j的GUI工具查看
> 观察 neo4j 数据库里面的数据是否添加成功，数据量是否统计正常



# 附录
## (1) 参考相关博客：
> 1. [https://blog.csdn.net/qq_24598601/article/details/124572979](https://blog.csdn.net/qq_24598601/article/details/124572979)