package com.iyeong.portfolio.api.certification.service;

import com.iyeong.portfolio.api.certification.dto.CertificationDto;
import com.iyeong.portfolio.domain.certification.entity.PfCertification;
import com.iyeong.portfolio.domain.certification.service.CertificationDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificationApiService {

    private final CertificationDomainService certificationDomainService;

    // post (이름 변경 및 type 추가)
    public CertificationDto.Response createCertification(CertificationDto.Request request) {
        PfCertification pfCertification = PfCertification.builder()
                .type(request.getType()) // 🌟 추가
                .name(request.getName())
                .organization(request.getOrganization())
                .status(request.getStatus())
                .acquiredDate(request.getAcquiredDate())
                .logoUrl(request.getLogoUrl())
                .build();

        PfCertification savedCertification = certificationDomainService.saveCertification(pfCertification);

        return new CertificationDto.Response(
                savedCertification.getId(),
                savedCertification.getType(), // 🌟 추가
                savedCertification.getName(),
                savedCertification.getOrganization(),
                savedCertification.getStatus(),
                savedCertification.getAcquiredDate(),
                savedCertification.getLogoUrl()
        );
    }

    // get
    public List<CertificationDto.Response> getAllCertifications() {
        List<PfCertification> certificationList = certificationDomainService.getAllCertifications();

        return certificationList.stream()
                .map(certification -> new CertificationDto.Response(
                        certification.getId(),
                        certification.getType(), // 🌟 추가
                        certification.getName(),
                        certification.getOrganization(),
                        certification.getStatus(),
                        certification.getAcquiredDate(),
                        certification.getLogoUrl()
                ))
                .collect(Collectors.toList());
    }

    // update
    public void updateCertification(CertificationDto.UpdateRequest request) {
        // 🌟 도메인 서비스의 파라미터에 type 추가 전달
        certificationDomainService.updateCertification(
                request.getId(),
                request.getName(),
                request.getOrganization(),
                request.getStatus(),
                request.getAcquiredDate(),
                request.getLogoUrl(),
                request.getType() // 🌟 엔티티 update 파라미터 순서에 맞게 추가
        );
    }

    // delete
    public void deleteCertification(Long certificationId) { // 🌟 변수명 stackId -> certificationId 변경
        certificationDomainService.deleteCertification(certificationId);
    }
}