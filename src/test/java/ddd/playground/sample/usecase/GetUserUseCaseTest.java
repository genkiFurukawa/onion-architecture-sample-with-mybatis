package ddd.playground.sample.usecase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetUserUseCaseTest {
    @Autowired
    private GetUserUseCase getUserUseCase;

    @Test
    void Userテーブルからレコードを取得するサンプルコード() {
        var user = getUserUseCase.getUser("11111111-1111-1111-1111-111111111111");
        assertNotNull(user);
        assertEquals("11111111-1111-1111-1111-111111111111", user.getUserId());
    }
}