package com.iyeong.portfolio.api.project.service;

import com.iyeong.common.core.exception.BaseException;
import com.iyeong.portfolio.api.project.dto.ProjectDto;
import com.iyeong.portfolio.api.project.dto.ProjectImageDto;
import com.iyeong.portfolio.api.project.dto.ProjectDetailDto;
import com.iyeong.portfolio.domain.project.entity.*;
import com.iyeong.portfolio.domain.project.service.ProjectDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectApiService {

    private final ProjectDomainService projectDomainService;

    /////////////////////////////////////
    ///  CRUD
    /////////////////////////////////////

    // post
    @Transactional
    public ProjectDto.Response createProject(ProjectDto.Request request) {

        // 1. 기본 정보 조립 및 Enum 변환
        String formattedStatus = request.getStatus().replace(" ", "_");

        PfProject project = PfProject.builder()
                .name(request.getName())
                .subTitle(request.getSubTitle())
                .description(request.getDescription())
                .status(PfProject.ProjectStatus.valueOf(formattedStatus))
                .url(request.getUrl())
                .gitUrl(request.getGitUrl())
                .pdfUrl(request.getPdfUrl())
                .domain(request.getDomain())
                .teamSize(request.getTeamSize())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .submitContest(request.getSubmitContest())
                .build();

        // 🌟 2. 스택 추가 (버그 수정 완료!)
        if (request.getStacks() != null && !request.getStacks().isEmpty()) {
            request.getStacks().forEach(stackStr ->
                    project.addStack(PfProjectStack.builder().type(PfProjectStack.StackType.valueOf(stackStr)).build())
            );
        }

        // 🌟 3. 태그 추가 (버그 수정 완료!)
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            request.getTags().forEach(tagStr ->
                    project.addTag(PfProjectTag.builder().tag(PfProjectTag.TagType.valueOf(tagStr)).build())
            );
        }

        // 🌟 4. 이미지 추가
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            request.getImages().forEach(img ->
                    project.addImage(PfProjectImage.builder().imageUrl(img.getImageUrl()).isMain(img.getIsMain()).sortOrder(img.getSortOrder()).build())
            );
        }

        // 🌟 5. 디테일 추가
        if (request.getDetails() != null && !request.getDetails().isEmpty()) {
            request.getDetails().forEach(detail ->
                    project.addDetail(PfProjectDetail.builder()
                            .intention(detail.getIntention())
                            .planning(detail.getPlanning())
                            .stackDesc(detail.getStackDesc())
                            .teamRoles(detail.getTeamRoles())
                            .feel(detail.getFeel())
                            .build())
            );
        }

        // 6. 도메인 서비스에 저장 요청
        PfProject savedProject = projectDomainService.saveProject(project);

        // 7. 결과 반환
        return convertToResponseDto(savedProject);
    }

    // put
    @Transactional
    public ProjectDto.Response updateProject(Long projectId, ProjectDto.UpdateRequest request) {

        // 1. 기본 정보용 껍데기 조립
        String formattedStatus = request.getStatus().replace(" ", "_");
        PfProject updateInfo = PfProject.builder()
                .name(request.getName())
                .subTitle(request.getSubTitle())
                .description(request.getDescription())
                .status(PfProject.ProjectStatus.valueOf(formattedStatus))
                .url(request.getUrl())
                .gitUrl(request.getGitUrl())
                .pdfUrl(request.getPdfUrl())
                .domain(request.getDomain())
                .teamSize(request.getTeamSize())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .submitContest(request.getSubmitContest())
                .build();

        // 2. 자식 리스트들 조립 (null이면 빈 리스트로 넘겨서 싹 다 지우도록 처리)
        List<PfProjectStack> newStacks = request.getStacks() == null ? Collections.emptyList() :
                request.getStacks().stream()
                .map(s -> PfProjectStack.builder().type(PfProjectStack.StackType.valueOf(s)).build())
                .collect(Collectors.toList());

        List<PfProjectTag> newTags = request.getTags() == null ? Collections.emptyList() :
                request.getTags().stream()
                .map(t -> PfProjectTag.builder().tag(PfProjectTag.TagType.valueOf(t)).build())
                .collect(Collectors.toList());

        List<PfProjectImage> newImages = request.getImages() == null ? Collections.emptyList() :
                request.getImages().stream()
                .map(img -> PfProjectImage.builder().imageUrl(img.getImageUrl()).isMain(img.getIsMain()).sortOrder(img.getSortOrder()).build())
                .collect(Collectors.toList());

        List<PfProjectDetail> newDetails = request.getDetails() == null ? Collections.emptyList() :
                request.getDetails().stream()
                .map(detail -> PfProjectDetail.builder()
                               .intention(detail.getIntention())
                               .planning(detail.getPlanning())
                               .stackDesc(detail.getStackDesc())
                               .teamRoles(detail.getTeamRoles())
                               .feel(detail.getFeel())
                               .build())
                .collect(Collectors.toList());

        // 3. 도메인 서비스 호출 (부모와 조립된 자식들을 통째로 넘김)
        projectDomainService.updateProject(projectId, updateInfo, newStacks, newTags, newImages, newDetails);

        // 4. 영속성 컨텍스트에 반영된 최신 데이터 다시 조회 후 반환
        PfProject updatedProject = projectDomainService.getProjectById(projectId);
        return convertToResponseDto(updatedProject);
    }

    @Transactional
    public void modifySpecificDetail(Long projectId, Long detailId, ProjectDetailDto.Request request) {
        PfProject project = projectDomainService.getProjectById(projectId);

        // 1. 부모가 가진 디테일 리스트 중에서, 내가 수정하고 싶은 ID를 가진 디테일을 찾는다!
        PfProjectDetail targetDetail = project.getDetails().stream()
                .filter(detail -> detail.getId().equals(detailId))
                .findFirst()
                .orElseThrow(() -> new BaseException(404, "해당 디테일 정보를 찾을 수 없습니다."));

        // 2. 찾은 디테일의 정보만 쏙 바꾼다. (더티 체킹으로 자동 UPDATE 쿼리 발생!)
        targetDetail.updateDetailInfo(
                request.getIntention(),
                request.getPlanning(),
                request.getStackDesc(),
                request.getTeamRoles(),
                request.getFeel()
        );
    }

    // get
    @Transactional(readOnly = true)
    public List<ProjectDto.Response> getAllProjects() {
        return projectDomainService.getAllProjects().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // delete
    @Transactional
    public void deleteProject(Long projectId) {
        projectDomainService.deleteProject(projectId);
    }

    // get - 단건
    @Transactional(readOnly = true)
    public ProjectDto.Response getProject(Long projectId) {

        PfProject project = projectDomainService.getProjectById(projectId);
        return convertToResponseDto(project);
    }


    /////////////////////////////////////
    ///  개별 API
    /////////////////////////////////////


    @Transactional
    public void addImageToProject(Long projectId, ProjectImageDto.Request request) {
        PfProject project = projectDomainService.getProjectById(projectId);
        project.addImage(PfProjectImage.builder()
                .imageUrl(request.getImageUrl())
                .isMain(request.getIsMain())
                .sortOrder(request.getSortOrder())
                .build());
    }

    @Transactional
    public void deleteImageFromProject(Long projectId, Long imageId) {
        PfProject project = projectDomainService.getProjectById(projectId);
        project.getImages().removeIf(img -> img.getId().equals(imageId));
    }

    @Transactional
    public void updateDetailOfProject(Long projectId, ProjectDetailDto.Request request) {
        PfProject project = projectDomainService.getProjectById(projectId);
        project.getDetails().clear(); // 기존 디테일 밀어버리기
        project.addDetail(PfProjectDetail.builder()
                .intention(request.getIntention())
                .planning(request.getPlanning())
                .stackDesc(request.getStackDesc())
                .teamRoles(request.getTeamRoles())
                .feel(request.getFeel())
                .build());
    }



    /////////////////////////////////////
    ///  공통 변환 메서드
    /////////////////////////////////////

    // 코드가 너무 길어지는 것을 방지하기 위해 Response 변환 로직을 따로 뺐습니다.
    private ProjectDto.Response convertToResponseDto(PfProject project) {
        return new ProjectDto.Response(
                project.getId(),
                project.getName(),
                project.getSubTitle(),
                project.getDescription(),
                project.getStatus().name(),
                project.getUrl(),
                project.getGitUrl(),
                project.getPdfUrl(),
                project.getDomain(),
                project.getTeamSize(),
                project.getStartDate(),
                project.getEndDate(),
                project.getSubmitContest(),

                // 스택, 태그 변환
                project.getStacks().stream().map(s -> s.getType().name()).collect(Collectors.toList()),
                project.getTags().stream().map(t -> t.getTag().name()).collect(Collectors.toList()),

                // 이미지 변환 (실제 저장된 데이터를 보내줍니다)
                project.getImages().stream()
                        .map(img -> new ProjectDto.ImageReq(img.getImageUrl(), img.getIsMain(), img.getSortOrder()))
                        .collect(Collectors.toList()),

                // 디테일 변환 (실제 저장된 데이터를 보내줍니다)
                project.getDetails().stream()
                        .map(detail -> new ProjectDto.DetailReq(
                                detail.getIntention(), detail.getPlanning(), detail.getStackDesc(), detail.getTeamRoles(), detail.getFeel()
                        ))
                        .collect(Collectors.toList())
        );
    }
}