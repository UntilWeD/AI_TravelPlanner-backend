package com.teamsix.firstteamproject.travelplan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasketItem {

    @GeneratedValue @Id
    private Long id;

    @Column(name = "basket_id")
    private Long basketId;

    @Column(name = "category")
    private String category;

    @Column(name = "content")
    private String content;
}
