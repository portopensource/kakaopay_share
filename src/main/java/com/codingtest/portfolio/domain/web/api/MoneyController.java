package com.codingtest.portfolio.domain.web.api;

import com.codingtest.portfolio.domain.money.MoneyService;
import com.codingtest.portfolio.domain.money.json.MakeInfo;
import com.codingtest.portfolio.domain.money.json.ReceiveInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class MoneyController {

    @Autowired
    private MoneyService moneyService;

    // 받기
    @GetMapping(value = "/receive", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getItem(
            @RequestParam(value = "token", required = true) String token
			, @RequestHeader(name = "X-USER-ID", required = true) Integer userId
			, @RequestHeader(name = "X-ROOM-ID", required = true) String roomId
    ) {
        ReceiveInfo receiveInfo = moneyService.receiveShare(token, userId, roomId);
        String code = receiveInfo.getCost() > 0 ? ResponseCode.NORMAL.getCode() :  ResponseCode.RECEIVE_FAIL.getCode();
        return ResponseEntity.ok(receiveInfo.code(code));
    }

    // 뿌리기
    @PostMapping(value = "/make", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity make(
		    @RequestParam(value = "budget", defaultValue = "", required = true) Integer budget
		    , @RequestParam(value = "amount", defaultValue = "", required = true) Integer amount
            , @RequestHeader(name = "X-USER-ID", required = true) Integer userId
            , @RequestHeader(name = "X-ROOM-ID", required = true) String roomId
    ) {
        String token = moneyService.makeShare(roomId, userId, budget, amount);
        ResponseCode code = token == null ? ResponseCode.MAKE_FAIL : ResponseCode.NORMAL;
        return ResponseEntity.ok(MakeInfo.builder().token(token).build().code(code.getCode()));
    }

    // 조회
    @GetMapping(value = "/info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity info(
            @RequestParam(value = "token", required = true) String token
			, @RequestHeader(name = "X-USER-ID", required = true) Integer userId
			, @RequestHeader(name = "X-ROOM-ID", required = true) String roomId
    ) {
        ApiResponse info = moneyService.findShare(token, userId);
        return ResponseEntity.ok(info);
    }


}
