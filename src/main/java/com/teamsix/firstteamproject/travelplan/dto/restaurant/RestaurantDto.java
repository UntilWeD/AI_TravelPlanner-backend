package com.teamsix.firstteamproject.travelplan.dto.restaurant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestaurantDto {

    //시티투어 주변 맛집 식당명
    private String restaurantName;

    //시티투어 주변맛집식당 분류명
    private String restaurantClass;

    //시티투어 주변맛집식당 도로명 주소
    private String roadAddress;


    //시티투어 주변맛집식당 지번 주소
    private String LnbrAddress;

}
