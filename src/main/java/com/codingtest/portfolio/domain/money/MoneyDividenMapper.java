package com.codingtest.portfolio.domain.money;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MoneyDividenMapper {
    List<MoneyDividen> selectBalanceByToken(String token);

    boolean insert(MoneyDividen body);

    int updateBalanceWithCollision(MoneyDividen body);
}
