package com.catholic.moyeo.board.service;

import com.catholic.moyeo.board.domain.BoardComment;
import com.catholic.moyeo.board.dto.BoardAuthorResponse;
import com.catholic.moyeo.board.dto.BoardCommentResponse;
import com.catholic.moyeo.board.repository.BoardCommentRepository;
import com.catholic.moyeo.board.repository.BoardPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BoardCommentService {

    private final BoardCommentRepository boardCommentRepository;
    private final BoardPostRepository boardPostRepository;
    private final BoardAuthorReader boardAuthorReader;

    /**
     * 댓글 작성
     *
     * - 게시글 존재 여부를 먼저 확인한다.
     * - 내용은 공백만 들어올 수 없다.
     * - 작성 후 댓글 정보를 반환한다.
     */
    @Transactional
    public BoardCommentResponse create(Long userId, Long postId, String content) {

        boardPostRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));

        if (content == null || content.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글 내용은 비어 있을 수 없습니다.");
        }

        BoardComment saved = boardCommentRepository.save(
                new BoardComment(postId, userId, content.trim())
        );

        BoardAuthorResponse author = boardAuthorReader.getAuthor(saved.getUserId());

        return BoardCommentResponse.from(
                saved.getId(),
                saved.getContent(),
                author,
                true,
                saved.getCreatedAt()
        );
    }

    /**
     * 댓글 목록 조회
     *
     * 구조:
     * - 대댓글 없이 평면 댓글 목록으로 반환한다.
     * - 최신 댓글부터 순서대로 내려준다.
     *
     * 최적화:
     * - 작성자 정보는 댓글마다 따로 조회하지 않고 한 번에 모아서 조회한다.
     */
    @Transactional(readOnly = true)
    public List<BoardCommentResponse> getComments(Long postId, Long me) {

        List<BoardComment> comments =
                boardCommentRepository.findByBoardPostIdOrderByCreatedAtDescIdDesc(postId);

        Set<Long> userIds = new HashSet<>();
        for (BoardComment c : comments) {
            userIds.add(c.getUserId());
        }

        Map<Long, BoardAuthorResponse> authors =
                boardAuthorReader.getAuthors(userIds);

        return comments.stream()
                .map(c -> BoardCommentResponse.from(
                        c.getId(),
                        c.getContent(),
                        authors.getOrDefault(
                                c.getUserId(),
                                new BoardAuthorResponse(c.getUserId(), null)
                        ),
                        me != null && c.getUserId().equals(me),
                        c.getCreatedAt()
                ))
                .toList();
    }

    /**
     * 댓글 삭제
     *
     * - 본인 댓글만 삭제 가능하다.
     * - 평면 댓글 구조이므로 물리삭제한다.
     */
    @Transactional
    public void delete(Long userId, Long commentId) {

        BoardComment comment = boardCommentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));

        if (userId == null || !comment.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인 댓글만 삭제할 수 있습니다.");
        }

        boardCommentRepository.delete(comment);
    }

    /**
     * 댓글 수정
     *
     * - 본인 댓글만 수정 가능하다.
     * - 내용은 공백만 들어올 수 없다.
     * - 수정 후 댓글 정보를 반환한다.
     */
    @Transactional
    public BoardCommentResponse update(Long userId, Long commentId, String content) {

        BoardComment comment = boardCommentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));

        if (userId == null || !comment.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인 댓글만 수정할 수 있습니다.");
        }

        if (content == null || content.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글 내용은 비어 있을 수 없습니다.");
        }

        comment.updateContent(content.trim());

        BoardAuthorResponse author = boardAuthorReader.getAuthor(comment.getUserId());

        return BoardCommentResponse.from(
                comment.getId(),
                comment.getContent(),
                author,
                true,
                comment.getCreatedAt()
        );
    }
}