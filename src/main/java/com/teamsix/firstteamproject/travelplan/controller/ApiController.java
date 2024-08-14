package com.teamsix.firstteamproject.travelplan.controller;


import com.teamsix.firstteamproject.global.dto.ResultDTO;
import com.teamsix.firstteamproject.global.util.ApiUtils;
import com.teamsix.firstteamproject.travelplan.dto.amadeus.AmadeusCond;
import com.teamsix.firstteamproject.travelplan.dto.amadeus.FlightResponse;
import com.teamsix.firstteamproject.travelplan.dto.restaurant.RestaurantCond;
import com.teamsix.firstteamproject.travelplan.dto.restaurant.RestaurantResponse;
import com.teamsix.firstteamproject.travelplan.service.AmadeusApiService;
import com.teamsix.firstteamproject.travelplan.service.RestaurantApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/travelplan")
@Tag(name = "외부 API", description = "외부 api 요청")
@Slf4j
public class ApiController {

    private final RestaurantApiService restaurantApiService;
    private final AmadeusApiService amadeusApiService;


    public ApiController(RestaurantApiService restaurantApiService, AmadeusApiService amadeusApiService) {
        this.restaurantApiService = restaurantApiService;
        this.amadeusApiService = amadeusApiService;
    }


    @Operation(summary = "식당 정보(공공데이터)", description = "식당정보를 요청할 때 사용하는 API")
    @PostMapping("/restaurant")
    public ResultDTO<RestaurantResponse> restaurantRequest(@RequestBody RestaurantCond restaurantCond){
        return ApiUtils.ok(restaurantApiService.getRestaurantDetails(restaurantCond));
    }

    @Operation(summary = "비행기 정보(아마데우스)", description = "비행기 검색을 하는 것으로 아직 예약은 미구현")
    @PostMapping("flight-offers")
    public Mono<ResultDTO<FlightResponse>> flightRequest(@RequestBody AmadeusCond cond){
        log.info("[flightRequest] method Start!");
        return ApiUtils.MonoOk(amadeusApiService.getFlightOffers(cond));
    }


}
