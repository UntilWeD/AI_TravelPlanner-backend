package com.teamsix.firstteamproject.travelplan.service;

import com.teamsix.firstteamproject.travelplan.dto.travelplan.TravelPlanDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TravelPlanService {


    private AwsS3Service awsS3Service;

    // 미구현
    public TravelPlanDTO saveTravelPlan(){
        return new TravelPlanDTO();
    }

}
