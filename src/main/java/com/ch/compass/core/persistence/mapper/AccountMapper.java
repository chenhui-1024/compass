package com.ch.compass.core.persistence.mapper;

import com.ch.compass.core.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountMapper {
    Account selectOne(String username);

    int insertOne(Account account);

    int updateOne(Account account);

    int deleteOne(String username);

    int count();

    List<Account> selectMany(
            @Param("offset")int offset,
            @Param("limit")int limit,
            @Param("orderBy")String orderBy,
            @Param("order")String order
    );

    List<Account> selectMany();

}
