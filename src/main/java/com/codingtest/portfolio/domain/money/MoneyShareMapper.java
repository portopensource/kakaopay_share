package com.codingtest.portfolio.domain.money;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MoneyShareMapper {
    List<MoneyShare> selectAll();

    MoneyShare selectByToken(String token);

    boolean insert(MoneyShare share);

    int updateBalanceWithCollision(MoneyShare share);
}
