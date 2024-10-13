package project.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.server.dto.response.user.GetUserInfoResponseDto;
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
    public ResponseEntity<? super GetUserInfoResponseDto> userInfo(@AuthenticationPrincipal CustomUser customUser) {

        return userService.getUserInfo(customUser);
    }

    /**
     * 회원 정보 수정
     */
//    @Secured("ROLE_USER") //USER 권한 설정
//    @PutMapping("")
//    public ResponseEntity<?> update(@RequestBody UserUpdateRequest request) throws Exception {
//        log.info("[PUT] - /users");
//        int result = userService.update(request);
//
//        if( result >= 0 ) {
//            log.info("회원수정 성공! - SUCCESS");
//            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
//        }
//        else {
//            log.info("회원수정 실패! - FAIL");
//            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
//        }
//    }

    /**
     * 회원 삭제
     */
//    @Secured("ROLE_ADMIN")
//    @DeleteMapping("/{loginId}")
//    public ResponseEntity<?> destroy(@PathVariable("loginId") String loginId) throws Exception {
//        log.info("[DELETE] - /users/{userId}");
//
//        int result = userService.delete(loginId);
//
//        if( result >= 0 ) {
//            log.info("회원삭제 성공! - SUCCESS");
//            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
//        }
//        else {
//            log.info("회원삭제 실패! - FAIL");
//            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
//        }
//
//    }
}
