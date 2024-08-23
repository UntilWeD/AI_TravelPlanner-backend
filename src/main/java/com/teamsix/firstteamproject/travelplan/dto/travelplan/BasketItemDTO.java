package com.teamsix.firstteamproject.travelplan.dto.travelplan;



import com.teamsix.firstteamproject.travelplan.entity.BasketItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class BasketItemDTO {
    private String title;
    private String content;
    private String imageUrl;
    private MultipartFile image;

    public BasketItem toEntity(){
        return BasketItem.builder()
                .title(this.title)
                .content(this.content)
                .imageUrl(this.imageUrl)
                .build();
    }
}
