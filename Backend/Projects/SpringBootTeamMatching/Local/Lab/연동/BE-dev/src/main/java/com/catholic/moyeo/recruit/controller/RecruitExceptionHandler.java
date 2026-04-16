package com.catholic.moyeo.recruit.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Recruit 전용 예외 핸들러
 *
 * 정책:
 * - 잘못된 요청 / 행위 불가 -> 400
 * - 권한 없음 -> 403
 * - 서버 오류 -> 500
 *
 * NOTE
 * - 인증 실패(401)는 Security 레이어에서 처리한다.
 * - Validation 실패는 message + errors 구조
 * - 일반 정책 위반은 message 단일 필드
 *
 * 카테고리 2단계 정책:
 * - ActivityCategory(1차 필터), RecruitCategory(2차 필터) 검증 실패는
 *   서비스에서 IllegalArgumentException으로 처리하고 여기서 400으로 응답한다.
 */
@RestControllerAdvice(basePackages = "com.catholic.moyeo.recruit")
public class RecruitExceptionHandler {

    /**
     * 잘못된 값 / 정책 위반 -> 400
     *
     * 예:
     * - 허용되지 않은 activityCategory
     * - 허용되지 않은 recruitCategory
     * - status 잘못된 값
     * - totalHeadcount 정책 위반
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> badRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", e.getMessage()));
    }

    /**
     * 현재 상태에서 허용되지 않는 행위 -> 400
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> invalidState(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", e.getMessage()));
    }

    /**
     * Bean Validation 실패 (Body DTO)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validationError(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "message", "Validation failed",
                        "errors", errors
                ));
    }

    /**
     * Query param validation 실패
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> constraintViolation(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", e.getMessage()));
    }

    /**
     * 권한 없음
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> forbidden(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("message", e.getMessage()));
    }

    /**
     * DB 무결성 오류
     *
     * 예:
     * - unique constraint
     * - FK 오류
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> dataIntegrity(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Database constraint violation"));
    }

    /**
     * 예상하지 못한 서버 오류
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> serverError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Internal server error"));
    }
}