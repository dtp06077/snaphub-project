package project.backend.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.backend.constants.SecurityConstants;
import project.backend.domain.User;
import project.backend.prop.JwtProp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private JwtProp jwtProp;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user) {
        String loginId = user.getLoginId();
        String password = user.getPassword();

        log.info("loginId : " + loginId);
        log.info("password : " + password);

        //사용자 권한
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");

        //시크릿키 -> 바이트
        byte[] signingKey = jwtProp.getSecret().getBytes();

        //토큰 생성
        String jwt = Jwts.builder()
                        // .signWith( 시크릿키 , 알고리즘 )
                        .signWith(Keys.hmacShaKeyFor(signingKey), Jwts.SIG.HS512 )
                        .header()
                            .add("type", SecurityConstants.TOKEN_TYPE)
                        .and()
                        .expiration(new Date( System.currentTimeMillis() + 1000*60*60*24*5 ))
                        .claim("id", loginId)
                        .claim("rol", roles)
                        .compact();

        log.info("jwt : " +jwt);

        return new ResponseEntity<String>(jwt, HttpStatus.OK);
    }

}
