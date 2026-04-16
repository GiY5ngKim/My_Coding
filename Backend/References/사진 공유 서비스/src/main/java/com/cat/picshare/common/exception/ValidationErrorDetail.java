package com.cat.picshare.common.exception; // 공통 예외 패키지
public class ValidationErrorDetail { // 검증 오류 상세 DTO
    private final String field; // 오류 필드명
    private final String message; // 오류 메시지
    public ValidationErrorDetail(String field, String message) { // 생성자
        this.field = field; // field 저장
        this.message = message; // message 저장
    } // 생성자 종료
    public String getField() { return field; } // field getter
    public String getMessage() { return message; } // message getter
} // 클래스 종료