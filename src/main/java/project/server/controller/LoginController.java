package project.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.server.dto.UserLoginRequest;

@Slf4j
@RestController
public class LoginController {

    /**
     * JWT 을 생성하는 Login 요청
     * (filter) [POST] - /login
     * body :
     {
     "loginId" : "test",
     "password" : "123456"
     }
     */
//    @PostMapping("login")
//    public void login(@RequestBody UserLoginRequest request) {
//        //사용자로부터 전달받은 인증 정보
//        String loginId = request.getLoginId();
//        String password = request.getPassword();
//    }

}
