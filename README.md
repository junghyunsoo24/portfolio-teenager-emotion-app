# AI 감정분석/챗봇을 활용한 모니터링 및 상담 앱
### 2023 과학기술정보통신부 ICT멘토링 한이음 프로젝트
- 아동 학대 예방 관련 특허 출원(10-2023-0162935)
- 청소년 상담 관련 특허 출원(10-2024-0019225)
- 청소년/아동은 생성형 AI를 통한 편안한 대화, 전문가는 감정분석 결과를 확인 후 실시간 상담, 관리자는 인원 관리 및 매칭
- 모바일 프론트엔드1, 백엔드1, 클라우드1, 삼성 SDS 멘토님1, 교수님1으로 구성

## 1. 문제 정의
#### 1) 가정학대 및 재학대 지속적으로 증가
  - 아동 학대 사례 건수: 2019년 30,045건, 2020년 30,905건, 2021년 37,605건(KOSIS, 학대 피해 아동 보호 현황, 사례 판단 결과 (2018~2021))
  - 재학대 발생 건수: 2019년 3,431건, 2020년 3,671건, 2021년 5,517건 (KOSIS, 재학대 사례 발생 건 (2019~2021))
#### 2) 정서 불안 문제 겪는 청소년들의 사회적 이슈
  - 우울증 갤러리에서 활동한 청소년들이 자살하거나 성착취 당한 사건들 보도 ex)경향신문,연합뉴스
  - ->원래 목적은 위로해 줄 사람을 찾아간건데 이를 악용
#### 3) 대면 상담의 단점
  - 아동/청소년은 직접 상담사에게 말하기 힘든 내용도 존재
  - 대면 상담시 직접 센터를 방문해야 함
#### 앱 내에서 (1)아동/청소년은 생성형 AI와 자유롭게 대화, (2)전문가는 감정 분석 결과 확인 후 실시간 상담도 진행하는 시스템 필요

## 2. 목표
#### 1) 아동/청소년
  - 시간 및 공간의 제약에서 자유로운 AI를 통하여 친구처럼 일상 대화를 나눌 수 있는 채팅 제공
  - 신원이 불분명한 사람과 대화를 나누는 것이 아닌 전문가와 채팅으로 상담
#### 2) 전문가
  - 아동/청소년의 감정을 모니터링 할 수 있고, 필요 시 실시간 상담도 제공
  - 대면 상담에 들어가는 시간적, 금전적 등 다양한 비용을 비대면 채팅 상담을 통해 절감
#### 3) 관리자
  - 전문가들의 전문성을 확인 후 승인 여부 결정
  - 전문가에게 적절한 아동/청소년들을 할당

## 3. 시스템 구조
![project](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/75d01731-0d93-409c-b7a4-e44922f3af33)

## 4. 맡은 역할 및 사용 기술
### Kotlin을 통한 Android Native App 개발
#### 1) 역할
- AI 챗봇 채팅(GPT-3.5 Turbo 연동)
- Socket.IO를 통한 실시간 아동/청소년-전문가 채팅
- MPAndroidChart를 통한 청소년 감정 통계
- 청소년-전문가 리스트 및 구성원 할당
#### 2) 기술
- ViewModel를 통한 LiveData 이용 및  MVVM 패턴
- Adapter를 통한 RecyclerView  관리
- DataBinding 및 XML  이용
- Retrofit과 OkHttp 통한 HTTP 성능 최적화
- 협업 통한 Flask 서버와 RESTful API 통신 및 AWS 연동

## 5. 깨달음
- Firebase를 이용해 앱 내에서 가질 수 있는 정보들은 가지게 하여 서버에 정보 최소화 필요
- 단순한 기능은 Flutter를 이용해 웹/Android/IOS 모두 활용하는 앱 개발 필요
- 핸드폰 별 크기가 달라도 화면이 모두 동일하게 나올 수 있도록 하는 것 필요

## 6. 성과
- 2023 과학기술정보통신부 ICT멘토링 한이음 프로젝트 완료
- 아동/청소년 관련 특허 출원
- 2025년 1월 플레이스토어 출시 예정

## 7. UI 화면
### 1) AI 챗봇과 채팅
![image1](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/8b26a108-1f7a-4daf-bbe3-4757867061af) ![image2](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/52561c89-7e45-4219-b786-40110788f8e5)
### 2) 청소년-전문가 채팅
![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/0094f1a7-1615-484e-ae56-31bedb22910a)![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/7d7e11db-6b8b-48ce-bb90-9cbe108864ac)
### 3) 할당된 청소년 목록 조회
![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/a61f2b50-32a1-4138-8963-bd8ef97ebdb7)![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/64bbcd6b-0277-4a33-9fcb-ee5f28065d00)
### 4) 할당된 청소년 모니터링
![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/47e394de-0440-42e9-a984-de03ecdb10f0)![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/cc99807b-6297-444e-816e-54658d7603af)
### 5) 전문가 승인
![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/2bb42ccb-5db2-435a-af2e-7b46741ca033)![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/9c0b1f0f-4270-42a3-8ac7-f47be91226c9)
### 6) 청소년-전문가 매칭
![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/aed60fad-a0fb-4c97-bdf9-d86c3f723a01)![image](https://github.com/junghyunsoo24/portfolio-teenager-emotion-prevent-app-teenagers/assets/117528532/262ad7ac-91a6-4732-b4e0-4e73639071c4)
