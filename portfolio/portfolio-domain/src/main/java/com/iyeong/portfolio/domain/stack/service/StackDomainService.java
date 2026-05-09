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
//        return stackRepository.findAll();
        return stackRepository.findAllByOrderBySortOrderAsc();
    }

    // 수정 (PUT)
    @Transactional
    public void updateStack(Long id, PfStack updateParam) {
        // 1. 기존 데이터가 있는지 찾아보고 없으면 에러!
        PfStack stack = stackRepository.findById(id)
                .orElseThrow(() -> new BaseException(404, "존재하지 않는 스택 번호입니다."));

        // 2. 이름을 바꿀 건데, 그 바꿀 이름이 이미 다른 곳에서 쓰이고 있다면 에러!
        if (!stack.getName().equals(updateParam.getName()) && stackRepository.existsByName(updateParam.getName())) {
            throw new BaseException(400, "이미 등록된 스택 이름입니다.");
        }

        // 3. 문제 없으면 엔티티 내용물 업데이트 (Dirty Checking으로 인해 자동 업데이트됨)
        stack.update(
                updateParam.getName(),
                updateParam.getCategory(),
                updateParam.getExpertise(),
                updateParam.getProficiency(),
                updateParam.getSortOrder()
        );
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