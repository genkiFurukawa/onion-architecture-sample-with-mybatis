package ddd.playground.sample.infrastructure.repository;

import ddd.playground.sample.domain.entity.User;
import ddd.playground.sample.domain.repository.UserRepository;
import ddd.playground.sample.infrastructure.repository.mapper.UserTableMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final UserTableMapper userTableMapper;

    public UserRepositoryImpl(
            UserTableMapper userTableMapper
    ) {
        this.userTableMapper = userTableMapper;
    }

    @Override
    public User fetchById(String userId) {
        var user = userTableMapper.selectById(userId);
        if (user == null) {
            return null;
        } else {
            return user.toUserEntity();
        }
    }
}
