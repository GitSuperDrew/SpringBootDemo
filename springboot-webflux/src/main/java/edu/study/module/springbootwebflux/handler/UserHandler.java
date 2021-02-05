package edu.study.module.springbootwebflux.handler;

import edu.study.module.springbootwebflux.dao.UserRepository;
import edu.study.module.springbootwebflux.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Mono ：实现发布者，并返回 0或1 个元素，即单对象；
 * Flux ：实现发布者，并返回 N 个元素，即 List 列表对象；
 * @author drew
 * @date 2021/2/5 22:04
 **/
@Component
public class UserHandler {

    private final UserRepository userRepository;

    @Autowired
    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<Long> save(User user) {
        return Mono.create(userMonoSink -> userMonoSink.success(userRepository.save(user)));
    }

    public Mono<User> findUserById(Long id) {
        return Mono.justOrEmpty(userRepository.findUserById(id));
    }

    public Flux<User> findAllUser() {
        return Flux.fromIterable(userRepository.findAll());
    }

    public Mono<Long> modifyUser(User user) {
        return Mono.create(userMonoSink -> userMonoSink.success(userRepository.updateUser(user)));
    }

    public Mono<Long> deleteUser(Long id) {
        return Mono.create(userMonoSink -> userMonoSink.success(userRepository.deleteUser(id)));
    }
}
