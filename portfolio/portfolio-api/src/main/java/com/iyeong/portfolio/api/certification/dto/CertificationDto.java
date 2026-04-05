package com.iyeong.portfolio.api.certification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CertificationDto {

    // 1. 프론트엔드 -> 서버 (새로운 스택을 등록할 때 받는 상자)
    @Getter
    public static class Request {
//        @Min(value = 0, message = "숙련도는 0 이상이어야 합니다.")
//        @Max(value = 100, message = "숙련도는 100 이하여야 합니다.")
//        @NotBlank(message = "스택 이름은 필수입니다.") // 비어있으면 안 됨
        //@NotNull ^^는 문자열만, 이거는 그외,

        @NotBlank(message = "자격증 이름을 비워둘 수 없습니다.")
        private String name;

        @NotBlank(message = "자격증 카테고리는 비워둘 수 없습니다.")
        private String organization;

        private Boolean status;

        private LocalDate acquiredDate;

        private String logoUrl;
    }

    // 2. 서버 -> 프론트엔드 (DB에서 스택을 꺼내서 프론트로 보낼 때 쓰는 상자)
    @Getter
    public static class Response {
        private Long id;
        private String name;
        private String organization;
        private Boolean status;
        private LocalDate acquiredDate;
        private String logoUrl;

        // DB에서 꺼낸 엔티티 데이터를 이 DTO 상자에 옮겨 담는 생성자
        public Response(Long id, String name, String organization, Boolean status, LocalDate acquiredDate, String logoUrl) {
            this.id = id;
            this.name = name;
            this.organization = organization;
            this.status = status;
            this.acquiredDate = acquiredDate;
            this.logoUrl = logoUrl;
        }
    }

    // 3. 수정용 상자 (Update Request)
    @Getter
    public static class UpdateRequest {
        @NotNull(message = "수정할 자격증 ID는 필수입니다.")
        private Long id;

        @NotBlank(message = "자격증 이름을 비워둘 수 없습니다.")
        private String name;

        @NotBlank(message = "발행 기관을 비워둘 수 없습니다.")
        private String organization;

        private Boolean status;

        private LocalDate acquiredDate;

        private String logoUrl;
    }
}