package com.catholic.moyeo.board.service;

import com.catholic.moyeo.board.domain.BoardPost;
import com.catholic.moyeo.board.domain.BoardPostBookmark;
import com.catholic.moyeo.board.domain.BoardPostImage;
import com.catholic.moyeo.board.dto.BoardAuthorResponse;
import com.catholic.moyeo.board.dto.BoardCreateRequest;
import com.catholic.moyeo.board.dto.BoardDetailResponse;
import com.catholic.moyeo.board.dto.BoardListResponse;
import com.catholic.moyeo.board.dto.BoardPostBookmarkResponse;
import com.catholic.moyeo.board.dto.BoardSummaryResponse;
import com.catholic.moyeo.board.dto.BoardUpdateRequest;
import com.catholic.moyeo.board.dto.PageInfoResponse;
import com.catholic.moyeo.board.repository.BoardCommentRepository;
import com.catholic.moyeo.board.repository.BoardPostBookmarkRepository;
import com.catholic.moyeo.board.repository.BoardPostImageRepository;
import com.catholic.moyeo.board.repository.BoardPostLikeRepository;
import com.catholic.moyeo.board.repository.BoardPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 게시글 서비스
 *
 * 정책:
 * - 작성자는 현재 로그인 사용자
 * - 수정/삭제는 작성자만 가능
 * - 목록/내 목록/북마크 목록/좋아요 목록은 같은 summary DTO를 사용한다.
 *
 * 참고:
 * - 현재 게시판은 category 없이 title + content만 사용한다.
 */
@Service
public class BoardService {

    private final BoardPostRepository boardPostRepository;
    private final BoardAuthorReader boardAuthorReader;
    private final BoardPostLikeRepository boardPostLikeRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final BoardPostImageRepository boardPostImageRepository;
    private final BoardPostBookmarkRepository boardPostBookmarkRepository;

    public BoardService(
            BoardPostRepository boardPostRepository,
            BoardAuthorReader boardAuthorReader,
            BoardPostLikeRepository boardPostLikeRepository,
            BoardCommentRepository boardCommentRepository,
            BoardPostImageRepository boardPostImageRepository,
            BoardPostBookmarkRepository boardPostBookmarkRepository
    ) {
        this.boardPostRepository = boardPostRepository;
        this.boardAuthorReader = boardAuthorReader;
        this.boardPostLikeRepository = boardPostLikeRepository;
        this.boardCommentRepository = boardCommentRepository;
        this.boardPostImageRepository = boardPostImageRepository;
        this.boardPostBookmarkRepository = boardPostBookmarkRepository;
    }

    /**
     * 게시글 목록 조회
     *
     * 정책:
     * - keyword가 null/blank면 전체 게시글 조회
     * - keyword가 있으면 제목/본문 검색
     * - 정렬이 비어 있으면 createdAt desc 기본 정렬 적용
     *
     * 이유:
     * - JPQL에서 ":keyword is null" 과 LIKE 검색을 한 쿼리에 같이 넣으면
     *   PostgreSQL에서 파라미터 타입 추론 문제가 발생할 수 있으므로
     *   Service에서 미리 분기한다.
     */
    @Transactional(readOnly = true)
    public BoardListResponse listPosts(String keyword, Pageable pageable, Long me) {

        String normalizedKeyword = normalizeKeyword(keyword);
        Pageable normalizedPageable = normalizePageable(pageable);

        Page<BoardPost> page;
        if (normalizedKeyword == null) {
            page = boardPostRepository.findAll(normalizedPageable);
        } else {
            page = boardPostRepository.searchByKeyword(normalizedKeyword, normalizedPageable);
        }

        List<BoardSummaryResponse> posts = toSummaryResponses(page.getContent(), me);

        return BoardListResponse.of(posts, PageInfoResponse.from(page));
    }

