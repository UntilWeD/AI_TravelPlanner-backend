package com.teamsix.firstteamproject.travelplan.dto.travelplan;



import com.teamsix.firstteamproject.travelplan.entity.BasketItem;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketItemDTO {
    private String title;
    private String content;
    private String imageUrl;
    private String imageName;

    public BasketItem toEntity(){
        return BasketItem.builder()
                .title(this.title)
                .content(this.content)
                .imageUrl(this.imageUrl)
                .imageName(this.imageName)
                .build();
    }
}
