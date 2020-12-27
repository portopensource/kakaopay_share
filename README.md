# [카카오 페이 뿌리기 테스트]

# 사용환경
- spring boot + Mybatis + H2 내장

# 영속 데이터 설계
- MoneyShare
<br> 뿌리기 아이템의 정보, 잔여 개수 등 
- MoneyDividen
<br> 뿌리기시 결과로 만들어진 금액 아이템의 금액, 명수 저장
<br> cost(금액), amount(명수)
- MoneyGiven
<br> 받기 요청시 해당 유저 기록을 위해 사용

#Transaction 
- 받기 요청시 데이터 경쟁 발생 가능 <br>
- JPA의 낙관적 락과 유사한 방식으로 version 컬럼 두고 데이터 경쟁 처리

# API 명세
- 서버 자체 40x 5xx 오류외 서비스적인 응답은 모두 code로 정의
- 모든 API응답은 depth없이 사용. code 와 같은 detph로 설계

# 기타
- lombok 코드 줄임.
- Error 핸들링 @ControllerAdvice, @ExceptionHandler, BasicErrorController 활용

# 메인 서비스 명세 테스트
- MoneyServiceTest 테스트 케이스 실행

# 테스트 방법
- 헤더 설정
  <br>X-USER-ID=?
  <br>X-ROOM-ID=?
- http://127.0.0.1:8080/make?budget=100&amount=3
- http://127.0.0.1:8080/info?token=EDE
- http://127.0.0.1:8080/receive?token=EDE