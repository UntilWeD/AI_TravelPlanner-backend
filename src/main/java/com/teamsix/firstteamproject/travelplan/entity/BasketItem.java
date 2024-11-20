package com.teamsix.firstteamproject.travelplan.entity;

import com.teamsix.firstteamproject.travelplan.converter.StringListConverter;
import com.teamsix.firstteamproject.travelplan.dto.travelplan.BasketItemDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * ---BasketItem---
 * Title
 * Content
 * Image
 */
@Entity
@Getter
@Setter
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

    @Column(name = "title")
    private String title;

    @Column(name = "address")
    private String address;

    @Column(name = "rating")
    private int rating;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lng")
    private String lng;

    @Column(name = "types")
    @Convert(converter = StringListConverter.class)
    private List<String> types;


    public static BasketItemDTO toDto(BasketItem basketItem){
        return BasketItemDTO.builder()
                .id(basketItem.getId())
                .title(basketItem.getTitle())
                .address(basketItem.getAddress())
                .rating(basketItem.getRating())
                .imageUrl(basketItem.getImageUrl())
                .lat(basketItem.getLat())
                .lng(basketItem.getLng())
                .types(basketItem.getTypes())
                .build();
    }

    public void setTravelBasket(TravelBasket travelBasket) {
        // 기존 연관관계 제거
        if (this.travelBasket != null) {
            this.travelBasket.getBasketItems().remove(this);
        }
        this.travelBasket = travelBasket;
        // 새로운 연관관계 설정
        if (travelBasket != null && !travelBasket.getBasketItems().contains(this)) {
            travelBasket.getBasketItems().add(this);
        }
    }

}

//    /**
//     * @Enumerated(EnumType.STRING) 애너테이션을 작성함으로써 DB에 문자로 쉽게 저장된다
//     */
//    @Enumerated(EnumType.STRING)
//    @Column(name = "category", nullable = false)
//    private Category category;
