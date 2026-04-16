package com.catholic.moyeo.board.dto;

/**
 * Board 모듈 공통 에러 응답
 */
public class ErrorResponse {

    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public static ErrorResponse of(String message) {
        return new ErrorResponse(message);
    }

    public String getMessage() { return message; }
}