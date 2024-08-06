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
public class TravelBasket {

    @GeneratedValue @Id
    private Long id;

    @Column(name = "travelplan_id")
    private Long travelPlanId;

    //JPA 책 참고 후 수정하기
    @OneToMany
    private List<BasketItem> basketItems;

}
