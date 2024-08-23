package com.teamsix.firstteamproject.travelplan.dto.travelplan;

import com.teamsix.firstteamproject.travelplan.entity.TravelBasket;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

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

    public TravelBasket toEntity(){
        return TravelBasket.builder()
                .basketItems(this.basketItems != null ?
                        this.basketItems.stream()
                        .map(BasketItemDTO::toEntity)
                        .collect(Collectors.toList()) : null
                ).build();
    }

}
