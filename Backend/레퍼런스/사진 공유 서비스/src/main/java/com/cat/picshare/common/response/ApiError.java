package com.cat.picshare.common.response;                  // 공통 응답 패키지

import com.fasterxml.jackson.annotation.JsonInclude;       // JSON 직렬화 설정

@JsonInclude(JsonInclude.Include.NON_NULL)                 // null 필드 JSON 제외
public class ApiError {                                    // 에러 응답 객체
    private final String code;                              // 에러 코드
    private final String message;                           // 에러 메시지
    private final Object details;                           // 추가 정보

    public ApiError(String code, String message, Object details) { // 전체 필드 생성자
        this.code = code;                                   // 코드 초기화
        this.message = message;                             // 메시지 초기화
        this.details = details;                             // 상세 정보 초기화
    }

    public static ApiError of(String code, String message) { // details 없는 생성
        return new ApiError(code, message, null);           // details=null
    }

    public static ApiError of(String code, String message, Object details) { // details 포함 생성
        return new ApiError(code, message, details);        // 전체 필드 설정
    }

    public String getCode() { return code; }                // code getter
    public String getMessage() { return message; }          // message getter
    public Object getDetails() { return details; }          // details getter
}