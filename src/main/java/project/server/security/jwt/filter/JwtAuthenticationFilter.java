package project.server.security.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.server.security.jwt.constants.JwtConstants;
import project.server.security.domain.CustomUser;
import project.server.security.jwt.provider.JwtTokenProvider;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  client -> filter -> server
 *           (/login)
 * loginId, password로 인증 시도 -> attemptAuthentication
 *
 * 1. 인증 성공 시 -> successfulAuthentication -> JWT 생성
 *    생성된 JWT -> response-header-authorization 에 담김
 *
 * 2. 인증 실패 시 -> response-status -> 401 (UNAUTHORIZED)
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    //생성자에서 AuthenticationManager, JwtTokenProvider 설정
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        // 필터 URL 경로 설정 : /login
        setFilterProcessesUrl(JwtConstants.AUTH_LOGIN_URL); // /login
    }

    /**
     * 인증 시도 메서드
     * : /login 경로로 요청하면, 필터로 걸러서 인증을 시도
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");

        log.info("loginId : " + loginId);
        log.info("password : " + password);

        // 사용자 인증정보 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginId, password);

        // 사용자 인증 (로그인)
        authentication = authenticationManager.authenticate(authentication);

        log.info("인증 여부 : " + authentication.isAuthenticated());

        //인증 실패 (loginId, password 불일치)
        //TODO 인증 실패 상태 코드 변경
        if (!authentication.isAuthenticated()) {
            log.info("인증 실패 : 아이디 또는 비밀번호가 일치하지 않습니다.");
            response.setStatus(401);  //401 UNAUTHORIZED (인증 실패)
        }

        return authentication;
    }

    /**
     * 인증 성공 메서드
     * - JWT 토큰 발급
     * - JWT 응답 헤더에 설정
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) {

        log.info("인증 성공");

        CustomUser user = (CustomUser) authentication.getPrincipal();
        //회원 정보
        int userId = user.getUser().getId();
        String loginId = user.getUser().getLoginId();
    
        //회원 권한
        List<String> roles = user.getUser().getAuths().stream()
                .map( (auth) -> auth.getAuth())
                .collect(Collectors.toList());

        // jwt 토큰 발급 요청
        String jwt = jwtTokenProvider.createToken(userId, loginId, roles);

        // jwt 응답 헤더에 설정 { Authorization : Bearer + {jwt} }
        response.addHeader(JwtConstants.TOKEN_HEADER, JwtConstants.TOKEN_PREFIX + jwt);
        response.setStatus(200);
    }
}
