package com.iyeong.portfolio.domain.project.repository;

import com.iyeong.portfolio.domain.project.entity.PfProjectTag;
import com.iyeong.portfolio.domain.project.entity.PfProjectTag.TagType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PfProjectTagRepository extends JpaRepository<PfProjectTag, Long> {
    
    // 기본적인 save, findById, deleteById, findAll 등은 이미 JpaRepository 안에 다 들어있습니다!
    
    // tag 값이 "STORE"인 것의 개수를 셉니다.
    long countByTag(PfProjectTag.TagType tag);
}