package com.iyeong.portfolio.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

    @Getter
    @Entity
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
            FULL, FRONT, BACK, TEAM_LEADER
        }

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private TagType tag;

        protected PfProjectTag() {}

        public PfProjectTag(TagType tag, PfProject project) {
            this.tag = tag;
            this.project = project;
        }
    }
