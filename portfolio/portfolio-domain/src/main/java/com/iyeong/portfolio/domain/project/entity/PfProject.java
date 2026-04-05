package com.iyeong.portfolio.domain.project.entity;

import jakarta.persistence.*; // DB 연결 도구 불러오기
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "pf_project") // DB에 "pf_project"으로 생성
public class PfProject {

    public enum ProjectStatus {
        제작_완료,
        제작_중,
        기획
    }

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 50)
    private String subTitle; // 노란색 텍스트

    @Column(nullable = false, length = 100)
    private String description; // 1줄 ~ 2줄 정도의 간단 설명

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;

    @Column(nullable = false)
    private String url; // 포트폴리오 내부 주소 ex: "/esc"

    @Column(nullable = false)
    private String gitUrl; // GITHUB URL

    @Column(nullable = true)
    private String pdfUrl; // PDF 다운로드 주소

    @Column(nullable = false)
    private String domain; // 운영 당시 구매한 도메인

    @Column(nullable = false)
    private Integer teamSize; // 팀원 수

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = true)
    private String submitContest;


    // 관계 설정 - Stack
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PfProjectStack> stacks = new ArrayList<>();

    // 관계 설정 - Tag
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PfProjectTag> tags = new ArrayList<>();

    // 관계 설정 - image
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PfProjectImage> images = new ArrayList<>();

    // 관계 설정 - detail
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PfProjectDetail> details = new ArrayList<>();


    protected PfProject(){};

    public PfProject(String name,String subTitle, String description, String statusStr, String url, String gitUrl, String pdfUrl, String domain, Integer teamSize, LocalDate startDate, LocalDate endDate, String submitContest){
        this.name = name;
        this.subTitle = subTitle;
        this.description = description;

        if (statusStr != null) {
            String formattedStatus = statusStr.replace(" ", "_");
            this.status = ProjectStatus.valueOf(formattedStatus);
        }

        this.url = url;
        this.gitUrl = gitUrl;
        this.pdfUrl = pdfUrl;
        this.domain = domain;
        this.teamSize = teamSize;
        this.startDate = startDate;
        this.endDate = endDate;
        this.submitContest = submitContest;
    }

    // 연관관계 메서드 - stack
    public void addStack(PfProjectStack stack) {
        this.stacks.add(stack);
    }

    // 연관관계 메서드 - tag
    public void addTag(PfProjectTag tag) {
        this.tags.add(tag);
    }

    // 연관관계 메서드 - image
    public void addImage(PfProjectImage image) {
        this.images.add(image);
    }

    // 연관관계 메서드 - image
    public void addDetail(PfProjectDetail detail) {
        this.details.add(detail);
    }


}
