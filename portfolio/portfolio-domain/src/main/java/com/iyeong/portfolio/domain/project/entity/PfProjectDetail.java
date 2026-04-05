package com.iyeong.portfolio.domain.project.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "pf_project_image")
public class PfProjectDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 1:N 연결 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private PfProject project;

    @Column(columnDefinition = "TEXT")
    private String intention; // 의도

    @Column(columnDefinition = "TEXT")
    private String planning; // 기획

    @Column(columnDefinition = "TEXT")
    private String stackDesc; // 스택

    @Column(columnDefinition = "TEXT")
    private String teamRoles; // 팀 열할

    @Column(columnDefinition = "TEXT")
    private String feel; // 느낀 & 배운 점


    protected PfProjectDetail(){}

    public PfProjectDetail(PfProject project, String intention, String planning, String stackDesc, String teamRoles, String feel){
        this.project = project;
        this.intention = intention;
        this.planning = planning;
        this.stackDesc = stackDesc;
        this.teamRoles = teamRoles;
        this.feel = feel;


    }
}
