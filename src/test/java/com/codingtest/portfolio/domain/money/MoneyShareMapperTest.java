package com.codingtest.portfolio.domain.money;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MoneyShareMapperTest {

    @Autowired
    private MoneyShareMapper suv;

    @BeforeEach
    public void before() {
    }

    @Test
    public void test_조회() {
        assertDoesNotThrow(() -> {
            suv.insert(makeOne(""
            ));
            log.info("조회 개수 : " + suv.selectAll().size());
        });
    }

    @Test
    public void test_insert() {
        suv.insert(makeOne("test"));
    }

    @Test
    public void test_selectAll() {
        suv.selectAll();
    }

    @Test
    public void test_selectByToken() {
        suv.selectByToken("abc");
    }

    @Test
    public void test_select_token데이터없을때() {
        assertNull(suv.selectByToken("none exist"));
    }

    @Test
    public void test_잔액처리_다른트래픽_변경시_실패처리한다() {
        String token = "abc";
        suv.insert(makeOne(token));

        MoneyShare v1= suv.selectByToken(token);
        MoneyShare v2= suv.selectByToken(token);

        assertEquals(1, suv.updateBalanceWithCollision(v2));

        assertEquals(0, suv.updateBalanceWithCollision(v1));

        assertEquals(1, suv.selectByToken(token).getVersionBalance());

        assertEquals(v1.getBalance() - 1, suv.selectByToken(token).getBalance());
    }

    @Test
    public void insert_token중복() {
        MoneyShare dummy = makeOne("test01");

        assertDoesNotThrow(() -> {
            suv.insert(dummy);
        });
        assertThrows(DuplicateKeyException.class, () -> {
            suv.insert(dummy);
        });
    }

    public MoneyShare makeOne(String token) {
        return MoneyShare.builder()
                .token(token)
                .roomId("room01")
                .userId(243)
                .madeTime(System.currentTimeMillis())
                .budget(423)
                .balance(423)
                .build();
    }
}