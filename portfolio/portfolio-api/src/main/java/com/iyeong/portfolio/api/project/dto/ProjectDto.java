package com.iyeong.portfolio.api.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(name = "ProjectImageRequest") // 스키마 추가
    public static class ImageReq {
        @NotBlank private String imageUrl;
        private Boolean isMain;
        private Integer sortOrder;
    }

    // 디테일 생성 Req (이전의 ProjectDetailDto 역할을 여기서 수행)
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "ProjectDetailRequest") // 스키마 추가
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
    @Schema(name = "ProjectRequest", description = "프로젝트 생성 요청 DTO") // 스키마 추가
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

        // 🚨 런타임 에러 해결: @Pattern을 List 제네릭 내부로 이동!
        private List<@Pattern(regexp = "^(REACT|NEXT_JS|VUE|SEQUELIZE|SPRING_BOOT|POSTMAN|POSTGRES|MYSQL|DOCKER|GITHUB|PLAY_STORE|RAILWAY|VERCEL)$", message = "stacks는 정해진 값 중 하나여야 합니다.") String> stacks;

        private List<@Pattern(regexp = "^(FULL|FRONT|BACK|TEAM_LEADER|OPS|STORE)$", message = "태그는 FULL, FRONT, BACK, TEAM_LEADER, OPS, STORE 중 하나여야 합니다.") String> tags;

        private List<ImageReq> images;
        private List<DetailReq> details;
    }

    // Res
    @Getter
    @NoArgsConstructor
    @Schema(name = "ProjectResponse", description = "프로젝트 응답 DTO") // 스키마 추가
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
            // ... 기존 생성자 내용과 동일 ...
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
    @Schema(name = "ProjectUpdateRequest", description = "프로젝트 수정 요청 DTO") // 스키마 추가
    public static class UpdateRequest extends Request {
        @NotNull(message = "프로젝트 ID는 필수입니다.")
        private Long id;
    }
}