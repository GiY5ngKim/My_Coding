import { useEffect, useRef, useState } from "react";
import TeamMemberCard from "../components/memberCard";
import backIcon from "../assets/back.svg";
import plusIcon from "../assets/plus.svg";
import closeIcon from "../assets/close.svg";
import FieldLabel from "../components/fieldLabel";
import { membersApi } from "../api/member";
import type { Member, TeamProfileRegisterRequest } from "../types";

type Category = "전체" | "수업" | "프로젝트" | "스터디" | "공모전";
type InterestCategory = "전체" | "수업" | "프로젝트" | "공모전" | "스터디";

const categories: Category[] = ["전체", "수업", "프로젝트", "스터디", "공모전"];

const interestCategories: InterestCategory[] = [
  "전체",
  "수업",
  "프로젝트",
  "공모전",
  "스터디",
];

// 팀원 목록 조회용 query enum
const categoryToApiValue: Record<Category, string | undefined> = {
  전체: undefined,
  수업: "CLASS",
  프로젝트: "PROJECT",
  스터디: "STUDY",
  공모전: "CONTEST",
};

export default function TeamMemberPage() {
  const [selectedCategory, setSelectedCategory] = useState<Category>("전체");
  const [isRegisterOpen, setIsRegisterOpen] = useState(false);
  const [selectedInterest, setSelectedInterest] =
    useState<InterestCategory>("전체");
  const [sheetWidth, setSheetWidth] = useState<number>(430);

  const [members, setMembers] = useState<Member[]>([]);
  const [totalElements, setTotalElements] = useState(0);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  const [isLoading, setIsLoading] = useState(false);
  const [isError, setIsError] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const wrapperRef = useRef<HTMLDivElement | null>(null);

  const [form, setForm] = useState({
    name: "",
    role: "",
    bio: "",
    skills: "",
    email: "",
    phone: "",
    github: "",
  });

  const fetchMembers = async () => {
    try {
      setIsLoading(true);
      setIsError(false);

      const data = await membersApi.getMembers({
        activityCategory: categoryToApiValue[selectedCategory],
        page,
        size: 10,
      });

      setMembers(data.items);
      setTotalElements(data.pageInfo.totalElements);
      setTotalPages(data.pageInfo.totalPages);
    } catch (error) {
      console.error("팀원 목록 조회 실패:", error);
      setMembers([]);
      setTotalElements(0);
      setTotalPages(1);
      setIsError(true);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    const updateSheetWidth = () => {
      if (wrapperRef.current) {
        setSheetWidth(wrapperRef.current.offsetWidth);
      }
    };

    updateSheetWidth();
    window.addEventListener("resize", updateSheetWidth);

    return () => {
      window.removeEventListener("resize", updateSheetWidth);
    };
  }, []);

  useEffect(() => {
    document.body.style.overflow = isRegisterOpen ? "hidden" : "";

    return () => {
      document.body.style.overflow = "";
    };
  }, [isRegisterOpen]);

  useEffect(() => {
    fetchMembers();
  }, [selectedCategory, page]);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleOpenSheet = () => {
    if (wrapperRef.current) {
      setSheetWidth(wrapperRef.current.offsetWidth);
    }

    setIsRegisterOpen(true);
  };

  const handleCloseSheet = () => {
    setIsRegisterOpen(false);
  };

  const handleCategoryClick = (category: Category) => {
    setSelectedCategory(category);
    setPage(0);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (isSubmitting) return;

    if (!form.name.trim()) {
      alert("이름을 입력해주세요.");
      return;
    }

    if (!form.role.trim()) {
      alert("역할/포지션을 입력해주세요.");
      return;
    }

    if (!form.bio.trim()) {
      alert("자기소개를 입력해주세요.");
      return;
    }

    if (!form.skills.trim()) {
      alert("기술스택을 입력해주세요.");
      return;
    }

    if (!form.email.trim()) {
      alert("이메일을 입력해주세요.");
      return;
    }

    try {
      setIsSubmitting(true);

      const payload: TeamProfileRegisterRequest = {
        nickname: form.name.trim(),
        role: form.role.trim(),
        contactEmail: form.email.trim(),
        phoneNumber: form.phone.trim(),
        githubUrl: form.github.trim(),
        intro: form.bio.trim(),
        profileImageUrl: "",
        techStacks: form.skills
          .split(",")
          .map((skill) => skill.trim())
          .filter(Boolean),
        activityCategories:
          selectedInterest === "전체" ? [] : [selectedInterest],
      };

      await membersApi.registerTeamProfile(payload);

      alert("팀원 프로필이 등록되었습니다.");

      setIsRegisterOpen(false);

      setForm({
        name: "",
        role: "",
        bio: "",
        skills: "",
        email: "",
        phone: "",
        github: "",
      });

      setSelectedInterest("전체");
      setPage(0);

      const data = await membersApi.getMembers({
        activityCategory: categoryToApiValue[selectedCategory],
        page: 0,
        size: 10,
      });

      setMembers(data.items);
      setTotalElements(data.pageInfo.totalElements);
      setTotalPages(data.pageInfo.totalPages);
    } catch (error) {
      console.error("팀원 등록 실패:", error);
      alert("팀원 등록에 실패했습니다.");
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div
      ref={wrapperRef}
      className="mx-auto min-h-screen w-full max-w-[430px] bg-[#F9FAFB]"
    >
      {/* 헤더 */}
      <header className="border-b border-[#E5E7EB] bg-white">
        <div className="flex h-[96px] items-end px-[16px] pb-[20px] pt-[40px]">
          <button
            type="button"
            className="flex h-[24px] w-[24px] items-center justify-center"
          >
            <img src={backIcon} alt="뒤로가기" className="h-[24px] w-[24px]" />
          </button>

          <div className="flex flex-1 justify-center">
            <span className="text-[20px] font-bold leading-[28px] text-[#000000]">
              팀원 찾기
            </span>
          </div>

          <button type="button" onClick={handleOpenSheet}>
            <img src={plusIcon} alt="추가" className="h-[24px] w-[24px]" />
          </button>
        </div>
      </header>

      {/* 카테고리 */}
      <section className="border-b border-[#E5E7EB] bg-white px-4 py-3">
        <div className="flex w-full items-center gap-2 overflow-x-auto">
          {categories.map((category) => {
            const isSelected = selectedCategory === category;

            return (
              <button
                key={category}
                type="button"
                onClick={() => handleCategoryClick(category)}
                className={`whitespace-nowrap rounded-[8px] border px-3 py-1 text-[12px] font-medium leading-4 transition ${
                  isSelected
                    ? "border-[#356AE6] bg-[#356AE6] text-white"
                    : "border-[#E5E7EB] bg-white text-[#374151]"
                }`}
              >
                {category}
              </button>
            );
          })}
        </div>
      </section>

      {/* 본문 */}
      <main className="px-4 pt-4 pb-6">
        <p className="mb-4 text-[14px] font-medium leading-5 text-[#6B7280]">
          {totalElements}명의 팀원
        </p>

        {isLoading && (
          <div className="rounded-[12px] bg-white px-4 py-8 text-center text-[14px] text-[#6B7280]">
            팀원 목록을 불러오는 중입니다.
          </div>
        )}

        {isError && !isLoading && (
          <div className="rounded-[12px] bg-white px-4 py-8 text-center text-[14px] text-[#EF4444]">
            팀원 목록을 불러오지 못했습니다.
          </div>
        )}

        {!isLoading && !isError && members.length === 0 && (
          <div className="rounded-[12px] bg-white px-4 py-8 text-center text-[14px] text-[#6B7280]">
            등록된 팀원이 없습니다.
          </div>
        )}

        {!isLoading && !isError && members.length > 0 && (
          <div className="flex flex-col gap-3">
            {members.map((member) => (
              <TeamMemberCard
                key={member.memberId}
                name={member.nickname}
                role={member.role}
                description={member.intro}
                techStacks={member.techStacks}
                rating={0}
                profileInitial={member.nickname?.charAt(0) ?? "?"}
                isBookmarked={false}
                githubUrl={member.githubUrl}
              />
            ))}
          </div>
        )}

        {!isLoading && !isError && totalPages > 1 && (
          <div className="mt-5 flex items-center justify-center gap-3">
            <button
              type="button"
              disabled={page === 0}
              onClick={() => setPage((prev) => Math.max(prev - 1, 0))}
              className="h-[36px] rounded-[8px] border border-[#E5E7EB] bg-white px-4 text-[13px] font-medium text-[#374151] disabled:cursor-not-allowed disabled:opacity-40"
            >
              이전
            </button>

            <span className="text-[13px] font-medium text-[#6B7280]">
              {page + 1} / {totalPages}
            </span>

            <button
              type="button"
              disabled={page + 1 >= totalPages}
              onClick={() =>
                setPage((prev) => Math.min(prev + 1, totalPages - 1))
              }
              className="h-[36px] rounded-[8px] border border-[#E5E7EB] bg-white px-4 text-[13px] font-medium text-[#374151] disabled:cursor-not-allowed disabled:opacity-40"
            >
              다음
            </button>
          </div>
        )}
      </main>

      {/* 바텀시트 */}
      <div
        className={`fixed inset-0 z-50 transition-all duration-300 ${
          isRegisterOpen
            ? "pointer-events-auto bg-black/50"
            : "pointer-events-none bg-black/0"
        }`}
        onClick={handleCloseSheet}
      >
        <div
          className={`fixed bottom-0 left-1/2 z-[60] overflow-hidden rounded-t-[20px] bg-white transition-transform duration-300 ${
            isRegisterOpen ? "translate-y-0" : "translate-y-full"
          }`}
          style={{
            width: `${sheetWidth}px`,
            transform: `translateX(-50%) translateY(${
              isRegisterOpen ? "0" : "100%"
            })`,
          }}
          onClick={(e) => e.stopPropagation()}
        >
          {/* 시트 헤더 */}
          <div className="h-[68.8px] border-b border-[#E2E8F0] bg-white">
            <div className="flex h-full items-center justify-between px-5">
              <h2 className="text-[18px] font-bold leading-[32px] text-[#111827]">
                프로필 등록
              </h2>

              <button
                type="button"
                onClick={handleCloseSheet}
                className="flex h-6 w-6 items-center justify-center"
              >
                <img src={closeIcon} alt="닫기" className="h-7 w-7" />
              </button>
            </div>
          </div>

          <form
            onSubmit={handleSubmit}
            className="flex max-h-[calc(100vh-96px)] flex-col bg-white"
          >
            <div className="flex flex-col gap-[10px] overflow-y-auto px-4 py-4">
              <FieldLabel label="이름" required />
              <input
                name="name"
                value={form.name}
                onChange={handleChange}
                placeholder="김가톨릭"
                className="h-[45px] w-full rounded-[10px] border border-[#E2E8F0] px-4 text-[14px] text-[#111827] outline-none placeholder:text-[#9CA3AF]"
              />

              <FieldLabel label="역할/포지션" required />
              <input
                name="role"
                value={form.role}
                onChange={handleChange}
                placeholder="예: Frontend Developer"
                className="h-[45px] w-full rounded-[10px] border border-[#E2E8F0] px-4 text-[14px] text-[#111827] outline-none placeholder:text-[#9CA3AF]"
              />

              <FieldLabel label="자기소개" required />
              <textarea
                name="bio"
                value={form.bio}
                onChange={handleChange}
                placeholder={
                  "자신을 소개하고 어떤 프로젝트에 관심있는지 알려주세요\n(전공을 적고 싶으시면 적어도 됩니다)"
                }
                className="min-h-[64px] w-full resize-none rounded-[10px] border border-[#E2E8F0] px-4 py-3 text-[14px] leading-5 text-[#111827] outline-none placeholder:text-[#9CA3AF]"
              />

              <FieldLabel label="기술스택" required />
              <input
                name="skills"
                value={form.skills}
                onChange={handleChange}
                placeholder="예: React, TypeScript, Node.js (쉼표로 구분)"
                className="h-[45px] w-full rounded-[10px] border border-[#E2E8F0] px-4 text-[14px] text-[#111827] outline-none placeholder:text-[#9CA3AF]"
              />

              <FieldLabel label="관심 분야" required />
              <div className="flex flex-wrap gap-2">
                {interestCategories.map((category) => {
                  const isSelected = selectedInterest === category;

                  return (
                    <button
                      key={category}
                      type="button"
                      onClick={() => setSelectedInterest(category)}
                      className={`h-[37.6px] rounded-[8px] border px-4 text-[12px] font-medium leading-4 ${
                        isSelected
                          ? "border-[#2F6BFF] bg-[#2F6BFF] text-white"
                          : "border-[#E2E8F0] bg-white text-[#314158]"
                      }`}
                    >
                      {category}
                    </button>
                  );
                })}
              </div>

              <FieldLabel label="이메일" required />
              <input
                name="email"
                value={form.email}
                onChange={handleChange}
                placeholder="name@catholic.ac.kr"
                className="h-[45px] w-full rounded-[10px] border border-[#E2E8F0] px-4 text-[14px] text-[#111827] outline-none placeholder:text-[#9CA3AF]"
              />

              <FieldLabel label="연락처" />
              <input
                name="phone"
                value={form.phone}
                onChange={handleChange}
                placeholder="010-1234-5678"
                className="h-[45px] w-full rounded-[10px] border border-[#E2E8F0] px-4 text-[14px] text-[#111827] outline-none placeholder:text-[#9CA3AF]"
              />

              <FieldLabel label="깃허브 주소" />
              <input
                name="github"
                value={form.github}
                onChange={handleChange}
                placeholder="github.com/username"
                className="h-[45px] w-full rounded-[10px] border border-[#E2E8F0] px-4 text-[14px] text-[#111827] outline-none placeholder:text-[#9CA3AF]"
              />
            </div>

            <div className="border-t border-[#E2E8F0] bg-white px-5 py-5">
              <div className="flex gap-3">
                <button
                  type="button"
                  onClick={handleCloseSheet}
                  disabled={isSubmitting}
                  className="h-[45px] flex-1 rounded-[10px] border border-[#E2E8F0] bg-white text-[14px] font-medium text-[#314158] disabled:opacity-60"
                >
                  취소
                </button>

                <button
                  type="submit"
                  disabled={isSubmitting}
                  className="h-[45px] flex-1 rounded-[10px] bg-gradient-to-r from-[#00A6F4] to-[#2B7FFF] text-[14px] font-semibold text-white disabled:opacity-60"
                >
                  {isSubmitting ? "등록 중..." : "등록하기"}
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}