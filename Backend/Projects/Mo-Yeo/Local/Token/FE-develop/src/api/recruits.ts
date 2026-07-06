import { apiClient } from './client';
import type { RecruitListResponse, RecruitDetail } from '../types';

export const recruitsApi = {
  // 1. 프로젝트 목록 조회 (검색 및 필터)
  getRecruits: async (params?: {
    activityCategory?: string;
    recruitCategory?: string;
    status?: string;
    keyword?: string;
    page?: number;
    size?: number;
  }) => {
    const { data } = await apiClient.get<RecruitListResponse>('/api/recruits', { params });
    return data;
  },

  // 2. 프로젝트 등록 (추가된 부분 ✨)
  createRecruit: async (payload: any) => {
    const { data } = await apiClient.post<RecruitDetail>('/api/recruits', payload);
    return data;
  },

  // 3. 단건 상세 조회
  getRecruitDetail: async (recruitId: number) => {
    const { data } = await apiClient.get<RecruitDetail>(`/api/recruits/${recruitId}`);
    return data;
  },

  // 4. 내가 쓴 모집글 조회 (내가 만든 프로젝트)
  getMyRecruits: async (params?: { page?: number; size?: number }) => {
    const { data } = await apiClient.get<RecruitListResponse>('/api/recruits/me', { params });
    return data;
  },

  // 5. 내가 지원한 프로젝트 목록 조회 (MyPage 통계용 추가 ✨)
  getAppliedRecruits: async (params?: { page?: number; size?: number }) => {
    const { data } = await apiClient.get<RecruitListResponse>('/api/recruits/applied', { params });
    return data;
  },

  // 6. 지원하기 (ApplySheet에서 사용)
  apply: async (recruitId: number, payload?: any) => {
    // 지원 시 이름, 연락처 등 폼 데이터를 함께 보낼 경우 payload 추가
    const { data } = await apiClient.post(`/api/recruits/${recruitId}/apply`, payload);
    return data;
  },

  // 7. 지원 취소하기
  cancelApply: async (recruitId: number) => {
    const { data } = await apiClient.delete(`/api/recruits/${recruitId}/apply`);
    return data;
  }
};