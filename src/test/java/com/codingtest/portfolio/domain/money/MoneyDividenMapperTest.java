package com.codingtest.portfolio.domain.money;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MoneyDividenMapperTest {

    @Autowired
    private MoneyDividenMapper suv;

    @BeforeEach
    public void before() {
        String token = "abc";
        Arrays.asList(1,2,3,4,5).forEach((cost) -> {
            suv.insert(makeOne(token, cost));
        });
    }

    @Test
    @Rollback(true)
    public void test_잔액처리_다른트래픽_변경시_실패처리한다() {
        String token = "abc";

        List<MoneyDividen> v1= suv.selectBalanceByToken(token);

        MoneyDividen dividen = v1.get(0);

        assertEquals(1, suv.updateBalanceWithCollision(dividen));

        assertEquals(0, suv.updateBalanceWithCollision(dividen));
    }

    @Test
    @Rollback(true)
    public void test_insert() {
        suv.insert(makeOne("abc", 20));

        assertThrows(DuplicateKeyException.class, () -> {
            suv.insert(makeOne("abc", 20));
        });
    }

    @Test
    @Rollback(true)
    public void test_select() {
        List<MoneyDividen> v1= suv.selectBalanceByToken("abc");

        assertTrue(v1.size() > 0);
    }

    public MoneyDividen makeOne(String token, int cost) {
        return MoneyDividen.builder()
                .token(token)
                .cost(cost)
                .balance(3)
                .build();
    }
}