    /**
     * 내가 북마크한 게시글 목록 조회
     *
     * - Bookmark 테이블 기준으로 조회
     * - 정렬이 비어 있으면 createdAt desc 기본 정렬 적용
     */
    @Transactional(readOnly = true)
    public BoardListResponse listBookmarkedPosts(Long me, Pageable pageable) {

        Pageable normalizedPageable = normalizePageable(pageable);

        Page<BoardPost> page = boardPostRepository.findBookmarkedPostsByUserId(me, normalizedPageable);

        List<BoardSummaryResponse> posts = toSummaryResponses(page.getContent(), me);

        return BoardListResponse.of(posts, PageInfoResponse.from(page));
    }

    /**
     * 내가 좋아요한 게시글 목록 조회
     *
     * - Like 테이블 기준으로 조회
     * - 정렬이 비어 있으면 createdAt desc 기본 정렬 적용
     */
    @Transactional(readOnly = true)
    public BoardListResponse listLikedPosts(Long me, Pageable pageable) {

        Pageable normalizedPageable = normalizePageable(pageable);

        Page<BoardPost> page = boardPostRepository.findLikedPostsByUserId(me, normalizedPageable);

        List<BoardSummaryResponse> posts = toSummaryResponses(page.getContent(), me);

        return BoardListResponse.of(posts, PageInfoResponse.from(page));
    }

    /**
     * 게시글 상세 조회
     *
     * - mine: 내가 작성한 글인지 여부
     * - likedByMe: 내가 좋아요 눌렀는지
     * - bookmarkedByMe: 내가 북마크 했는지
     */
    @Transactional(readOnly = true)
    public BoardDetailResponse getPost(Long postId, Long me) {

        BoardPost post = getPostEntity(postId);

        BoardAuthorResponse author = boardAuthorReader.getAuthor(post.getAuthorUserId());

        long likeCount = boardPostLikeRepository.countByBoardPostId(post.getId());
        long commentCount = boardCommentRepository.countByBoardPostId(post.getId());

        boolean likedByMe = (me != null) &&
                boardPostLikeRepository.existsByBoardPostIdAndUserId(post.getId(), me);

        boolean bookmarkedByMe = (me != null) &&
                boardPostBookmarkRepository.existsByBoardPostIdAndUserId(post.getId(), me);

        List<String> images = getImages(post.getId());

        return BoardDetailResponse.from(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                author,
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getAuthorUserId().equals(me),
                likeCount,
                commentCount,
                likedByMe,
                bookmarkedByMe,
                images
        );
    }

    /**
     * 게시글 작성
     *
     * - 제목/내용 trim 처리
     * - 이미지가 있으면 함께 저장
     */
    @Transactional
    public BoardDetailResponse createPost(Long me, BoardCreateRequest request) {

        BoardPost saved = boardPostRepository.save(
                new BoardPost(
                        me,
                        request.getTitle().trim(),
                        request.getContent().trim()
                )
        );

        if (request.getImages() != null) {
            for (String url : request.getImages()) {
                boardPostImageRepository.save(
                        new BoardPostImage(saved.getId(), url)
                );
            }
        }

        BoardAuthorResponse author = boardAuthorReader.getAuthor(saved.getAuthorUserId());

        long likeCount = boardPostLikeRepository.countByBoardPostId(saved.getId());
        long commentCount = boardCommentRepository.countByBoardPostId(saved.getId());

        List<String> images = getImages(saved.getId());

        return BoardDetailResponse.from(
                saved.getId(),
                saved.getTitle(),
                saved.getContent(),
                author,
                saved.getCreatedAt(),
                saved.getUpdatedAt(),
                true,
                likeCount,
                commentCount,
                false,
                false,
                images
        );
    }

    /**
     * 게시글 북마크 추가
     *
     * 흐름:
     * 1. 게시글 존재 확인
     * 2. 이미 북마크했는지 체크
     * 3. 없으면 insert
     *
     * 응답:
     * - 최종 상태는 항상 bookmarked=true
     */
    @Transactional
    public BoardPostBookmarkResponse addBookmark(Long me, Long postId) {

        BoardPost post = getPostEntity(postId);

        boolean alreadyBookmarked = boardPostBookmarkRepository
                .existsByBoardPostIdAndUserId(post.getId(), me);

        if (!alreadyBookmarked) {
            boardPostBookmarkRepository.save(new BoardPostBookmark(post.getId(), me));
        }

        return BoardPostBookmarkResponse.of(true);
    }

