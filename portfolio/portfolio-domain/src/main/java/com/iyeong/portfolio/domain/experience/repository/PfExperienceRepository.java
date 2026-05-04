package com.iyeong.portfolio.domain.experience.repository;

import com.iyeong.portfolio.domain.experience.entity.PfExperience;
import com.iyeong.portfolio.domain.experience.entity.PfExperience.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<다룰 엔티티 타입, 엔티티의 PK(ID) 타입>
public interface PfExperienceRepository extends JpaRepository<PfExperience, Long> {
    // 기본적인 CRUD(저장, 조회, 수정, 삭제) 기능이 이미 JpaRepository 안에 포함
    boolean existsByTitle(String title);

    // 🌟 수정 완료: Type -> Types
    // types 리스트 안에 해당 ActivityType이 포함되어 있는지 개수를 셉니다.
    long countByTypes(ActivityType type);
}