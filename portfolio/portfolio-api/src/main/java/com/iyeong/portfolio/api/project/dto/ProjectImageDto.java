package com.iyeong.portfolio.api.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class ProjectImageDto {
    @Getter
    @Schema(name = "ProjectImageRequest", description = "프로젝트 이미지 요청 DTO")
    public static class Request {
        @NotBlank(message = "이미지 URL은 필수입니다.")
        private String imageUrl;
        private Boolean isMain;
        private Integer sortOrder;
    }
}