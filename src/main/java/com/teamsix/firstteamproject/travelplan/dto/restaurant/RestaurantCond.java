package com.teamsix.firstteamproject.travelplan.dto.restaurant;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RestaurantCond {

    //세션당 요청 레코드 수 [필수]
    private String numOfRows;

    //페이지 수
    private String pageNo;

    //시군구명(한글) [필수]
    private String areaNm;

    //식당분류명
    private String clNm;




}
