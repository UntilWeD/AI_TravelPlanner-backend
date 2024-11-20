package com.teamsix.firstteamproject.travelplan.controller;

import com.teamsix.firstteamproject.global.dto.ResultDTO;
import com.teamsix.firstteamproject.global.util.ApiUtils;
import com.teamsix.firstteamproject.travelplan.dto.travelplan.SimpleTravelPlanDTO;
import com.teamsix.firstteamproject.travelplan.dto.travelplan.TravelPlanDTO;
import com.teamsix.firstteamproject.travelplan.service.TravelPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user/{userId}")
@Slf4j
@PreAuthorize("hasRole('ADMIN') or @userSecurityExpression.isOwner(#userId)")
@Tag(name = "TravelPlan", description = "Travel Plan API")
public class TravelPlanController {

    private final TravelPlanService travelPlanService;

    public TravelPlanController(TravelPlanService travelPlanService) {
        this.travelPlanService = travelPlanService;
    }

    /**
     * TravelPlan 저장
     */
    @Operation(summary = "여행계획저장", description = "요청받은 여행계획을 저장한다.")
    @PostMapping("/travel-plans")
    public ResultDTO<TravelPlanDTO> saveTravelPlan(
            @PathVariable Long userId,
            @RequestBody TravelPlanDTO travelPlan){
        return ApiUtils.ok(travelPlanService.saveTravelPlan(userId, travelPlan));
    }

    /**
     * SimpleTravelPlanDTO 조회
     * @param userId
     * @return
     */
    @Operation(summary = "간략한여행계획조회", description = "유저가 만든 여행계획리스트를 조회하여 간략한 정보로 반환한다.")
    @GetMapping("/travel-plans")
    public ResultDTO<List<SimpleTravelPlanDTO>> getTravelPlan(@PathVariable Long userId){
        return ApiUtils.ok(travelPlanService.getSimpleTravelPlans(userId));
    }

    /**
     * 특정 id의 TravelPlan 반환
     * @param userId
     * @param travelPlanId
     * @return
     */
    @Operation(summary = "여행계획조회", description = "유저가 여행계획리스트에서 하나의 TravelPlan을 조회시 반환한다.")
    @GetMapping("/travel-plans/{travelPlanId}")
    public ResultDTO<TravelPlanDTO> getTravelPlan(
            @PathVariable Long userId,
            @PathVariable Long travelPlanId){
        return ApiUtils.ok(travelPlanService.getTravelPlan(travelPlanId));
    }



    /**
     * TravelPlan 삭제(단일)
     * @param userId
     * @param travelPlanId
     * @return
     */
    @Operation(summary = "여행계획삭제", description = "TravelPlan을 삭제한다.")
    @DeleteMapping("/travel-plans/{travelPlanId}")
    public ResultDTO<String> deleteTravelPlan(
            @PathVariable Long userId,
            @PathVariable Long travelPlanId){
        travelPlanService.deleteTravelPlan(travelPlanId);
        return ApiUtils.ok("TravelPlan이 정상적으로 삭제되었습니다.");
    }

    /**
     * TravelPlan 변경
     * @param userId
     * @param travelPlanId
     * @param travelPlan
     * @return
     */
    @Operation(summary = "여행계획수정", description = "TravelPlan을 수정한다.")
    @PostMapping("/travel-plans/{travelPlanId}")
    public ResultDTO<TravelPlanDTO> updateTravelPlan(
            @PathVariable Long userId,
            @PathVariable Long travelPlanId,
            @RequestBody TravelPlanDTO travelPlan){

        return ApiUtils.ok(travelPlanService.updateTravelPlan(travelPlan, userId));
    }




}
