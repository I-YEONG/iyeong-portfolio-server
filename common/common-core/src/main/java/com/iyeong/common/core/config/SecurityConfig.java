package com.iyeong.common.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF 보안 해제 (메서드 참조 방식)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. HTTP 요청 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 🟢 [전체 공개] 로그인 없이도 가능한 API (Auth, 상품 조회 등)
                        // .requestMatchers("/api/auth/**", "/api/portfolio/**").permitAll()

                        // 🔴 [관리자 전용] 어드민 권한이 있어야 하는 API (등록, 수정, 삭제 등)
                        // .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 🟡 [회원 전용] 로그인한 사용자만 가능한 API (내 정보, 저장 등)
                        // .anyRequest().authenticated()

                        // 🚀 현재 테스트를 위해 모든 요청 허용 (나중에 위 주석들을 풀면 됨)
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}