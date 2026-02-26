package com.cat.picshare.config; // config 패키지 선언

import com.cat.picshare.auth.handler.AuthenticationEntryPointImpl; // 401 JSON 처리를 위한 EntryPoint를 사용하기 위한 임포트
import com.cat.picshare.auth.jwt.JwtAuthenticationFilter; // JWT 필터를 사용하기 위한 임포트
import com.cat.picshare.auth.jwt.JwtProperties; // JWT 설정 바인딩 클래스를 사용하기 위한 임포트
import com.cat.picshare.auth.jwt.JwtTokenProvider; // JWT Provider를 사용하기 위한 임포트
import org.springframework.boot.context.properties.EnableConfigurationProperties; // ConfigurationProperties 활성화를 위한 임포트
import org.springframework.context.annotation.Bean; // Bean 등록을 위한 임포트
import org.springframework.context.annotation.Configuration; // 설정 클래스 표시를 위한 임포트
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // HttpSecurity 설정을 위한 임포트
import org.springframework.security.config.http.SessionCreationPolicy; // 무상태 세션 정책을 위한 임포트
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // BCrypt 기반 PasswordEncoder 구현체를 사용하기 위한 임포트
import org.springframework.security.crypto.password.PasswordEncoder; // 비밀번호 해시/검증을 위한 PasswordEncoder 타입을 사용하기 위한 임포트
import org.springframework.security.web.SecurityFilterChain; // 필터 체인 타입을 위한 임포트
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // 필터 순서 지정을 위한 임포트

@Configuration // 설정 클래스임을 선언
@EnableConfigurationProperties(JwtProperties.class) // JwtProperties 바인딩을 활성화
public class SecurityConfig { // Spring Security 설정 클래스

    @Bean // PasswordEncoder를 스프링 빈으로 등록(회원가입 시 비밀번호 해시, 로그인 시 비밀번호 검증에 필요)
    public PasswordEncoder passwordEncoder() { // PasswordEncoder 빈 생성 메서드
        return new BCryptPasswordEncoder(); // BCrypt로 비밀번호를 안전하게 해시하는 구현체를 반환
    } // 메서드 종료

    @Bean // JWT 필터를 스프링 빈으로 등록
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenProvider tokenProvider, JwtProperties jwtProperties) { // 필터 빈 생성 메서드
        return new JwtAuthenticationFilter(tokenProvider, jwtProperties); // 필터를 생성해 반환
    } // 메서드 종료

    @Bean // SecurityFilterChain을 스프링 빈으로 등록
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception { // 보안 구성을 정의
        http.csrf(csrf -> csrf.disable()); // REST API는 일반적으로 CSRF를 끈다(세션을 안 쓰기 때문).
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션을 사용하지 않는 무상태로 설정
        http.formLogin(form -> form.disable()); // 폼 로그인 페이지를 비활성화
        http.httpBasic(basic -> basic.disable()); // Basic 인증을 비활성화
        http.exceptionHandling(ex -> ex.authenticationEntryPoint(new AuthenticationEntryPointImpl())); // 인증 실패 시 401 JSON을 내려주게 설정
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()      // 로그인/회원가입 허용
                .requestMatchers("/api/test/ok").permitAll()      // ok는 열어둠
                .requestMatchers("/api/test/custom").permitAll()  // custom도 열어둠
                .anyRequest().authenticated()                     // 나머지는 인증 필요
        );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // UsernamePasswordAuthenticationFilter 앞에 JWT 필터를 둔다.
        return http.build(); // 필터 체인을 빌드해 반환
    } // 메서드 종료
} // 클래스 종료