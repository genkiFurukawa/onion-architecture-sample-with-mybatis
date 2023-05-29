package ddd.playground.sample.infrastructure.repository.table;

import ddd.playground.sample.domain.value.UserMailAddress;

import java.util.List;

public class User {
    private String id;
    private String name;
    private List<String> mailAddressList;

    // NOTE: entityと同じ名前のためパッケージ名込みでクラスを指定する必要がある。
    // 頭にTやRつける（Tableの略・Recordの略）をつけるなどチームで命名規則などを議論して決めたほうがいい
    public ddd.playground.sample.domain.entity.User toUserEntity() {
        return new ddd.playground.sample.domain.entity.User(
                this.id,
                this.name,
                this.mailAddressList.stream().map(item -> new UserMailAddress(item)).toList()
        );
    }
}
