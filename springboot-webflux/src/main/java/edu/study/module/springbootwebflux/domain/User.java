package edu.study.module.springbootwebflux.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author drew
 * @date 2021/2/5 22:02
 **/
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String realName;
    private String password;
    private Integer age;
    private String sex;
}
