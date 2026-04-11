package com.iyeong.portfolio.api._common.service;

import com.iyeong.portfolio.api._common.dto.MainOverviewDto;
import com.iyeong.portfolio.domain.experience.repository.PfExperienceRepository;
import com.iyeong.portfolio.domain.project.repository.PfProjectRepository;
import com.iyeong.portfolio.domain.project.repository.PfProjectTagRepository;
import com.iyeong.portfolio.domain.certification.repository.PfCertificationRepository;
import com.iyeong.portfolio.domain.experience.entity.PfExperience.ActivityType;
import com.iyeong.portfolio.domain.project.entity.PfProjectTag.TagType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MainApiService {

    private final PfProjectRepository projectRepository;
    private final PfExperienceRepository experienceRepository;
    private final PfCertificationRepository certificationRepository;
    private final PfProjectTagRepository projectTagRepository;

    @Transactional(readOnly = true)
    // 🌟 변경: 반환 타입을 OverviewData로 수정합니다.
    public MainOverviewDto.OverviewData getOverviewData() {

        long awardCount = experienceRepository.countByType(ActivityType.수상);
        long educationCount = experienceRepository.countByType(ActivityType.강의);
        long projectCount = projectRepository.count();
        long qualificationCount = certificationRepository.countByStatusTrue();
        long playStoreCount = projectTagRepository.countByTag(TagType.STORE);

        // 🌟 변경: Response로 감싸지 않고, 데이터가 담긴 OverviewData를 바로 리턴합니다.
        return MainOverviewDto.OverviewData.builder()
                .award(awardCount)
                .project(projectCount)
                .qualifications(qualificationCount)
                .education(educationCount)
                .playStore(playStoreCount)
                .build();
    }
}