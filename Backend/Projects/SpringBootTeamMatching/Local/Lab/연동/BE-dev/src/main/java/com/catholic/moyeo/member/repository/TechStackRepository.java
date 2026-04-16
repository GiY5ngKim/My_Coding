package com.catholic.moyeo.member.repository;

import com.catholic.moyeo.member.domain.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {

    Optional<TechStack> findByName(String name);

}