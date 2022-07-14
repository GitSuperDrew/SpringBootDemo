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
