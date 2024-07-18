package project.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.server.domain.User;
import project.server.dto.UserJoinRequest;
import project.server.dto.UserUpdateRequest;
import project.server.security.domain.CustomUser;
import project.server.service.UserService;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 회원 정보 조회
     */
    @Secured("ROLE_USER") //USER 권한 설정
    @GetMapping("/info")
    public ResponseEntity<?> userInfo(@AuthenticationPrincipal CustomUser customUser) {

        log.info("===== customUser =====");
        log.info("customUser : " + customUser);

        User user = customUser.getUser();
        log.info("user : " + user.getName());

        //인증된 회원 정보
        if (user != null) return new ResponseEntity<>(user, HttpStatus.OK);

        //인증 되지 않음
        return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
    }

    /**
     *  회원 가입
     */
    @PostMapping("")
    public ResponseEntity<?> join(@RequestBody UserJoinRequest request) throws Exception {
        log.info("[POST] - /users");
        Long result = userService.insert(request);

        if( result >= 0) {
            log.info("회원가입 성공! - SUCCESS");
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else {
            log.info("회원가입 실패! - FAIL");
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 회원 정보 수정
     */
    @Secured("ROLE_USER") //USER 권한 설정
    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody UserUpdateRequest request) throws Exception {
        log.info("[PUT] - /users");
        Long result = userService.update(request);

        if( result >= 0 ) {
            log.info("회원수정 성공! - SUCCESS");
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else {
            log.info("회원수정 실패! - FAIL");
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }
    }
}
