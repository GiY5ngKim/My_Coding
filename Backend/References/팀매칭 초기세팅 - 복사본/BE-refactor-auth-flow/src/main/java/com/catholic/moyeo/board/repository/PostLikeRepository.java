package com.catholic.moyeo.board.repository;

import com.catholic.moyeo.board.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}
