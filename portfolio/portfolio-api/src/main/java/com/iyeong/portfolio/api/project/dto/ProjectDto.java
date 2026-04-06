package com.iyeong.portfolio.api.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
public class ProjectDto {

    // 이미지 생성 Req
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageReq {
        @NotBlank private String imageUrl;
        private Boolean isMain;
        private Integer sortOrder;
    }

    // 디테일 생성 Req
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailReq {
        private String intention;
        private String planning;
        private String stackDesc;
        private String teamRoles;
        private String feel;
    }

    // 생성 Req
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        @NotBlank private String name;
        @NotBlank private String subTitle;
        @NotBlank private String description;
        @NotBlank private String status; // ex: "제작 완료"
        @NotBlank private String url;
        @NotBlank private String gitUrl;
        private String pdfUrl;
        @NotBlank private String domain;
        @NotNull private Integer teamSize;
        @NotNull private LocalDate startDate;
        @NotNull private LocalDate endDate;
        private String submitContest;

        // 자식 데이터들
        @Pattern(regexp = "^(REACT|NEXT_JS|VUE|SEQUELIZE|SPRING_BOOT|POSTMAN|POSTGRES|MYSQL|DOCKER|GITHUB|PLAY_STORE|RAILWAY|VERCEL)$", message = "stacks는 REACT, NEXT_JS, VUE, SEQUELIZE, SPRING_BOOT, POSTMAN, POSTGRES, MYSQL, DOCKER, GITHUB, PLAY_STORE, RAILWAY, VERCEL 중 하나여야 합니다.")
        private List<String> stacks; // ex: ["REACT", "SPRING_BOOT"]
        @Pattern(regexp = "^(FULL|FRONT|BACK|TEAM_LEADER|OPS|STORE)$", message = "태그는 FULL, FRONT, BACK, TEAM_LEADER, OPS, STORE 중 하나여야 합니다.")
        private List<String> tags;   // ex: ["FRONT", "BACK"]
        private List<ImageReq> images;
        private List<DetailReq> details;
    }

    // Res
    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private String name;
        private String subTitle;
        private String description;
        private String status;
        private String url;
        private String gitUrl;
        private String pdfUrl;
        private String domain;
        private Integer teamSize;
        private LocalDate startDate;
        private LocalDate endDate;
        private String submitContest;
        private List<String> stacks;
        private List<String> tags;
        private List<ImageReq> images;
        private List<DetailReq> details;

        public Response(Long id, String name, String subTitle, String description, String status, String url, String gitUrl, String pdfUrl, String domain, Integer teamSize, LocalDate startDate, LocalDate endDate, String submitContest, List<String> stacks, List<String> tags, List<ImageReq> images, List<DetailReq> details) {
            this.id = id;
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
            this.stacks = stacks;
            this.tags = tags;
            this.images = images;
            this.details = details;
        }
    }

    // --- [3. 수정 요청 (UpdateRequest)] ---
    @Getter
    @NoArgsConstructor
    public static class UpdateRequest extends Request { // Request 상자를 상속받아 재사용!
        @NotNull(message = "프로젝트 ID는 필수입니다.")
        private Long id;
    }
}