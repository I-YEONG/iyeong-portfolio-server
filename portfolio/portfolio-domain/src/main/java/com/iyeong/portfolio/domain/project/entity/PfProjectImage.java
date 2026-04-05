package com.iyeong.portfolio.domain.project.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "pf_project_image")
public class PfProjectImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 1:N 연결 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private PfProject project;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = true)
    private Boolean isMain = false;

    @Column(nullable = true)
    private Integer sortOrder = null;


    protected PfProjectImage(){}

    public PfProjectImage(PfProject project, String imageUrl, Boolean isMain, Integer sortOrder){
        this.project = project;
        this.imageUrl = imageUrl;
        this.isMain = isMain;
        this.sortOrder = sortOrder;
    }
}
