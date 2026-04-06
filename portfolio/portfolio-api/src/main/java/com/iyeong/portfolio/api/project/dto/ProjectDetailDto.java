package com.iyeong.portfolio.api.project.dto;

import lombok.Getter;

public class ProjectDetailDto {
    @Getter
    public static class Request {
        private String intention;
        private String planning;
        private String stackDesc;
        private String teamRoles;
        private String feel;
    }
}