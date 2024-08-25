package com.teamsix.firstteamproject.travelplan.dto.travelplan;


import com.teamsix.firstteamproject.travelplan.entity.TravelPlan;
import com.teamsix.firstteamproject.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelPlanDTO {

    @NotBlank
    private Long userId;

    @NotBlank
    private String title;

    private String content;

    @NotBlank
    private Date createdAt;

    private TravelBasketDTO travelBasket;


    // DTO -> Entity
    public TravelPlan toEntity(TravelPlanDTO dto, User user){
        TravelPlan travelPlan = TravelPlan.builder()
                .user(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdAt(new Date())
                .travelBasket(dto.getTravelBasket().toEntity())
                .build();
        travelPlan.getTravelBasket().makingDependency(travelPlan);
        return travelPlan;
    }



//    private String destination;
//    private String description;
//    private LocalDate startDate;
//    private LocalDate endDate;
}
