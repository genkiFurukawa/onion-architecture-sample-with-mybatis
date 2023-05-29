package ddd.playground.sample.domain.repository;

import ddd.playground.sample.domain.entity.User;

public interface UserRepository {
    User fetchById(String userId);
}
