package com.iyeong.portfolio.domain.entity;

import jakarta.persistence.*; // DB 연결 도구 불러오기
import lombok.Getter;

@Getter
@Entity
@Table(name = "pf_stack") // DB에 "pf_stack"으로 생성
public class PfStack {

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;

    @Column(nullable = false) // Not Null
    private String name; // 스택 이름

    @Column(nullable = false)
    private String category; // [ back, front, ops, etc ]

    @Column(nullable = false)
    private String expertise; // [ core, expert, basic ]

    @Column(nullable = false)
    private Integer proficiency; // [ 0 ~ 100 ]

    protected PfStack() {}

    // 생성자
    public PfStack(String name, String category, String expertise, Integer proficiency) {
        this.name = name;
        this.category = category;
        this.expertise = expertise;
        this.proficiency = proficiency;
    }
}