package com.iyeong.portfolio.domain.stack.entity;

import jakarta.persistence.*; // DB 연결 도구 불러오기
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Builder
@Table(name = "pf_stack") // DB에 "pf_stack"으로 생성
public class PfStack {

    public enum CategoryList{
        back, front, ops, etc
    }

    public enum ExpertiseList {
        core, sub
    }

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;

    @Column(nullable = false) // Not Null
    private String name; // 스택 이름

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryList category; // [ back, front, ops, etc ]

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpertiseList expertise; // [ core, expert, basic ]

    @Column(nullable = false)
    private Integer proficiency; // [ 0 ~ 100 ]

    protected PfStack() {}

    // 생성자
    public PfStack(String name, CategoryList category, ExpertiseList expertise, Integer proficiency) {
        this.name = name;
        this.category = category;
        this.expertise = expertise;
        this.proficiency = proficiency;
    }
}