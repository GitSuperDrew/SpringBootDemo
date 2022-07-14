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