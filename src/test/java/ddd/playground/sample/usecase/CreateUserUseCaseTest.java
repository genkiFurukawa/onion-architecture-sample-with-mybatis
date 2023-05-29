package ddd.playground.sample.usecase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreateUserUseCaseTest {
    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Test
    void Userテーブルにレコードを入れるサンプルコード() {
        createUserUseCase.createUser("test", Arrays.asList("sample@example.com", "test@example.com"));
    }
}