package ddd.playground.sample.usecase;

import ddd.playground.sample.domain.entity.User;
import ddd.playground.sample.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetUserUseCase {
    private final UserRepository userRepository;

    GetUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User getUser(String userId) {
        var user = userRepository.fetchById(userId);
        return user;
    }
}
