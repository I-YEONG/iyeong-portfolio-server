package com.iyeong.portfolio.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.iyeong"}) // 1. 전체 컴포넌트 스캔
@EntityScan(basePackages = "com.iyeong")                // 2. 전체 엔티티 스캔
@EnableJpaRepositories(basePackages = "com.iyeong")     // 3. 전체 레포지토리 스캔
public class PortfolioApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortfolioApiApplication.class, args);
    }
}