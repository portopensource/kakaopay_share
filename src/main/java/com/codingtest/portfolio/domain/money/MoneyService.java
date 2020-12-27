    package com.codingtest.portfolio.domain.money;

import com.codingtest.portfolio.domain.money.json.JsonMapper;
import com.codingtest.portfolio.domain.money.json.MoneyInfo;
import com.codingtest.portfolio.domain.money.json.ReceiveInfo;
import com.codingtest.portfolio.domain.web.api.ApiResponse;
import com.codingtest.portfolio.domain.web.api.ResponseCode;
import com.codingtest.portfolio.support.MoneyConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class MoneyService {
    @Autowired
    private MoneyShareMapper shareMapper;
    @Autowired
    private MoneyDividenMapper dividenMapper;
    @Autowired
    private MoneyGivenMapper givenMapper;
    @Autowired
    private JsonMapper jsonMapper;

//    @PersistenceContext
//    EntityManager entityManager;

    public ApiResponse findShare(String token, int userId) {
        MoneyShare share = shareMapper.selectByToken(token);

        if (share == null
            || userId != share.getUserId()
            || !canSearchWithInValid(share.getMadeTime())
        ) {
            return MoneyInfo.builder()
//                    .code(ResponseCode.FAIL_RECEIVE.getCode())
                    .build()
                    .code(ResponseCode.INFO_NOTVALID.getCode())
                    ;
        }

        List<MoneyGiven> givenList = givenMapper.selectByToken(token);

        int sum = 0;
        for (MoneyGiven given : givenList) {
            sum += given.getCost();
        }

        return jsonMapper.moneyShareAndGivenListToJson(share, sum, givenList).code(ResponseCode.NORMAL.getCode());
    }

    public String makeShare(String roomId, Integer userId, int budget, int amount) {
        String token = createToken();

        List<int[]> dist = distribute(budget, amount);

        MoneyShare share = MoneyShare.builder()
                .token(token)
                .roomId(roomId)
                .userId(userId)
                .madeTime(System.currentTimeMillis())
                .budget(budget)
                .amount(amount)
                .balance(amount)
                .build();

        try {
            shareMapper.insert(share);

            for (int[] el : dist) {
                dividenMapper.insert(MoneyDividen.builder().token(token)
                        .amount(el[1])
                        .cost(el[0])
                        .balance(el[1]).build()
                );
            }

            return token;
        } catch (DuplicateKeyException e) {
            log.info("TOKEN DUPLICATE");
        }

        return null;
    }

    public ReceiveInfo receiveShare(String token, int userId, String roomId) {
        MoneyShare share = shareMapper.selectByToken(token);

        if (share == null
                || share.getUserId() == userId
                || givenMapper.selectByTokenAndUserId(token, userId) != null
                || !roomId.equals(share.getRoomId())
                || share.getBalance() == 0
                || new Date().getTime() - share.getMadeTime() > 10 * 1000 * 60
        ) {
            return ReceiveInfo.builder().build();
        }

        // 잔고 없음
        List<MoneyDividen> dividenList = dividenMapper.selectBalanceByToken(token);

        MoneyDividen dividen = pickAmongstDividen(dividenList);

        if (0 == shareMapper.updateBalanceWithCollision(share)) {
            return ReceiveInfo.builder().build();
        }

        // #TODO collision issue
        dividenMapper.updateBalanceWithCollision(dividen);
        givenMapper.insert(MoneyGiven.builder().token(token).cost(dividen.getCost()).userId(userId).build());

        return ReceiveInfo.builder().cost(dividen.getCost()).build();
    }

    /**
     * move to service logic
     */
    private String createToken() {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bit = (System.currentTimeMillis() + "").getBytes(StandardCharsets.UTF_8);
            return DatatypeConverter.printHexBinary(digest.digest(bit)).substring(0, 3);
        } catch (NoSuchAlgorithmException e) {
        }

        return "";
    }

    private boolean canSearchWithInValid(long time) {
//        return (DateUtils.truncate(new Date(), Calendar.DATE).getTime() - DateUtils.truncate(new Date(time), Calendar.DATE).getTime())
//                / (1000 * 60 * 60 * 24) <= MoneyConst.LimitFindDays;
        return (new Date().getTime() - time) <= 1000 * 60 * 60 * 24 * MoneyConst.LimitFindDays;
    }

    /**
     * budget, amount > 0
     * budget > amount
     * both natural number
     */
    public List<int[]> distribute(int budget, int amount) {
        List<int[]> costList = new ArrayList<>();

        if (budget % amount > 0) {
            costList.add(new int[]{budget / amount + budget % amount, 1});
            costList.add(new int[]{budget / amount, amount - 1});
        } else {
            costList.add(new int[]{budget / amount, amount});
        }

        return costList;
    }

    public MoneyDividen pickAmongstDividen(List<MoneyDividen> dividenList) {
        return dividenList.get(new Random().nextInt(dividenList.size()));
    }
}
