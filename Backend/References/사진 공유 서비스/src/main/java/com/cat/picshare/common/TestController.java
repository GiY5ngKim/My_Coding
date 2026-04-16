package com.cat.picshare.common; // common 패키지

import com.cat.picshare.common.response.ApiResponse; // 응답 래퍼 import
import com.cat.picshare.common.exception.CustomException; // 커스텀 예외
import com.cat.picshare.common.exception.ErrorCode; // 에러 코드
import org.springframework.web.bind.annotation.GetMapping; // GET 매핑
import org.springframework.web.bind.annotation.RequestMapping; // 공통 경로
import org.springframework.web.bind.annotation.RestController; // REST 컨트롤러

@RestController // JSON 응답 컨트롤러
@RequestMapping("/api/test") // 테스트 API 공통 경로
public class TestController { // 테스트 컨트롤러

    @GetMapping("/ok") // 정상 응답 테스트
    public ApiResponse<String> ok() { // 정상 응답 메서드
        return ApiResponse.ok("ok"); // 성공 응답 반환
    } // 메서드 종료

    @GetMapping("/custom") // CustomException 테스트
    public ApiResponse<Void> custom() { // 커스텀 예외 메서드
        throw new CustomException(ErrorCode.INVALID_REQUEST, "강제 예외 발생"); // 400용 예외 강제 발생
    } // 메서드 종료
} // 클래스 종료
