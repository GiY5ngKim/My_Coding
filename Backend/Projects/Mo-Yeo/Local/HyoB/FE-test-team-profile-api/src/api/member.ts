import { apiClient } from "./client";
import type {
  MemberListResponse,
  MyProfileResponse,
  MemberDetail,
  UpdateMyProfileRequest,
  TeamProfileRegisterRequest,
  TeamProfileRegisterResponse,
} from "../types";

export const membersApi = {
  // 내 프로필 조회
  getMyProfile: async () => {
    const { data } = await apiClient.get<MyProfileResponse>("/api/members/me");
    return data;
  },

  // 내 프로필 수정
  updateMyProfile: async (payload: UpdateMyProfileRequest) => {
    const { data } = await apiClient.patch<MyProfileResponse>(
      "/api/members/me",
      payload,
    );
    return data;
  },

  // 팀원 등록
  registerTeamProfile: async (payload: TeamProfileRegisterRequest) => {
    const { data } = await apiClient.post<TeamProfileRegisterResponse>(
      "/api/members/me/team-profile",
      payload,
    );
    return data;
  },

  // 팀원 목록 조회
  getMembers: async (params?: {
    techStack?: string;
    activityCategory?: string;
    page?: number;
    size?: number;
  }) => {
    const { data } = await apiClient.get<MemberListResponse>("/api/members", {
      params,
    });
    return data;
  },

  // 팀원 상세 조회
  getMemberDetail: async (memberId: number) => {
    const { data } = await apiClient.get<MemberDetail>(
      `/api/members/${memberId}`,
    );
    return data;
  },
};

