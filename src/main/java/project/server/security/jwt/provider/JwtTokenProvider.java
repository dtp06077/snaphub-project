package project.server.security.jwt.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import project.server.domain.User;
import project.server.repository.UserRepository;
import project.server.security.domain.CustomUser;
import project.server.prop.JwtProps;
import project.server.security.constants.JwtConstants;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private UserRepository userRepository;

    /**
     * 토큰 생성
     */
    public String createToken(Long userId, String loginId, List<String> roles) {

        //jwt 토큰 생성
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

    /**
     * 토큰 해석
     * Authorization : Bearer + {jwt}  (authHeader)
     * -> jwt 추출
     * -> UsernamePasswordAuthenticationToken
     */
    public UsernamePasswordAuthenticationToken getAuthentication(String authHeader) {

        if(authHeader == null || authHeader.length() == 0 ) return null;

        try {

            //jwt 추출 (Bearer + {jwt}) -> {jwt}
            String jwt = authHeader.replace("Bearer ", "");

            //jwt 파싱
            Jws<Claims> parsedToken = Jwts.parser()
                                            .verifyWith(getShaKey())
                                            .build()
                                            .parseSignedClaims(jwt);

            log.info("parsedToken : " + parsedToken);

            //인증된 회원 PK
            String id = parsedToken.getPayload().get("uid").toString();
            Long userId = (id == null) ? 0 : Long.parseLong(id);
            log.info("userId : " + id);

            //인증된 회원 아이디
            String loginId = parsedToken.getPayload().get("lid").toString();
            log.info("loginId : " + loginId);

            //인증된 회원 권한
            Claims claims = parsedToken.getPayload();
            Object roles = claims.get("role");
            log.info("roles : " + roles);

            //토큰에 userId 있는지 확인
            if( loginId == null || loginId.length() ==0 ) return null;

            //TODO : User 객체에 PK와 아이디 담기
            User user = new User();
            user.setId(userId);
            user.setLoginId(loginId);

            //TODO : User 객체에 권한 담기
            List<SimpleGrantedAuthority> authorities = ((List<?>) roles )
                    .stream()
                    .map(auth -> new SimpleGrantedAuthority( (String) auth ))
                    .collect( Collectors.toList() );

            //토큰 유효하면
            //name ,email, profile 도 담아주기
            try {
                User userInfo = userRepository.findById(userId);
                user.setName(userInfo.getName());
                user.setEmail(userInfo.getEmail());
                user.setProfile(userInfo.getProfile());
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("토큰 유효 -> DB 추가 정보 조회시 에러 발생");
            }

            UserDetails userDetails = new CustomUser(user);

            // OK
            // new UsernamePasswordAuthenticationToken( 사용자정보객체, 비밀번호, 사용자의 권한목록 );
            return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        }
        catch (ExpiredJwtException exception) {
            log.warn("Request to parse expired JWT : {} failed : {}", authHeader, exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.warn("Request to parse unsupported JWT : {} failed : {}", authHeader, exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.warn("Request to parse invalid JWT : {} failed : {}", authHeader, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.warn("Request to parse empty or null JWT : {} failed : {}", authHeader, exception.getMessage());
        }

        return null;
    }

    /**
     * 토큰 유효성 검사 - 만료기간이 넘었는지
     * @param jwt
     * @return
     * true : 유효
     * false : 만료
     */
    public boolean validateToken(String jwt) {
        try {
            //jwt 파싱
            Jws<Claims> parsedToken = Jwts.parser()
                    .verifyWith(getShaKey())
                    .build()
                    .parseSignedClaims(jwt);

            return !parsedToken.getPayload().getExpiration().before(new Date());
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired"); //토큰 만료
            return false;
        } catch (JwtException exception) {
            log.error("Token Tampered"); //토큰 손상
            return false;
        } catch (NullPointerException exception) {
            log.error("Token is null"); //토큰 부재
            return false;
        } catch (Exception e) {
            return false;
        }
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
