package com.catholic.moyeo.board.service;

/**
 * 현재 로그인 사용자 ID 제공 추상화
 */
public interface CurrentUserProvider {

    Long getCurrentUserId();
}