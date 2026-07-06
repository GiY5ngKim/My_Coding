import { useNavigate } from "react-router-dom";
import backIcon from "../../assets/back.svg";
import { PATH } from "../../components/path";
import ProjectCard from "../../components/projectCard";

export default function myRecruitedProject() {
  const navigate = useNavigate();

  const recruitedProjects = [
    {
      id: 1,
      category: "공모전",
      dDay: "D-709",
      title: "부천시 영상 공모전 같이 하실 분",
      description:
        "저는 에펙 사용할 줄 알아요! 이왕이면 에펙 사용 가능 하신분을 구합니다 입상을 목표로 열심히 해봐요",
      recruitCount: 5,
      techStacks: ["Python", "TensorFlow", "FastAPI"],
      writer: "박머신",
      department: "미디어기술콘텐츠학과",
    },
  ];

  return (
    <main className="mx-auto min-h-screen w-full max-w-[430px] bg-[#F8FAFC] pb-[88px]">
      <header className="border-b border-[#E5E7EB] bg-white">
        <div className="flex h-[96px] items-end px-[16px] pb-[20px] pt-[40px]">
          <button type="button" onClick={() => navigate(-1)}>
            <img src={backIcon} alt="뒤로가기" className="h-[24px] w-[24px]" />
          </button>

          <div className="flex flex-1 justify-center">
            <span className="text-[20px] font-bold leading-[28px] text-[#000000]">
              내가 모집한 프로젝트
            </span>
          </div>

          <div className="h-[24px] w-[24px]" />
        </div>
      </header>

      <section className="px-[16px] py-[16px]">
        <div className="flex flex-col gap-[12px]">
          {recruitedProjects.map((project) => (
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
            buttonLabel="지원자 확인하기"
            onButtonClick={() => navigate("/my/recruited-projects/applicants")}
            />
          ))}
        </div>
      </section>
    </main>
  );
}