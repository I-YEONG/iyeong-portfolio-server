package com.iyeong.portfolio.api._common.service;

import com.iyeong.portfolio.api._common.dto.MainOverviewDto;
import com.iyeong.portfolio.domain.experience.repository.PfExperienceRepository;
import com.iyeong.portfolio.domain.project.repository.PfProjectRepository;
import com.iyeong.portfolio.domain.project.repository.PfProjectTagRepository;
import com.iyeong.portfolio.domain.certification.repository.PfCertificationRepository;
// 🌟 ActivityType 임포트 추가 (본인의 패키지 경로에 맞게 확인하세요)
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
    public MainOverviewDto.Response getOverviewData() {

        // 🌟 1. 문자열 "수상"이 아니라 Enum 상수 ActivityType.수상 을 전달합니다.
        long awardCount = experienceRepository.countByType(ActivityType.수상);
        long educationCount = experienceRepository.countByType(ActivityType.강의);

        long projectCount = projectRepository.count();
        long qualificationCount = certificationRepository.countByStatusTrue();
        long playStoreCount = projectTagRepository.countByTag(TagType.STORE);

        // 2. 응답 데이터 조립
        MainOverviewDto.OverviewData overviewData = MainOverviewDto.OverviewData.builder()
                .award(awardCount)
                .project(projectCount)
                .qualifications(qualificationCount)
                .education(educationCount)
                .playStore(playStoreCount)
                .build();

        return MainOverviewDto.Response.builder()
                .overview(overviewData)
                .build();
    }
}