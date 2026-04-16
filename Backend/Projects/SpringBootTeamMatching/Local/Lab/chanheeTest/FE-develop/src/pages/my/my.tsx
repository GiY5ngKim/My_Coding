import { useEffect, useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import PostPreviewCard from "../../components/PostPreviewCard";
import ReviewModal from "../../components/reviewModal";
import ProfileEditModal from "../../components/profileEditModal";
import { PATH } from "../../components/path";
import backIcon from "../../assets/back.svg";
import editIcon from "../../assets/edit.svg";
import mailIcon from "../../assets/mail.svg";
import projectIcon from "../../assets/project.svg";
import postIcon from "../../assets/post.svg";
import logoutIcon from "../../assets/logout.svg";
import chevronRightIcon from "../../assets/chevronRight.svg";
import { membersApi } from "../../api/member";
import { recruitsApi } from "../../api/recruits";
import { boardsApi } from "../../api/boards";

type ReviewMember = {
  id: number;
  name: string;
  role: string;
};

type ProfileEditType = {
  name: string;
  role: string;
  email: string;
  bio: string;
  techStacks: string[];
  githubUrl: string;
};

export default function MyPage() {
  const navigate = useNavigate();

  const [isReviewModalOpen, setIsReviewModalOpen] = useState(false);
  const [selectedProjectTitle, setSelectedProjectTitle] = useState("");
  const [selectedMembers, setSelectedMembers] = useState<ReviewMember[]>([]);

  const [isEditModalOpen, setIsEditModalOpen] = useState(false);

  const [editableProfile, setEditableProfile] = useState<ProfileEditType>({
    name: "",
    role: "",
    email: "",
    bio: "",
    techStacks: [],
    githubUrl: "",
  });

  const { data: profile, isLoading: isProfileLoading } = useQuery({
    queryKey: ["myProfile"],
    queryFn: membersApi.getMyProfile,
  });

  const { data: myRecruits, isLoading: isRecruitsLoading } = useQuery({
    queryKey: ["myRecruits"],
    queryFn: () => recruitsApi.getMyRecruits({ size: 5 }),
  });

  const { data: myPosts, isLoading: isPostsLoading } = useQuery({
    queryKey: ["myPosts"],
    queryFn: () => boardsApi.getMyPosts({ size: 5 }),
  });

  const { data: scrappedPosts } = useQuery({
    queryKey: ["myScrappedPosts"],
    queryFn: () => boardsApi.getScrappedPosts({ size: 1 }),
  });

  const { data: likedPosts } = useQuery({
    queryKey: ["myLikedPosts"],
    queryFn: () => boardsApi.getLikedPosts({ size: 1 }),
  });

  const profileInitial =
  ((profile?.nickname ?? editableProfile.name)?.trim().charAt(0)) || "?";

  useEffect(() => {
    if (!profile) return;

    setEditableProfile({
      name: profile.nickname ?? "",
      role: profile.role || "가톨릭대 재학생",
      email: profile.email ?? "",
      bio: profile.content || "",
      techStacks: profile.tags ?? [],
      githubUrl: profile.githubUrl ?? "",
    });
  }, [profile]);

  const openReviewModal = (projectTitle: string, members: ReviewMember[]) => {
    setSelectedProjectTitle(projectTitle);
    setSelectedMembers(members);
    setIsReviewModalOpen(true);
  };

  const closeReviewModal = () => {
    setIsReviewModalOpen(false);
    setSelectedProjectTitle("");
    setSelectedMembers([]);
  };

  const handleLogout = () => {
    localStorage.removeItem("access_token");
    window.location.href = `${import.meta.env.VITE_API_BASE_URL}/logout`;
  };

  const stats = useMemo(
    () => [
      {
        label: "지원한\n프로젝트",
        value: 0,
        path: PATH.MY_APPLIED_PROJECT,
      },
      {
        label: "스크랩",
        value: scrappedPosts?.pageInfo?.totalElements || 0,
        path: PATH.MY_SCRAP,
      },
      {
        label: "좋아요",
        value: likedPosts?.pageInfo?.totalElements || 0,
        path: PATH.MY_LIKE,
      },
    ],
    [scrappedPosts, likedPosts]
  );

  const participatedProjects = [
    {
      id: 1,
      title: "2026 정처기 한방에 끝내자 스터디원 모집",
      role: "Frontend",
      reviewText: "팀원 리뷰달기 >",
      members: [
        { id: 1, name: "김철수", role: "Backend Developer" },
        { id: 2, name: "이영희", role: "Designer" },
        { id: 3, name: "박민수", role: "Frontend Developer" },
      ],
    },
    {
      id: 2,
      title: "2026 ICT 공모전 팀원 모집",
      role: "Frontend",
      reviewText: "팀원 리뷰달기 >",
      members: [
        { id: 4, name: "최수진", role: "PM" },
        { id: 5, name: "정민호", role: "Backend Developer" },
      ],
    },
  ];

  return (
    <>
      <main className="mx-auto min-h-screen w-full max-w-[430px] bg-[#F8FAFC] pb-[88px]">
        <header className="border-b border-[#E5E7EB] bg-white">
          <div className="flex h-[96px] items-end px-[16px] pb-[20px] pt-[40px]">
            <button type="button" onClick={() => navigate(-1)}>
              <img src={backIcon} alt="뒤로가기" className="h-[24px] w-[24px]" />
            </button>

            <div className="flex flex-1 justify-center">
              <span className="text-[20px] font-bold leading-[28px] text-[#000000]">
                마이페이지
              </span>
            </div>

            <div className="h-[36px] w-[36px] shrink-0" />
          </div>
        </header>

        <section className="px-[16px] pt-[16px]">
          <div className="flex flex-col gap-[16px]">
            <section className="rounded-[14px] border border-[#E2E8F0] bg-white px-[20px] pb-[20px] pt-[20px] shadow-[0_2px_8px_rgba(15,23,42,0.06)]">
              {isProfileLoading ? (
                <div className="flex h-[120px] items-center justify-center text-[#94A3B8]">
                  프로필 로딩 중...
                </div>
              ) : (
                <>
                  <div className="flex items-start justify-between gap-[12px]">
                    <div className="flex min-w-0 flex-1 items-start gap-[14px]">
                      <div className="flex h-[80px] w-[80px] shrink-0 items-center justify-center rounded-full bg-[#2563EB] text-[28px] font-bold text-white">
                        {profileInitial}
                      </div>

                      <div className="min-w-0 flex-1 pt-[4px]">
                        <div className="text-[18px] font-bold leading-[26px] text-[#111827]">
                          {(profile?.nickname ?? editableProfile.name) || "사용자"}
                        </div>

                        <div className="mt-[6px] whitespace-pre-line text-[14px] leading-[20px] text-[#475569]">
                          {profile?.role || editableProfile.role || "가톨릭대 재학생"}
                        </div>

                        <div className="mt-[8px] flex items-center gap-[8px] text-[12px] leading-[18px] text-[#94A3B8]">
                          <img src={mailIcon} alt="" className="h-[18px] w-[18px]" />
                          <span className="truncate">
                            {profile?.email ?? editableProfile.email}
                          </span>
                        </div>
                      </div>
                    </div>

                    <button
                      type="button"
                      onClick={() => setIsEditModalOpen(true)}
                      className="mt-[6px] flex shrink-0 items-center gap-[6px] text-[12px] font-medium leading-[20px] text-[#2563EB]"
                    >
                      <span>수정하기</span>
                      <img src={editIcon} alt="" className="h-[18px] w-[18px]" />
                    </button>
                  </div>

                  <p className="mt-[20px] text-[14px] leading-[20px] text-[#45556C]">
                    {profile?.content || editableProfile.bio || "등록된 자기소개가 없습니다."}
                  </p>

                  <div className="mt-[16px] flex flex-wrap gap-[10px]">
                    {(profile?.tags ?? editableProfile.techStacks ?? []).map(
                      (stack: string) => (
                        <span
                          key={stack}
                          className="rounded-[8px] bg-[#EFF6FF] px-[8px] py-[4px] text-[12px] font-medium leading-[16px] text-[#1447E6]"
                        >
                          {stack}
                        </span>
                      )
                    )}
                  </div>
                </>
              )}
            </section>

            <section className="rounded-[14px] border border-[#E2E8F0] bg-white px-[16px] py-[18px] shadow-[0_2px_8px_rgba(15,23,42,0.06)]">
              <div className="grid grid-cols-3">
                {stats.map((item, index) => (
                  <button
                    key={item.label}
                    type="button"
                    onClick={() => navigate(item.path)}
                    className={`flex min-h-[96px] flex-col items-center justify-center ${
                      index !== stats.length - 1 ? "border-r border-[#E2E8F0]" : ""
                    }`}
                  >
                    <span className="h-[28px] text-[22px] font-bold leading-[28px] text-[#3B82F6]">
                      {item.value}
                    </span>

                    <span className="mt-[12px] h-[32px] whitespace-pre-line text-center text-[12px] leading-[16px] text-[#62748E]">
                      {item.label}
                    </span>
                  </button>
                ))}
              </div>
            </section>

            <section>
              <div className="mb-[12px] flex items-center justify-between">
                <div className="flex items-center gap-[8px]">
                  <img src={projectIcon} alt="" className="h-[22px] w-[22px]" />
                  <h2 className="text-[16px] font-bold leading-[24px] text-[#111827]">
                    내가 모집한 프로젝트
                  </h2>
                </div>

                <button
                  type="button"
                  onClick={() => navigate(PATH.MY_RECRUITED_PROJECTS)}
                  className="text-[12px] font-semibold leading-[20px] text-[#0EA5E9]"
                >
                  전체보기
                </button>
              </div>

              <div className="flex flex-col gap-[12px]">
                {isRecruitsLoading ? (
                  <div className="rounded-[14px] border border-[#E2E8F0] bg-white px-[16px] py-[14px] text-center text-[13px] text-[#94A3B8] shadow-[0_2px_8px_rgba(15,23,42,0.06)]">
                    로딩 중...
                  </div>
                ) : myRecruits?.recruits?.length ? (
                  myRecruits.recruits.map((project: any) => (
                    <article
                      key={project.recruitId}
                      className="rounded-[14px] border border-[#E2E8F0] bg-white px-[16px] py-[14px] shadow-[0_2px_8px_rgba(15,23,42,0.06)]"
                    >
                      <div className="flex items-start justify-between gap-[12px]">
                        <div className="min-w-0 flex-1">
                          <h3 className="truncate text-[15px] font-bold leading-[24px] text-[#1E293B]">
                            {project.title}
                          </h3>
                        </div>

                        <span
                          className={`shrink-0 rounded-[14px] px-[14px] py-[6px] text-[12px] font-bold leading-[20px] text-white shadow-[0_2px_6px_rgba(29,155,240,0.25)] ${
                            project.status === "OPEN" ? "bg-[#1D9BF0]" : "bg-[#94A3B8]"
                          }`}
                        >
                          {project.status === "OPEN" ? "모집중" : "마감"}
                        </span>
                      </div>

                      <div className="mt-[14px] flex items-end justify-between gap-[12px]">
                        <div className="text-[12px] leading-[20px] text-[#62748E]">
                          역할: {project.category || "개발"}
                        </div>

                        <button
                          type="button"
                          onClick={() =>
                            navigate(`/projects/${project.recruitId}/applicants`)
                          }
                          className="text-right text-[12px] font-semibold leading-[20px] text-[#2563EB]"
                        >
                          {project.applicantCount}/{project.totalHeadcount} 지원자 확인하기
                        </button>
                      </div>
                    </article>
                  ))
                ) : (
                  <div className="rounded-[14px] border border-[#E2E8F0] bg-white px-[16px] py-[14px] text-center text-[13px] text-[#94A3B8] shadow-[0_2px_8px_rgba(15,23,42,0.06)]">
                    내가 모집한 프로젝트가 없습니다.
                  </div>
                )}
              </div>
            </section>

            <section>
              <div className="mb-[12px] flex items-center justify-between">
                <div className="flex items-center gap-[8px]">
                  <img src={projectIcon} alt="" className="h-[22px] w-[22px]" />
                  <h2 className="text-[16px] font-bold leading-[24px] text-[#111827]">
                    내가 참여한 프로젝트
                  </h2>
                </div>

                <button
                  type="button"
                  onClick={() => navigate(PATH.MY_PARTICIPATED_PROJECTS)}
                  className="text-[12px] font-semibold leading-[20px] text-[#0EA5E9]"
                >
                  전체보기
                </button>
              </div>

              <div className="flex flex-col gap-[12px]">
                {participatedProjects.map((project) => (
                  <article
                    key={project.id}
                    className="rounded-[14px] border border-[#E2E8F0] bg-white px-[20px] py-[18px] shadow-[0_2px_8px_rgba(15,23,42,0.06)]"
                  >
                    <h3 className="truncate text-[15px] font-bold leading-[24px] text-[#1E293B]">
                      {project.title}
                    </h3>

                    <div className="mt-[18px] flex items-end justify-between gap-[12px]">
                      <div className="text-[12px] leading-[20px] text-[#64748B]">
                        역할: {project.role}
                      </div>

                      <button
                        type="button"
                        onClick={() => openReviewModal(project.title, project.members)}
                        className="text-[12px] font-semibold leading-[20px] text-[#2563EB]"
                      >
                        {project.reviewText}
                      </button>
                    </div>
                  </article>
                ))}
              </div>
            </section>

            <section>
              <div className="mb-[12px] flex items-center justify-between">
                <div className="flex items-center gap-[8px]">
                  <img src={postIcon} alt="" className="h-[22px] w-[22px]" />
                  <h2 className="text-[16px] font-bold leading-[24px] text-[#111827]">
                    내가 작성한 게시물
                  </h2>
                </div>

                <button
                  type="button"
                  onClick={() => navigate(PATH.MY_POSTS)}
                  className="text-[12px] font-semibold leading-[20px] text-[#0EA5E9]"
                >
                  전체보기
                </button>
              </div>

              <div className="flex flex-col gap-[12px]">
                {isPostsLoading ? (
                  <div className="rounded-[14px] border border-[#E2E8F0] bg-white px-[16px] py-[14px] text-center text-[13px] text-[#94A3B8] shadow-[0_2px_8px_rgba(15,23,42,0.06)]">
                    로딩 중...
                  </div>
                ) : myPosts?.posts?.length ? (
                  myPosts.posts.map((post: any) => (
                    <div
                      key={post.postId}
                      onClick={() => navigate(`/board/${post.postId}`)}
                      className="cursor-pointer"
                    >
                      <PostPreviewCard
                        title={post.title}
                        likeCount={post.likeCount}
                        commentCount={post.commentCount}
                        date={new Date(post.createdAt).toLocaleDateString()}
                      />
                    </div>
                  ))
                ) : (
                  <div className="rounded-[14px] border border-[#E2E8F0] bg-white px-[16px] py-[14px] text-center text-[13px] text-[#94A3B8] shadow-[0_2px_8px_rgba(15,23,42,0.06)]">
                    작성한 게시물이 없습니다.
                  </div>
                )}
              </div>
            </section>

            <button
              type="button"
              onClick={handleLogout}
              className="mt-[4px] flex h-[52px] w-full items-center justify-between rounded-[14px] border border-[#E2E8F0] bg-white px-[16px] text-left shadow-[0_2px_8px_rgba(15,23,42,0.06)]"
            >
              <div className="flex items-center gap-[10px]">
                <img
                  src={logoutIcon}
                  alt="로그아웃"
                  className="h-[16px] w-[16px] shrink-0"
                />

                <span className="text-[14px] font-medium leading-[20px] text-[#1D293D]">
                  로그아웃
                </span>
              </div>

              <img
                src={chevronRightIcon}
                alt=""
                className="h-[16px] w-[16px] shrink-0"
              />
            </button>
          </div>
        </section>
      </main>

      <ReviewModal
        isOpen={isReviewModalOpen}
        onClose={closeReviewModal}
        projectTitle={selectedProjectTitle}
        members={selectedMembers}
        onSubmit={(reviews) => {
          console.log("리뷰 제출 데이터:", reviews);
        }}
      />

      <ProfileEditModal
        isOpen={isEditModalOpen}
        onClose={() => setIsEditModalOpen(false)}
        initialProfile={editableProfile}
        onSave={(updatedProfile) => {
          setEditableProfile((prev) => ({
            ...prev,
            ...updatedProfile,
          }));
          setIsEditModalOpen(false);
        }}
      />
    </>
  );
}