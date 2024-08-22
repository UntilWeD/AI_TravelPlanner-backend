package com.teamsix.firstteamproject.travelplan.controller;

import com.teamsix.firstteamproject.global.dto.ResultDTO;
import com.teamsix.firstteamproject.global.util.ApiUtils;
import com.teamsix.firstteamproject.travelplan.dto.travelplan.TravelPlanDTO;
import com.teamsix.firstteamproject.travelplan.service.TravelPlanService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/{userId}/")
public class TravelPlanController {

    private TravelPlanService travelPlanService;


    /**
     * TravelPlan 저장
     */
    @Operation(summary = "여행계획조회", description = "유저가 만든 여행계획의 리스트를 반환한다.")
    @PostMapping("/travel-plan")
    public ResultDTO<TravelPlanDTO> getAllTravelPlans(
            @PathVariable Long userId, @RequestBody TravelPlanDTO travelPlan){
        return ApiUtils.ok(travelPlanService.saveTravelPlan(userId, travelPlan));
    }



}
