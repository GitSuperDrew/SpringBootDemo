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
