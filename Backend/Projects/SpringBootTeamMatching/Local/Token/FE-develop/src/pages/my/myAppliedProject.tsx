import { useState } from "react";
import { useNavigate } from "react-router-dom";
import backIcon from "../../assets/back.svg";
import ProjectCard from "../../components/projectCard";

type AppliedProject = {
  id: number;
  category: string;
  dDay: string;
  title: string;
  description: string;
  recruitCount: number;
  techStacks: string[];
  writer: string;
  department: string;
};

export default function MyAppliedProject() {
  const navigate = useNavigate();

  const [appliedProjects, setAppliedProjects] = useState<AppliedProject[]>([
    {
      id: 1,
      category: "공모전",
      dDay: "D-7",
      title: "2026 ICT 공모전 팀원 모집",
      description: "프론트엔드 중심으로 함께 공모전을 준비할 팀원을 모집합니다.",
      recruitCount: 5,
      techStacks: ["React", "TypeScript", "Figma"],
      writer: "김가톨릭",
      department: "미디어기술콘텐츠학과",
    },
    {
      id: 2,
      category: "스터디",
      dDay: "D-3",
      title: "정처기 한방 합격 스터디",
      description: "정처기 필기와 실기를 함께 준비할 스터디원을 구합니다.",
      recruitCount: 6,
      techStacks: ["CS", "알고리즘", "정처기"],
      writer: "릴리",
      department: "미디어기술콘텐츠학과",
    },
    {
      id: 3,
      category: "프로젝트",
      dDay: "D-10",
      title: "AI 면접 서비스 MVP 개발",
      description: "프론트엔드와 기획 중심으로 빠르게 MVP를 제작할 팀원을 찾고 있습니다.",
      recruitCount: 4,
      techStacks: ["React", "Vite", "OpenAI API"],
      writer: "이영희",
      department: "컴퓨터정보공학부",
    },
  ]);

  const [selectedProjectId, setSelectedProjectId] = useState<number | null>(null);

  const handleOpenCancelModal = (projectId: number) => {
    setSelectedProjectId(projectId);
  };

  const handleCloseCancelModal = () => {
    setSelectedProjectId(null);
  };

  const handleConfirmCancel = () => {
    if (selectedProjectId === null) return;

    setAppliedProjects((prev) =>
      prev.filter((project) => project.id !== selectedProjectId)
    );
    setSelectedProjectId(null);
  };

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
                내가 지원한 프로젝트
              </span>
            </div>

            <div className="h-[24px] w-[24px]" />
          </div>
        </header>

        <section className="px-[16px] pt-[16px]">
          <div className="flex flex-col gap-[12px]">
            {appliedProjects.map((project) => (
              <ProjectCard
                key={project.id}
                category={project.category}
                dDay={project.dDay}
                title={project.title}
                description={project.description}
                recruitCount={project.recruitCount}
                techStacks={project.techStacks}
                writer={project.writer}
                department={project.department}
                buttonLabel="지원취소"
                onButtonClick={() => handleOpenCancelModal(project.id)}
              />
            ))}

            {appliedProjects.length === 0 && (
              <div className="rounded-[14px] border border-[#E2E8F0] bg-white px-[16px] py-[18px] text-center text-[14px] font-medium leading-[20px] text-[#64748B] shadow-[0_2px_8px_rgba(15,23,42,0.06)]">
                지원한 프로젝트가 없습니다.
              </div>
            )}
          </div>
        </section>
      </main>

      {selectedProjectId !== null && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 px-[24px]">
          <div className="w-full max-w-[320px] rounded-[16px] bg-white px-[20px] py-[24px] shadow-lg">
            <p className="text-center text-[16px] font-semibold leading-[24px] text-[#111827]">
              지원을 취소하시겠습니까?
            </p>

            <div className="mt-[20px] flex gap-[10px]">
              <button
                type="button"
                onClick={handleCloseCancelModal}
                className="flex-1 rounded-[10px] border border-[#D1D5DB] bg-white py-[12px] text-[14px] font-medium text-[#374151]"
              >
                아니오
              </button>

              <button
                type="button"
                onClick={handleConfirmCancel}
                className="flex-1 rounded-[10px] bg-[#2F6BFF] py-[12px] text-[14px] font-semibold text-white"
              >
                예
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}