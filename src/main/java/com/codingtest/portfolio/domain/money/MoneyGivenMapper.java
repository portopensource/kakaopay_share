package com.codingtest.portfolio.domain.money;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MoneyGivenMapper {
    List<MoneyGiven> selectByToken(String token);

    MoneyGiven selectByTokenAndUserId(String token, int userId);

    boolean insert(MoneyGiven given);
}
