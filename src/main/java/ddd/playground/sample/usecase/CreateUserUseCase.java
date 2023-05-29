package ddd.playground.sample.usecase;

import ddd.playground.sample.domain.entity.User;
import ddd.playground.sample.domain.repository.UserRepository;
import ddd.playground.sample.domain.value.UserMailAddress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CreateUserUseCase {
    private final UserRepository userRepository;

    CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * NOTE: コントローラでメールアドレスのフォーマットチェックと重複チェックはできている前提とする
     *
     * @param name            name
     * @param mailAddressList mailAddressList
     */
    @Transactional
    public void createUser(String name, List<String> mailAddressList) {
        var id = UUID.randomUUID().toString();
        var user = new User(id, name, mailAddressList.stream().map(item -> new UserMailAddress(item)).toList());

        // TODO: idがすでに登録されているものかどうかチェックして、すでに使用済みのものであればuuidを再生成する

        userRepository.createUser(user);
    }
}
