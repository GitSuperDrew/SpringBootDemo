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
