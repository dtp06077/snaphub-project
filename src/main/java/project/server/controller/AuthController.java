package project.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.server.dto.request.auth.JoinRequestDto;
import project.server.dto.response.auth.JoinResponseDto;
import project.server.service.AuthService;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 회원 가입
     */
    @PostMapping("/join")
    public ResponseEntity<? super JoinResponseDto> join (
            @RequestBody @Valid JoinRequestDto request) {
        return authService.join(request);
    }
    /**
     * 중복 아이디 조회
     */
    @GetMapping("join/check-loginId")
    public ResponseEntity<? super JoinResponseDto> checkLoginId (
            @RequestParam String loginId) {
        return authService.checkDuplicateId(loginId);
    }
    /**
     * 중복 닉네임 조회
     */
    @GetMapping("join/check-name")
    public ResponseEntity<? super JoinResponseDto> checkName (
            @RequestParam String name) {
        return authService.checkDuplicateName(name);
    }
}
