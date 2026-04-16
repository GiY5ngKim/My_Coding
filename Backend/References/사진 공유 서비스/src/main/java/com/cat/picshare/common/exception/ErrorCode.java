package com.cat.picshare.common.exception; // 공통 예외 패키지
import org.springframework.http.HttpStatus; // HTTP 상태코드 타입
public enum ErrorCode { // 에러 코드 열거형
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C000", "서버 내부 오류"), // 500 기본
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "C001", "잘못된 요청"), // 400 기본
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "C002", "인증이 필요합니다"), // 401 기본
    FORBIDDEN(HttpStatus.FORBIDDEN, "C003", "권한이 없습니다"), // 403 기본
    NOT_FOUND(HttpStatus.NOT_FOUND, "C004", "대상을 찾을 수 없습니다"), // 404 기본
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "C005", "요청 값 검증 실패"); // 400 검증
    private final HttpStatus httpStatus; // 반환할 HTTP 상태코드
    private final String code; // 클라이언트 분기용 코드
    private final String message; // 기본 메시지
    ErrorCode(HttpStatus httpStatus, String code, String message) { // 생성자
        this.httpStatus = httpStatus; // 상태코드 저장
        this.code = code; // 코드 저장
        this.message = message; // 메시지 저장
    } // 생성자 종료
    public HttpStatus getHttpStatus() { return httpStatus; } // 상태코드 getter
    public String getCode() { return code; } // 코드 getter
    public String getMessage() { return message; } // 메시지 getter
} // enum 종료