    /**
     * 게시글 북마크 삭제
     *
     * - 존재하지 않아도 delete는 안전하게 동작
     * - 최종 상태는 항상 bookmarked=false
     */
    @Transactional
    public BoardPostBookmarkResponse removeBookmark(Long me, Long postId) {

        BoardPost post = getPostEntity(postId);

        boardPostBookmarkRepository.deleteByBoardPostIdAndUserId(post.getId(), me);

        return BoardPostBookmarkResponse.of(false);
    }

    /**
     * 게시글 수정
     *
     * - 작성자 검증
     * - null 아닌 값만 업데이트
     * - 이미지 전체 교체 전략 사용
     */
    @Transactional
    public BoardDetailResponse updatePost(Long me, Long postId, BoardUpdateRequest request) {

        BoardPost post = getPostEntity(postId);

        ensureAuthor(post, me);

        post.update(
                trimToNull(request.getTitle()),
                trimToNull(request.getContent())
        );

        if (request.getImages() != null) {
            boardPostImageRepository.deleteByPostId(postId);

            for (String url : request.getImages()) {
                boardPostImageRepository.save(
                        new BoardPostImage(postId, url)
                );
            }
        }

        BoardAuthorResponse author = boardAuthorReader.getAuthor(post.getAuthorUserId());

        long likeCount = boardPostLikeRepository.countByBoardPostId(post.getId());
        long commentCount = boardCommentRepository.countByBoardPostId(post.getId());

        boolean likedByMe = (me != null) &&
                boardPostLikeRepository.existsByBoardPostIdAndUserId(post.getId(), me);

        boolean bookmarkedByMe = (me != null) &&
                boardPostBookmarkRepository.existsByBoardPostIdAndUserId(post.getId(), me);

        List<String> images = getImages(post.getId());

        return BoardDetailResponse.from(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                author,
                post.getCreatedAt(),
                post.getUpdatedAt(),
                true,
                likeCount,
                commentCount,
                likedByMe,
                bookmarkedByMe,
                images
        );
    }

    /**
     * 게시글 삭제
     *
     * - 작성자만 삭제 가능
     * - 이미지 + 북마크 + 좋아요 + 댓글 같이 정리
     *
     * 참고:
     * - 현재 구조는 연관 엔티티를 ID 기반으로 직접 관리하므로
     *   게시글 삭제 전에 참조 데이터도 먼저 삭제한다.
     */
    @Transactional
    public void deletePost(Long me, Long postId) {

        BoardPost post = getPostEntity(postId);

        ensureAuthor(post, me);

        boardPostImageRepository.deleteByPostId(postId);
        boardPostBookmarkRepository.deleteByBoardPostId(postId);
        boardPostLikeRepository.deleteByBoardPostId(postId);
        boardCommentRepository.deleteByBoardPostId(postId);

        boardPostRepository.delete(post);
    }

    /**
     * 내가 작성한 게시글 목록
     *
     * - 정렬이 비어 있으면 createdAt desc 기본 정렬 적용
     */
    @Transactional(readOnly = true)
    public BoardListResponse listMyPosts(Long me, Pageable pageable) {

        Pageable normalizedPageable = normalizePageable(pageable);

        Page<BoardPost> page = boardPostRepository.findByAuthorUserId(me, normalizedPageable);

        List<BoardSummaryResponse> posts = toSummaryResponses(page.getContent(), me);

        return BoardListResponse.of(posts, PageInfoResponse.from(page));
    }

    /**
     * 게시글 단건 조회 (없으면 예외)
     */
    private BoardPost getPostEntity(Long postId) {
        return boardPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. postId=" + postId));
    }

