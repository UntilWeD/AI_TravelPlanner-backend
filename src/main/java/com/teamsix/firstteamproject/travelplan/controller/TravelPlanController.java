package com.teamsix.firstteamproject.travelplan.controller;

import com.teamsix.firstteamproject.global.dto.ResultDTO;
import com.teamsix.firstteamproject.global.util.ApiUtils;
import com.teamsix.firstteamproject.travelplan.dto.travelplan.TravelPlanDTO;
import com.teamsix.firstteamproject.travelplan.service.TravelPlanService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user/{userId}")
@Slf4j
public class TravelPlanController {

    private final TravelPlanService travelPlanService;

    public TravelPlanController(TravelPlanService travelPlanService) {
        this.travelPlanService = travelPlanService;
    }

    /**
     * TravelPlan 저장
     */
    @Operation(summary = "여행계획저장", description = "요청받은 여행계획을 저장한다.")
    @PostMapping("/travel-plan")
    public ResultDTO<TravelPlanDTO> saveTravelPlan(
            @RequestPart(value = "image", required = false) List<MultipartFile> images,
            @PathVariable Long userId,
            @RequestPart("travelPlan") TravelPlanDTO travelPlan){
        return ApiUtils.ok(travelPlanService.saveTravelPlan(images ,userId, travelPlan));
    }

    /**
     * TravelPlanDTO 조회
     * @param userId
     * @return
     */
    @Operation(summary = "여행계획조회", description = "유저가 만든 여행계획리스트를 조회하여 반환한다.")
    @GetMapping("/travel-plan")
    public ResultDTO<List<TravelPlanDTO>> getTravelPlan(@PathVariable Long userId){
        return ApiUtils.ok(travelPlanService.getTravelPlans(userId));
    }


    /**
     * TravelPlan 삭제(단일)
     * @param userId
     * @param travelPlanId
     * @return
     */
    @Operation(summary = "여행계획삭제", description = "TravelPlan을 삭제한다.")
    @DeleteMapping("/travel-plan")
    public ResultDTO<String> getTravelPlan(@PathVariable Long userId, @RequestParam Long travelPlanId){
        travelPlanService.deleteTravelPlan(travelPlanId);
        return ApiUtils.ok("TravelPlan이 정상적으로 삭제되었습니다.");
    }

    /**
     * TravelPlan 변경
     * @param images
     * @param userId
     * @param travelPlanId
     * @param travelPlan
     * @return
     */
    @Operation(summary = "여행계획수정", description = "TravelPlan을 수정한다.")
    @PostMapping("/travel-plan/{travelPlanId}")
    public ResultDTO<TravelPlanDTO> saveTravelPlan(
            @RequestPart(value = "image", required = false) List<MultipartFile> images,
            @PathVariable Long userId,
            @PathVariable Long travelPlanId,
            @RequestPart("travelPlan") TravelPlanDTO travelPlan){
        return ApiUtils.ok(travelPlanService.updateTravelPlan(images, travelPlan));
    }




}
