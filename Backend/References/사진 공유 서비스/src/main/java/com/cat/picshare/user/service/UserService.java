package com.cat.picshare.user.service; // 사용자 서비스 패키지

import com.cat.picshare.common.exception.CustomException; // 커스텀 예외
import com.cat.picshare.common.exception.ErrorCode; // 에러 코드
import com.cat.picshare.user.dto.UserMeResponse; // 응답 DTO
import com.cat.picshare.user.entity.User; // 사용자 엔티티
import com.cat.picshare.user.repository.UserRepository; // 레포지토리
import org.springframework.stereotype.Service; // 서비스 어노테이션
import org.springframework.transaction.annotation.Transactional; // 트랜잭션
import java.util.Optional; // Optional

@Service // 서비스 등록
@Transactional(readOnly = true) // 조회 트랜잭션
public class UserService { // 사용자 서비스

    private final UserRepository userRepository; // 사용자 레포지토리

    public UserService(UserRepository userRepository) { // 생성자 주입
        this.userRepository = userRepository; // 주입 저장
    }

    public UserMeResponse getMeByEmail(String email) { // 이메일 기준 내 정보 조회
        Optional<User> userOpt = userRepository.findByEmail(email); // 사용자 조회
        User user = userOpt.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "사용자를 찾을 수 없습니다")); // 없으면 404
        return new UserMeResponse(user.getId(), user.getEmail()); // DTO 반환
    }
} // 클래스 종료
