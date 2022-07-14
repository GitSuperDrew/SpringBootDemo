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
