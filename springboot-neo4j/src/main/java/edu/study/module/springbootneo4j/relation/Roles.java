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