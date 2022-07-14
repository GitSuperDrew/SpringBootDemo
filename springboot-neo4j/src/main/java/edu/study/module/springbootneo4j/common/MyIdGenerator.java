package edu.study.module.springbootneo4j.common;

import org.springframework.data.neo4j.core.schema.IdGenerator;
import org.springframework.stereotype.Component;

@Component
public class MyIdGenerator implements IdGenerator<Long> {
    @Override
    public Long generateId(String primaryLabel, Object entity) {
        return Long.parseLong(String.valueOf((int) (Math.random() * 100)));
    }
}
