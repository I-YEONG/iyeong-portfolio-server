package com.iyeong.portfolio.domain.project.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "pf_project_stack")
public class PfProjectStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 1:N 연결 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private PfProject project;

    public enum StackType {
        // front
        REACT, NEXT_JS, VUE,
        // back
        SEQUELIZE, SPRING_BOOT, POSTMAN, POSTGRES, MYSQL,
        // etc
        DOCKER, GITHUB, PLAY_STORE, RAILWAY, VERCEL
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StackType type;

    protected PfProjectStack() {}

    public PfProjectStack(StackType type, PfProject project) {
        this.type = type;
        this.project = project;
    }
}