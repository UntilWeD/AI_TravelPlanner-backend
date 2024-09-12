package com.teamsix.firstteamproject.travelplan.service;

import com.teamsix.firstteamproject.travelplan.dto.travelplan.BasketItemDTO;
import com.teamsix.firstteamproject.travelplan.dto.travelplan.SimpleTravelPlanDTO;
import com.teamsix.firstteamproject.travelplan.dto.travelplan.TravelPlanDTO;
import com.teamsix.firstteamproject.travelplan.entity.BasketItem;
import com.teamsix.firstteamproject.travelplan.entity.TravelPlan;
import com.teamsix.firstteamproject.travelplan.repository.TravelPlanRepository;
import com.teamsix.firstteamproject.user.repository.UserRepositoryJDBC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TravelPlanService {

    private final AwsS3Service awsS3Service;
    private final TravelPlanRepository travelPlanRepository;
    private final UserRepositoryJDBC userRepositoryJDBC;

    public TravelPlanService(AwsS3Service awsS3Service, TravelPlanRepository travelPlanRepository,
                             UserRepositoryJDBC userRepositoryJDBC) {
        this.awsS3Service = awsS3Service;
        this.travelPlanRepository = travelPlanRepository;
        this.userRepositoryJDBC = userRepositoryJDBC;
    }

    /**
     * ====== 저장 과정 ======
     * 1. 변환작업 후 AwsS3Serice에서 이미지리스트를 저장 한 후 이미지 URL 반환
     * 2. 반환된 이미지 URL을 basketItem의 ImageUrl속성에 넣음
     * 3. imageUrl을 포함한 travePlanDTO의 .toentity Method를 실행한 다음 엔티티 반환
     * 4. 반환된 엔티티를 TravelPlanRepository에 저장
     * 5. 기존의 엔티티객체를 그대로 반환 (후에 변경)
     * @param
     * @return
     */
    public TravelPlanDTO saveTravelPlan(Long userId, TravelPlanDTO dto){
        TravelPlan travelPlan = dto.toEntity(dto, userRepositoryJDBC.findUserById(userId));

        return TravelPlan.toDto(travelPlanRepository.save(travelPlan));
    }

    /**
     * 해당 유저의 Simple TravelPlan객체들을 updated_at속성에 따라
     * @param userId
     * @return
     */
    public List<SimpleTravelPlanDTO> getSimpleTravelPlans(Long userId) {
        return travelPlanRepository.findByUser_Id(userId)
            .stream().map(travelPlan -> SimpleTravelPlanDTO.toSimpleTravelPlanDTO(travelPlan))
            .collect(Collectors.toList());
    }

    /**
     * 해당 유저의 travelPlanList중 travelPlanId에 해당하는 TravelPlan객체를
     * DTO화 시켜 반환한다.
     * @param travelPlanId
     * @return
     */
    public TravelPlanDTO getTravelPlan(Long travelPlanId){
        return TravelPlan.toDto(travelPlanRepository.findById(travelPlanId).get());
    }



    public void deleteTravelPlan(Long travelPlanId) {
        //[리팩토링 필수] 나중에 다른 repository를 생성하던가 구조를 바꾸어 해결해야함
        TravelPlan travelPlan = travelPlanRepository.findById(travelPlanId).get();
        travelPlanRepository.delete(travelPlan);

        return;
    }


    @Transactional
    public TravelPlanDTO updateTravelPlan(TravelPlanDTO dto, Long userId) {
        // 기존 TravelPlan 영속성 컨텍스트에 넣고 기본 속성 수정하기
        TravelPlan travelPlan = travelPlanRepository.findById(dto.getId()).get();
        travelPlan.setTitle(dto.getTitle());
        travelPlan.setContent(dto.getContent());

        // BasketItem 기존에 있던 것과 비교하여 없는 것은 삭제하기
        List<BasketItem> basketItems = travelPlan.getTravelBasket().getBasketItems();
        List<Long> basketItemDTOS = dto.getTravelBasket().getBasketItems().stream()
                .map(BasketItemDTO::getId).collect(Collectors.toList());;

        for(int i = basketItems.size() - 1 ; i >= 0 ; i--){
            if(!basketItemDTOS.contains(basketItems.get(i).getId())){
                basketItems.remove(i);
            }
        }

        // BasketItem에 기존에 없던 것을 추가하기
        for(BasketItemDTO basketItem : dto.getTravelBasket().getBasketItems()){
            if(basketItem.getId() == null){
                travelPlan.getTravelBasket().addBasketItem(basketItem.toEntity());
            }
        }




        return TravelPlan.toDto(travelPlan);
    }

}

