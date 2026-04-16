export interface PageInfo {
  totalElements: number;
  totalPages: number;
  page: number;
  size: number;
}

// ------------------------
// Member Types
// ------------------------
export interface MyProfileResponse {
  memberId: number;
  email: string;
  nickname: string;
  departmentName: string;
  githubUrl: string;
  blogUrl: string;
  baekjoonId: string;
  content: string;
  tags: string[];
  role: string;
}

export interface MemberDetail {
  memberId: number;
  nickname: string;
  departmentName: string;
  tags: string[];
  content: string;
  isBookmarked?: boolean;
}

export interface MemberListResponse {
  members: MemberDetail[];
  pageInfo: PageInfo;
}

// ------------------------
// Recruit (Project) Types
// ------------------------
export interface Author {
  memberId: number;
  nickname: string;
  departmentName: string;
}

export interface RecruitSummary {
  recruitId: number;
  type: string;
  category: string;
  tag: string;
  department: string;
  title: string;
  status: 'OPEN' | 'CLOSED';
  skills: string[];
  appliedByMe: boolean;
  applicantCount: number;
  totalHeadcount: number;
  deadline: string;
  createdAt: string;
  author: Author;
}

export interface RecruitListResponse {
  recruits: RecruitSummary[];
  pageInfo: PageInfo;
}

export interface RecruitDetail extends RecruitSummary {
  content: string;
  contactUrl: string;
}

// ------------------------
// Board Types
// ------------------------
export interface BoardPostSummary {
  postId: number;
  title: string;
  content: string;
  createdAt: string;
  likeCount: number;
  commentCount: number;
  isLiked: boolean;
  isBookmarked: boolean;
  authorName: string;
  authorId: number;
}

export interface BoardListResponse {
  posts: BoardPostSummary[];
  pageInfo: PageInfo;
}
