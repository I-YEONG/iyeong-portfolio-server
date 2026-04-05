package com.iyeong.portfolio.domain.experience.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 🌟 JPA를 위한 기본 생성자 (필수)
@AllArgsConstructor // 🌟 @Builder를 위한 전체 생성자 (필수)
@Table(name = "pf_experience") // DB에 "pf_experience"으로 생성
public class PfExperience {

    // type 열거형 정의
    public enum ActivityType {
        해커톤,
        강의,
        동아리,
        프로젝트,
        개인,
        알바,
        인턴,

    }

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType type; // [ 해커톤, 강의, 동아리, 프로젝트, 개인, 알바, 인턴 ]

    // TEXT
    @Column(nullable = false, columnDefinition = "TEXT")
    private String detail;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = true)
    private String note = ""; // 비고


    // 업데이트 메서드
    public  void update( String title, ActivityType type, String detail, LocalDate startDate, LocalDate endDate, String note) {
        this.title = title;
        this.type = type;
        this.detail = detail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.note = note;
    }
}