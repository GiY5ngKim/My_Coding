import { lazy, type LazyExoticComponent, type ComponentType } from "react"; 
import { PATH } from "../components/path";

// 1. 라우트 객체를 위한 타입 정의 (타입 에러 방지)
interface RouteConfig {
  path: string;
  Component: LazyExoticComponent<ComponentType<any>> | ComponentType<any>;
}

// 2. 페이지 컴포넌트 Lazy Loading
const HomePage = lazy(() => import("../pages/home"));
const BoardPage = lazy(() => import("../pages/board/board"));
const BoardDetailPage = lazy(() => import("../pages/board/boardDetail")); // ✨ 추가된 부분
const ProjectPage = lazy(() => import("../pages/project/project"));
const MyPage = lazy(() => import("../pages/my/my"));
const MyPostPage = lazy(() => import("../pages/my/myPost"));
const MyLikePage = lazy(() => import("../pages/my/myLike"));
const MyScrapPage = lazy(() => import("../pages/my/myScrap"));
const MyAppliedProjectPage = lazy(() => import("../pages/my/myAppliedProject"));
const MyRecruitedProjectsPage = lazy(() => import("../pages/my/myRecruitedProject"));
const MyApplicantsPage = lazy(() => import("../pages/my/myApplicants"));
const MyParticipatedProjectsPage = lazy(() => import("../pages/my/myParticipatedProject"));
const NotFoundPage = lazy(() => import("../pages/notFound"));
const MemberPage = lazy(() => import("../pages/member"));
const SignupPage = lazy(() => import("../pages/signUp"));
const InquiryPage = lazy(() => import("../pages/Inquiry"));


// 3. 보호된 라우트 목록 구성
export const protectedRoutes: RouteConfig[] = [
  { path: PATH.HOME, Component: HomePage },
  { path: PATH.BOARD, Component: BoardPage },
  { 
    path: PATH.BOARD_DETAIL, 
    Component: BoardDetailPage 
  },
  { path: PATH.PROJECTS, Component: ProjectPage },
  { path: PATH.MEMBER, Component: MemberPage },
  { path: PATH.SIGNUP, Component: SignupPage },
  { path: PATH.INQUIRY, Component: InquiryPage },
  
  // 마이페이지 관련
  { path: PATH.MY, Component: MyPage },
  { path: PATH.MY_POSTS, Component: MyPostPage },
  { path: PATH.MY_LIKE, Component: MyLikePage },
  { path: PATH.MY_SCRAP, Component: MyScrapPage },
  { path: PATH.MY_APPLIED_PROJECT, Component: MyAppliedProjectPage },
  { path: PATH.MY_RECRUITED_PROJECTS, Component: MyRecruitedProjectsPage },
  { path: PATH.MY_APPLICANTS, Component: MyApplicantsPage },
  { path: PATH.MY_PARTICIPATED_PROJECTS, Component: MyParticipatedProjectsPage },
  { path: PATH.NOT_FOUND, Component: NotFoundPage },
];

export default protectedRoutes;