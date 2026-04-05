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

    // post
    public void createStack(CertificationDto.Request request) {
        PfCertification pfCertification = PfCertification.builder()
                .name(request.getName())
                .organization(request.getOrganization())
                .status(request.getStatus())
                .acquiredDate(request.getAcquiredDate())
                .logoUrl(request.getLogoUrl())
                .build();

        certificationDomainService.saveCertification(pfCertification);
    }

    // get
    public List<CertificationDto.Response> getAllCertifications() {
        // 도메인 서비스에서 전체 스택(엔티티)을 가져옵니다.
        List<PfCertification> certificationList = certificationDomainService.getAllCertifications();

        // 엔티티를 프론트엔드가 요구하는 DTO 형태로 변환해서 돌려줍니다.
        return certificationList.stream()
                .map(certification -> new CertificationDto.Response(
                        certification.getId(),
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
        certificationDomainService.updateCertification(
                request.getId(),
                request.getName(),
                request.getOrganization(),
                request.getStatus(),
                request.getAcquiredDate(),
                request.getLogoUrl()
        );
    }

    // delete
    public void deleteStack(Long stackId) {
        certificationDomainService.deleteCertification(stackId);
    }
}