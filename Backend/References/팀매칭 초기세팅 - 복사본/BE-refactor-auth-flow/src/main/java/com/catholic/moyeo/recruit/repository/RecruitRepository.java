package com.catholic.moyeo.recruit.repository;

import com.catholic.moyeo.recruit.domain.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
}
