package com.iyeong.portfolio.domain.project.service;

import com.iyeong.common.core.exception.BaseException;
import com.iyeong.portfolio.domain.project.entity.*;
import com.iyeong.portfolio.domain.project.repository.PfProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectDomainService {

    private final PfProjectRepository projectRepository;

    // 1. 저장 (부모를 저장하면 Cascade.ALL 덕분에 자식들도 다 같이 저장됩니다)
    @Transactional
    public PfProject saveProject(PfProject project) {
        return projectRepository.save(project);
    }

    // 2. 전체 조회
    public List<PfProject> getAllProjects() {
        return projectRepository.findAll();
    }

    // 3. 단건 조회 (주로 업데이트할 때 원본 데이터를 찾기 위해 사용)
    public PfProject getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new BaseException(404, "존재하지 않는 프로젝트입니다."));
    }

    // 4. 삭제
    @Transactional
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new BaseException(404, "존재하지 않는 프로젝트입니다.");
        }
        projectRepository.deleteById(id);
    }

    // 🌟 5. 업데이트 (대망의 핵심 로직!)
    @Transactional
    public void updateProject(Long id, PfProject updateData, 
                              List<PfProjectStack> newStacks, 
                              List<PfProjectTag> newTags, 
                              List<PfProjectImage> newImages, 
                              List<PfProjectDetail> newDetails) {
        
        // 1) DB에서 수정할 원본 프로젝트(부모)를 불러옵니다.
        PfProject origin = getProjectById(id);

        // 2) 부모의 기본 정보(텍스트, 날짜 등)를 덮어씌웁니다.
        origin.updateInfo(
                updateData.getName(),
                updateData.getSubTitle(),
                updateData.getDescription(),
                updateData.getStatus(),
                updateData.getUrl(),
                updateData.getGitUrl(),
                updateData.getPdfUrl(),
                updateData.getDomain(),
                updateData.getTeamSize(),
                updateData.getStartDate(),
                updateData.getEndDate(),
                updateData.getSubmitContest()
        );

        // 3) 자식 데이터들을 통째로 갈아 끼웁니다. 
        // (엔티티 내부에서 clear() -> add() 과정을 거치며 고아 객체는 삭제되고 새 객체가 들어갑니다)
        origin.updateStacks(newStacks);
        origin.updateTags(newTags);
        origin.updateImages(newImages);

        // 4) 디테일(Detail)은 선택값이므로, null 체크를 해줍니다.
        if (newDetails != null) {
            origin.updateDetails(newDetails);
        } else {
            // 프론트엔드에서 디테일을 안 보냈다면, 기존 디테일을 싹 비워줍니다.
            origin.updateDetails(Collections.emptyList()); 
        }

        // 🌟 @Transactional이 걸려있으므로, 메서드가 끝나는 이 시점에 
        // 변경된 부모 정보(UPDATE)와 교체된 자식들(DELETE & INSERT) 쿼리가 한 방에 쫙 날아갑니다!
    }
}