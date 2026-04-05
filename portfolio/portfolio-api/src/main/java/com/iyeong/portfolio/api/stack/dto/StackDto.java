package com.iyeong.portfolio.api.stack.dto;

import lombok.Getter;

@Getter
public class StackDto {

    // 1. 프론트엔드 -> 서버 (새로운 스택을 등록할 때 받는 상자)
    @Getter
    public static class Request {
//        @Min(value = 0, message = "숙련도는 0 이상이어야 합니다.")
//        @Max(value = 100, message = "숙련도는 100 이하여야 합니다.")
//        @NotBlank(message = "스택 이름은 필수입니다.") // 비어있으면 안 됨

        private String name;
        private String category;
        private String expertise;
        private Integer proficiency;
    }

    // 2. 서버 -> 프론트엔드 (DB에서 스택을 꺼내서 프론트로 보낼 때 쓰는 상자)
    @Getter
    public static class Response {
        private Long id;
        private String name;
        private String category;
        private String expertise;
        private Integer proficiency;

        // DB에서 꺼낸 엔티티 데이터를 이 DTO 상자에 옮겨 담는 생성자
        public Response(Long id, String name, String category, String expertise, Integer proficiency) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.expertise = expertise;
            this.proficiency = proficiency;
        }
    }
}