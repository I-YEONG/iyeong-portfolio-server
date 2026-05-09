package com.iyeong.portfolio.domain.project.repository;

import com.iyeong.portfolio.domain.project.entity.PfProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PfProjectRepository extends JpaRepository<PfProject, Long> {
    
    // 기본적인 save, findById, deleteById, findAll 등은 이미 JpaRepository 안에 다 들어있습니다!
    
    // 만약 "프로젝트 이름이 중복되는지" 검사하고 싶다면 아래처럼 한 줄만 추가해 두면 됩니다.
    boolean existsByName(String name);
}