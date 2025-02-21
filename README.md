# 여행플래너

여행의 시작부터 끝까지 편리하게 여행플래너에게 맡겨보세요

![프로젝트대표사진_](https://github.com/user-attachments/assets/c7446863-66ed-458a-a42c-0f0d5d7764bc)


## 1. 프로젝트 소개

- 여행의 추천부터 여행계획작성 및 리뷰까지 여행의 시작과 끝을 계획하는 웹서비스입니다.
- 사용자는 자신의 자산, 여행지에 대한 선호정보를 입력하여 여행지를 추천받습니다.
- 사용자는 숙소, 식당, 관광명소 총 3가지의 카테고리로 원하는 값들을 바구니에 담습니다.
- 그 후 여행플래너로 이동하여 바구니에 담았던 값들을 자신이 원하는 1,2, n일차에 옮깁니다.
- 원한다면 텍스트를 작성하고 여행계획 저장을 하여 자신의 유저페이지에서 여행플래너를 확인합니다.
- 마지막으로 유저는 자신의 여행플래너와 담았두었던 바구니를 다른 사용자와 커뮤니티에서 공유할 수 있습니다. (백엔드만 구현)


### 개발팀 및 역할 분담

- 김진우 : 팀장(설계 및 작업분배), 백엔드, 데브옵스
- 노현비 : 추천서비스
- 인태영 : 프론트엔드

### 개발 환경
- Frontend : React
- Backend : SpringBoot, Redis, MYSQL
- DB : MYSQL, AWS_S3
- Devops : AWS(EC2, RDS), Docker(+compose), Jenkins
- 버전 및 이슈관리 : Github Organization, Github Project
- 협업툴 : Discord, Notion

### 개발 기간

-2024.03 ~ 2024.10

## 2. 전체 서비스 구조와 ERD


### 전체 서비스 구조


![ai여행플래너 아키텍처_수정본](https://github.com/user-attachments/assets/7a023ed1-e98f-4694-8a08-651ed7a8b03f)



- github webhook를 사용하여 AWS EC2에 Jenkins를 배포하여 github push시 자동으로 배포되도록 CI/CD 파이프라인을 구축하였습니다.






### ERD


![Image](https://github.com/user-attachments/assets/f0f2cf4d-fd98-4db1-80ec-847259d5773b)




- basket_Item은 숙소, 호텔과 같은 여행지정보를 담는 아이템으로 이를 travel_Basket에 담아서 관리합니다.
- travel_plan과 travel_basket으로 나눈 이유는 커뮤니티에서 여행정보 공유시 사적인 내용이 담기는 여행 플랜보다는 그 정보들을 담는 바구니 형태로 공유하는 것이 더 편리할거라 생각하였기 때문입니다.


### 패키지 구조

```
└── src
    └── main
        └── java
            └── com
                └── teamsix
                    └── firstteamproject
                        ├── FirstTeamprojectApplication.java
                        ├── community
                        │   ├── controller
                        │   ├── dto
                        │   ├── entity
                        │   ├── repository
                        │   └── service
                        ├── global
                        │   ├── auth
                        │   ├── config
                        │   ├── dto
                        │   ├── error
                        │   ├── security
                        │   └── util
                        ├── travelplan
                        │   ├── config
                        │   ├── controller
                        │   ├── converter
                        │   ├── dto
                        │   ├── entity
                        │   ├── exception
                        │   ├── repository
                        │   └── service
                        └── user
                            ├── controller
                            ├── dto
                            ├── entity
                            ├── exception
                            ├── repository
                            └── service
```


- 크게 User, Community, TravelPlan, Global로 나누었습니다.
- 도메인 별로 패키지를 나누어 유지보수를 쉽게하기 위함입니다.


## 3. 주요 기능

----------------------

### 1 - 여행 추천
<br/>

![1_여행추천](https://github.com/user-attachments/assets/3e424389-ac20-481a-832f-88a47db43ee0)

<br/>

- 사용자는 여러 항목의 질문에 맞게 답변을 모두 작성하고 제출을 눌러 여행지를 추천 받습니다.
- 위 사용자 답변으로 프롬프트를 완성하여 특정 값으로 요청하여 받아온 정보로 추천합니다.

<br/>

----------------------


### 2 - 여행 정보 확인

<br/>

![2_여행정보확인](https://github.com/user-attachments/assets/87d7569e-e5a7-4964-902a-ba9477dc9b26)

<br/>

- 추천 받은 여행지를 바탕으로 숙소, 식당, 관광명소를 확인할 수 있습니다.
- 왼쪽에는 검색된 장소들과 오른쪽에는 직접한 선택된 장소들을 확인할 수 있습니다.
- 이러한 각 아이템은 오른쪽 구글맵에 클릭하여 구글맵으로 이동하여 상세히 확인할 수 있습니다.

<br/>

----------------------

### 3 - 바구니 여행정보아이템 담기

<br/>

![3_바구니 여행정보담기](https://github.com/user-attachments/assets/f612733e-aa4b-43b2-9d16-22314214fff2)

<br/>

- 왼쪽에서 여러 아이템을 오른쪽으로 옮겨 바구니에 담을 수 있습니다.
- 바구니에 담긴 아이템은 지도에 마커로 보여지게 되며 각 카테고리에 따라 다른 마커를 보여줍니다.
- 더 원하는 장소를 보고싶다면 아래에 더보기를 클릭하여 더 많은 장소를 볼 수 있습니다.

<br/>

----------------------

### 4 - 여행플래너에서 원하는 시간에 아이템 옮기기

<br/>

![4_여행플래너에서 아이템옮기기](https://github.com/user-attachments/assets/d7bd01a1-5b08-42f0-a662-643ffd678ac5)

<br/>

- 원하는 아이템을 전부 담았으면 여행플래너로 이동하여 원하는 날짜에 아이템을 옮길 수 있습니다.
- 마커는 전부 지도에 보여지며, 마커를 클릭하면 해당 아이템의 정보를 확인할 수 있습니다.

<br/>

----------------------

### 5 - 여행플래너 작성

<br/>

![5_여행플래너 작성](https://github.com/user-attachments/assets/a3217b7f-f9c4-420f-8dcc-7c8f403e8fa3)

<br/>

- 마지막으로 여행플러너의 제목과 내용을 작성합니다.
- 작성을 완료하면 저장을 눌러 자신의 유저페이지에서 여행플래너를 확인할 수 있습니다.

<br/>

----------------------

### 6 - 저장된 정보 유저에서 확인

<br/>

![6_저장된 정보 유저에서 확인](https://github.com/user-attachments/assets/d16ffaca-3ddb-4422-83b6-c9d509a7be08)

<br/>

- 사용자는 여행 플래너를 여러개 가질 수 있고 여행계획목록에서 확인하여 클릭하면 상세여행정보를 확인할 수 있습니다.
- 저장된 여행플래너를 유저페이지에서 확인할 수 있습니다.
- 또한 바구니에 담았던 아이템들도 확인이 가능하고 지도에 마커들 또한 볼 수 있습니다.

<br/>


(로그인 및 회원가입, 이메일인증은 생략하였습니다. )


-----------------------


## 4. 프로젝트 회고

### 1 - 프로젝트를 진행하면서 가장 어려웠던 점

아무래도 팀장으로서 서비스설계, 작업분배, 백엔드개발 등 다양한 일을 병행해야 해서 힘들었습니다.
특히 기능정의문서와 기한설정이 중요한데 이를 초반에 미숙하게 하여 나중에 기능추가 및 수정이 많이 발생하였습니다.
이러한 경험으로 팀원들과의 정확한 의사소통과 기능정의문서의 중요성을 깨달았습니다. 
후에 협업을 다시 하게 된다면 팀원들이 각 기능에 대해 어떻게 이해하고 있는지 서로 확인하고 
문서를 소홀히 작성하지 않도록 해야겠다고 생각했습니다.



### 2 - 프로젝트를 진행하면서 가장 즐거웠던 점

1. 이전에는 스프링에 대해서 강의를 계속 들었는데 이를 제대로 활용할 수 있는 기회가 되어서 좋았습니다.
프로젝트에서 제가 설계한 기능에 맞게 알맞은 스프링 기술을 사용하여 개발한다는 것이 놀라우면서도 즐거웠습니다.
2. 따로 환경에 제약받지 않고 새로운 기술(aws, docker, jenkins)등들을 학습하고 개발에 적용할 수 있어 좋았습니다.
프로젝트 초반에는 새로운기술을 배운다는 것에 굉장히 거부감이 있었지만 점점 새로운 것을 학습할수록 거부감이 사라졌고 
개발에 사용하여 작동하는 것을 보면 뿌듯했습니다. 특히 docker와 jenkins를 사용하여 자동배포를 구현하였을때 매우 편리하여 놀라웠습니다.
신기술이 괜히 매력적인게 아니구나 라고 생각했습니다.
3. 내가 만든 기능이 다른 팀원과의 결과물과 연결되어 하나의 서비스로 완성되어 작동하는 것이 뿌듯했습니다.



### 3 - 아쉬웠던 점

앞에서도 말했듯이 초반에 기능정의문서, 기한, 기능이해, 의사소통 등이 미숙하여 시간이 많이 부족하였습니다.
 시간에 쫒겨 기능구현에 집중하다보니 스프링에 대한 깊은 이해를 바탕으로 개발하지 못한 것이 아쉬웠습니다.
그 외에도 더미데이터를 넣어 성능테스트를 하지 못하고 각 클래스에 대하여 단위테스트코드를 작성하지 못한 것이 아쉬웠습니다.
Mockito를 사용하여 user에 대한 단위테스트를 작성하였으나 후에 시간이 부족하여 나머지는 작성하지 못하였습니다.

### 4 - 개선해야할 점과 앞으로의 계획

이번 프로젝트는 저에게 있어서 가장 크고 의미있던 프로젝트였습니다.
제가 정말로 개발에 흥미나 적성이 있나 확인할 수 있었고, 프로젝트가 어떻게 진행되어야 하는 지에 대해 많이 배울 수 있었습니다.
앞으로 위의 경험들을 기반으로 다음 프로젝트에서는 더욱 성장하여 더 나은 결과물을 만들어내고 싶습니다.


