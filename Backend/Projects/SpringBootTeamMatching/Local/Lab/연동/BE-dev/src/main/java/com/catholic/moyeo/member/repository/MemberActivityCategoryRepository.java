package com.catholic.moyeo.member.repository;

import com.catholic.moyeo.member.domain.Member;
import com.catholic.moyeo.member.domain.MemberActivityCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberActivityCategoryRepository extends JpaRepository<MemberActivityCategory, Long> {
    List<MemberActivityCategory> findByMember(Member member);
    void deleteByMember(Member member);
}
