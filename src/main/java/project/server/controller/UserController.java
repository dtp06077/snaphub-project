package project.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.server.domain.User;
import project.server.dto.request.UserInfoRequest;
import project.server.dto.request.UserJoinRequest;
import project.server.dto.request.UserUpdateRequest;
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
        if (user != null) {
            UserInfoRequest userInfo = userService.select(user);
            return new ResponseEntity<>(userInfo, HttpStatus.OK);
        }

        //인증 되지 않음
        return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
    }
    /**
     * 중복 아이디 조회
     */
    @GetMapping("/check-loginId")
    public ResponseEntity<?> checkLoginId(@RequestParam String loginId) {

        if(loginId=="") {
            log.info("아이디를 입력하세요");
           return new ResponseEntity<>("아이디를 입력하세요.", HttpStatus.BAD_REQUEST);
        }

        User user = userService.selectByLoginId(loginId);

        if (user == null) {
            log.info(loginId + " 는 존재하지 않는 아이디입니다.");
            return new ResponseEntity<>("사용 가능한 아이디입니다.", HttpStatus.OK);
        }
        log.info(loginId + " 는 존재하는 아이디입니다.");
        return new ResponseEntity<>("이미 사용중인 아이디입니다.", HttpStatus.BAD_REQUEST);
    }

    /**
     * 중복 닉네임 조회
     */
    @GetMapping("/check-name")
    public ResponseEntity<?> checkName(@RequestParam String name) {

        if(name=="") {
            log.info("닉네임을 입력하세요");
            return new ResponseEntity<>("닉네임을 입력하세요.", HttpStatus.BAD_REQUEST);
        }

        User user = userService.selectByName(name);

        if (user == null) {
            log.info(name + " 는 존재하지 않는 닉네임입니다.");
            return new ResponseEntity<>("사용 가능한 닉네임입니다.", HttpStatus.OK);
        }
        log.info(name + " 는 존재하는 닉네임입니다.");
        return new ResponseEntity<>("이미 사용중인 닉네임입니다.", HttpStatus.BAD_REQUEST);
    }

    /**
     *  회원 가입
     */
    @PostMapping("")
    public ResponseEntity<?> join(@RequestBody UserJoinRequest request) throws Exception {
        log.info("[POST] - /users");
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("비밀번호가 비어있습니다."); // 예외 처리
        }

        try {
            int result = userService.insert(request);

            if( result >= 1) {
                log.info("회원가입 성공! - SUCCESS");
                return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
            }
            else {
                log.info("회원가입 실패! - FAIL");
                return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e) {
            return new ResponseEntity<>("중복 확인을 해주시길 바랍니다.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 회원 정보 수정
     */
    @Secured("ROLE_USER") //USER 권한 설정
    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody UserUpdateRequest request) throws Exception {
        log.info("[PUT] - /users");
        int result = userService.update(request);

        if( result >= 0 ) {
            log.info("회원수정 성공! - SUCCESS");
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else {
            log.info("회원수정 실패! - FAIL");
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 회원 삭제
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{loginId}")
    public ResponseEntity<?> destroy(@PathVariable("loginId") String loginId) throws Exception {
        log.info("[DELETE] - /users/{userId}");

        int result = userService.delete(loginId);

        if( result >= 0 ) {
            log.info("회원삭제 성공! - SUCCESS");
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else {
            log.info("회원삭제 실패! - FAIL");
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }

    }
}
