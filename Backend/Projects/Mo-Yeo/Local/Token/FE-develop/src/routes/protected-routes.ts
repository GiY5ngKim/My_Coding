// src/routes/protected-routes.ts
import { lazy } from "react";
import { PATH } from "../components/path";

// 컴포넌트 Lazy 로딩
const HomePage = lazy(() => import("../pages/home"));
const BoardPage = lazy(() => import("../pages/board/board"));
const ProjectPage = lazy(() => import("../pages/project/project")); // projects.tsx
const NotFoundPage = lazy(() => import("../pages/notFound")); // notFound.tsx
const MyPage = lazy(() => import("../pages/my/my")); // pages/my/my.tsx
const MyPostsPage = lazy(() => import("../pages/my/myPost")); // pages/my/myPost.tsx
const MyLikePage = lazy(() => import("../pages/my/myLike")); // pages/my/myLike.tsx
const MyScrapPage = lazy(() => import("../pages/my/myScrap")); // pages/my/myScrap.tsx
const MyAppliedProjectPage = lazy(() => import("../pages/my/myAppliedProject"));
 // pages/my/myAppliedProject.tsx
const MyRecruitedProjectsPage = lazy(() => import("../pages/my/myRecruitedProject"));
const MyApplicantsPage = lazy(() => import("../pages/my/myApplicants"));
const MyParticipatedProjectsPage = lazy(() => import("../pages/my/myParticipatedProject"));
 // pages/my/myRecruitedProjects.tsx

const MemberPage = lazy(() => import("../pages/member"));
const SignupPage = lazy(() => import("../pages/signUp"));
const InquiryPage = lazy(() => import("../pages/Inquiry"));

export const protectedRoutes = [
  {
    path: PATH.HOME,
    Component: HomePage,
  },
  {
    path: PATH.BOARD,
    Component: BoardPage,
  },
  {
    path: PATH.PROJECTS,
    Component: ProjectPage,
  },
  {
    path: PATH.SIGNUP,
    Component: SignupPage,
  },
  {
    path: PATH.MEMBER,
    Component: MemberPage,
  },
  // MyPage 서브 라우팅
  {
    path: PATH.MY,
    Component: MyPage,
  },
  {
    path: PATH.MY_POSTS,
    Component: MyPostsPage,
  },
  {
    path: PATH.MY_LIKE,
    Component: MyLikePage,
  },
  {
    path: PATH.MY_SCRAP,
    Component: MyScrapPage,
  },
  {
    path: PATH.MY_APPLIED_PROJECT,
    Component: MyAppliedProjectPage,
  },
  {
    path: PATH.MY_RECRUITED_PROJECTS,
    Component: MyRecruitedProjectsPage,
  },
  
  {
    path: PATH.MY_APPLICANTS,
    Component: MyApplicantsPage,
  },
  {
    path: PATH.MY_PARTICIPATED_PROJECTS,
    Component: MyParticipatedProjectsPage,
  },
  // 404 페이지도 로그인 한 사람에게 보여주도록 Protected에 추가
  {
    path: PATH.NOT_FOUND,
    Component: NotFoundPage,
  },
   {
    path: PATH.INQUIRY,
    Component: InquiryPage,
  },
];
