package com.cat.picshare.user.repository; // 사용자 레포지토리 패키지

import com.cat.picshare.user.entity.User; // 사용자 엔티티
import org.springframework.data.jpa.repository.JpaRepository; // JPA 레포지토리
import java.util.Optional; // Optional 타입

public interface UserRepository extends JpaRepository<User, Long> { // 사용자 레포지토리
    Optional<User> findByEmail(String email); // 이메일로 조회
    boolean existsByEmail(String email); // 이메일 중복 체크
} // 인터페이스 종료