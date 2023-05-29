package ddd.playground.sample.usecase;

import ddd.playground.sample.domain.entity.User;
import ddd.playground.sample.domain.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

public class DeleteUserUseCase {
    private final UserRepository userRepository;

    DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void deleteUser(String userId) {
        // NOTE: 削除できる権限を持っているか？などチェックしてから本来は実行すべき
        var user = userRepository.fetchById(userId);
    }
}
