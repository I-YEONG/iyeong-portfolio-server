package com.iyeong.portfolio.api.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class ProjectImageDto {
    @Getter
    public static class Request {
        @NotBlank(message = "이미지 URL은 필수입니다.")
        private String imageUrl;
        private Boolean isMain;
        private Integer sortOrder;
    }
}