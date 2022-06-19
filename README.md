# 업비트 자동 매매 프로젝트

#### [Reference](https://github.com/WooSungHwan/demo-coin)
#### [Dependency](https://github.com/WooSungHwan/trader-common)

### 프로젝트 설명
5분 캔들 차트를 분석하여 단타 매매를 자동으로 해주는 프로젝트입니다.

#### 제공 기능
- [X] 비트코인 5분봉 차트 정보 크롤링
- [X] 단타 전략을 사용한 자동 매매
  - 기존 5분봉 3틱룰의 원리를 활용
  - 백테스팅을 통해 최고의 이익을 발생시킨 하락폭과 1틱의 갯수를 결정
- [X] 자산 현황, 매매 정보, 백테스팅 결과에 대한 알림 서비스
- [X] 지난 한 달 간의 비트코인 데이터를 활용한 단타 전략 백테스팅

### 결과
하락폭 5%가 1번 발생한 경우 전량 매수하는 전략을 사용한 과거 한 달(2022/5/14 ~ 2020/6/14) 모의 투자 결과 1.9%의 수익률 발생

#### 사용 기술
`Spring Boot` `Spring JPA` `MySQL` `Docker` `Upbit API` `Slack API`


#### 결과 화면

![](https://velog.velcdn.com/images/y005/post/1ed7b273-8f74-4518-af5e-8b4af720d00b/image.png)

![](https://velog.velcdn.com/images/y005/post/490a4d10-b59f-4c39-a0ac-98b68e01264c/image.png)

![](https://velog.velcdn.com/images/y005/post/1b828a0b-7956-4903-87ff-57bcd5280611/image.png)

