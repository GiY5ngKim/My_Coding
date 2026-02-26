package com.catholic.moyeo.member.repository;


import com.catholic.moyeo.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;



public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByProviderAndProviderSub(String provider, String providerSub);

    Optional<Member> findByEmail(String email);
}