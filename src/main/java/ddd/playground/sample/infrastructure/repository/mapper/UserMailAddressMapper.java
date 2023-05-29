package ddd.playground.sample.infrastructure.repository.mapper;

import ddd.playground.sample.infrastructure.repository.table.UserMailAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMailAddressMapper {
    void save(List<UserMailAddress> userMailAddressList);

    void deleteById(String userId);
}
