package com.teamsix.firstteamproject.travelplan.entity;

import com.teamsix.firstteamproject.travelplan.dto.travelplan.TravelBasketDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TRAVEL_BASKET")
public class TravelBasket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * optional= false 는 TravelPlan은 nullable이 false이기 때문에 내부조인을 사용하게함
     */
    @OneToOne(mappedBy = "travelBasket", fetch = FetchType.LAZY, optional = false)
    private TravelPlan travelPlan;

    @OneToMany(mappedBy = "travelBasket", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketItem> basketItems;

    public void makingDependency(TravelPlan travelPlan){
        this.travelPlan = travelPlan;
        for(BasketItem item : basketItems){
            item.setTravelBasket(this);
        }
    }

    public static TravelBasketDTO toDto(TravelBasket travelBasket){
        return TravelBasketDTO.builder()
                .id(travelBasket.getId())
                .basketItems(travelBasket.getBasketItems().stream()
                        .map(BasketItem::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public void addBasketItem(BasketItem basketItem) {
        basketItems.add(basketItem);
        basketItem.setTravelBasket(this);
    }

    public void removeBasketItem(BasketItem basketItem) {
        basketItems.remove(basketItem);
        basketItem.setTravelBasket(null);
    }



}
