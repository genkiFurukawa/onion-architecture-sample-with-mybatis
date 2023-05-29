# MyBatisを使ってオニオンアーキテクチャを実現するときの素振り用リポジトリ
## 概要
- [`ドメイン駆動設計 サンプルコード&FAQ`](https://booth.pm/ja/items/3363104)のサンプルコードでORマッパーの部分は[`jOOQ`](https://www.jooq.org/)を使って実装している。
- [`MyBatis`](https://blog.mybatis.org/)を使った場合にどうすればよいかの素振り用のリポジトリ。
- パッケージ構成などは`ドメイン駆動設計 サンプルコード&FAQ`に準拠する。

## TL;DR
- MyBatisを使ってオニオンアーキテクチャを実現するときにどうすればよいか？
  - `domain.repository`配下にinterfaceを置き、`repository`配下でinterfaceの実装クラスを置く。
  - `repository`の配下に`MyBatis`のマッパー用のinterfaceを置く。
    - `MyBatis`のマッパー用のinterfaceに`@Mapper`をつけておくと、アノテーションを検知して起動時に実装クラスを生成してDIしてくれる。
  - `repository`配下にあるinterfaceの実装クラスからマッパー関連のメソッドを呼ぶ。
  - `SELECT`するときはユースケースでマッピングしたいクラスが変わる可能性が高いので、`CQRS`の考え方をインフラ層で適用してインフラ層のクラス関係がごちゃつくのを防いだ方が良さそう。

## そもそも
- オニオンアーキテクチャではドメイン層に定義したリポジトリの実装をインフラ層に実装することでインフラ側の詳細がドメイン層に漏れないようにしている。
- MyBatisはinterfaceを定義すると、起動時に設定ファイルなどを見て実装クラスを生成し、DIしてくれる。
- 初めてオニオンアーキテクチャを採用しようとしたときに、これまでの慣習からインフラ層の実装クラスはMyBatisに生成させるべきという謎の思い込みがあり、時間もなかった関係からそのまま実装に突入した。
- インピーダンスミスマッチの解消をユースケース側に責任転嫁したり、エンティティとSQLのレコードをマッピングするエンティティが混在し、混乱を招くことがあった。
  - その後入ってきたメンバーの一言で思い込みに過ぎないことがわかったので、整理してこうしたい案をまとめておきたいと思ったので、まとめてリポジトリ。
  - それまでは`@Service`層に全てのコードが集約されていて、ユニットテストを書くにも書けない状態だったので、パッケージ構成を事前に決めることで新規プロダクトではそのようなことを避けたいという目的は達成できたので...

## 素振りに使ったもの
### ドメインモデル
- `ドメイン駆動設計 サンプルコード&FAQ`の`p.34`にある`ユーザ集約`を素振りに使う。

```java
public class User {
    private String userId;
    private String userName;
    private List<UserMailAddress> mailAddressList;
}
```

### パッケージ詳細

```
src/main/java
└── ddd
   └── playground
      └── sample
         ├── domain
         │  ├── entity
         │  │  └── User.java
         │  ├── repository
         │  │  └── UserRepository.java : リポジトリのinterface
         │  └── value
         │     └── UserMailAddress.java
         ├── infrastructure : SQLと通信する詳細はこのパッケージ配下で実装する
         │  └── repository
         │     ├── mapper
         │     │  ├── UserMailAddressMapper.java : MyBatis用のinterface
         │     │  └── UserTableMapper.java : MyBatis用のinterface
         │     ├── table
         │     │  ├── User.java : 　SQLのクエリの結果をマッピングするクラス
         │     │  └── UserMailAddress.java : SQLで永続化するときにmapperの引数に渡す用のクラス
         │     └── UserRepositoryImpl.java : domain.repository配下のinterfaceを実装したクラス
         ├── presentation
         ├── SampleApplication.java
         └── usecase
            ├── CreateUserUseCase.java
            └── GetUserUseCase.java
```

### テーブル定義
- flywayに書いてある通り

```sql
CREATE TABLE user (
  id VARCHAR(36) NOT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE user_mail_address (
  id VARCHAR(36) NOT NULL,
  mail_address VARCHAR(255) NOT NULL,
  PRIMARY KEY (id, mail_address),
  FOREIGN KEY (id) REFERENCES user(id)
);
```

### レコードのサンプル

```sql
INSERT INTO user (id, name) VALUES
('11111111-1111-1111-1111-111111111111', 'John Doe'),
('22222222-2222-2222-2222-222222222222', 'Jane Smith');

INSERT INTO user_mail_address (id, mail_address) VALUES
('11111111-1111-1111-1111-111111111111', 'john@example.com'),
('11111111-1111-1111-1111-111111111111', 'john.doe@example.com'),
('22222222-2222-2222-2222-222222222222', 'jane@example.com'),
('22222222-2222-2222-2222-222222222222', 'jane.smith@example.com');
```

## 今後やりたいこと
- ユースケースが増えたときに備えて、`CQRS`を導入する
  - `INSERT`、`UPDATE`、`DELETE`は同じ書き方になるが、`SELECT`はユースケースによってマッピングするクラスが変わることが想定され、インフラ層のクラスの内部の秩序が乱れそうなため

## 参考
- [CQRS実践入門 [ドメイン駆動設計]](https://little-hands.hatenablog.com/entry/2019/12/02/cqrs)
  - > 状況によってはCQRSは価値を発揮しますが、多くのシステムではCQRSによってリスク、複雑さが増すことに注意してください。