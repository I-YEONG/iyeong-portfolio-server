package com.iyeong.portfolio.api.experience.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class ExperienceDto {

    @Getter
    @NoArgsConstructor
    public static class Request {
        @NotBlank(message = "타이틀을 비워둘 수 없습니다.")
        private String title;

        // 🌟 단일 String -> List<String> 으로 변경
        @NotNull(message = "타입은 필수입니다.")
        private List<String> types;

        @NotBlank(message = "디테일 내용을 비워둘 수 없습니다.")
        private String detail;

        @NotNull(message = "시작 날짜를 비워둘 수 없습니다.")
        private LocalDate startDate;

        @NotNull(message = "종료 날짜를 비워둘 수 없습니다.")
        private LocalDate endDate;

        private String note;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String title;
        // 🌟 단일 String -> List<String> 으로 변경
        private List<String> types;
        private String detail;
        private LocalDate startDate;
        private LocalDate endDate;
        private String note;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateRequest {
        @NotNull(message = "수정할 데이터의 ID는 필수입니다.")
        private Long id;

        @NotBlank(message = "타이틀을 비워둘 수 없습니다.")
        private String title;

        // 🌟 단일 String -> List<String> 으로 변경
        @NotNull(message = "타입은 필수입니다.")
        private List<String> types;

        @NotBlank(message = "디테일 내용을 비워둘 수 없습니다.")
        private String detail;

        @NotNull(message = "시작 날짜를 비워둘 수 없습니다.")
        private LocalDate startDate;

        @NotNull(message = "종료 날짜를 비워둘 수 없습니다.")
        private LocalDate endDate;

        private String note;
    }
}