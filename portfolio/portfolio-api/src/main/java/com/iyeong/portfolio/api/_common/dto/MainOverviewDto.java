package com.iyeong.portfolio.api._common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MainOverviewDto {

    // 1. 최상위 응답 상자
    @Getter
    @Builder
    public static class Response {
        @JsonProperty("overview")
        private OverviewData overview;
    }

    // 2. 실제 데이터가 들어가는 알맹이 상자
    @Getter
    @Builder
    public static class OverviewData {
        private long award;          // 수상경력
        private long project;        // 프로젝트 경험
        private long qualifications; // 자격증
        private long education;      // 강의 수강 갯수
        private long playStore;      // 플레이스토어 등록 앱 갯수
    }
}