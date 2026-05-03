package com.iyeong.portfolio.api.certification.dto;

import com.iyeong.portfolio.domain.certification.entity.PfCertification.CertificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CertificationDto {

    // 1. 프론트엔드 -> 서버
    @Schema(name = "CertificationRequest")
    @Getter
    public static class Request {

        @NotNull(message = "자격증 타입은 비워둘 수 없습니다.") // enum 타입이므로 @NotNull 사용
        private CertificationType type;

        @NotBlank(message = "자격증 이름을 비워둘 수 없습니다.")
        private String name;

        @NotBlank(message = "발행 기관은 비워둘 수 없습니다.")
        private String organization;

        private Boolean status;
        private LocalDate acquiredDate;
        private String logoUrl;
    }

    // 2. 서버 -> 프론트엔드
    @Schema(name = "CertificationResponse")
    @Getter
    public static class Response {
        private Long id;
        private CertificationType type; // 🌟 추가
        private String name;
        private String organization;
        private Boolean status;
        private LocalDate acquiredDate;
        private String logoUrl;

        // 생성자에 type 추가
        public Response(Long id, CertificationType type, String name, String organization, Boolean status, LocalDate acquiredDate, String logoUrl) {
            this.id = id;
            this.type = type;
            this.name = name;
            this.organization = organization;
            this.status = status;
            this.acquiredDate = acquiredDate;
            this.logoUrl = logoUrl;
        }
    }

    // 3. 수정용
    @Schema(name = "CertificationUpdateRequest")
    @Getter
    public static class UpdateRequest {
        @NotNull(message = "수정할 자격증 ID는 필수입니다.")
        private Long id;

        @NotNull(message = "자격증 타입은 비워둘 수 없습니다.")
        private CertificationType type; // 🌟 추가

        @NotBlank(message = "자격증 이름을 비워둘 수 없습니다.")
        private String name;

        @NotBlank(message = "발행 기관을 비워둘 수 없습니다.")
        private String organization;

        private Boolean status;
        private LocalDate acquiredDate;
        private String logoUrl;
    }
}