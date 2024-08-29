package com.teamsix.firstteamproject.travelplan.dto.travelplan;

import com.teamsix.firstteamproject.travelplan.entity.TravelPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleTravelPlanDTO {

    private Long id;
    private String title;
    private Date createdAt;

    public static SimpleTravelPlanDTO toSimpleTravelPlanDTO(TravelPlan travelPlan){
       return SimpleTravelPlanDTO.builder()
               .id(travelPlan.getId())
               .title(travelPlan.getTitle())
               .createdAt(travelPlan.getCreatedAt())
               .build();
    }
}
