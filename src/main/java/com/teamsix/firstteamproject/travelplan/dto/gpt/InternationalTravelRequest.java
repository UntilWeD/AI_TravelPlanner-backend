package com.teamsix.firstteamproject.travelplan.dto.gpt;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InternationalTravelRequest {

    private LocalDate startDate;
    private LocalDate endDate;
    private String gender;
    private String companions;
    private int age;
    private String preference;
    private String budget;
    private String departureCity;

}
