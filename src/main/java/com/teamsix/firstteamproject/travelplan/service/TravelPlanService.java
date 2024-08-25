package com.teamsix.firstteamproject.travelplan.service;

import com.teamsix.firstteamproject.travelplan.dto.travelplan.BasketItemDTO;
import com.teamsix.firstteamproject.travelplan.dto.travelplan.TravelPlanDTO;
import com.teamsix.firstteamproject.travelplan.entity.TravelPlan;
import com.teamsix.firstteamproject.travelplan.repository.TravelPlanRepository;
import com.teamsix.firstteamproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TravelPlanService {

    private final AwsS3Service awsS3Service;
    private final TravelPlanRepository travelPlanRepository;
    private final UserRepository userRepository;

    public TravelPlanService(AwsS3Service awsS3Service, TravelPlanRepository travelPlanRepository,
                             UserRepository userRepository) {
        this.awsS3Service = awsS3Service;
        this.travelPlanRepository = travelPlanRepository;
        this.userRepository = userRepository;
    }

    /**
     * ======  Travel-Plan 저장 단계 ======
     * 1. 변환작업 후 AwsS3Serice에서 이미지리스트를 저장 한 후 이미지 URL 반환
     * 2. 반환된 이미지 URL을 basketItem의 ImageUrl속성에 넣음
     * 3. imageUrl을 포함한 travePlanDTO의 .toentity Method를 실행한 다음 엔티티 반환
     * 4. 반환된 엔티티를 TravelPlanRepository에 저장
     * 5. 기존의 엔티티객체를 그대로 반환 (후에 변경)
     * @param
     * @return
     */
    public TravelPlanDTO saveTravelPlan(List<MultipartFile> images,Long userId, TravelPlanDTO travelPlanDTO){
        // 후에 리팩토링 필수 적용해보기
        List<BasketItemDTO> basketItems = travelPlanDTO.getTravelBasket().getBasketItems();

        List<String> imageUrls = awsS3Service.uploadImageList(images, userId);
        travelPlanDTO.getTravelBasket().mappingImageNameAndUrl(imageUrls);

        TravelPlan travelPlan = travelPlanDTO.toEntity(travelPlanDTO, userRepository.findUserById(userId));
        return TravelPlan.toDto(travelPlanRepository.save(travelPlan));
    }

}
