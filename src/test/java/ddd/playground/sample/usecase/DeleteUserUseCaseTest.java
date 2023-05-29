package ddd.playground.sample.usecase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeleteUserUseCaseTest {
    @Autowired
    private DeleteUserUseCase deleteUserUseCase;

    @Test
    void Userテーブルにレコードを入れるサンプルコード() {
        deleteUserUseCase.deleteUser("59890224-6e47-4d65-b883-2b78301dd2ed");
    }
}