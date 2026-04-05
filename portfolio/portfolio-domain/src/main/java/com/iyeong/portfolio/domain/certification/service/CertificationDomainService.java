package com.iyeong.portfolio.domain.certification.service;

import com.iyeong.common.core.exception.BaseException;
import com.iyeong.portfolio.domain.certification.entity.PfCertification;
import com.iyeong.portfolio.domain.certification.repository.PfCertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CertificationDomainService {

    private final PfCertificationRepository certificationRepository;

    @Transactional
    public PfCertification saveCertification(PfCertification pfCertification) {
        // 데이터 중복 검사
        if (certificationRepository.existsByName(pfCertification.getName())) {
            throw new BaseException(400, "이미 등록된 스택 이름입니다.");
        }

        return certificationRepository.save(pfCertification);
    }

    // 조회
    public List<PfCertification> getAllCertifications() {
        return certificationRepository.findAll();
    }

    // 삭제
    @Transactional
    public void deleteCertification(Long certificationId) {
        // 데이터 확인
        if (!certificationRepository.existsById(certificationId)) {
            throw new BaseException(404, "존재하지 않는 스택 번호입니다.");
        }
        certificationRepository.deleteById(certificationId);
    }

    // 수정 (더티 체킹)
    @Transactional
    public void updateCertification(Long id, String name, String organization, Boolean status, LocalDate acquiredDate, String logoUrl) {
        // 기존 데이터 호출
        PfCertification originCertification = certificationRepository.findById(id)
                .orElseThrow(() -> new BaseException(404, "수정할 자격증이 존재하지 않습니다."));

        // 덮어 씌우기
        originCertification.update(name, organization, status, acquiredDate, logoUrl);
    }
}