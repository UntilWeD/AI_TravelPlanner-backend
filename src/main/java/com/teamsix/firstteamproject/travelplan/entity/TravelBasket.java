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

    @OneToOne(mappedBy = "travelBasket")
    private TravelPlan travelPlan;

    @OneToMany(mappedBy = "travelBasket")
    private List<BasketItem> basketItems;

}
