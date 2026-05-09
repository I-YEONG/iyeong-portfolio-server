package com.iyeong.portfolio.domain.project.entity;

import jakarta.persistence.*; // DB 연결 도구 불러오기
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    /////////////////////////////////////
    ///  업데이트 로직
    /////////////////////////////////////
    public void updateInfo(String name, String subTitle, String description, ProjectStatus status,
                           String url, String gitUrl, String pdfUrl, String domain,
                           Integer teamSize, LocalDate startDate, LocalDate endDate, String submitContest) {
        this.name = name;
        this.subTitle = subTitle;
        this.description = description;
        this.status = status;
        this.url = url;
        this.gitUrl = gitUrl;
        this.pdfUrl = pdfUrl;
        this.domain = domain;
        this.teamSize = teamSize;
        this.startDate = startDate;
        this.endDate = endDate;
        this.submitContest = submitContest;
    }

    // 🌟 1. 스택 리스트 교체
    public void updateStacks(List<PfProjectStack> newStacks) {
        this.stacks.clear(); // 기존 스택들을 싹 비우고 (고아 객체로 만들어 삭제)
        newStacks.forEach(this::addStack); // 새로 들어온 스택들을 하나씩 부모 품에 연결!
    }

    // 🌟 2. 태그 리스트 교체
    public void updateTags(List<PfProjectTag> newTags) {
        this.tags.clear();
        newTags.forEach(this::addTag);
    }

    // 🌟 3. 이미지 리스트 교체
    public void updateImages(List<PfProjectImage> newImages) {
        this.images.clear();
        newImages.forEach(this::addImage);
    }

    // 🌟 4. 디테일 리스트 교체
    public void updateDetails(List<PfProjectDetail> newDetails) {
        this.details.clear();
        newDetails.forEach(this::addDetail);
    }


    /////////////////////////////////////
    ///  관계 설정
    /////////////////////////////////////

    // 관계 설정 - Stack
    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PfProjectStack> stacks = new ArrayList<>();

    // 관계 설정 - Tag
    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PfProjectTag> tags = new ArrayList<>();

    // 관계 설정 - image
    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PfProjectImage> images = new ArrayList<>();

    // 관계 설정 - detail
    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PfProjectDetail> details = new ArrayList<>();


    /////////////////////////////////////
    ///  연관 관계 메서드
    /////////////////////////////////////

    public void addStack(PfProjectStack stack) {
        this.stacks.add(stack);    // 내 데이터에 자식을 넣는다.
        stack.assignProject(this); // 자식에게도 부모 ID를 주입한다
    }

    public void addTag(PfProjectTag tag) {
        this.tags.add(tag);
        tag.assignProject(this);
    }

    public void addImage(PfProjectImage image) {
        this.images.add(image);
        image.assignProject(this);
    }

    public void addDetail(PfProjectDetail detail) {
        this.details.add(detail);
        detail.assignProject(this);
    }


}
