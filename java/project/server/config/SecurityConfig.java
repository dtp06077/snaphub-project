package project.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import project.server.security.custom.CustomUserDetailService;
import project.server.security.jwt.filter.JwtAuthenticationFilter;
import project.server.security.jwt.provider.JwtTokenProvider;

@Slf4j
@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록
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

        // 폼 기반 로그인 비활성화
        http.formLogin( (login) -> login.disable());
        // HTTP 기본 인증 비활성화
        http.httpBasic( (basic) -> basic.disable());
        // CSRF 공격 방어 기능 비활성화
        http.csrf((csrf) -> csrf.disable());
        //세션 관리 정책 설정
//        세션 인증을 사용하지 않고, JWT 를 사용하여 인증하기 때문에 세션 불필요
//        http.sessionManagement( (management) -> management
//                                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //필터 설정
        http.addFilterAt(new JwtAuthenticationFilter(authenticationManager, jwtTokenProvider), null)
            .addFilterBefore(null, null)
            ;

        //인가 설정
        http.authorizeHttpRequests( authorizeRequests ->
                                    authorizeRequests
                                            //static 경로의 자원 접근 허용
                                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                            .requestMatchers("/").permitAll()
                                            .requestMatchers("/login").permitAll()
                                            .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                                            .requestMatchers("/admin/**").hasRole("ADMIN")
                                            .anyRequest().authenticated());

        /**
         * 인증 방식 설정
         * 인메모리 방식
         * JDBC 방식
         * 커스텀 방식 * -> userDetailService
         */
        http.userDetailsService(customUserDetailService);


        return http.build();
    }
    //PasswordEncoder 빈 등록
    //암호화 알고리즘 방식: Bcrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //AuthenticationManager 빈 등록
    private AuthenticationManager authenticationManager;

    @Bean
    public AuthenticationManager authenticationManager
                    (AuthenticationConfiguration authenticationConfiguration) throws Exception {

        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        return authenticationManager;
    }
}
