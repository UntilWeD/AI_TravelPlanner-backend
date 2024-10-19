package com.teamsix.firstteamproject.user.controller;

import com.amazonaws.Request;
import com.teamsix.firstteamproject.global.dto.ResultDTO;
import com.teamsix.firstteamproject.global.util.ApiUtils;
import com.teamsix.firstteamproject.user.dto.UserDTO;
import com.teamsix.firstteamproject.user.dto.UserUpdateDTO;
import com.teamsix.firstteamproject.user.entity.JwtToken;
import com.teamsix.firstteamproject.user.entity.Role;
import com.teamsix.firstteamproject.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
@Tag(name = "User", description = "User API")
public class UserController{

    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원가입 API")
    @PostMapping("/register")
    public ResultDTO<UserDTO> register(@RequestBody UserDTO dto) {
        return ApiUtils.ok(userService.register(dto));
    }

    @Operation(summary = "로그인", description = "유저가 가입된 정보로 로그인하며 jwt토큰을 반환한다.")
    @PostMapping("/signIn")
    public ResultDTO<JwtToken> signIn(@RequestBody UserDTO dto) {
        return ApiUtils.ok(userService.signIn(dto));
    }

    //    @PostAuthorize("returnObject.getData().getEmail() == authentication.principal.username or hasRole('ROLE_ADMIN')")
    @Operation(summary = "유저 관리 홈", description = "유저가 자신의 개인정보를 수정하거나 여행플랜들을 확인한다.")
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @userSecurityExpression.isOwner(#userId)")
    public ResultDTO<UserDTO> userHome(@PathVariable Long userId, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        log.info("authentication : {}", user.getUsername());
        log.info("username : {}", userService.findUserDTOById(userId).getEmail());
        return ApiUtils.ok(userService.findUserDTOById(userId));
    }

    @Operation(summary = "유저 수정", description = "유저 개인의 비밀번호나 이름을 수정한다.")
    @PostMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @userSecurityExpression.isOwner(#userId)")
    public ResultDTO<UserDTO> updateUser(
            @PathVariable Long userId,
            @RequestBody UserUpdateDTO dto
            ){
        return ApiUtils.ok(userService.updateUser(userId, dto));
    }

    @Operation(summary = "유저 삭제", description = "해당 유저의 관련된 모든 정보를 db에서 삭제한다.")
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @userSecurityExpression.isOwner(#userId)")
    public ResultDTO<Long> deleteUser(
            @PathVariable Long userId
    ){
        return ApiUtils.ok(userService.deleteUser(userId));
    }

    @Operation(summary = "어드민 계정 생성", description = "어드민 계정을 생성한다.")
    @PostMapping("/admin")
    public ResultDTO<UserDTO> createAdmin(@RequestBody UserDTO dto, @RequestParam String adminKey){
        return ApiUtils.ok(userService.createAdmin(dto, adminKey));
    }


}
