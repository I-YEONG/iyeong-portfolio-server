package com.iyeong.portfolio.api.experience.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ExperienceDto {

    // 1. 프론트엔드 -> 서버 (새로운 스택을 등록할 때 받는 상자)
    @Getter
    public static class Request {
//        @Min(value = 0, message = "숙련도는 0 이상이어야 합니다.")
//        @Max(value = 100, message = "숙련도는 100 이하여야 합니다.")
//        @NotBlank(message = "스택 이름은 필수입니다.") // 비어있으면 안 됨
        //@NotNull ^^는 문자열만, 이거는 그외,

        @NotBlank(message = "타이틀을 비워둘 수 없습니다.")
        private String title;

        @Pattern(regexp = "^(해커톤|강의|동아리|프로젝트|개인|알바|인턴)$", message = "카테고리는 [ 해커톤, 강의, 동아리, 프로젝트, 개인, 알바, 인턴 ] 중 하나여야 합니다.")
        @NotBlank(message = "타입은 비워둘 수 비워둘 수 없습니다.")
        private String type;

        @NotBlank(message = "디테일 내용을 비워둘 수 없습니다.")
        private String detail;

        @NotNull(message = "시작 날짜를 비워둘 수 없습니다.")
        private LocalDate startDate;

        @NotNull(message = "종료 날짜 비워둘 수 없습니다.")
        private LocalDate endDate;

        private String note;
    }

    // 2. 서버 -> 프론트엔드 (DB에서 스택을 꺼내서 프론트로 보낼 때 쓰는 상자)
    @Getter
    public static class Response {
        private Long id;
        private String title;
        private String type;
        private String detail;
        private LocalDate startDate;
        private LocalDate endDate;
        private String note;

        // DB에서 꺼낸 엔티티 데이터를 이 DTO 상자에 옮겨 담는 생성자
        public Response(Long id, String title, String type, String detail, LocalDate startDate, LocalDate endDate, String note) {
            this.id = id;
            this.title = title;
            this.type = type;
            this.detail = detail;
            this.startDate = startDate;
            this.endDate = endDate;
            this.note = note;
        }
    }

    // 3. 수정용 상자 (Update Request)
    @Getter
    public static class UpdateRequest {
        @NotNull(message = "수정할 Data ID는 필수입니다.")
        private Long id;

        @NotBlank(message = "타이틀을 비워둘 수 없습니다.")
        private String title;

        @Pattern(regexp = "^(해커톤|강의|동아리|프로젝트|개인|알바|인턴)$", message = "카테고리는 [ 해커톤, 강의, 동아리, 프로젝트, 개인, 알바, 인턴 ] 중 하나여야 합니다.")
        @NotBlank(message = "타입은 비워둘 수 비워둘 수 없습니다.")
        private String type;

        @NotBlank(message = "디테일 내용을 비워둘 수 없습니다.")
        private String detail;

        @NotNull(message = "시작 날짜를 비워둘 수 없습니다.")
        private LocalDate startDate;

        @NotNull(message = "종료 날짜 비워둘 수 없습니다.")
        private LocalDate endDate;

        private String note;
    }
}