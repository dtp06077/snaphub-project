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

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    //생성자에서 AuthenticationManager 설정
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        // 필터 URL 경로 설정 : /login
        setFilterProcessesUrl("/login");
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
        if (!authentication.isAuthenticated()) {
            log.info("인증 실패 : 아이디 또는 비밀번호가 일치하지 않습니다.");
            response.setStatus(401);  //401 UNAUTHORIZED (인증 실패)
        }

        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
