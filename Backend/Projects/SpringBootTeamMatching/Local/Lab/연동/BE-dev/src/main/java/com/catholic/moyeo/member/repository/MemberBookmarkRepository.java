package com.catholic.moyeo.member.repository;
import com.catholic.moyeo.member.domain.Member;
import com.catholic.moyeo.member.domain.MemberBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberBookmarkRepository extends JpaRepository<MemberBookmark, Long> {

    boolean existsByUserAndTarget(Member user, Member target);

    void deleteByUserAndTarget(Member user, Member target);

    List<MemberBookmark> findByUser(Member user);
}