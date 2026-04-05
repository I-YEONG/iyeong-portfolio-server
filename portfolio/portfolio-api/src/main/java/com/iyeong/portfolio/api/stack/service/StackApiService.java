package com.iyeong.portfolio.api.stack.service;

import com.iyeong.portfolio.api.stack.dto.StackDto;
import com.iyeong.portfolio.domain.stack.entity.PfStack;
import com.iyeong.portfolio.domain.stack.service.StackDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StackApiService {

    private final StackDomainService stackDomainService;

    // post
    public StackDto.Response createStack(StackDto.Request request) {
        PfStack pfStack = PfStack.builder()
                .name(request.getName())
                .category(PfStack.CategoryList.valueOf(request.getCategory()))
                .expertise(PfStack.ExpertiseList.valueOf(request.getExpertise()))
                .proficiency(request.getProficiency())
                .build();

        PfStack savedStack =  stackDomainService.saveStack(pfStack);

        return new StackDto.Response(
                savedStack.getId(), // 이제 null이 아니라 DB에서 준 숫자(예: 1, 2, 3...)가 들어있습니다!
                savedStack.getName(),
                savedStack.getCategory().name(),
                savedStack.getExpertise().name(),
                savedStack.getProficiency()
        );
    }

    // get
    public List<StackDto.Response> getAllStacks() {
        // 도메인 서비스에서 전체 스택(엔티티)을 가져옵니다.
        List<PfStack> stackList = stackDomainService.getAllStacks();

        // 엔티티를 프론트엔드가 요구하는 DTO 형태로 변환해서 돌려줍니다.
        return stackList.stream()
                .map(stack -> new StackDto.Response(
                        stack.getId(),
                        stack.getName(),
                        stack.getCategory().name(),
                        stack.getExpertise().name(),
                        stack.getProficiency()
                ))
                .collect(Collectors.toList());
    }

    // delete
    public void deleteStack(Long stackId) {
        stackDomainService.deleteStack(stackId);
    }
}