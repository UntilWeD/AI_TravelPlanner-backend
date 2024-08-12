package com.teamsix.firstteamproject.travelplan.controller;

import com.teamsix.firstteamproject.global.dto.ResultDTO;
import com.teamsix.firstteamproject.global.util.ApiUtils;
import com.teamsix.firstteamproject.travelplan.dto.travelplan.TravelPlanDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/{userId}/")
public class TravelPlanController {

    /**
     * 모든 TravelPlan 조회
     */
    @Operation(summary = "로그인", description = "유저가 가입된 정보로 로그인하며 jwt토큰을 반환한다.")
    @GetMapping("/travel-plans")
    public ResultDTO<TravelPlanDTO> getAllTravelPlans(@PathVariable Long userId){
        //서비스 미구현 entity 객체 구조 필요
        return ApiUtils.ok(new TravelPlanDTO());
    }



}
