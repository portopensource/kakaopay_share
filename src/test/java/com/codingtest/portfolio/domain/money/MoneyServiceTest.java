package com.codingtest.portfolio.domain.money;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.junit.JUnitTestRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootTest
class MoneyServiceTest {

    @Autowired
    private MoneyService service;

    @Test
    public void test_분배() {
        int[] budget = new int[]{100, 59, 22, 15, 62, 32, 5};
        int[] amount = new int[]{6, 4, 20, 9, 30, 25, 1};

        for (int i=0; i < budget.length; i++) {
            log.info(i + "회차");
            for (int[] cost : service.distribute(budget[i], amount[i])) {
                log.info("분배:" + cost[0] + ":" + cost[1]);
            }
        }
    }

    @Test
    public void test_분배금액_고르기() {
        List<MoneyDividen> list = Arrays.asList(
                makeOne("1")
                , makeOne("2")
        );
        for (int i : Arrays.asList(1,2,3,4,5)) {
            service.pickAmongstDividen(list);
        }
    }

    @Test
    public void test_입력_시나리오() {
        String roomId = "room01";
        int maker = 0;
        int rec1 = 1;
        int rec2 = 2;
        int budget = 1023;
        int amount = 32;

        String[] token = new String[3];
        for (int i=0;i<token.length;i++) {
            token[i] = service.makeShare(roomId, maker, budget, amount);
            log.info("입력 결과 : " + service.findShare(token[i], maker));
        }
        // 예산 여러개로 만들기

        // when 7일 되었을 때 조회

        // 주인외에 조회

        // 받기

        // when 10분 넘었을 때 받기

        // userId 주인이 받기

        // 한 유저로 여러번 받기

        // 동일 방 유저만 받기

        // 받고나서 잔여량, 배분, 분배 잔여량 확인

        // 잔고 없을 때 불가능할 때 받기

        // 잔고 리스트, 뿌리기 아이템 정보 경쟁 테스트 -> 일어날 일 한 방에 있는 사람들

        // 현재 방에 있는지 확인. 이건 Cookie와 Auth관련된 것. RoomId, UserId 조작 불가능해야 함.

    }

    public MoneyDividen makeOne(String token) {
        return MoneyDividen.builder().token(token).cost(30).build();
    }

}