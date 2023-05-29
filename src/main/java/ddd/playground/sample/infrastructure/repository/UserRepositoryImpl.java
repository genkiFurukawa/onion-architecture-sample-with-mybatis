package ddd.playground.sample.infrastructure.repository;

import ddd.playground.sample.domain.entity.User;
import ddd.playground.sample.domain.repository.UserRepository;
import ddd.playground.sample.infrastructure.repository.mapper.UserMailAddressMapper;
import ddd.playground.sample.infrastructure.repository.mapper.UserTableMapper;
import ddd.playground.sample.infrastructure.repository.table.UserMailAddress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRepositoryImpl implements UserRepository {
    private final UserTableMapper userTableMapper;
    private final UserMailAddressMapper userMailAddressMapper;

    public UserRepositoryImpl(
            UserTableMapper userTableMapper,
            UserMailAddressMapper userMailAddressMapper
    ) {
        this.userTableMapper = userTableMapper;
        this.userMailAddressMapper = userMailAddressMapper;
    }

    @Transactional
    @Override
    public User fetchById(String userId) {
        var user = userTableMapper.selectById(userId);
        if (user == null) {
            return null;
        } else {
            // NOTE: エンティティのクラスに変換する
            return user.toUserEntity();
        }
    }

    @Transactional
    @Override
    public void createUser(User user) {
        userTableMapper.save(user.getUserId(), user.getUserName());

        if (!user.getMailAddressList().isEmpty()) {
            var saveMailAddressList = user.getMailAddressList().stream().map(item -> new UserMailAddress(user.getUserId(), item.value())).toList();
            userMailAddressMapper.save(saveMailAddressList);
        }
    }

    @Transactional
    @Override
    public void deleteUser(String userId) {
        // NOTE: 外部キー制約を貼っているので、メールアドレスの方から先に削除する。
        userMailAddressMapper.deleteById(userId);
        userTableMapper.deleteById(userId);
    }
}
