package com.teamsix.firstteamproject.travelplan.dto.travelplan;



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
    private MultipartFile image;
    private String imageUrl;
}
