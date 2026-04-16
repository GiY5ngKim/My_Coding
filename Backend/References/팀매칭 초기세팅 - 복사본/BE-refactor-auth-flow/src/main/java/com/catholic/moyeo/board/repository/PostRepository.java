package com.catholic.moyeo.board.repository;

import com.catholic.moyeo.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
