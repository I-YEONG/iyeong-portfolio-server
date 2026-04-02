package com.iyeong.portfolio.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// (scanBasePackages = "com.iyeong")을 붙여야
// 다른 모듈(common, domain)에 있는 설정들을 스프링이 찾아낼 수 있습니다.
@SpringBootApplication(scanBasePackages = "com.iyeong")
public class PortfolioApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortfolioApiApplication.class, args);
    }
}