package com.teamsix.firstteamproject.travelplan.dto.travelplan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketItemDTO {
    private String title;
    private String content;
    private String imageUrl;


}
