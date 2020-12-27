package com.codingtest.portfolio.domain.money;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MoneyGivenMapperTest {

    @Autowired
    private MoneyGivenMapper givenMapper;

    @BeforeEach
    public void before() {
        String token = "test";
        for (int i=0; i< 100; i++) {
            givenMapper.insert(makeOne(token, i));
        }
    }

    @Test
    public void test_조회() {
        assertTrue(givenMapper.selectByToken("test").size() > 0);

        assertNotNull(givenMapper.selectByToken("notgood"));
    }

    @Test
    public void test_유일성() {
        assertNull(givenMapper.selectByTokenAndUserId("notexist", 23));
        assertNotNull(givenMapper.selectByTokenAndUserId("test", 23));
    }

    public MoneyGiven makeOne(String token, int userId) {
        return MoneyGiven.builder().token(token).userId(userId).cost(200).build();
    }

}