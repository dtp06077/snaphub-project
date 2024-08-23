package project.server.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.server.security.custom.CustomUserDetailService;
import project.server.security.jwt.filter.JwtAuthenticationFilter;
import project.server.security.jwt.filter.JwtRequestFilter;
import project.server.security.jwt.provider.JwtTokenProvider;

import java.io.IOException;

@Slf4j
@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록
// @preAuthorize, @PostAuthorize, @Secured 활성화
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    /**
     *  deprecated된 기존 WebSecurityConfigurerAdapter 방식 대신
     *  직접 component-based security 설정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("시큐리티 설정");

        http
                // 폼 기반 로그인 비활성화
                .formLogin( (login) -> login.disable())
                // HTTP 기본 인증 비활성화
                .httpBasic( (basic) -> basic.disable())
                // CSRF 공격 방어 기능 비활성화
                .csrf((csrf) -> csrf.disable())
                //필터 설정
                .addFilterAt(new JwtAuthenticationFilter(authenticationManager, jwtTokenProvider)
                        , UsernamePasswordAuthenticationFilter.class)

                .addFilterBefore(new JwtRequestFilter(jwtTokenProvider)
                        , UsernamePasswordAuthenticationFilter.class)
                //인가 설정
                .authorizeHttpRequests( authorizeRequests ->
                                    authorizeRequests
                                            //static 경로의 자원 접근 허용
                                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                            .requestMatchers("/").permitAll()
                                            .requestMatchers("/auth/**").permitAll()
                                            //게시물, 다른 사용자 정보 불러오는 메서드는 인증 필요 X
                                            .requestMatchers(HttpMethod.GET, "/post/**", "/users/*").permitAll()
                                            //검색 메서드는 인증 필요 X
                                            .requestMatchers("/search/**").permitAll()
                                            .requestMatchers("/file/**").permitAll()
                                            .requestMatchers("/admin/**").hasRole("ADMIN")
                                            .anyRequest().authenticated())
                //인증 실패 설정
                .exceptionHandling(exceptionConfig ->
                                    exceptionConfig.authenticationEntryPoint(new FailedAuthenticationEntryPoint()))
                /**
                 * 인증 방식 설정
                 * 인메모리 방식
                 * JDBC 방식
                 * 커스텀 방식 * -> userDetailService
                 */
                .userDetailsService(customUserDetailService);
                //세션 관리 정책 설정
//              세션 인증을 사용하지 않고, JWT 를 사용하여 인증하기 때문에 세션 불필요
//              .sessionManagement( (management) -> management
//                                           .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
    //PasswordEncoder 빈 등록
    //암호화 알고리즘 방식: Bcrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private AuthenticationManager authenticationManager;

    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration authenticationConfiguration) throws Exception {

        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        return authenticationManager;
    }
}

//인증 실패시
class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{ \"code\": \"AF\", \"message\": \"Authorization Failed.\" }");
    }
}

