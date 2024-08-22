package com.teamsix.firstteamproject.travelplan.dto.travelplan;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TravelBasketDTO {

    private List<BasketItemDTO> basketItems;


    public void mappingImageUrl(List<String> imageUrls){
        for(BasketItemDTO item : basketItems){
            for(String url : imageUrls){
                if(imageUrls.contains(item.getImage().getOriginalFilename())){
                    item.setImageUrl(url);
                    break;
                }
            }
        }
    }

}