//
///**
// * === 수정 과정 ===
// * 이미지 처리
// * 1. basketItem의 이미지 이름들과 저장소 내에 이미지파일들을 비교
// * 2. 만약 basketItems 이미지 이름이 없을 경우 삭제리스트에 추가하여 해당 이미지들을 삭제
// * 3. 새로운 images 업로드 ( 이미지 원본파일이 오는 것은 새로 추가된 이미지 밖에 없기 때문)
// * 객체 처리
// * 1. findById로 TravelPlan객체 조회
// * 2. 가져온 dto와 객체와 데이터를 비교 후 수정
// * 3. 수정하면 영속성 컨텍스트에 있기때문에 자동으로 수정
// * @param images
// * @param dto
// * @return
// */
//    @Transactional
//    public TravelPlanDTO updateTravelPlan(List<MultipartFile> images, TravelPlanDTO dto, Long userId) {
//        // 기존 TravelPlan 영속성 컨텍스트에 넣고 기본 속성 수정하기
//        TravelPlan travelPlan = travelPlanRepository.findById(dto.getId()).get();
//        travelPlan.setTitle(dto.getTitle());
//        travelPlan.setContent(dto.getContent());
//
//        // 이전 이미지 이름 리스트
//        List<String> preImageNames = travelPlan.getTravelBasket().getBasketItems()
//                .stream().map(basketItem -> basketItem.getImageName()).collect(Collectors.toList());
//
//        // 현재 이미지 이름 리스트
//        List<String> imageNames = dto.getTravelBasket().getBasketItems()
//                .stream().map(basketItemDTO -> basketItemDTO.getImageName()).collect(Collectors.toList());
//
//
//        for(int i=preImageNames.size() - 1 ; i >= 0; i--){
//            if(imageNames.contains(preImageNames.get(i))){
//                preImageNames.remove(i);
//            }
//        }
//        log.info("삭제할 리스트 preImageNames : {}", preImageNames);
//
//
//        List<BasketItem> basketItems = travelPlan.getTravelBasket().getBasketItems();
//        // 현재 이미지 이름 리스트에 존재하지 않는 이미지 삭제
//        if(!preImageNames.isEmpty()){
//            awsS3Service.deleteImage(preImageNames, userId);
//
//            // preImageNames에 존재하는 이미지 이름을 이전 baskItems에서 삭제
//            basketItems.removeIf(basketItem -> preImageNames.contains(basketItem.getImageName()));
//        }
//
//        // 이미지가 존재한다면
//        if( images != null ){
//
//            // 새로 추가된 이미지 업로드
//            List<BasketItemDTO> basketItemDTOS = dto.getTravelBasket().getBasketItems();
//            List<String> imageUrls = awsS3Service.uploadTravelPlanImageList(images, userId);
//
//            List<String> addedImages = dto.getTravelBasket().mappingImageNameAndUrl(imageUrls);
//
//            log.info("addedImages : {} ", addedImages);
//            // BasketItemDTOS에 새로 추가된 basketItem 추가
//            for(BasketItemDTO basketItemDTO : basketItemDTOS){
//                if(addedImages.contains(basketItemDTO.getImageName())){
//                    log.info("method 실행");
//                    travelPlan.getTravelBasket().addBasketItem(basketItemDTO.toEntity());
//                }
//            }
//
//        }
//
//
//        return TravelPlan.toDto(travelPlan);
//    }