import { useState } from "react";
import { useNavigate } from "react-router-dom";
import backIcon from "../../assets/back.svg";
import ProjectCard from "../../components/projectCard";
import ReviewModal from "../../components/reviewModal";

type ReviewMember = {
  id: number;
  name: string;
  role: string;
};

export default function myParticipatedProject() {
  const navigate = useNavigate();

  const [isReviewModalOpen, setIsReviewModalOpen] = useState(false);
  const [selectedProjectTitle, setSelectedProjectTitle] = useState("");
  const [selectedMembers, setSelectedMembers] = useState<ReviewMember[]>([]);

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

  const participatedProjects = [
    {
      id: 1,
      category: "스터디",
      dDay: "D-15",
      title: "2026 정처기 한방에 끝내자 스터디원 모집",
      description:
        "정처기 필기와 실기를 같이 준비할 스터디원을 구합니다. 성실하게 참여하실 분 환영합니다.",
      recruitCount: 5,
      techStacks: ["CS", "정처기", "스터디"],
      writer: "김가톨릭",
      department: "컴퓨터정보공학부",
      members: [
        { id: 1, name: "김철수", role: "Backend Developer" },
        { id: 2, name: "이영희", role: "Designer" },
        { id: 3, name: "박민수", role: "Frontend Developer" },
      ],
    },
    {
      id: 2,
      category: "공모전",
      dDay: "D-30",
      title: "2026 ICT 공모전 팀원 모집",
      description:
        "프론트엔드 중심으로 진행 중인 공모전 프로젝트입니다. 함께 완성도 있게 만들어갈 분을 찾습니다.",
      recruitCount: 4,
      techStacks: ["React", "TypeScript", "Figma"],
      writer: "박머신",
      department: "미디어기술콘텐츠학과",
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
                내가 참여한 프로젝트
              </span>
            </div>

            <div className="h-[24px] w-[24px]" />
          </div>
        </header>

        <section className="px-[16px] py-[16px]">
          <div className="flex flex-col gap-[12px]">
            {participatedProjects.map((project) => (
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
                buttonLabel="팀원 리뷰달기"
                onButtonClick={() =>
                  openReviewModal(project.title, project.members)
                }
              />
            ))}
          </div>
        </section>
      </main>

      <ReviewModal
        isOpen={isReviewModalOpen}
        onClose={closeReviewModal}
        projectTitle={selectedProjectTitle}
        members={selectedMembers}
        onSubmit={(reviews) => {
          console.log("전체보기 리뷰 제출 데이터:", reviews);
        }}
      />
    </>
  );
}