    /**
     * 작성자 검증
     */
    private void ensureAuthor(BoardPost post, Long me) {
        if (!post.getAuthorUserId().equals(me)) {
            throw new IllegalStateException("작성자만 접근할 수 있습니다.");
        }
    }

    /**
     * 목록 변환
     *
     * 최적화:
     * - author는 한 번에 조회
     * - likeCount / commentCount / likedByMe / bookmarkedByMe 도 배치 조회
     * - 응답 DTO 구조는 그대로 유지하고, 내부 쿼리 수만 줄인다.
     */
    private List<BoardSummaryResponse> toSummaryResponses(List<BoardPost> posts, Long me) {

        if (posts.isEmpty()) {
            return List.of();
        }

        Set<Long> userIds = new LinkedHashSet<>();
        List<Long> postIds = new ArrayList<>();

        for (BoardPost post : posts) {
            userIds.add(post.getAuthorUserId());
            postIds.add(post.getId());
        }

        Map<Long, BoardAuthorResponse> authors = boardAuthorReader.getAuthors(userIds);

        Map<Long, Long> likeCountMap = toCountMap(
                boardPostLikeRepository.countGroupByBoardPostIds(postIds)
        );

        Map<Long, Long> commentCountMap = toCountMap(
                boardCommentRepository.countGroupByBoardPostIds(postIds)
        );

        Set<Long> likedPostIds = (me == null)
                ? Set.of()
                : new HashSet<>(boardPostLikeRepository
                .findLikedPostIdsByUserIdAndBoardPostIds(me, postIds));

        Set<Long> bookmarkedPostIds = (me == null)
                ? Set.of()
                : new HashSet<>(boardPostBookmarkRepository
                .findBookmarkedPostIdsByUserIdAndBoardPostIds(me, postIds));

        return posts.stream()
                .map(post -> BoardSummaryResponse.from(
                        post.getId(),
                        post.getTitle(),
                        authors.getOrDefault(
                                post.getAuthorUserId(),
                                new BoardAuthorResponse(post.getAuthorUserId(), null)
                        ),
                        post.getCreatedAt(),
                        likeCountMap.getOrDefault(post.getId(), 0L),
                        commentCountMap.getOrDefault(post.getId(), 0L),
                        likedPostIds.contains(post.getId()),
                        bookmarkedPostIds.contains(post.getId())
                ))
                .toList();
    }

    /**
     * group by count 쿼리 결과를
     * Map<boardPostId, count> 형태로 변환한다.
     */
    private Map<Long, Long> toCountMap(List<Object[]> rows) {

        Map<Long, Long> result = new HashMap<>();

        for (Object[] row : rows) {
            Long postId = (Long) row[0];
            Long count = (Long) row[1];
            result.put(postId, count);
        }

        return result;
    }

    /**
     * 게시글 이미지 조회
     */
    private List<String> getImages(Long postId) {

        List<BoardPostImage> images = boardPostImageRepository.findByPostId(postId);

        List<String> result = new ArrayList<>();

        for (BoardPostImage img : images) {
            result.add(img.getImageUrl());
        }

        return result;
    }

    /**
     * 검색어 normalize
     *
     * 정책:
     * - null -> null
     * - 공백 문자열 -> null
     * - 그 외에는 trim 결과 반환
     */
    private String normalizeKeyword(String keyword) {
        if (keyword == null) {
            return null;
        }
        String trimmed = keyword.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    /**
     * 빈 문자열 -> null 처리
     */
    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    /**
     * Pageable 기본 정렬 보정
     *
     * 정책:
     * - 사용자가 정렬을 명시하지 않으면 createdAt desc를 기본값으로 사용
     * - 이미 정렬이 들어온 경우 그대로 유지
     *
     * 이유:
     * - Repository JPQL에 order by를 박아두면 Pageable 정렬과 중복될 수 있으므로
     *   기본 정렬은 Service에서 보정한다.
     */
    private Pageable normalizePageable(Pageable pageable) {
        if (pageable.getSort().isSorted()) {
            return pageable;
        }

        return PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
    }
}