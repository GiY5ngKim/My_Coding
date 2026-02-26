package com.cat.picshare.common.exception; // 예외 테스트 패키지(임시)
import com.cat.picshare.common.response.ApiResponse; // 공통 응답 래퍼
import org.springframework.web.bind.annotation.GetMapping; // GET 매핑
import org.springframework.web.bind.annotation.RequestMapping; // 공통 경로 매핑
import org.springframework.web.bind.annotation.RestController; // REST 컨트롤러
@RestController // JSON 응답 컨트롤러
@RequestMapping("/api/test/exception") // 테스트 경로 프리픽스
public class ExceptionTestController { // 예외 테스트 컨트롤러
    @GetMapping("/ok") // 정상 응답 테스트
    public ApiResponse<String> ok() { // ok 엔드포인트
        return ApiResponse.ok("ok"); // 성공 응답 반환
    } // 메서드 종료
    @GetMapping("/custom") // CustomException 테스트
    public ApiResponse<Void> custom() { // custom 엔드포인트
        throw new CustomException(ErrorCode.INVALID_REQUEST, "강제 예외 발생"); // 강제 예외
    } // 메서드 종료
} // 클래스 종료