import { useNavigate } from "react-router-dom";
import backIcon from "../../assets/back.svg";
import PostCard from "../../components/PostCard";
import TeamMemberCard from "../../components/memberCard";
import ProjectCard from "../../components/projectCard";

type ScrapItem =
  | {
      id: number;
      type: "post";
      data: {
        title: string;
        content: string;
        author: string;
        time: string;
        likeCount: number;
        commentCount: number;
      };
    }
  | {
      id: number;
      type: "member";
      data: {
        name: string;
        role: string;
        description: string;
        techStacks: string[];
        githubLabel?: string;
        githubUrl?: string;
        rating: number;
        profileInitial?: string;
        isBookmarked?: boolean;
        onBookmarkClick?: () => void;
      };
    }
  | {
      id: number;
      type: "project";
      data: {
        category: string;
        dDay: string;
        title: string;
        description: string;
        recruitCount: number;
        techStacks: string[];
        writer: string;
        department: string;
        buttonLabel: string;
        onButtonClick?: () => void;
      };
    };

export default function MyScrap() {
  const navigate = useNavigate();

  const scrapItems: ScrapItem[] = [
    {
      id: 1,
      type: "post",
      data: {
        title: "오늘 도서관가실분",
        content: "5시부터 같이 공부하고 밥도먹어요",
        author: "꿀범",
        time: "14:31",
        likeCount: 1,
        commentCount: 2,
      },
    },
    {
      id: 2,
      type: "member",
      data: {
        name: "김가톨릭",
        role: "Frontend Developer",
        description: "안녕하세요! 프론트엔드와 UI 구현에 관심이 많고 React, TypeScript를 공부하고 있습니다.",
        techStacks: ["React", "TypeScript", "Tailwind"],
        githubLabel: "GitHub",
        githubUrl: "https://github.com/user",
        rating: 4.8,
        profileInitial: "김",
        isBookmarked: true,
        onBookmarkClick: () => console.log("member bookmark click"),
      },
    },
    {
      id: 3,
      type: "project",
      data: {
        category: "공모전",
        dDay: "D-10",
        title: "2026 ICT 공모전 팀원 모집",
        description: "프론트엔드 1명, 디자이너 1명 모집 중입니다. 함께 서비스 완성해보실 분 구해요.",
        recruitCount: 5,
        techStacks: ["React", "Figma", "TypeScript"],
        writer: "꿀범",
        department: "미디어기술콘텐츠학과",
        buttonLabel: "지원하기",
        onButtonClick: () => console.log("project button click"),
      },
    },
    {
      id: 4,
      type: "post",
      data: {
        title: "정처기 스터디 하실 분",
        content: "정처기 필기 같이 준비하실 분 구합니다. 꾸준히 참여 가능한 분 환영해요.",
        author: "릴리",
        time: "16:20",
        likeCount: 3,
        commentCount: 1,
      },
    },
    {
      id: 5,
      type: "member",
      data: {
        name: "이영희",
        role: "Backend Developer",
        description: "Spring 기반 서버 개발 경험이 있고 협업을 중요하게 생각합니다.",
        techStacks: ["Java", "Spring", "MySQL"],
        githubLabel: "GitHub",
        githubUrl: "https://github.com/backend-user",
        rating: 4.6,
        profileInitial: "이",
        isBookmarked: false,
        onBookmarkClick: () => console.log("member bookmark click"),
      },
    },
    {
      id: 6,
      type: "project",
      data: {
        category: "스터디",
        dDay: "D-3",
        title: "AI 면접 서비스 팀원 모집",
        description: "프론트엔드 중심으로 MVP를 빠르게 구현할 팀원을 찾고 있습니다.",
        recruitCount: 4,
        techStacks: ["React", "Vite", "OpenAI API"],
        writer: "야호",
        department: "미콘컴공",
        buttonLabel: "상세보기",
        onButtonClick: () => console.log("project button click"),
      },
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
              나의 스크랩
            </span>
          </div>

          <div className="h-[24px] w-[24px]" />
        </div>
      </header>

      <section className="px-[16px] pt-[16px]">
        <div className="flex flex-col gap-[12px]">
          {scrapItems.map((item) => {
            if (item.type === "post") {
              return <PostCard key={item.id} {...item.data} />;
            }

            if (item.type === "member") {
              return <TeamMemberCard key={item.id} {...item.data} />;
            }

            return <ProjectCard key={item.id} {...item.data} />;
          })}
        </div>
      </section>
    </main>
  );
}