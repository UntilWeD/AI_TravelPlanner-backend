package com.teamsix.firstteamproject.travelplan.controller;

import com.teamsix.firstteamproject.global.dto.ResultDTO;
import com.teamsix.firstteamproject.global.util.ApiUtils;
import com.teamsix.firstteamproject.travelplan.dto.travelplan.TravelPlanDTO;
import com.teamsix.firstteamproject.travelplan.entity.TravelPlan;
import com.teamsix.firstteamproject.travelplan.service.TravelPlanService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "여행계획조회", description = "유저가 만든 여행계획의 리스트를 반환한다.")
    @PostMapping("/travel-plan")
    public ResultDTO<TravelPlan> saveTravelPlan(
            @PathVariable Long userId, @RequestBody TravelPlanDTO travelPlan){
        return ApiUtils.ok(travelPlanService.saveTravelPlan(userId, travelPlan));
    }



}
