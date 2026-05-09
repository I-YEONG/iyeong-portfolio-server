package com.iyeong.portfolio.domain.project.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    //.assignProject(this) 메서드
    public void assignProject(PfProject project) {
        this.project = project;
    }

}
