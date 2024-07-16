package com.teamsix.firstteamproject.travelplan.dto.restaurant;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RestaurantResponse {

    @JsonProperty("response")
    private Response response;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response{
        private Header header;
        private Body body;
    }



    @Getter
    @Setter
    @NoArgsConstructor
    public static class Header {
        private String resultCode;
        private String resultMsg;


    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Body {
        private Items items;
        private String numOfRows;
        private String pageNo;
        private String totalCount;


    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Items {
        private List<Item> item;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Item {
        private String title;
        private String description;
        private String subDescription;
        private String reference;
        private String rights;
        private String source;
        private String spatial;
        private String dataStdDt;
        private String dataOfferInst;
        private String rstrNm;
        private String rstrBhfNm;
        private String rstrAsstnNm;
        private String rstrClNm;
        private String rstrRoadAddr;
        private String rstrLnbrAddr;
        private String rstrPnu;
        private String rstrLatPos;
        private String rstrLotPos;
        private String rstrGidCd;
        private String rstrInfoStdDt;

    }
}
