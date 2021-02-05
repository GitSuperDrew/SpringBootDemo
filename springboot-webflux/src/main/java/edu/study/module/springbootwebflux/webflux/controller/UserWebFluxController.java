package edu.study.module.springbootwebflux.webflux.controller;

import edu.study.module.springbootwebflux.domain.User;
import edu.study.module.springbootwebflux.handler.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author drew
 * @date 2021/2/5 22:04
 **/
@RestController
@RequestMapping(path = "/user")
public class UserWebFluxController {

    @Autowired
    private UserHandler userHandler;

    @GetMapping(path = "/{id}")
    public Mono<User> findUserById(@PathVariable("id") Long id) {
        return userHandler.findUserById(id);
    }

    @GetMapping()
    public Flux<User> findAllUser() {
        return userHandler.findAllUser();
    }

    @PostMapping()
    public Mono<Long> saveUser(@RequestBody User user) {
        return userHandler.save(user);
    }

    @PutMapping()
    public Mono<Long> modifyUser(@RequestBody User user) {
        return userHandler.modifyUser(user);
    }

    @DeleteMapping(path = "/{id}")
    public Mono<Long> deleteUser(@PathVariable(value = "id") Long id) {
        return userHandler.deleteUser(id);
    }
}
