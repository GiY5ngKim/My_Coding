package com.catholic.moyeo.member.repository;

import com.catholic.moyeo.member.domain.Member;
import com.catholic.moyeo.member.domain.MemberTechStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberTechStackRepository extends JpaRepository<MemberTechStack, Long> {

    List<MemberTechStack> findByMember(Member member);

    void deleteByMember(Member member);
    List<MemberTechStack> findByMemberIdIn(List<Long> memberIds); //여러 회원 기술스택 한 번에 조회
}