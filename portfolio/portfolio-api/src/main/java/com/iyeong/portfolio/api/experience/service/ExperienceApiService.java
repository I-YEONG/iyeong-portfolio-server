package com.iyeong.portfolio.api.experience.service;

import com.iyeong.portfolio.api.experience.dto.ExperienceDto;
import com.iyeong.portfolio.domain.experience.entity.PfExperience;
import com.iyeong.portfolio.domain.experience.service.ExperienceDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 🌟 필수 추가

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 🌟 1. 전체 세션 유지 (GET 에러 해결의 핵심!)
public class ExperienceApiService {

    private final ExperienceDomainService experienceDomainService;

    // post
    @Transactional // 🌟 2. 쓰기 작업 허용
    public ExperienceDto.Response createExperience(ExperienceDto.Request request) {
        List<PfExperience.ActivityType> activityTypes = request.getTypes().stream()
                .map(PfExperience.ActivityType::valueOf)
                .collect(Collectors.toList());

        PfExperience pfExperience = PfExperience.builder()
                .title(request.getTitle())
                .types(activityTypes)
                .detail(request.getDetail())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .note(request.getNote())
                .build();

        PfExperience savedExperience = experienceDomainService.saveExperience(pfExperience);

        List<String> typeNames = savedExperience.getTypes().stream()
                .map(Enum::name)
                .collect(Collectors.toList());

        return new ExperienceDto.Response(
                savedExperience.getId(),
                savedExperience.getTitle(),
                typeNames,
                savedExperience.getDetail(),
                savedExperience.getStartDate(),
                savedExperience.getEndDate(),
                savedExperience.getNote()
        );
    }

    // get
    // 🌟 클래스에 붙은 readOnly = true 덕분에 세션이 유지되어 에러 없이 types를 가져옵니다!
    public List<ExperienceDto.Response> getAllExperiences() {
        List<PfExperience> experienceList = experienceDomainService.getAllExperiences();

        return experienceList.stream()
                .map(experience -> {
                    List<String> typeNames = experience.getTypes().stream()
                            .map(Enum::name)
                            .collect(Collectors.toList());

                    return new ExperienceDto.Response(
                            experience.getId(),
                            experience.getTitle(),
                            typeNames,
                            experience.getDetail(),
                            experience.getStartDate(),
                            experience.getEndDate(),
                            experience.getNote()
                    );
                })
                .collect(Collectors.toList());
    }

    // update
    @Transactional // 🌟 2. 쓰기 작업 허용
    public void updateExperience(ExperienceDto.UpdateRequest request) {
        List<PfExperience.ActivityType> activityTypes = request.getTypes().stream()
                .map(PfExperience.ActivityType::valueOf)
                .collect(Collectors.toList());

        experienceDomainService.updateExperience(
                request.getId(),
                request.getTitle(),
                activityTypes,
                request.getDetail(),
                request.getStartDate(),
                request.getEndDate(),
                request.getNote()
        );
    }

    // delete
    @Transactional // 🌟 2. 쓰기(삭제) 작업 허용
    public void deleteExperience(Long experienceId) {
        experienceDomainService.deleteExperience(experienceId);
    }
}