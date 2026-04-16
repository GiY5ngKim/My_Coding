package com.catholic.moyeo.board.service;

import com.catholic.moyeo.board.dto.BoardAuthorResponse;
import com.catholic.moyeo.member.domain.Member;
import com.catholic.moyeo.member.repository.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * MemberRepository 기반 BoardAuthorReader 구현체
 *
 * 역할:
 * - Board 모듈에서 memberId(userId)를 기반으로 Member 정보를 조회한다.
 * - 조회된 Member 정보를 Board API 응답용 DTO로 변환한다.
 *
 * 주의:
 * - 현재 게시판 명세에서는 nickname만 필요하므로 memberId, nickname만 반환한다.
 */
@Component
@Transactional(readOnly = true)
public class MemberBoardAuthorReader implements BoardAuthorReader {

    private final MemberRepository memberRepository;

    public MemberBoardAuthorReader(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 단일 작성자 조회
     *
     * 작성자 정보가 없으면 잘못된 데이터 상태이므로 예외를 던진다.
     */
    @Override
    public BoardAuthorResponse getAuthor(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + userId));

        return new BoardAuthorResponse(
                member.getId(),
                member.getNickname()
        );
    }

    /**
     * 여러 작성자 일괄 조회
     *
     * 현재 MemberRepository에 findAllById가 있으므로 이를 활용해
     * userId -> BoardAuthorResponse map을 만든다.
     */
    @Override
    public Map<Long, BoardAuthorResponse> getAuthors(Collection<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Long, BoardAuthorResponse> result = new LinkedHashMap<>();

        Iterable<Member> members = memberRepository.findAllById(userIds);
        for (Member member : members) {
            result.put(
                    member.getId(),
                    new BoardAuthorResponse(
                            member.getId(),
                            member.getNickname()
                    )
            );
        }

        return result;
    }
}