package project.server.security.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import project.server.dto.response.auth.LoginResponseDto;
import project.server.security.jwt.constants.JwtConstants;
import project.server.security.domain.CustomUser;
import project.server.security.jwt.provider.JwtTokenProvider;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  client -> filter -> server
 *           (/auth/login)
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
        // 필터 URL 경로 설정 : /auth/login
        setFilterProcessesUrl(JwtConstants.AUTH_LOGIN_URL); // auth/login
    }

    /**
     * 인증 시도 메서드
     * : /auth/login 경로로 요청하면, 필터로 걸러서 인증을 시도
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");

        log.info("loginId : " + loginId);
        log.info("password : " + password);

        //AuthenticationManager
        //아이디, 패스워드 인증 토큰 생성
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(loginId, password);

        // 토큰에 요청정보 등록
        token.setDetails(new WebAuthenticationDetails(request));

        // 토큰을 이용하여 인증 요청 - 로그인
        Authentication authentication = authenticationManager.authenticate(token);

        try {
            log.info("Authentication Status : " + authentication.isAuthenticated());

            //인증 실패 (loginId, password 불일치)
            if (!authentication.isAuthenticated()) {
                log.info("Authentication failed: id or password does not match.");

                LoginResponseDto.authorizationFail(response);  //401 UNAUTHORIZED (인증 실패)
            }
            return authentication;
        }   catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());

            LoginResponseDto.databaseError(response);

            return authentication;
        }
    }

    /**
     * 인증 성공 메서드
     * - JWT 토큰 발급
     * - JWT 응답 헤더에 설정
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException {
        //인증 성공
        log.info("Authentication success");

        //시큐리티 컨텍스트에 인증 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);

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
        LoginResponseDto.success(response, jwt);
    }
}
