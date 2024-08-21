package com.teamsix.firstteamproject.travelplan.dto.travelplan;

import com.teamsix.firstteamproject.travelplan.entity.TravelBasket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelPlanDTO {

    private Long id;
    private String title;
    private Date createdAt;
    private TravelBasket travelBasket;

//    private String destination;
//    private String description;
//    private LocalDate startDate;
//    private LocalDate endDate;
}
