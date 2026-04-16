// src/components/path.ts
export const PATH = {
  // 1. Public
  ONBOARDING: "/",
  LOGIN: "/login",
  SIGNUP: "/signUp",

  // 2. Protected
  HOME: "/home",
  BOARD: "/board",
  BOARD_DETAIL: "/board/:id", 
  PROJECTS: "/project",

  // 3. MyPage
  MY: "/my",
  MY_POSTS: "/my/myposts",
  MY_LIKE: "/my/myLike",
  MY_SCRAP: "/my/myScrap",
  MY_APPLIED_PROJECT: "/my/myApplied-projects",
  MY_RECRUITED_PROJECTS: "/my/recruited-projects",
  MY_APPLICANTS: "/my/recruited-projects/applicants",
  MY_PARTICIPATED_PROJECTS: "/my/participated-projects",


  MEMBER: "/member",
  INQUIRY: "/inquiry",

  // 4. Error
  NOT_FOUND: "*",
} as const;