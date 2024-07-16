package com.teamsix.firstteamproject.travelplan.controller;


import com.teamsix.firstteamproject.travelplan.dto.amadeus.AmadeusAccessToken;
import com.teamsix.firstteamproject.travelplan.dto.amadeus.AmadeusCond;
import com.teamsix.firstteamproject.travelplan.dto.amadeus.FlightResponse;
import com.teamsix.firstteamproject.travelplan.dto.restaurant.RestaurantCond;
import com.teamsix.firstteamproject.travelplan.dto.restaurant.RestaurantResponse;
import com.teamsix.firstteamproject.travelplan.service.AmadeusApiService;
import com.teamsix.firstteamproject.travelplan.service.RestaurantApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/travelplan")
@Slf4j
public class ApiController {

    private final RestaurantApiService restaurantApiService;
    private final AmadeusApiService amadeusApiService;


    public ApiController(RestaurantApiService restaurantApiService, AmadeusApiService amadeusApiService) {
        this.restaurantApiService = restaurantApiService;
        this.amadeusApiService = amadeusApiService;
    }



    @PostMapping("/restaurant")
    public ResponseEntity<RestaurantResponse> restaurantRequest(@RequestBody RestaurantCond restaurantCond){
        return ResponseEntity.ok().body(restaurantApiService.getRestaurantDetails(restaurantCond));
    }

    @PostMapping("flight-offers")
    public Mono<FlightResponse> flightRequest(@RequestBody AmadeusCond cond){
        log.info("[flightRequest] method Start!");
        return amadeusApiService.getFlightOffers(cond);
    }


}
