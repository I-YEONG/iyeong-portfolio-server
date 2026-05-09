package com.iyeong.portfolio.api.stack.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
public class StackDto {

    // 1. 프론트엔드 -> 서버 (새로운 스택을 등록할 때 받는 상자)
    @Getter
    @Schema(name = "StackRequest")
    public static class Request {
//        @Min(value = 0, message = "숙련도는 0 이상이어야 합니다.")
//        @Max(value = 100, message = "숙련도는 100 이하여야 합니다.")
//        @NotBlank(message = "스택 이름은 필수입니다.") // 비어있으면 안 됨

        @NotBlank(message = "이름은 비워둘 수 없습니다.")
        private String name;

        @NotBlank(message = "카테고리는 비워둘 수 없습니다.")
        @Pattern(regexp = "^(back|front|ops|etc)$", message = "카테고리는 back, front, ops, etc 중 하나여야 합니다.")
        private String category;

        @NotBlank(message = "수준 항목은 비워둘 수 없습니다.")
        @Pattern(regexp = "^(core|expert|basic)$", message = "수준은 core, expert, basic 중 하나여야 합니다.")
        private String expertise;

        @NotNull(message = "퍼센티지 항목을 비워둘 수 없습니다.")
        @Min(value = 0, message = "0~100 사이 값이 필요합니다")
        @Max(value = 100, message = "0~100 사이 값이 필요합니다")
        private Integer proficiency;

        @NotNull(message = "순서를 비워둘 수 없습니다.")
        private Integer sortOrder;
    }

    // 2. 서버 -> 프론트엔드 (DB에서 스택을 꺼내서 프론트로 보낼 때 쓰는 상자)
    @Getter
    @Schema(name = "StackResponse")
    public static class Response {
        private Long id;
        private String name;
        private String category;
        private String expertise;
        private Integer proficiency;
        private Integer sortOrder;

        // DB에서 꺼낸 엔티티 데이터를 이 DTO 상자에 옮겨 담는 생성자
        public Response(Long id, String name, String category, String expertise, Integer proficiency, Integer sortOrder) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.expertise = expertise;
            this.proficiency = proficiency;
            this.sortOrder = sortOrder;
        }
    }

    // 기존 코드 아래에 추가하세요.

    // 3. 수정용 상자 (Update Request)
    @Getter
    @Schema(name = "StackUpdateRequest")
    public static class UpdateRequest {

        @NotNull(message = "수정할 스택 ID는 필수입니다.")
        private Long id;

        @NotBlank(message = "이름은 비워둘 수 없습니다.")
        private String name;

        @NotBlank(message = "카테고리는 비워둘 수 없습니다.")
        @Pattern(regexp = "^(back|front|ops|etc)$", message = "카테고리는 back, front, ops, etc 중 하나여야 합니다.")
        private String category;

        @NotBlank(message = "수준 항목은 비워둘 수 없습니다.")
        @Pattern(regexp = "^(core|expert|basic)$", message = "수준은 core, expert, basic 중 하나여야 합니다.")
        private String expertise;

        @NotNull(message = "퍼센티지 항목을 비워둘 수 없습니다.")
        @Min(value = 0, message = "0~100 사이 값이 필요합니다")
        @Max(value = 100, message = "0~100 사이 값이 필요합니다")
        private Integer proficiency;

        @NotNull(message = "순서를 비워둘 수 없습니다.")
        private Integer sortOrder;
    }
}