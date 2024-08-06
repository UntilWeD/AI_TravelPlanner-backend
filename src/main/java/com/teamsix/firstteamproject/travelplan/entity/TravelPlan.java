package com.teamsix.firstteamproject.travelplan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TravelPlan {

    @GeneratedValue @Id
    Long id;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "basket_id")
    Long basketId;

    @Column(name = "content")
    String content;

    @Builder
    public TravelPlan(Long userId, Long basketId, String content) {
        this.userId = userId;
        this.basketId = basketId;
        this.content = content;
    }
}
