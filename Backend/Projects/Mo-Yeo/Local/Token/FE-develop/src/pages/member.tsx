import { useEffect, useRef, useState } from "react";
import TeamMemberCard from "../components/memberCard";
import backIcon from "../assets/back.svg";
import plusIcon from "../assets/plus.svg";
import closeIcon from "../assets/close.svg";
import FieldLabel from "../components/fieldLabel";

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

const members = [
  {
    id: 1,
    name: "김백엔드",
    role: "Backend Developer",
    description:
      "Java/Spring 백엔드 개발자입니다. MSA 아키텍처에 관심이 많습니다.",
    techStacks: ["Spring", "MySQL", "AWS", "Docker"],
    rating: 95,
    profileInitial: "김",
    isBookmarked: false,
    githubUrl: "https://github.com/",
  },
  {
    id: 2,
    name: "이프론트",
    role: "Frontend Developer",
    description: "UI/UX에 관심 많은 프론트엔드 개발자입니다.",
    techStacks: ["React", "TypeScript", "Next.js", "Tailwind"],
    rating: 92,
    profileInitial: "이",
    isBookmarked: true,
    githubUrl: "https://github.com/",
  },
  {
    id: 3,
    name: "박풀스택",
    role: "Full Stack Developer",
    description: "풀스택 개발에 도전하고 있습니다!",
    techStacks: ["MERN Stack", "GraphQL", "PostgreSQL"],
    rating: 88,
    profileInitial: "박",
    isBookmarked: false,
    githubUrl: "https://github.com/",
  },
];

export default function TeamMemberPage() {
  const [selectedCategory, setSelectedCategory] = useState<Category>("전체");
  const [isRegisterOpen, setIsRegisterOpen] = useState(false);
  const [selectedInterest, setSelectedInterest] =
    useState<InterestCategory>("전체");
  const [sheetWidth, setSheetWidth] = useState<number>(430);

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

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("등록 데이터", { ...form, interest: selectedInterest });
    setIsRegisterOpen(false);
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
                onClick={() => setSelectedCategory(category)}
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
          3명의 팀원
        </p>

        <div className="flex flex-col gap-3">
          {members.map((member) => (
            <TeamMemberCard
              key={member.id}
              name={member.name}
              role={member.role}
              description={member.description}
              techStacks={member.techStacks}
              rating={member.rating}
              profileInitial={member.profileInitial}
              isBookmarked={member.isBookmarked}
              githubUrl={member.githubUrl}
            />
          ))}
        </div>
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
            transform: `translateX(-50%) translateY(${isRegisterOpen ? "0" : "100%"})`,
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
                  className="h-[45px] flex-1 rounded-[10px] border border-[#E2E8F0] bg-white text-[14px] font-medium text-[#314158]"
                >
                  취소
                </button>

                <button
                  type="submit"
                  className="h-[45px] flex-1 rounded-[10px] bg-gradient-to-r from-[#00A6F4] to-[#2B7FFF] text-[14px] font-semibold text-white"
                >
                  등록하기
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
