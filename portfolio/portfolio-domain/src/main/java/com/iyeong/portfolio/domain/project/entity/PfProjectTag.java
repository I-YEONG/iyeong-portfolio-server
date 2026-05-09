package com.iyeong.portfolio.domain.project.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "pf_project_tag")
public class PfProjectTag {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        // 1:N 연결 설정
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "project_id", nullable = false)
        private PfProject project;

        public enum TagType {
            FULL, FRONT, BACK, TEAM_LEADER, OPS, STORE
        }

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private TagType tag;

        //.assignProject(this) 메서드
        public void assignProject(PfProject project) {
            this.project = project;
        }

}
