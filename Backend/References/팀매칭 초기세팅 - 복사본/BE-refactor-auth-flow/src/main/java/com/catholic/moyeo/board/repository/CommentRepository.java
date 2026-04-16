package com.catholic.moyeo.board.repository;

import com.catholic.moyeo.board.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
