package com.catholic.moyeo.board.repository;

import com.catholic.moyeo.board.domain.BoardPostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardPostImageRepository extends JpaRepository<BoardPostImage, Long> {

    List<BoardPostImage> findByPostId(Long postId);

    void deleteByPostId(Long postId);
}