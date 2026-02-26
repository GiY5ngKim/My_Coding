package com.cat.picshare.common.exception; // 공통 예외 패키지
public class CustomException extends RuntimeException { // 비즈니스 예외 베이스
    private final ErrorCode errorCode; // 표준 에러 코드
    public CustomException(ErrorCode errorCode) { // 기본 생성자(기본 메시지)
        super(errorCode.getMessage()); // RuntimeException 메시지 세팅
        this.errorCode = errorCode; // 에러 코드 저장
    } // 생성자 종료
    public CustomException(ErrorCode errorCode, String message) { // 커스텀 메시지
        super(message); // RuntimeException 메시지 세팅
        this.errorCode = errorCode; // 에러 코드 저장
    } // 생성자 종료
    public ErrorCode getErrorCode() { return errorCode; } // 에러 코드 getter
} // 클래스 종료