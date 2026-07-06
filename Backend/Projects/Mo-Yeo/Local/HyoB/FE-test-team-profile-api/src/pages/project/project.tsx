import { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import backIcon from "../../assets/back.svg";
import plusIcon from "../../assets/plus.svg";
import closeIcon from "../../assets/close.svg";
import FieldLabel from "../../components/fieldLabel";
import Input from "../../components/input";
import Textarea from "../../components/textarea";
import ProjectCard from "../../components/projectCard";

const menu = ["전체", "수업", "프로젝트", "공모전", "스터디"] as const;
const tagMenu = ["전체", "기획", "개발", "디자인", "마케팅", "기타"] as const;

const ProjectPage = () => {
  const [selectMenu, setSelectMenu] = useState<(typeof menu)[number]>("전체");
  const [selectTagMenu, setSelectTagMenu] =
    useState<(typeof tagMenu)[number]>("전체");

  const [sheetWidth, setSheetWidth] = useState<number>(430);
  const wrapperRef = useRef<HTMLDivElement | null>(null);

  const [isRegisterOpen, setIsRegisterOpen] = useState(false);
  const [isApplyOpen, setIsApplyOpen] = useState(false);

  const handleOpenSheet = (type: "register" | "apply") => {
    if (wrapperRef.current) {
      setSheetWidth(wrapperRef.current.offsetWidth);
    }

    if (type === "register") {
      setIsRegisterOpen(true);
    } else {
      setIsApplyOpen(true);
    }
  };

  const handleCloseSheet = (type: "register" | "apply") => {
    if (type === "register") {
      setIsRegisterOpen(false);
    } else {
      setIsApplyOpen(false);
    }
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const data = Object.fromEntries(formData.entries());

    const finalData = {
      ...data,
    };

    console.log("최종 데이터:", finalData);
  };

  const navigate = useNavigate();

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
    const isOpen = isRegisterOpen || isApplyOpen;
    document.body.style.overflow = isOpen ? "hidden" : "";
    return () => {
      document.body.style.overflow = "";
    };
  }, [isRegisterOpen, isApplyOpen]);

  return (
    <div className="flex min-h-full flex-col" ref={wrapperRef}>
      {/* 헤더 */}
      <header className="border-b border-[#E5E7EB] bg-white">
        <div className="flex flex-col justify-center gap-4 px-[16px] pt-[40px] pb-[20px]">
          <div className="flex items-center">
            <button
              type="button"
              className="flex h-[24px] w-[24px] items-center justify-center cursor-pointer"
              onClick={() => navigate(-1)}
            >
              <img
                src={backIcon}
                alt="뒤로가기"
                className="h-[24px] w-[24px]"
              />
            </button>

            <div className="flex flex-1 justify-center">
              <span className="text-[20px] font-bold leading-[28px] text-[#000000]">
                프로젝트
              </span>
            </div>

            <button
              type="button"
              onClick={() => handleOpenSheet("register")}
              className="flex h-[36px] w-[36px] shrink-0 cursor-pointer items-center justify-center rounded-[10px] bg-[#356AE6]"
            >
              <img src={plusIcon} alt="추가" className="h-[20px] w-[20px]" />
            </button>
          </div>

          <input
            className="h-11 rounded-lg border border-[#E5E7EB] p-3 focus:outline-none"
            placeholder="프로젝트 검색..."
          />
        </div>
      </header>

      <div className="flex gap-2 border-b border-[#E5E7EB] bg-[#F9FAFB] px-4 py-2">
        {menu.map((menuItem) => {
          return (
            <button
              key={menuItem}
              type="button"
              className={`cursor-pointer text-[14px] font-bold ${
                menuItem === selectMenu ? "text-[#356AE6]" : "text-[#4A5565]"
              }`}
              onClick={() => setSelectMenu(menuItem)}
            >
              {menuItem}
            </button>
          );
        })}
      </div>

      <div className="flex gap-2 border-b border-[#E5E7EB] bg-[#F9FAFB] px-4 py-2">
        {tagMenu.map((tag) => (
          <button
            key={tag}
            type="button"
            className={`cursor-pointer rounded-xl border px-3 py-[6px] text-[12px] font-bold ${
              tag === selectTagMenu
                ? "border-[#356AE6] bg-[#356AE6] text-white"
                : "border-[#E5E7EB] bg-white text-[#111827]"
            }`}
            onClick={() => setSelectTagMenu(tag)}
          >
            {tag}
          </button>
        ))}
      </div>

      <div className="flex-1 bg-[#F9FAFB] px-5 py-4">
        <ProjectCard
          category="공모전"
          dDay="D-709"
          title="부천시 영상 공모전 같이 하실 분"
          description="저는 에펙 사용할 줄 알아요! 이왕이면 에펙 사용 가능 하신분을 구합니다 입상을 목표로 열심히 해봐요"
          recruitCount={5}
          techStacks={["Python", "TensorFlow", "FastAPI"]}
          writer="박머신"
          department="미디어과"
          buttonLabel="지원하기"
          onButtonClick={() => handleOpenSheet("apply")}
        />
      </div>

      {/* 프로젝트 등록 바텀시트 */}
      <RegisterSheet
        onClick={() => handleCloseSheet("register")}
        onSubmit={handleSubmit}
        isRegisterOpen={isRegisterOpen}
        sheetWidth={sheetWidth}
      />

      {/* 지원하기 바텀시트 */}
      <ApplySheet
        onClick={() => handleCloseSheet("apply")}
        onSubmit={handleSubmit}
        isApplyOpen={isApplyOpen}
        sheetWidth={sheetWidth}
      />
    </div>
  );
};

