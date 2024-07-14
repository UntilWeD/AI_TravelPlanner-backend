package com.teamsix.firstteamproject.travelplan.dto.Restaurant;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RestaurantResponse {

    private Header header;
    private Body body;

    // getters and setters

    public static class Header {
        private String resultCode;
        private String resultMsg;

        // getters and setters
    }

    public static class Body {
        private Items items;
        private String numOfRows;
        private String pageNo;
        private String totalCount;

        // getters and setters
    }

    public static class Items {
        private List<RestaurantDto> item;

        // getters and setters
    }
}
