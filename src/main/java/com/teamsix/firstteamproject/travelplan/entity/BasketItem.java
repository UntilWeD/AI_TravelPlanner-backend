package com.teamsix.firstteamproject.travelplan.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "basket_item")
public class BasketItem {

    @Id
    /**
     * GenerationType.IDENTITY는 호출시 자동으로 id값을 조회하여 값을 바로 채워놓는다
     * 그래서 쓰기 지연 기능을 지원하지 않는다.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id", nullable = false)
    private TravelBasket travelBasket;

    /**
     * @Enumerated(EnumType.STRING) 애너테이션을 작성함으로써 DB에 문자로 쉽게 저장된다
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
}
