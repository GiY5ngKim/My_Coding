package com.catholic.moyeo.board.service;

import com.catholic.moyeo.board.dto.BoardAuthorResponse;

import java.util.Collection;
import java.util.Map;

/**
 * 작성자 프로필 조회 추상화
 *
 * board가 member 패키지 내부 구현에 직접 의존하지 않도록 분리했다.
 */
public interface BoardAuthorReader {

    BoardAuthorResponse getAuthor(Long userId);

    Map<Long, BoardAuthorResponse> getAuthors(Collection<Long> userIds);
}