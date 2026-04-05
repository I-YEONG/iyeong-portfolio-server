package com.iyeong.portfolio.domain.stack.repository;

import com.iyeong.portfolio.domain.stack.entity.PfStack; // (엔티티 위치에 맞게 import)
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<다룰 엔티티 타입, 엔티티의 PK(ID) 타입>
public interface PfStackRepository extends JpaRepository<PfStack, Long> {
    // 기본적인 CRUD(저장, 조회, 수정, 삭제) 기능이 이미 JpaRepository 안에 포함
    boolean existsByName(String name);
}