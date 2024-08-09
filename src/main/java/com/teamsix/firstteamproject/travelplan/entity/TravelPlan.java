package com.teamsix.firstteamproject.travelplan.entity;

import com.teamsix.firstteamproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TRAVEL_PLAN")
public class TravelPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne
    @JoinColumn(name = "TRAVEL_BASKET_ID")
    private TravelBasket travelBasket;


    /**
     * @Lob으로 String 속성을 CLOB으로 매핑해준다.
     */
    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;


    @Builder
    public TravelPlan(String content) {
        this.content = content;
    }
}
