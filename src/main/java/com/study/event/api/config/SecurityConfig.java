package com.study.event.api.config;

import com.study.event.api.auth.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

// 스프링 시큐리티 설정 파일
// 인터셉터, 필터 처리
// 세션 인증, 토큰 인증
// 권한 처리
// OAuth2 - SNS 로그인
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    // 비밀번호 암호화 객체 컨테이너에 등록하기 (스프링에게 주입받는 설정)
    @Bean
    public PasswordEncoder passwordEncoder() {
        //암호화 알고리즘 아무거나 사용해도 되긴한데 제일 사용량이 많음
//        return new BCryptPasswordEncoder();
        return new BCryptPasswordEncoder();
    }

    // 시큐리티 설정 (스프리 부트 2.7 버전 이전 인터페이스를 통해 오버라이딩)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable() // 필터설정 off
                .httpBasic().disable() // 베이직 인증 off
                .formLogin().disable() // 로그인창 off
                //세션 인증은 더이상 사용하지 않음
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() // 요청 별로 인가 설정
                .antMatchers("/", "/auth/**").permitAll()
//                .antMatchers("/", "/auth/**").permitAll()
//                .authenticated() // 인가 설정 on

                //나머지 요청은 전부 인증(로그인) 후 진행하기
                .anyRequest().authenticated() // 인가 설정 on
        ;

        //토큰 위조 검사
        //CorsFilter(spring의 필터) 뒤에 커스텀 필터 연결
        http.addFilterAfter(jwtAuthFilter, CorsFilter.class);

        return http.build();
    }

}
