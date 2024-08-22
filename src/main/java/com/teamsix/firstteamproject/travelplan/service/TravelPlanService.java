package com.teamsix.firstteamproject.travelplan.service;

import com.teamsix.firstteamproject.travelplan.dto.travelplan.BasketItemDTO;
import com.teamsix.firstteamproject.travelplan.dto.travelplan.TravelPlanDTO;
import com.teamsix.firstteamproject.travelplan.repository.TravelPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TravelPlanService {

    private AwsS3Service awsS3Service;
    private TravelPlanRepository travelPlanRepository;


    /**
     * ======  Travel-Plan 저장 단계 ======
     * 1. 변환작업 후 AwsS3Serice에서 이미지리스트를 저장 한 후 이미지 URL 반환
     * 2. 반환된 이미지 URL을 basketItem의 ImageUrl속성에 넣음
     * 3. imageUrl을 포함한 travePlanDTO의 .toentity Method를 실행한 다음 엔티티 반환
     * 4. 반환된 엔티티를 TravelPlanRepository에 저장
     * 5. 기존의 엔티티객체를 그대로 반환 (후에 변경)
     * @param travelPlan
     * @return
     */
    public TravelPlanDTO saveTravelPlan(Long userId, TravelPlanDTO travelPlan){
        // 후에 리팩토링 필수 적용해보기
        List<MultipartFile> multipartFiles = new ArrayList<>();
        List<BasketItemDTO> basketItems = travelPlan.getTravelBasket().getBasketItems();
        for(BasketItemDTO item : basketItems ){
            MultipartFile image = item.getImage();
            if(!image.isEmpty() && image != null){
                multipartFiles.add(image);
            }
        }

        List<String> imageUrls = awsS3Service.uploadImageList(multipartFiles, userId);
        travelPlan.getTravelBasket().mappingImageUrl(imageUrls);


        return new TravelPlanDTO();
    }

}
