package com.codingtest.portfolio.domain.money;

import com.codingtest.portfolio.domain.web.api.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class MoneyServiceTest {

    @Autowired
    private MoneyService service;

    /**
     * 조회
     */
    @Rollback
    @Test
    public void test_7일되었을때_정보조회() {
        String token = "ABC";
        int userId = 1;
        String roomId = "test";

        MoneyShare share = makeOneShare(token, roomId, userId);

        long madeTime1 = DateUtils.addDays(new Date(), -7).getTime();
        long madeTime2 = DateUtils.addDays(new Date(), -8).getTime();

        assertTrue(service.canSearchWithInValid(madeTime1));
        assertFalse(service.canSearchWithInValid(madeTime2));
    }

    @Rollback
    @Test
    public void test_주인외에_조회() {
        String token = "ABC";
        int userId = 1;
        String roomId = "test";

        token = service.makeShare(roomId, userId, 100, 3);

        assertEquals(ResponseCode.INFO_NOTVALID.getCode(), service.findShare(token, 2).getCode());
        assertEquals(ResponseCode.NORMAL.getCode(), service.findShare(token, userId).getCode());
    }

    @Rollback
    @Test
    public void test_유효하지않은_토큰으로조회() {
        String token = "ABC";
        int userId = 1;
        String roomId = "test";

        token = service.makeShare(roomId, userId, 100, 3);

        assertEquals(ResponseCode.INFO_NOTVALID.getCode(), service.findShare(token + "wrong", userId).getCode());
        assertEquals(ResponseCode.NORMAL.getCode(), service.findShare(token, userId).getCode());
    }

    /**
     * 받기
     */
    @Rollback
    @Test
    public void test_주인이_받기시도() {
        String token = "ABC";
        int userId = 1;
        String roomId = "test";

        token = service.makeShare(roomId, userId, 100, 3);

        assertEquals(0, service.receiveShare(token, userId, roomId).getCost());
    }

    @Rollback
    @Test
    public void test_동일하지않은_방사용자_받기시도() {
        String token = "ABC";
        int userId = 1;
        String roomId = "test";

        token = service.makeShare(roomId, userId, 100, 3);

        assertEquals(0, service.receiveShare(token, userId, "anotherRoom").getCost());
        assertEquals(0, service.receiveShare(token, userId, roomId).getCost());
    }

    @Rollback
    @Test
    public void test_동일유저로_여러번_받기시도() {
        String token = "ABC";
        int userId = 1;
        String roomId = "test";

        token = service.makeShare(roomId, userId, 100, 1);

        assertNotEquals(Integer.valueOf(0), service.receiveShare(token, 3, roomId).getCost());
        assertEquals(0, service.receiveShare(token, 3, roomId).getCost());
    }

    @Rollback
    @Test
    public void test_10분지나_받기시도() {
        long madeTime = new Date().getTime() - 1000 * 60 * 10 - 1;
        boolean notValid = new Date().getTime() - madeTime > 10 * 1000 * 60;

        assertTrue(notValid);

        notValid = new Date().getTime() - madeTime + 2 > 10 * 1000 * 60;
        assertTrue(notValid);

    }

    @Rollback
    @Test
    public void test_잔고없을때_받기시도() {
        String token = "ABC";
        int userId = 1;
        String roomId = "test";

        token = service.makeShare(roomId, userId, 100, 1);

        service.receiveShare(token, 2, roomId);

        assertEquals(Integer.valueOf(0), service.receiveShare(token, 2, roomId).getCost());
    }

    /**
     * 뿌리기
     */
    @Test
    @Rollback
    public void test_예산_커버리지로_만들기시도() {
        String token = "ABC";
        int userId = 1;
        String roomId = "test";

        assertDoesNotThrow(() -> {
            try {
                service.makeShare(roomId, userId, 100, 1);
                service.makeShare(roomId, userId, 10, 10);
                service.makeShare(roomId, userId, 100, 20);
                service.makeShare(roomId, userId, 20, 3);
                service.makeShare(roomId, userId, 1, 1);
            } catch (Exception e) {
                log.error("TOKEN DUPLICATE 가능");
            }
        });
    }

    /**
     * 기타 테스트
     */
    @Test
    @Rollback
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
    @Rollback
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
    @Rollback
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
    }

    public MoneyDividen makeOne(String token) {
        return MoneyDividen.builder().token(token).cost(30).build();
    }

    public MoneyShare makeOneShare(String token, String roomId, int userId) {
            return MoneyShare.builder()
                    .token(token)
                    .roomId(roomId)
                    .userId(userId)
                    .madeTime(System.currentTimeMillis())
                    .budget(100)
                    .amount(3)
                    .balance(3)
                    .build();
        }

}