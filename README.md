# 블록체인 기반 의료 데이터 인증 및 AI를 활용한 노약자 맞춤형 의료 애플리케이션 

## 활동
* 2024 프로보노 프로젝트 진행
* 특허출원


## 1. 문제 정의
### 1.1 문제 정의
  1) 병원 이동 시, 환자의 효율적인 데이터 제공 필요성
     - 현재 의료 체계에서 환자가 병원 이동 시, 환자의 데이터를 다시 제공
     - 응급 상황에서 환자의 치료 이력과 건강 특이 상황을 신속하게 파악하는 것이 중요


   2) 구두로 진행되어 일관적이지 않고 부정확한 진료 상담
      - 환자는 증상 설명 시, 환자는 잘못된 기억에 의존하여 사실이 아닌 설명 가능
      - 환자마다 증상 표현 방법이 달라 일관적으로 환자 상태 파악하기 어려움
      - 의료진은 이러한 이유로 환자의 상태를 정확히 파악하는데 어려움. 이로 인한 오진 가능성 존재

   3) 의료 데이터 접근성 낮음
      - 일반인들은 의료 데이터 접근성이 낮아 의료 지식을 갖추기 어려움 
      - 환자 본인의 의료 데이터 기록(본인의 병이나, 처방 기록, 수술 이력 등) 을 하나의 시스템으로 손쉽게 조회하기 어려움

  위의 세 가지 이유로 환자 데이터를 의료진에게 손쉽게 공유하고, 환자가 본인의 의료 데이터를 손쉽게 조회 및 기록하며, 의료 커뮤니티를 통해 의료 데이터 접근성을 높이는 시스템이 필요하다고 판단


### 1.2 목표
#### 1) 환자
   -블록체인을 통해 검증된 자신의 의료 데이터 조회
   
   -자신의 의료 데이터를 본인의 기기에 저장
   
   -자신의 의료 데이터에서 필요 내용 추가 기록
   
   -필요한 의료진들에게 자신의 의료 데이터 공유
   
   -알림을 통한 효과적인 약물 복용 관리
   
   -게시판에서 의료진으로부터 질문 답변
#### 2) 의료진
   -진료 전 혹은 긴급 상황 시 환자의 의료 데이터 조회를 통해 더욱 효과적인 진단
   
  -환자의 의료 정보들을 본인의 데스크톱에 저장
 
  -블록체인을 통해 자신이 환자에게 제공하는 의료 데이터 무결성 보장

  -게시판에서 의료진으로써 답변하여 전문성 어필 


## 2. 시스템 구조
![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/07d85dc9-4d0d-40ae-b013-8534f7c4afc7)

## 3. 내가 맡은 구현기술
### 크로스플랫폼 앱 개발
1. 역할
   1) 프론트엔드(Android 앱, Window 앱)
   2) 블록체인

2. 프론트 개발 환경 및 사용 기술
   1) 개발 도구: Android Studio
   2) 개발 언어: Dart 

3. 블록체인 개발 환경 및 사용 기술
   1) 개발 도구: Visual Studio Code
   2) 개발 언어: Python
   
4. 프론트 구현 기능
   
    (1) FireBase FireStore를 통한 다른 플랫폼 데이터 송수신 구현
   
    (2) 다른 플랫폼에서 데이터 받을 시 알림 기능 및 데이터 조회 구현
   
    (3) 다른 플랫폼으로 데이터 전송 기능 구현
   
    (4) 의료 API HTTP 통신을 통해 받아올 수 있도록 구현
   
    (5) 회원가입, 로그인, 로그아웃 기능 구현

6. 블록체인 구현 기능
   
    (1) 의료 데이터가 해시값으로 변환하여 블록체인에 저장할 수 있도록 구현


## 4. UI 화면
* 메인페이지
  
![스크린샷 2024-07-03 175842](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/81362a24-ed9c-463a-8865-52818c184f7c)


* 환자->의료진 의료 데이터 전송
  
![스크린샷 2024-07-03 182709](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/b8d579f0-4d32-4ba6-97d8-10d4abaf93b8)

* 의료진->환자 의료 데이터 전송
  
![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/534bfab3-7c03-4d53-8cea-9b8d1937643f)


* 의료진의 환자로부터 받은 전송 이력
  
![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/23927bd4-331e-4f4d-97b0-786083dcfa39)


* 환자의 의료 게시판 이용

![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/7647a3b6-0372-48d8-bbfd-a889c4cd80e2)


* 의료진의 의료 게시판 이용

![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/d7cff2a3-2ff1-467d-a64a-bd30d16ff7d4)


* 환자의 진료 전 추가 의료 데이터 등록
  
![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/6c7b8beb-2c1a-42f2-8fdc-6a1691e21900)


* 환자의 의료 API 불러오기
  
![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/644ca5ea-4816-4692-9847-779d65d40b6b)


