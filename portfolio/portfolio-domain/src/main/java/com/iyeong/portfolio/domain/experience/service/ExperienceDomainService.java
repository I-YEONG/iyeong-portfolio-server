package com.iyeong.portfolio.domain.experience.service;

import com.iyeong.common.core.exception.BaseException;
import com.iyeong.portfolio.domain.experience.entity.PfExperience;
import com.iyeong.portfolio.domain.experience.repository.PfExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExperienceDomainService {

    private final PfExperienceRepository experienceRepository;

    @Transactional
    public PfExperience saveExperience(PfExperience pfExperience) {
        // 데이터 중복 검사
        if (experienceRepository.existsByTitle(pfExperience.getTitle())) {
            throw new BaseException(400, "이미 등록된 스택 이름입니다.");
        }

        return experienceRepository.save(pfExperience);
    }

    // 조회
    public List<PfExperience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    // 삭제
    @Transactional
    public void deleteExperience(Long experienceId) {
        // 데이터 확인
        if (!experienceRepository.existsById(experienceId)) {
            throw new BaseException(404, "존재하지 않는 스택 번호입니다.");
        }
        experienceRepository.deleteById(experienceId);
    }

    // 수정 (더티 체킹)
    @Transactional
    public void updateExperience(Long id, String title, PfExperience.ActivityType type, String detail, LocalDate startDate, LocalDate endDate, String note) {
        // 기존 데이터 호출
        PfExperience originCertification = experienceRepository.findById(id)
                .orElseThrow(() -> new BaseException(404, "수정할 자격증이 존재하지 않습니다."));

        // 덮어 씌우기
        originCertification.update(title, type, detail, startDate, endDate, note);
    }
}