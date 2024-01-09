# AI 감정분석과 챗봇을 활용한 청소년 앱

## 활동
* 학교에서 주관하는 엔지니어링 페어 전시
* 특허출원

## 1. 문제 정의
### 1.1 문제 정의
  정서 불안 문제를 겪고 있는 청소년들과 관련된 사건들이 최근 사회적으로 이슈가 되고 있다. 우울증 갤러리에서 활동한 청소년들이 자살하거나 성착취 당한 
 사건들이 보도되었다. 우울증갤러리를 찾는 원래 목적은 너무 외로워서 내 얘기를 들어주고 위로해 줄 사람을 찾아간 거다. 그렇지만 외롭고 고립된 상황을 악용하는 사례들이 존재하였다. 
  
  한편 밀양시 보건소에서 ‘찾아가는 정신 건강 상담 서비스를 운영하였다. 이와
 같이 우울증이나 정서 불안 환자들이 직접 찾아가지 않고, 접근성 향상을 높이기 위한 정신 건강 복지 서비스들이 시행되고 있다. 하지만 직접 찾아다니면서 들어가는 여러 시간적, 금전적 등 다양한 비용들이 존재한다.

 정서 불안 청소년들은 이야기를 나눌 대상과 방황 속에서 올바른 길로 나아갈 수 있도록 하는 사람이 필요하다. 그리고 정신 건강 복지 관련자들은 시간과 비용이 적게 드는 불안 정서 모니터링 및 상담 서비스가 필요할 수 있다.

### 1.2 목표
#### 1) 청소년
  친구처럼 일상 대화를 나눌 수 있는 채팅으로 긍정적인 정서를 함양한다. 
특히 우울증을 가지고 있거나 정서적으로 불안한 청소년들에게 시간 및 공간의 
제약에서 자유로운 AI를 통하여 대화할 수 있는 기능을 제공한다. 
청소년이 신원이 불분명한 사람과 대화를 나누는 것이 아닌 전문가와 채팅으로 
상담받을 수 있게 한다. 
#### 2) 전문가
  손쉽게 청소년들의 정서를 모니터링 할 수 있고 상담할 수 있다. 
대면 상담에 들어가는 시간적, 금전적 등 다양한 비용을 비대면 채팅 상담을 통해 절감할 수 있다. 
#### 3) 관리자
 전문가들의 전문성을 확인하고 승인하여 전문가들의 승인 여부를 결정한다.
전문가에게 적절한 청소년들을 할당하여 해당 청소년들의 감정을 전문가가 
모니터링 할 수 있도록 한다.

## 2. 시스템 구조
![project](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/75d01731-0d93-409c-b7a4-e44922f3af33)

## 3. 구현기술
### 3.1 안드로이드 
1. Kotlin을 이용해 안드로이드 Native 앱 개발 
2. Socket.IO를 이용하여 실시간 양방향 채팅할 수 있도록 구현
3. 데이터가 공유될 수 있도록 ViewModel 아키텍처 컴포넌트 이용
4. UI 업데이트 간소화 및 일관성 유지를 위해서 DataBinding 이용
5. 그래프를 활용할 수 있도록 MPAndroidChart 라이브러리 이용
### 3.2 챗봇 모델
1. openAI의 GPT-4 챗봇 모델 사용
2. QnA의 형태가 아닌 감정적이거나 일상적인 대화를 할 수 있도록 AI Hub
데이터 셋을 통한 학습
3. 사전 프롬프트 작업을 통해 공감 잘하는 챗봇으로 학습
### 3.3 감정 분석 모델
1. RNN, LSTM, GRU, Bi-LSTM 등의 신경망들로 텍스트 다중 분류 모델을 개발하여 성능을 비교하고 가장 나은 학습 신경망 모델을 사용
### 3.4 백엔드
API 서버(클라이언트와 통신하는 서버), 챗봇 서버(사용자 문장에 대한 답변 API 호출 서버), 
감정 분석 서버(청소년 문장의 감정 분석), 채팅 서버(청소년과 전문가 간 채팅) 개발
1) API 서버
   (1) NestJS 프레임워크를 사용
   (2) 클라이언트와 통신하기 위한 엔드포인트를 제공
   (3) 사용자 정보와 관련된 내용에 대해 데이터베이스에 접근   
2) 챗봇 서버, 감정 분석 서버
   Flask 프레임워크를 사용
3) 채팅 서버
   Socket.io와 Redis를 활용해 전문가와 청소년 간 실시간 양방향 채팅 구현
### 3.5 배포
#### 1) 서버구축
  Nginx를 이용한 80번 포트를 사용하는 Http 서비스 구성
  Nginx를 활용한 API 서버 및 채팅 서버 간의 리버스 프록시 설정
#### 2) 도커를 활용한 가용성 및 배포
  Docker를 사용하여 각 서버 역할을 하는 컨테이너로 패키징
### 3.6 데이터베이스
#### 1) AWS RDS 활용
  AWS RDS를 이용하여 데이터베이스를 외부로 분리하여 안정적인 접근 및 확장성을 확보하고 데이터 일관성과 안정성을 유지

## 4. 모바일 화면
* AI 챗봇과 채팅
  
![image1](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/8b26a108-1f7a-4daf-bbe3-4757867061af) ![image2](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/52561c89-7e45-4219-b786-40110788f8e5)

* 청소년-전문가 채팅
  
![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/0094f1a7-1615-484e-ae56-31bedb22910a)![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/7d7e11db-6b8b-48ce-bb90-9cbe108864ac)

* 할당된 청소년 목록 조회
  
![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/a61f2b50-32a1-4138-8963-bd8ef97ebdb7)![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/64bbcd6b-0277-4a33-9fcb-ee5f28065d00)

* 할당된 청소년 모니터링
  
![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/47e394de-0440-42e9-a984-de03ecdb10f0)![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/cc99807b-6297-444e-816e-54658d7603af)

* 전문가 승인

![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/2bb42ccb-5db2-435a-af2e-7b46741ca033)![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/9c0b1f0f-4270-42a3-8ac7-f47be91226c9)

* 청소년-전문가 매칭

![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/aed60fad-a0fb-4c97-bdf9-d86c3f723a01)![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/262ad7ac-91a6-4732-b4e0-4e73639071c4)


