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
