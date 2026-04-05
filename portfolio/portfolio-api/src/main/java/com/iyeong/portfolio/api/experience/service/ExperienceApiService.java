package com.iyeong.portfolio.api.experience.service;

import com.iyeong.portfolio.api.experience.dto.ExperienceDto;
import com.iyeong.portfolio.domain.experience.entity.PfExperience;
import com.iyeong.portfolio.domain.experience.service.ExperienceDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExperienceApiService {

    private final ExperienceDomainService experienceDomainService;

    // post
    public ExperienceDto.Response createExperience(ExperienceDto.Request request) {
        PfExperience pfExperience = PfExperience.builder()
                .title(request.getTitle())
                .type(PfExperience.ActivityType.valueOf(request.getType()))
                .detail(request.getDetail())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .note(request.getNote())
                .build();

        PfExperience savedExperience =  experienceDomainService.saveExperience(pfExperience);
        return new ExperienceDto.Response(
                savedExperience.getId(),
                savedExperience.getTitle(),
                savedExperience.getType().name(),
                savedExperience.getDetail(),
                savedExperience.getStartDate(),
                savedExperience.getEndDate(),
                savedExperience.getNote()
        );
    }

    // get
    public List<ExperienceDto.Response> getAllExperiences() {
        // 도메인 서비스에서 전체 스택(엔티티)을 가져옵니다.
        List<PfExperience> experienceList = experienceDomainService.getAllExperiences();

        // 엔티티를 프론트엔드가 요구하는 DTO 형태로 변환해서 돌려줍니다.
        return experienceList.stream()
                .map(experience -> new ExperienceDto.Response(
                        experience.getId(),
                        experience.getTitle(),
                        experience.getType().name(),
                        experience.getDetail(),
                        experience.getStartDate(),
                        experience.getEndDate(),
                        experience.getNote()
                ))
                .collect(Collectors.toList());
    }

    // update
    public void updateExperience(ExperienceDto.UpdateRequest request) {
        experienceDomainService.updateExperience(
                request.getId(),
                request.getTitle(),
                PfExperience.ActivityType.valueOf(request.getType()),
                request.getDetail(),
                request.getStartDate(),
                request.getEndDate(),
                request.getNote()
        );
    }

    // delete
    public void deleteExperience(Long stackId) {
        experienceDomainService.deleteExperience(stackId);
    }
}