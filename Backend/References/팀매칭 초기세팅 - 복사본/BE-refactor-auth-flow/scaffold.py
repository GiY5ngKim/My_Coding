import os

BASE_DIR = r"c:\Users\kimky\Documents\Git\My_Coding\Backend\References\팀매칭 초기세팅 - 복사본\BE-refactor-auth-flow\src\main\java\com\catholic\moyeo"

files_to_create = {
    "auth/controller/AuthController.java": """package com.catholic.moyeo.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }
}
""",
    "member/controller/MemberController.java": """package com.catholic.moyeo.member.controller;

import com.catholic.moyeo.member.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    
    @GetMapping("/me")
    public ResponseEntity<MemberProfileResponse> getMyProfile() {
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/me")
    public ResponseEntity<MemberProfileResponse> updateMyProfile(@RequestBody MemberProfileUpdateRequest request) {
        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<Map<String, List<MemberListResponse>>> getMembers() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDetailResponse> getMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/me/profile-image")
    public ResponseEntity<Map<String, String>> uploadProfileImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(Map.of("profileImageUrl", "url"));
    }
}
""",
    "member/dto/MemberProfileUpdateRequest.java": """package com.catholic.moyeo.member.dto;

public record MemberProfileUpdateRequest(
    String nickname,
    String profileImageUrl,
    String role,
    String contactEmail,
    String phone
) {}
""",
    "member/dto/MemberProfileResponse.java": """package com.catholic.moyeo.member.dto;

public record MemberProfileResponse(
    Long memberId,
    String email,
    String contactEmail,
    Boolean emailVerified,
    String nickname,
    String role,
    String intro,
    String githubUrl,
    String profileImageUrl,
    String techs
) {}
""",
    "member/dto/MemberListResponse.java": """package com.catholic.moyeo.member.dto;

public record MemberListResponse(
    Long memberId,
    String nickname,
    String role,
    String intro,
    String githubUrl,
    String profileImageUrl,
    String techs
) {}
""",
    "member/dto/MemberDetailResponse.java": """package com.catholic.moyeo.member.dto;

public record MemberDetailResponse(
    Long memberId,
    String nickname,
    String role,
    String intro,
    String githubUrl,
    String profileImageUrl,
    String techs
) {}
""",
    "member/service/MemberService.java": """package com.catholic.moyeo.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
}
""",
    "recruit/domain/Recruit.java": """package com.catholic.moyeo.recruit.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String category;
    private String tag;
    private String department;
    private String title;
    private String content;
    private String status;
    private Integer totalHeadcount;
    private LocalDateTime deadline;
}
""",
    "recruit/domain/RecruitApplication.java": """package com.catholic.moyeo.recruit.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;
    
    private Long memberId;
    private String status;
}
""",
    "recruit/repository/RecruitRepository.java": """package com.catholic.moyeo.recruit.repository;

import com.catholic.moyeo.recruit.domain.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
}
""",
    "recruit/repository/RecruitApplicationRepository.java": """package com.catholic.moyeo.recruit.repository;

import com.catholic.moyeo.recruit.domain.RecruitApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitApplicationRepository extends JpaRepository<RecruitApplication, Long> {
}
""",
    "recruit/dto/RecruitRequest.java": """package com.catholic.moyeo.recruit.dto;

import java.util.List;
import java.time.LocalDateTime;

public record RecruitRequest(
    String type,
    String category,
    String tag,
    String department,
    String title,
    String content,
    List<String> skills,
    Integer totalHeadcount,
    LocalDateTime deadline
) {}
""",
    "recruit/dto/RecruitStatusRequest.java": """package com.catholic.moyeo.recruit.dto;

public record RecruitStatusRequest(String status) {}
""",
    "recruit/dto/ApplicationStatusRequest.java": """package com.catholic.moyeo.recruit.dto;

public record ApplicationStatusRequest(String status) {}
""",
    "recruit/controller/RecruitController.java": """package com.catholic.moyeo.recruit.controller;

import com.catholic.moyeo.recruit.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/recruits")
@RequiredArgsConstructor
public class RecruitController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> getRecruits() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{recruitId}")
    public ResponseEntity<Map<String, Object>> getRecruit(@PathVariable Long recruitId) {
        return ResponseEntity.ok(null);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createRecruit(@RequestBody RecruitRequest request) {
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{recruitId}")
    public ResponseEntity<Map<String, Object>> updateRecruit(@PathVariable Long recruitId, @RequestBody RecruitRequest request) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{recruitId}")
    public ResponseEntity<Void> deleteRecruit(@PathVariable Long recruitId) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{recruitId}/status")
    public ResponseEntity<Map<String, Object>> updateRecruitStatus(@PathVariable Long recruitId, @RequestBody RecruitStatusRequest request) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getMyRecruits() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/applied")
    public ResponseEntity<Map<String, Object>> getAppliedRecruits() {
        return ResponseEntity.ok(null);
    }
}
""",
    "recruit/controller/RecruitApplicationController.java": """package com.catholic.moyeo.recruit.controller;

import com.catholic.moyeo.recruit.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/recruits/{recruitId}")
@RequiredArgsConstructor
public class RecruitApplicationController {

    @PostMapping("/apply")
    public ResponseEntity<Map<String, Object>> applyRecruit(@PathVariable Long recruitId) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/apply")
    public ResponseEntity<Map<String, Object>> cancelApplication(@PathVariable Long recruitId) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/applications")
    public ResponseEntity<Map<String, Object>> getApplications(@PathVariable Long recruitId) {
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/applications/{applicationId}")
    public ResponseEntity<Map<String, Object>> processApplication(@PathVariable Long recruitId, @PathVariable Long applicationId, @RequestBody ApplicationStatusRequest request) {
        return ResponseEntity.ok(null);
    }
}
""",
    "recruit/service/RecruitService.java": """package com.catholic.moyeo.recruit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitService {
}
""",
    "recruit/service/RecruitApplicationService.java": """package com.catholic.moyeo.recruit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitApplicationService {
}
""",
    "board/domain/Post.java": """package com.catholic.moyeo.board.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Long memberId;
}
""",
    "board/domain/Comment.java": """package com.catholic.moyeo.board.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    
    private String content;
    private Long memberId;
}
""",
    "board/domain/PostBookmark.java": """package com.catholic.moyeo.board.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostBookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    
    private Long memberId;
}
""",
    "board/domain/PostLike.java": """package com.catholic.moyeo.board.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    
    private Long memberId;
}
""",
    "board/repository/PostRepository.java": """package com.catholic.moyeo.board.repository;

import com.catholic.moyeo.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
""",
    "board/repository/CommentRepository.java": """package com.catholic.moyeo.board.repository;

import com.catholic.moyeo.board.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
""",
    "board/repository/PostBookmarkRepository.java": """package com.catholic.moyeo.board.repository;

import com.catholic.moyeo.board.domain.PostBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostBookmarkRepository extends JpaRepository<PostBookmark, Long> {
}
""",
    "board/repository/PostLikeRepository.java": """package com.catholic.moyeo.board.repository;

import com.catholic.moyeo.board.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}
""",
    "board/dto/PostRequest.java": """package com.catholic.moyeo.board.dto;

import java.util.List;

public record PostRequest(
    String title,
    String content,
    List<String> images
) {}
""",
    "board/dto/CommentRequest.java": """package com.catholic.moyeo.board.dto;

public record CommentRequest(String content) {}
""",
    "board/controller/BoardController.java": """package com.catholic.moyeo.board.controller;

import com.catholic.moyeo.board.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    @GetMapping("/posts")
    public ResponseEntity<Map<String, Object>> getPosts() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Map<String, Object>> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/posts")
    public ResponseEntity<Map<String, Object>> createPost(@RequestBody PostRequest request) {
        return ResponseEntity.ok(null);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Map<String, Object>> updatePost(@PathVariable Long id, @RequestBody PostRequest request) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts/me")
    public ResponseEntity<Map<String, Object>> getMyPosts() {
        return ResponseEntity.ok(null);
    }
}
""",
    "board/controller/CommentController.java": """package com.catholic.moyeo.board.controller;

import com.catholic.moyeo.board.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class CommentController {

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Map<String, Object>> createComment(@PathVariable Long postId, @RequestBody CommentRequest request) {
        return ResponseEntity.ok(null);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<Map<String, Object>> updateComment(@PathVariable Long id, @RequestBody CommentRequest request) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<Map<String, Object>>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(null);
    }
}
""",
    "board/controller/BookmarkController.java": """package com.catholic.moyeo.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/boards/posts")
@RequiredArgsConstructor
public class BookmarkController {

    @PostMapping("/{id}/bookmark")
    public ResponseEntity<Map<String, Object>> addBookmark(@PathVariable Long id) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}/bookmark")
    public ResponseEntity<Map<String, Object>> removeBookmark(@PathVariable Long id) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/bookmarks")
    public ResponseEntity<Map<String, Object>> getBookmarks() {
        return ResponseEntity.ok(null);
    }
}
""",
    "board/controller/LikeController.java": """package com.catholic.moyeo.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/boards/posts")
@RequiredArgsConstructor
public class LikeController {

    @PostMapping("/{id}/like")
    public ResponseEntity<Map<String, Object>> addLike(@PathVariable Long id) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<Map<String, Object>> removeLike(@PathVariable Long id) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/likes")
    public ResponseEntity<Map<String, Object>> getLikes() {
        return ResponseEntity.ok(null);
    }
}
""",
    "board/service/BoardService.java": """package com.catholic.moyeo.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
}
""",
    "board/service/CommentService.java": """package com.catholic.moyeo.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
}
"""
}

for rel_path, content in files_to_create.items():
    full_path = os.path.join(BASE_DIR, rel_path.replace('/', os.sep))
    os.makedirs(os.path.dirname(full_path), exist_ok=True)
    with open(full_path, 'w', encoding='utf-8') as f:
        f.write(content)

print(f"Created {len(files_to_create)} scaffolding files.")
