package com.teamsix.firstteamproject.travelplan.entity;

import com.teamsix.firstteamproject.travelplan.dto.travelplan.TravelBasketDTO;
import com.teamsix.firstteamproject.travelplan.dto.travelplan.TravelPlanDTO;
import com.teamsix.firstteamproject.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TRAVEL_PLAN")
public class TravelPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "TRAVEL_BASKET_ID")
    private TravelBasket travelBasket;

    @Setter
    @Column(name = "title")
    private String title;


    /**
     * @Lob으로 String 속성을 CLOB으로 매핑해준다.
     */
    @Setter
    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;


    public static TravelPlanDTO toDto(TravelPlan travelPlan){
        return TravelPlanDTO.builder()
                .id(travelPlan.getId())
                .userId(travelPlan.getUser().getId())
                .title(travelPlan.getTitle())
                .content(travelPlan.getContent())
                .createdAt(travelPlan.getCreatedAt())
                .travelBasket(TravelBasket.toDto(travelPlan.getTravelBasket()))
                .build();
    }



    // 여행플랜([id], userid) -> 여행 basket(plan_id) -> basket item(s)
    // 사용자가 -> 여행플랜이동
}
