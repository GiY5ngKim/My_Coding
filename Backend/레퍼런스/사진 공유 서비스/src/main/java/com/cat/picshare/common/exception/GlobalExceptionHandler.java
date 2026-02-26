package com.cat.picshare.common.exception; // 공통 예외 패키지
import com.cat.picshare.common.response.ApiError; // 에러 응답 DTO
import com.cat.picshare.common.response.ApiResponse; // 공통 응답 래퍼
import org.slf4j.Logger; // 로거 인터페이스
import org.slf4j.LoggerFactory; // 로거 생성기
import org.springframework.http.ResponseEntity; // HTTP 응답 래퍼
import org.springframework.validation.FieldError; // 검증 필드 에러 타입
import org.springframework.web.bind.MethodArgumentNotValidException; // @Valid 예외 타입
import org.springframework.web.bind.annotation.ExceptionHandler; // 예외 핸들러 어노테이션
import org.springframework.web.bind.annotation.RestControllerAdvice; // 전역 컨트롤러 어드바이스
import java.util.ArrayList; // 리스트 구현체
import java.util.List; // 리스트 인터페이스
@RestControllerAdvice // 전역 예외 처리 등록(JSON 응답)
public class GlobalExceptionHandler { // 예외 처리 클래스
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class); // 로거 생성
    @ExceptionHandler(CustomException.class) // 비즈니스 예외 처리
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) { // CustomException 핸들러
        ErrorCode ec = e.getErrorCode(); // 에러 코드 추출
        ApiError error = ApiError.of(ec.getCode(), e.getMessage()); // 에러 객체 생성
        return ResponseEntity.status(ec.getHttpStatus()).body(ApiResponse.fail(error)); // 상태코드+바디 반환
    } // 메서드 종료
    @ExceptionHandler(MethodArgumentNotValidException.class) // DTO 검증 실패 처리
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException e) { // Validation 핸들러
        ErrorCode ec = ErrorCode.VALIDATION_ERROR; // 검증용 표준 코드
        List<ValidationErrorDetail> details = new ArrayList<>(); // details 리스트 생성
        for (FieldError fe : e.getBindingResult().getFieldErrors()) { // 필드 에러 순회
            details.add(new ValidationErrorDetail(fe.getField(), fe.getDefaultMessage())); // 상세 추가
        } // 루프 종료
        ApiError error = ApiError.of(ec.getCode(), ec.getMessage(), details); // details 포함 에러 생성
        return ResponseEntity.status(ec.getHttpStatus()).body(ApiResponse.fail(error)); // 400 응답
    } // 메서드 종료
    @ExceptionHandler(Exception.class) // 예상 못한 모든 예외 처리
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) { // 최종 fallback
        log.error("Unhandled exception", e); // 서버 로그에 스택트레이스 기록
        ErrorCode ec = ErrorCode.INTERNAL_SERVER_ERROR; // 500 표준 코드
        ApiError error = ApiError.of(ec.getCode(), ec.getMessage()); // 에러 객체 생성
        return ResponseEntity.status(ec.getHttpStatus()).body(ApiResponse.fail(error)); // 500 응답
    } // 메서드 종료
} // 클래스 종료