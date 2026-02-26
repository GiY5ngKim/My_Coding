package com.cat.picshare.common.response;                  // 공통 응답 패키지

import com.fasterxml.jackson.annotation.JsonInclude;       // JSON 직렬화 설정

@JsonInclude(JsonInclude.Include.NON_NULL)                 // null 필드 JSON 제외
public class ApiResponse<T> {                               // 공통 응답 래퍼
    private final boolean success;                          // 성공 여부
    private final T data;                                   // 성공 데이터
    private final ApiError error;                           // 실패 에러

    private ApiResponse(boolean success, T data, ApiError error) { // 생성자
        this.success = success;                              // 성공 여부 설정
        this.data = data;                                    // 데이터 설정
        this.error = error;                                  // 에러 설정
    }

    public static <T> ApiResponse<T> ok(T data) {            // 성공 응답(데이터)
        return new ApiResponse<>(true, data, null);          // success=true
    }

    public static ApiResponse<Void> ok() {                   // 성공 응답(데이터 없음)
        return new ApiResponse<>(true, null, null);          // 빈 성공 응답
    }

    public static ApiResponse<Void> fail(ApiError error) {   // 실패 응답
        return new ApiResponse<>(false, null, error);        // success=false
    }

    public boolean isSuccess() { return success; }           // success getter
    public T getData() { return data; }                      // data getter
    public ApiError getError() { return error; }             // error getter
}