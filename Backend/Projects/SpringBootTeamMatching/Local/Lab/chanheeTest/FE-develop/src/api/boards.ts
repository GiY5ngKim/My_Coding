import { apiClient } from './client';
import type { BoardListResponse } from '../types';

export const boardsApi = {
  // 1. 게시글 목록 조회
  getPosts: async (params?: { keyword?: string; page?: number; size?: number }) => {
    // 백엔드 보고서 기준 엔드포인트: /api/posts
    const { data } = await apiClient.get<BoardListResponse>('/api/posts', { params });
    return data;
  },

  // 2. 내가 쓴 게시판 글 조회
  getMyPosts: async (params?: { page?: number; size?: number }) => {
    // 보통 /me 또는 query parameter로 처리
    const { data } = await apiClient.get<BoardListResponse>('/api/posts/me', { params });
    return data;
  },

  // 3. 스크랩한 게시물 조회 (MyPage 통계용 추가)
  getScrappedPosts: async (params?: { page?: number; size?: number }) => {
    const { data } = await apiClient.get<BoardListResponse>('/api/posts/scraps', { params });
    return data;
  },

  // 4. 좋아요한 게시물 조회 (MyPage 통계용 추가)
  getLikedPosts: async (params?: { page?: number; size?: number }) => {
    const { data } = await apiClient.get<BoardListResponse>('/api/posts/likes', { params });
    return data;
  },

  // 5. 게시글 상세 조회
  getPostDetail: async (postId: number) => {
    const { data } = await apiClient.get<any>(`/api/posts/${postId}`);
    return data;
  },

  // 6. 게시글 수정 (보고서의 PATCH 로직 반영)
  updatePost: async (postId: number, payload: { title: string; content: string }) => {
    const { data } = await apiClient.patch(`/api/posts/${postId}`, payload);
    return data;
  },

  // 7. 게시글 삭제
  deletePost: async (postId: number) => {
    const { data } = await apiClient.delete(`/api/posts/${postId}`);
    return data;
  },

  // 8. 댓글 작성
  createComment: async (postId: number, payload: { content: string }) => {
    const { data } = await apiClient.post(`/api/posts/${postId}/comments`, payload);
    return data;
  },

  // 9. 게시글 좋아요 및 취소 (보고서와 매칭)
  likePost: async (postId: number) => {
    const { data } = await apiClient.post(`/api/posts/${postId}/likes`);
    return data;
  },

  unlikePost: async (postId: number) => {
    const { data } = await apiClient.delete(`/api/posts/${postId}/likes`);
    return data;
  }
};