package com.teamsix.firstteamproject.travelplan.dto.amadeus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AmadeusCond {

    private String origin;

    private String departureDate;

    //boolean
    private String oneWay;

    private String duration;

    //boolean
    private String nonStop;

    // integer
    private String maxPrice;

    private String viewBy;


}
