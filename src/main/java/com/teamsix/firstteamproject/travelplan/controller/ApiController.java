package com.teamsix.firstteamproject.travelplan.controller;


import com.teamsix.firstteamproject.global.dto.ResultDTO;
import com.teamsix.firstteamproject.global.util.ApiUtils;
import com.teamsix.firstteamproject.travelplan.dto.AreaCodeResponse;
import com.teamsix.firstteamproject.travelplan.dto.TourEventRequest;
import com.teamsix.firstteamproject.travelplan.dto.TourEventResponse;
import com.teamsix.firstteamproject.travelplan.dto.amadeus.AmadeusCond;
import com.teamsix.firstteamproject.travelplan.dto.amadeus.FlightResponse;
import com.teamsix.firstteamproject.travelplan.dto.gpt.DomesticTravelRequest;
import com.teamsix.firstteamproject.travelplan.dto.gpt.InternationalTravelRequest;
import com.teamsix.firstteamproject.travelplan.dto.restaurant.RestaurantCond;
import com.teamsix.firstteamproject.travelplan.dto.restaurant.RestaurantResponse;
import com.teamsix.firstteamproject.travelplan.service.AmadeusApiService;
import com.teamsix.firstteamproject.travelplan.service.GPTService;
import com.teamsix.firstteamproject.travelplan.service.KoreaTourService;
import com.teamsix.firstteamproject.travelplan.service.RestaurantApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/travelplan")
@Tag(name = "외부 API", description = "외부 api 요청")
@Slf4j
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
public class ApiController {

    private final RestaurantApiService restaurantApiService;
    private final AmadeusApiService amadeusApiService;
    private final GPTService gptService;
    private final KoreaTourService koreaTourService;

    public ApiController(RestaurantApiService restaurantApiService, AmadeusApiService amadeusApiService, GPTService gptService, KoreaTourService koreaTourService) {
        this.restaurantApiService = restaurantApiService;
        this.amadeusApiService = amadeusApiService;
        this.gptService = gptService;
        this.koreaTourService = koreaTourService;
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

    @Operation(summary = "국내 여행 추천 GPT 생성", description = "여행지 추천에 대한 내용 답변")
    @PostMapping("/gpt/domestic")
    public ResultDTO<?> gptDomesticTravel(@RequestBody DomesticTravelRequest requestDTO){
        return ApiUtils.ok(gptService.getDomesticTravel(requestDTO));
    }

    @Operation(summary = "국외 여행 추천 GPT 생성", description = "여행지 추천에 대한 내용 답변")
    @PostMapping("/gpt/international")
    public ResultDTO<?> gptInternationalTravel(@RequestBody InternationalTravelRequest requestDTO){
        return ApiUtils.ok(gptService.getInternationalTravel(requestDTO));
    }

    @Operation(summary = "한국관광데이터 지역 코드 조회", description = "한국관광데이터 지역 코드 조회를 한다.")
    @GetMapping("/areaCode")
    public ResultDTO<AreaCodeResponse> getAreaCode() {
        return ApiUtils.ok(koreaTourService.getAreaCode());
    }

    @Operation(summary = "한국관광데이터 이벤트 조회", description = "한국관광데이터 이벤트 조회를 한다.")
    @GetMapping("/event")
    public ResultDTO<TourEventResponse> getEvent(@RequestBody TourEventRequest requestDTO) {
        return ApiUtils.ok(koreaTourService.getEvent(requestDTO));
    }


}
