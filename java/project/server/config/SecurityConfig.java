package project.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;


@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록
public class SecurityConfig {

    /**
     *  deprecated된 기존 WebSecurityConfigurerAdapter 방식 대신
     *  직접 component-based security 설정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 폼 기반 로그인 비활성화
        http.formLogin( (login) -> login.disable());
        // HTTP 기본 인증 비활성화
        http.httpBasic( (basic) -> basic.disable());
        // CSRF 공격 방어 기능 비활성화
        http.csrf((csrf) -> csrf.disable());
        //세션 관리 정책 설정
        //세션 인증을 사용하지 않고, JWT 를 사용하여 인증하기 때문에 세션 불필요
        http.sessionManagement( (management) -> management
                                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
    //PasswordEncoder 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //AuthenticationManager 빈 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
