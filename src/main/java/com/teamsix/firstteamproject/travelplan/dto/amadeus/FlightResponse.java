package com.teamsix.firstteamproject.travelplan.dto.amadeus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FlightResponse {

    private List<FlightDestination> data;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class FlightDestination {
        private String type;
        private String origin;
        private String destination;
        private String departureDate;
        private String returnDate;
        private Price price;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Price{
        private String total;
    }
}
