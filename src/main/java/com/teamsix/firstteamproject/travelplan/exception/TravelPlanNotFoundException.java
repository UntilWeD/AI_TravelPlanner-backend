package com.teamsix.firstteamproject.travelplan.exception;

public class TravelPlanNotFoundException extends RuntimeException{
    public TravelPlanNotFoundException(Long travelPlanId) {
        super("해당 " +travelPlanId + "의 TravelPlan은 존재하지 않습니다.");
    }
}
