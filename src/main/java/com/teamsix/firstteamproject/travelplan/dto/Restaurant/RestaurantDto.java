package com.teamsix.firstteamproject.travelplan.dto.Restaurant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestaurantDto {

    //시티투어 주변 맛집 식당명
    @JsonProperty("rstrNm")
    private String restaurantName;

    //시티투어 주변맛집식당 분류명
    @JsonProperty("rstrClNm")
    private String restaurantClass;

    //시티투어 주변맛집식당 도로명 주소
    @JsonProperty("rstrRoadAddr")
    private String roadAddress;


    //시티투어 주변맛집식당 지번 주소
    @JsonProperty("rstrLnbrAddr")
    private String LnbrAddress;

}
