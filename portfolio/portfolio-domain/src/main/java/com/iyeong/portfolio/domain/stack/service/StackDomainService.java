package com.iyeong.portfolio.domain.stack.service;

import com.iyeong.common.core.exception.BaseException;
import com.iyeong.portfolio.domain.stack.entity.PfStack;
import com.iyeong.portfolio.domain.stack.repository.PfStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StackDomainService {

    private final PfStackRepository stackRepository;

    @Transactional
    public PfStack saveStack(PfStack pfStack) {
        // [규칙 1] 이름이 중복되는 스택은 저장할 수 없다 (예시)
        if (stackRepository.existsByName(pfStack.getName())) {
            throw new BaseException(400, "이미 등록된 스택 이름입니다.");
        }
        
        return stackRepository.save(pfStack);
    }

    public List<PfStack> getAllStacks() {
        return stackRepository.findAll();
    }

    // 삭제
    @Transactional
    public void deleteStack(Long stackId) {
        // [규칙] 지우기 전에 데이터가 있는지 확인한다!
        if (!stackRepository.existsById(stackId)) {
            throw new BaseException(404, "존재하지 않는 스택 번호입니다.");
        }
        stackRepository.deleteById(stackId);
    }
}