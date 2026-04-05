package com.iyeong.portfolio.domain.certification.repository;

import com.iyeong.portfolio.domain.certification.entity.PfCertification; // (엔티티 위치에 맞게 import)
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<다룰 엔티티 타입, 엔티티의 PK(ID) 타입>
public interface PfCertificationRepository extends JpaRepository<PfCertification, Long> {
    // 기본적인 CRUD(저장, 조회, 수정, 삭제) 기능이 이미 JpaRepository 안에 포함
    boolean existsByName(String name);
}