package com.iyeong.portfolio.api.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class ProjectDetailDto {
    @Getter
    @Schema(name = "ProjectDetailRequest")
    public static class Request {
        private String intention;
        private String planning;
        private String stackDesc;
        private String teamRoles;
        private String feel;
    }
}