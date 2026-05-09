package com.iyeong.portfolio.domain.experience.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        수상,
        강의,
        동아리,
        프로젝트,
        개인,
        알바,
        인턴
    }

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;

    @Column(nullable = false)
    private String title; //타이틀

    // 🌟 수정된 부분: 단일 타입 -> 리스트로 변경
    @ElementCollection(fetch = FetchType.LAZY) // 필요할 때만 가져오도록 지연 로딩 설정
    @CollectionTable(
            name = "pf_experience_type", // DB에 생성될 별도의 테이블 이름
            joinColumns = @JoinColumn(name = "experience_id") // 메인 테이블(pf_experience)과 연결할 외래키 컬럼명
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false) // 생성되는 테이블의 타입 컬럼명
    @Builder.Default // 🌟 Builder 사용 시 빈 리스트 초기화를 유지하기 위해 필수
    private List<ActivityType> types = new ArrayList<>();

    // TEXT
    @Column(nullable = false, columnDefinition = "TEXT")
    private String detail; // 설명

    @Column(nullable = false)
    private LocalDate startDate; //시작 날짜

    @Column(nullable = false)
    private LocalDate endDate; // 종료 날짜

    @Column(nullable = true)
    private String note; // 비고


    // 업데이트 메서드 수정
    public void update(String title, List<ActivityType> types, String detail, LocalDate startDate, LocalDate endDate, String note) {
        this.title = title;

        // 🌟 기존 리스트를 비우고 새로 추가 (컬렉션 업데이트 시 권장되는 방식)
        this.types.clear();
        if (types != null) {
            this.types.addAll(types);
        }

        this.detail = detail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.note = note;
    }
}