package com.iyeong.common.core.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

@Service
public class SupabaseStorageService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.service-key}")
    private String serviceKey;

    /**
     * @param folderPath 저장할 폴더 경로 (예: "user", "admin", "/project/banner")
     * @param fileName   원본 파일명.확장자
     * @return 프론트엔드에서 사용할 임시 업로드 URL
     */
    public String generatePresignedUrl(String folderPath, String fileName) {

        // 유니크를 위해 날짜/시간 정보 추가
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm"));
        String uniqueFileName = timestamp + "_" + UUID.randomUUID().toString().substring(0, 8) + "_" + fileName;

        // 폴더 구조 설정
        String fullPath = folderPath + "/" + uniqueFileName;

        // API URL 구성 (iyeong-portfolio-file-bucket 버킷 뒤에 fullPath를 붙임)
        String apiUrl = supabaseUrl + "/storage/v1/object/upload/sign/iyeong-portfolio-file-bucket/" + fullPath;


        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + serviceKey);
        headers.set("apikey", serviceKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 유효 시간 3분
        String body = "{\"expiresIn\": 180}";
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class);

        String signedUrlPath = (String) response.getBody().get("url");
        return supabaseUrl + "/storage/v1" + signedUrlPath;
    }
}