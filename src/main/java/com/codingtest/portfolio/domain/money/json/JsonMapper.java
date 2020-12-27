package com.codingtest.portfolio.domain.money.json;

import com.codingtest.portfolio.domain.money.MoneyGiven;
import com.codingtest.portfolio.domain.money.MoneyShare;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JsonMapper {

    public List<GivenInfo> moneyGivenListToJson(List<MoneyGiven> givenList) {
        List<GivenInfo> result = new ArrayList<>();
        givenList.forEach((given) -> {
            result.add(moneyGivenToJson(given));
        });
        return result;
    }

    public GivenInfo moneyGivenToJson(MoneyGiven given) {
        return GivenInfo.builder().cost(given.getCost())
                .userId(given.getUserId()).build();
    }

    public MoneyInfo moneyShareToJson(MoneyShare share, List<GivenInfo> givenList) {
        return MoneyInfo.builder().budget(share.getBudget())
                .token(share.getToken())
                .given(share.getBudget() - share.getBalance())
                .timestamp(share.getMadeTime())
                .givenInfo(givenList)
                .build();
    }

    public MoneyInfo moneyShareAndGivenListToJson(MoneyShare share, int given, List<MoneyGiven> givenList) {
        return MoneyInfo.builder().budget(share.getBudget())
                .token(share.getToken())
                .given(given)
                .timestamp(share.getMadeTime())
                .givenInfo(moneyGivenListToJson(givenList))
                .build();
    }
}