export default ProjectPage;

function RegisterSheet({
  onClick,
  onSubmit,
  isRegisterOpen,
  sheetWidth,
}: {
  onClick: () => void;
  onSubmit: React.FormEventHandler<HTMLFormElement>;
  isRegisterOpen: boolean;
  sheetWidth: number;
}) {
  const [selectMenu, setSelectMenu] = useState<(typeof menu)[number]>("전체");
  const [selectTagMenu, setSelectTagMenu] =
    useState<(typeof tagMenu)[number]>("전체");

  return (
    <div
      className={`fixed inset-0 z-50 transition-all duration-300 ${
        isRegisterOpen
          ? "pointer-events-auto bg-black/50"
          : "pointer-events-none bg-black/0"
      }`}
      onClick={onClick}
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
              프로젝트 등록
            </h2>

            <button
              type="button"
              onClick={onClick}
              className="flex h-6 w-6 cursor-pointer items-center justify-center"
            >
              <img src={closeIcon} alt="닫기" className="h-7 w-7" />
            </button>
          </div>
        </div>

        {/*프로젝트 등록 폼 */}
        <form
          onSubmit={onSubmit}
          className="flex max-h-[calc(100vh-96px)] flex-col bg-white"
        >
          <div className="flex flex-col gap-[10px] overflow-y-auto px-4 py-4">
            <FieldLabel label="프로젝트 제목" required={true} />
            <Input name="title" placeholder="예: 2024 공모전 팀원 모집" />

            <FieldLabel label="카테고리" />
            <div className="shrink-0 overflow-x-auto px-4 py-2">
              <div className="flex gap-2">
                {menu.map((tag) => (
                  <button
                    key={tag}
                    type="button"
                    className={`shrink-0 cursor-pointer rounded-xl border px-3 py-[6px] text-[12px] font-bold ${
                      tag === selectMenu
                        ? "border-[#356AE6] bg-[#356AE6] text-white"
                        : "border-[#E5E7EB] bg-white text-[#111827]"
                    }`}
                    onClick={() => setSelectMenu(tag)}
                  >
                    {tag}
                  </button>
                ))}
              </div>
            </div>

            <FieldLabel label="세부 카테고리" />
            <div className="shrink-0 overflow-x-auto px-4 py-2">
              <div className="flex gap-2">
                {tagMenu.map((tag) => (
                  <button
                    key={tag}
                    type="button"
                    className={`shrink-0 cursor-pointer rounded-xl border px-3 py-[6px] text-[12px] font-bold ${
                      tag === selectTagMenu
                        ? "border-[#356AE6] bg-[#356AE6] text-white"
                        : "border-[#E5E7EB] bg-white text-[#111827]"
                    }`}
                    onClick={() => setSelectTagMenu(tag)}
                  >
                    {tag}
                  </button>
                ))}
              </div>
            </div>
            <FieldLabel label="프로젝트 설명" required={true} />
            <Textarea
              name="description"
              placeholder="프로젝트에 대해 설명해주세요"
            />

            <FieldLabel label="필요한 툴 / 기술 스택" required={false} />
            <Input
              name="techStacks"
              placeholder="예: React, 포토샵, 노션 (쉼표로 구분해주세요!)"
            />

            <FieldLabel label="모집 마감일" required={true} />
            <Input name="deadline" placeholder="예: 2026/12/12" />

            <FieldLabel label="나의 정보" required={false} />
            <Input
              name="myInfo"
              placeholder="예: 박머신 미디어기술콘텐츠학과"
            />
          </div>

          <div className="border-t border-[#E2E8F0] bg-white px-5 py-5">
            <div className="flex gap-3">
              <button
                type="button"
                onClick={onClick}
                className="h-[45px] flex-1 cursor-pointer rounded-[10px] border border-[#E2E8F0] bg-white text-[14px] font-medium text-[#314158]"
              >
                취소
              </button>

              <button
                type="submit"
                className="h-[45px] flex-1 cursor-pointer rounded-[10px] bg-gradient-to-r from-[#00A6F4] to-[#2B7FFF] text-[14px] font-semibold text-white"
              >
                등록하기
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}

const fields = [
  {
    id: "nickname",
    title: "이름",
    placeholder: "김가톨릭",
    required: true,
  },
  {
    id: "role",
    title: "역할/포지션",
    placeholder: "예: 디자이너, 팀원, 발표자",
    required: true,
  },
  {
    id: "intro",
    title: "자기소개",
    placeholder: "자기소개를 입력하세요",
    required: true,
  },
  {
    id: "skill",
    title: "사용 가능한 툴 / 기술스택",
    placeholder: "예: React, 포토샵, 노션 (쉼표로 구분해주세요!)",
    required: true,
  },
  {
    id: "phone",
    title: "연락처",
    placeholder: "010-0000-0000",
    required: true,
  },
  {
    id: "email",
    title: "이메일",
    placeholder: "example@email.com",
    required: false,
  },
  {
    id: "githubUrl",
    title: "깃허브 주소",
    placeholder: "https://github.com/username",
    required: false,
  },
];

function ApplySheet({
  onClick,
  onSubmit,
  isApplyOpen,
  sheetWidth,
}: {
  onClick: () => void;
  onSubmit: React.FormEventHandler<HTMLFormElement>;
  isApplyOpen: boolean;
  sheetWidth: number;
}) {
  return (
    <div
      className={`fixed inset-0 z-50 transition-all duration-300 ${
        isApplyOpen
          ? "pointer-events-auto bg-black/50"
          : "pointer-events-none bg-black/0"
      }`}
      onClick={onClick}
    >
      <div
        className={`fixed bottom-0 left-1/2 z-[60] overflow-hidden rounded-t-[20px] bg-white transition-transform duration-300 ${
          isApplyOpen ? "translate-y-0" : "translate-y-full"
        }`}
        style={{
          width: `${sheetWidth}px`,
          transform: `translateX(-50%) translateY(${
            isApplyOpen ? "0" : "100%"
          })`,
        }}
        onClick={(e) => e.stopPropagation()}
      >
        {/* 시트 헤더 */}
        <div className="h-[68.8px] border-b border-[#E2E8F0] bg-white">
          <div className="flex h-full items-center justify-between px-5">
            <h2 className="text-[18px] font-bold leading-[32px] text-[#111827]">
              지원하기
            </h2>

            <button
              type="button"
              onClick={onClick}
              className="flex h-6 w-6 cursor-pointer items-center justify-center"
            >
              <img src={closeIcon} alt="닫기" className="h-7 w-7" />
            </button>
          </div>
        </div>

        {/* 지원하기 폼 */}
        <form
          onSubmit={onSubmit}
          className="flex max-h-[calc(100vh-96px)] flex-col overflow-y-auto bg-white"
        >
          <div className="flex flex-col gap-[10px] overflow-y-auto px-4 py-4">
            {fields.map((field) => (
              <div key={field.id} className="flex w-full flex-col gap-2">
                <FieldLabel label={field.title} required={field.required} />
                {field.id === "intro" ? (
                  <textarea
                    name={field.id}
                    className="h-27 w-full resize-none rounded-lg border border-[#E2E8F0] bg-white px-4 py-[14px] text-[14px] focus:outline-none"
                    placeholder={field.placeholder}
                  />
                ) : (
                  <input
                    name={field.id}
                    className="w-full rounded-lg border border-[#E2E8F0] bg-white px-4 py-[14px] text-[14px] focus:outline-none"
                    placeholder={field.placeholder}
                  />
                )}
              </div>
            ))}
          </div>

          {/* 하단 버튼 */}
          <div className="border-t border-[#E2E8F0] bg-white px-5 py-5">
            <div className="flex gap-3">
              <button
                type="button"
                onClick={onClick}
                className="h-[45px] flex-1 cursor-pointer rounded-[10px] border border-[#E2E8F0] bg-white text-[14px] font-medium text-[#314158]"
              >
                취소
              </button>

              <button
                type="submit"
                className="h-[45px] flex-1 cursor-pointer rounded-[10px] bg-gradient-to-r from-[#00A6F4] to-[#2B7FFF] text-[14px] font-semibold text-white"
              >
                등록하기
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}