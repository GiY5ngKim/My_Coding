//api/boards.ts
import { apiClient } from './client';
import type { BoardListResponse } from '../types';

export const boardsApi = {
  // 게시글 전체 목록 조회
  getPosts: async (params?: { keyword?: string; page?: number; size?: number }) => {
    const { data } = await apiClient.get<BoardListResponse>('/api/boards/posts', { params });
    return data;
  },

  // 내가 쓴 게시판 글 조회
  getMyPosts: async (params?: { page?: number; size?: number }) => {
    const { data } = await apiClient.get<BoardListResponse>('/api/boards/posts/me', { params });
    return data;
  },

  // 스크랩한 게시물 조회 (마이페이지 통계용)
  getScrappedPosts: async (params?: { page?: number; size?: number }) => {
    const { data } = await apiClient.get<BoardListResponse>('/api/boards/posts/scraps', { params });
    return data;
  },

  // 좋아요한 게시물 조회 (마이페이지 통계용)
  getLikedPosts: async (params?: { page?: number; size?: number }) => {
    const { data } = await apiClient.get<BoardListResponse>('/api/boards/posts/likes', { params });
    return data;
  },

  // 상세 조회
  getPostDetail: async (postId: number) => {
    const { data } = await apiClient.get<any>(`/api/boards/posts/${postId}`);
    return data;
  },

  // 수정
  updatePost: async (postId: number, payload: { title: string; content: string }) => {
    const { data } = await apiClient.patch(`/api/boards/posts/${postId}`, payload);
    return data;
  },

  // 삭제
  deletePost: async (postId: number) => {
    const { data } = await apiClient.delete(`/api/boards/posts/${postId}`);
    return data;
  },

  // 댓글 작성
  createComment: async (postId: number, payload: { content: string }) => {
    const { data } = await apiClient.post(`/api/boards/posts/${postId}/comments`, payload);
    return data;
  },

  // 좋아요 및 취소
  likePost: async (postId: number) => {
    const { data } = await apiClient.post(`/api/boards/posts/${postId}/likes`);
    return data;
  },
  unlikePost: async (postId: number) => {
    const { data } = await apiClient.delete(`/api/boards/posts/${postId}/likes`);
    return data;
  }
};