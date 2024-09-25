package com.teamsix.firstteamproject.travelplan.dto.gpt;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DomesticTravelRequest {

    private LocalDate startDate;
    private LocalDate endDate;
    private String companions;
    private String departureCity;
    private String transportation;
    private String style;
}
