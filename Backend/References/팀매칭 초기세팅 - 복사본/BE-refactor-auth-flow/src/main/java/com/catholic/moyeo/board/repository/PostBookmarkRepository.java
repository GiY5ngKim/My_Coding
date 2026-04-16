package com.catholic.moyeo.board.repository;

import com.catholic.moyeo.board.domain.PostBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostBookmarkRepository extends JpaRepository<PostBookmark, Long> {
}
