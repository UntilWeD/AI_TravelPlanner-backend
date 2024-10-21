package com.teamsix.firstteamproject.travelplan.dto.travelplan;



import com.teamsix.firstteamproject.travelplan.entity.BasketItem;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketItemDTO {
    private Long id;
    private String title;
    private String address;
    private int rating;
    private String imageUrl;
    private String lat;
    private String lng;


    // 연관관계 설정 되있지 않음.
    public BasketItem toEntity(){
        return BasketItem.builder()
                .id(this.id)
                .title(this.title)
                .address(this.address)
                .rating(this.rating)
                .imageUrl(this.imageUrl)
                .lat(this.lat)
                .lng(this.lng)
                .build();
    }
}
