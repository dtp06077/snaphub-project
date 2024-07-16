package project.server.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.server.dto.AuthenticationRequest;
import project.server.prop.JwtProp;
import project.server.security.constants.JwtConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private JwtProp jwtProp;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        log.info("username : " + username);
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
                        .signWith(Keys.hmacShaKeyFor(signingKey), Jwts.SIG.HS512 )  // 시그니처 사용할 비밀키, 알고리즘 설정
                        .header()                                                   // 헤더 설정
                            .add("type", JwtConstants.TOKEN_TYPE)           // type : JWT
                        .and()
                        .expiration(new Date( System.currentTimeMillis() + 1000*60*60*24*5 )) // 토큰 만료 시간 설정 (5일)
                        .claim("uid", username)                                  //PAYLOAD - uid : user   (사용자 아이디)
                        .claim("role", roles)                                     //PAYLOAD - rol : [ROLE_USER, ROLE_ADMIN] (권한 정보)
                        .compact();                                                 //최종적으로 토큰 생성

        log.info("jwt : " +jwt);

        return new ResponseEntity<String>(jwt, HttpStatus.OK);
    }

    //토큰 해석
    @GetMapping("/user/info")
    public ResponseEntity<?> userInfo(@RequestHeader(name="Authorization") String header) {

        log.info("===== header ======");
        log.info("Authorization : " + header);

        // Authorization : Bearer ${jwt}
        String jwt = header.replace(JwtConstants.TOKEN_PREFIX, "");

        byte[] signingKey = jwtProp.getSecret().getBytes();

        Jws<Claims> parsedToken = Jwts.parser()
                                    .verifyWith(Keys.hmacShaKeyFor(signingKey))
                                    .build()
                                    .parseSignedClaims(jwt);

        log.info("parsedToken : "+ parsedToken);

        //uid = user
        String username = parsedToken.getPayload().get("uid").toString();
        log.info("username : " + username);

        Claims claims = parsedToken.getPayload();
        Object roles = claims.get("role");
        log.info("roles : " + roles);

        return new ResponseEntity<String>(parsedToken.toString(), HttpStatus.OK);
    }

}
