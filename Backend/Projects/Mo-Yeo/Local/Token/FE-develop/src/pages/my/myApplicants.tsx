import { useNavigate } from "react-router-dom";
import backIcon from "../../assets/back.svg";
import ApplicantCard from "../../components/applicantCard";

export default function myApplicants() {
  const navigate = useNavigate();

  const applicants = [
    {
      id: 1,
      name: "김백엔드",
      role: "팀원",
      intro:
        "저도 이 공모전 관심 있습니다. 같이 하고 싶어요! 열심히 하겠습니다",
      techStacks: ["포토샵", "프리미어프로"],
      email: "ㅇㅇㅇ@naver.com",
      matchRate: 95,
      reviewScore: 4.0,
      reviewCount: 2,
      reviews: [
        "열정 넘치게 적극적으로 참여해주셨습니다. 다만, 하루도 빠짐없이 지각을 하셔서... 별 하나 깎았어요.",
        "굿굿 좋아용",
      ],
    },
    {
      id: 2,
      name: "김백엔드",
      role: "팀원",
      intro: "비슷한 프로젝트 해서 수상한 경험 있습니다",
      techStacks: ["포토샵", "프리미어프로"],
      email: "ㅇㅇㅇ@naver.com",
      matchRate: 95,
      githubLabel: "GitHub",
      reviewScore: 0.0,
      reviewCount: 0,
      reviews: [],
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
              지원자 확인하기
            </span>
          </div>

          <div className="h-[24px] w-[24px]" />
        </div>
      </header>

      <section className="px-[16px] py-[16px]">
        <div className="flex flex-col gap-[12px]">
          {applicants.map((applicant) => (
            <ApplicantCard
              key={applicant.id}
              name={applicant.name}
              role={applicant.role}
              intro={applicant.intro}
              techStacks={applicant.techStacks}
              email={applicant.email}
              matchRate={applicant.matchRate}
              githubLabel={applicant.githubLabel}
              reviewScore={applicant.reviewScore}
              reviewCount={applicant.reviewCount}
              reviews={applicant.reviews}
            />
          ))}
        </div>
      </section>
    </main>
  );
}