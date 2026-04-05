package com.iyeong.portfolio.domain.certification.entity;

import jakarta.persistence.*; // DB 연결 도구 불러오기
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "pf_certification") // DB에 "pf_certification"으로 생성
public class PfCertification {

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;

    @Column(nullable = false) // Not Null
    private String name; // 자격증 이름

    @Column(nullable = false)
    private String organization; // 발행 기관

    @Column(nullable = false)
    private Boolean status = true;

    @Column(nullable = false)
    private LocalDate acquiredDate;

    private String logoUrl;

    protected PfCertification() {}

    // 생성자
    public PfCertification( String name, String organization, Boolean status, LocalDate acquiredDate, String  logoUrl) {
         this.name = name;
         this.organization = organization;
         this.status = status;
         this.acquiredDate = acquiredDate;
         this.logoUrl = logoUrl;
    }
}