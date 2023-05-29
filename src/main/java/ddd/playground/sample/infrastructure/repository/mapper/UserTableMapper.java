package ddd.playground.sample.infrastructure.repository.mapper;

import ddd.playground.sample.infrastructure.repository.table.User;
import org.apache.ibatis.annotations.Mapper;

// NOTE: MyBatisのマッピング用のインターフェース
@Mapper
public interface UserTableMapper {
    User selectById(String userId);

    void save(String userId, String userName);

    void deleteById(String userId);
}
