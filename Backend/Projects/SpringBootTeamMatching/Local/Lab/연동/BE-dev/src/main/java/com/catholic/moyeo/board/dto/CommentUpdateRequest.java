package com.catholic.moyeo.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 수정 요청
 *
 * - 수정 시에도 content만 받는다.
 */
@Getter
@NoArgsConstructor
public class CommentUpdateRequest {

    @NotBlank(message = "댓글 내용은 비어 있을 수 없습니다.")
    private String content;
}