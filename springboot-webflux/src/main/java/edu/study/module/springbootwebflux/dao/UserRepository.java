package edu.study.module.springbootwebflux.dao;

import edu.study.module.springbootwebflux.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author drew
 * @date 2021/2/5 22:03
 **/
@Repository
public class UserRepository {
    /**
     * 可以替换成 ORM框架（mybatis的*Mapper）
     */
    private ConcurrentMap<Long, User> repository = new ConcurrentHashMap<>();

    private static final AtomicLong idGenerator = new AtomicLong(0);

    public Long save(User user) {
        Long id = idGenerator.incrementAndGet();
        user.setId(id);
        repository.put(id, user);
        return id;
    }

    public Collection<User> findAll() {
        return repository.values();
    }

    public User findUserById(Long id) {
        return repository.get(id);
    }

    public Long updateUser(User user) {
        repository.put(user.getId(), user);
        return user.getId();
    }

    public Long deleteUser(Long id) {
        repository.remove(id);
        return id;
    }
}
