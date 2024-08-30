package com.teamsix.firstteamproject.user.controller;

import com.teamsix.firstteamproject.global.dto.ResultDTO;
import com.teamsix.firstteamproject.global.util.ApiUtils;
import com.teamsix.firstteamproject.global.util.SecurityUtil;
import com.teamsix.firstteamproject.user.dto.UserLoginDTO;
import com.teamsix.firstteamproject.user.dto.UserRegistryDTO;
import com.teamsix.firstteamproject.user.dto.UserDTO;
import com.teamsix.firstteamproject.user.dto.UserUpdateDTO;
import com.teamsix.firstteamproject.user.entity.JwtToken;
import com.teamsix.firstteamproject.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
@Tag(name = "User", description = "User API")
public class UserController{

    private final UserService userService;


    @Operation(summary = "로그인 후 테스트", description = "회원 가입 후 spring security를 거쳐 유저정보를 확인하는 API")
    @GetMapping("/test")
    public String test(){
        return SecurityUtil.getCurrentUsername();
    }


    @Operation(summary = "회원가입", description = "회원가입 API")
    @PostMapping("/register")
    public ResultDTO<UserRegistryDTO> register(@RequestBody UserRegistryDTO userRegistryDTO) {
        return ApiUtils.ok(userService.register(userRegistryDTO));
    }

    @Operation(summary = "로그인", description = "유저가 가입된 정보로 로그인하며 jwt토큰을 반환한다.")
    @PostMapping("/signIn")
    public ResultDTO<JwtToken> signIn(@RequestBody UserLoginDTO userLoginDTO) {
        JwtToken jwtToken = userService.signIn(userLoginDTO);
        return ApiUtils.ok(jwtToken);
    }

    @Operation(summary = "유저 관리 홈", description = "유저가 자신의 개인정보를 수정하거나 여행플랜들을 확인한다.")
    @GetMapping("/{userId}")
    public ResultDTO<UserDTO> userHome(@PathVariable Long userId){
        return ApiUtils.ok(userService.findUserById(userId));
    }

    @Operation(summary = "유저 수정", description = "유저 개인의 비밀번호나 이름을 수정한다.")
    @PostMapping("/{userId}")
    public ResultDTO<UserDTO> updateUser(
            @PathVariable Long userId,
            @RequestBody UserUpdateDTO userUpdateDTO
            ){
        return ApiUtils.ok(userService.updateUser(userId, userUpdateDTO));
    }

    @Operation(summary = "유저 삭제", description = "해당 유저의 관련된 모든 정보를 db에서 삭제한다.")
    @DeleteMapping("/{userId}")
    public ResultDTO<Long> deleteUser(
            @PathVariable Long userId
    ){
        return ApiUtils.ok(userService.deleteUser(userId));
    }
}
