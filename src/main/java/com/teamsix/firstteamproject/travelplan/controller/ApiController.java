package com.teamsix.firstteamproject.travelplan.controller;

import com.teamsix.firstteamproject.travelplan.dto.Restaurant.RestaurantCond;
import com.teamsix.firstteamproject.travelplan.service.RestaurantApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/travelplan")
public class ApiController {

    @Autowired
    private RestaurantApiService restaurantApiService;

    @PostMapping("/restaurant")
    public ResponseEntity<String> restaurantRequest(@RequestBody RestaurantCond restaurantCond){

        String result = restaurantApiService.getRestaurantDetails(restaurantCond);


        return ResponseEntity.ok(result);
    }


}
