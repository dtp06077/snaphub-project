package project.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.server.dto.request.user.UpdateNameRequestDto;
import project.server.dto.request.user.UpdateProfileImageRequestDto;
import project.server.dto.response.user.GetUserInfoResponseDto;
import project.server.dto.response.user.GetUserResponseDto;
import project.server.dto.response.user.UpdateNameResponseDto;
import project.server.dto.response.user.UpdateProfileImageResponseDto;
import project.server.security.domain.CustomUser;
import project.server.service.UserService;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원 정보 조회
     */
    @Secured("ROLE_USER") //USER 권한 설정
    @GetMapping("/info")
    public ResponseEntity<? super GetUserInfoResponseDto> getUserInfo(
            @AuthenticationPrincipal CustomUser customUser) {
        return userService.getUserInfo(customUser);
    }

    /**
     * 특정 회원 정보 조회
     */
    @GetMapping("{id}")
    public ResponseEntity<? super GetUserResponseDto> getUser(
            @PathVariable("id") int id
    ) {
        return userService.getUser(id);
    }

    /**
     * 닉네임 수정
     */
    @Secured("ROLE_USER")
    @PatchMapping("/name")
    public ResponseEntity<? super UpdateNameResponseDto> updateName(
            @RequestBody @Valid UpdateNameRequestDto request,
            @AuthenticationPrincipal CustomUser customUser
    ) {
        return userService.updateName(request, customUser);
    }

    /**
     * 프로필 이미지 수정
     */
    @Secured("ROLE_USER")
    @PatchMapping("/profile-image")
    public ResponseEntity<? super UpdateProfileImageResponseDto> updateProfileImage(
            @RequestBody @Valid UpdateProfileImageRequestDto request,
            @AuthenticationPrincipal CustomUser customUser
    ) {
        return userService.updateProfileImage(request, customUser);
    }
}
