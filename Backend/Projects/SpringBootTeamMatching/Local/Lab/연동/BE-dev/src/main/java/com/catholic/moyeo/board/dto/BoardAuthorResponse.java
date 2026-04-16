package com.catholic.moyeo.board.dto;

/**
 * 게시글 작성자 정보 응답
 *
 * 현재 Board 명세 기준:
 * - memberId
 * - nickname
 *
 * 참고:
 * - 예전 설계에서는 departmentName까지 포함하려고 했지만,
 *   현재 게시판 명세에서는 author에 memberId, nickname만 필요하다.
 */
public class BoardAuthorResponse {

    private final Long memberId;
    private final String nickname;

    public BoardAuthorResponse(Long memberId, String nickname) {
        this.memberId = memberId;
        this.nickname = nickname;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getNickname() {
        return nickname;
    }
}