package com.teamsix.firstteamproject.travelplan.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
            cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BasketItem> basketItems;

}
