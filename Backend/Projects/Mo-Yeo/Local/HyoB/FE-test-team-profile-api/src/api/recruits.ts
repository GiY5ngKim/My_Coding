import { apiClient } from './client';
import type { RecruitListResponse, RecruitDetail } from '../types';

export const recruitsApi = {
  // 프로젝트 목록 필터링 조회
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

  // 신규 프로젝트 등록
  createRecruit: async (payload: any) => {
    const { data } = await apiClient.post<RecruitDetail>('/api/recruits', payload);
    return data;
  },

  // 프로젝트 상세 조회
  getRecruitDetail: async (recruitId: number) => {
    const { data } = await apiClient.get<RecruitDetail>(`/api/recruits/${recruitId}`);
    return data;
  },

  // 내가 모집 중인 글 조회
  getMyRecruits: async (params?: { page?: number; size?: number }) => {
    const { data } = await apiClient.get<RecruitListResponse>('/api/recruits/me', { params });
    return data;
  },

  // 내가 지원 완료한 프로젝트 조회 (통계용)
  getAppliedRecruits: async (params?: { page?: number; size?: number }) => {
    const { data } = await apiClient.get<RecruitListResponse>('/api/recruits/applied', { params });
    return data;
  },

  // 프로젝트 지원하기 (ApplySheet 연동)
  apply: async (recruitId: number, payload?: any) => {
    const { data } = await apiClient.post(`/api/recruits/${recruitId}/apply`, payload);
    return data;
  },

  // 지원 취소하기
  cancelApply: async (recruitId: number) => {
    const { data } = await apiClient.delete(`/api/recruits/${recruitId}/apply`);
    return data;
  }
};