package project.server.security.jwt.provider;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.server.prop.JwtProps;
import project.server.security.constants.JwtConstants;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

/**
 * JWT 토큰 관련 기능을 제공해주는 클래스
 * 1. 토큰 생성
 * 2. 토큰 해석
 * 3. 토큰 유효성 검사
 */

@Slf4j
@Component
public class JwtTokenProvider {

    //시크릿키 정보를 담는 객체 의존 주입
    @Autowired
    private JwtProps jwtProps;

    /**
     * 토큰 생성
     */
    public String createToken(Long userId, String loginId, List<String> roles) {

        //토큰 생성
        String jwt = Jwts.builder()
                // .signWith( 시크릿키 , 알고리즘 )
                .signWith(getShaKey(), Jwts.SIG.HS512)                      // 서명에 사용할 키와 알고리즘 설정
                .header()                                                   // 헤더 설정
                    .add("type", JwtConstants.TOKEN_TYPE)                // type : JWT
                .and()
                .expiration(new Date( System.currentTimeMillis() + 1000*60*60*24*5 )) // 토큰 만료 시간 설정 (5일)
                .claim("uid", "" + userId)                 //클레임 설정 : 회원 PK
                .claim("lid", loginId)                      //클레임 설정 : 회원 로그인 아이디
                .claim("role", roles)                       //클레임 설정 : 회원 권한
                .compact();                                    //최종적으로 토큰 생성

        log.info("jwt : " +jwt);

        return jwt;
    }


    //secretKey -> signingKey
    private byte[] getSigningKey() {
        return jwtProps.getSecretKey().getBytes();
    }

    //secretKey -> HMAC-SHA 알고리즘 -> signingKey
    //jwt 라이브러리에서 사용하는 형식으로 변경하는 메서드
    private SecretKey getShaKey() {
        return Keys.hmacShaKeyFor(getSigningKey());
    }

}
