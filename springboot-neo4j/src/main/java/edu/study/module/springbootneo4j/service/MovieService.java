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
