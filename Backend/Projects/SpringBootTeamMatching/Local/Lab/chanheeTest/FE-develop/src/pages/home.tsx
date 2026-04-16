import React from "react";
import { useNavigate } from "react-router-dom";
import PostPreviewCard from "../components/PostPreviewCard";
import logo from "../assets/MO-YEOlogo.svg"; 
import projectIcon from "../assets/project.svg";
import postIcon from "../assets/post.svg";

// 1. 프로젝트 카드 컴포넌트 (팀원분 스타일 반영)
interface ProjectCardProps {
  title: string;
  author: string;
  members: number;
  time: string;
  tag: "Backend" | "Frontend" | "ML Engineer" | "Design";
}

const ProjectCard: React.FC<ProjectCardProps> = ({ title, author, members, time, tag }) => {
  const tagColor = {
    Backend: "bg-[#EFF6FF] text-[#1447E6]",
    Frontend: "bg-[#F0FDF4] text-[#16A34A]",
    "ML Engineer": "bg-[#FAF5FF] text-[#7E22CE]",
    Design: "bg-[#FFF7ED] text-[#EA580C]",
  };

  return (
    <div className="rounded-[14px] border border-[#E2E8F0] bg-white p-[20px] shadow-[0_2px_8px_rgba(15,23,42,0.06)] mb-3">
      <div className="flex justify-between items-start gap-3">
        <div className="flex-1 min-w-0">
          <h3 className="text-[16px] font-bold text-[#1E293B] truncate mb-1">{title}</h3>
          <p className="text-[12px] text-[#64748B]">작성자: {author}</p>
        </div>
        <span className={`${tagColor[tag]} text-[10px] px-2 py-1 rounded-[8px] font-bold shrink-0`}>
          {tag}
        </span>
      </div>
      <div className="mt-4 flex items-center justify-between">
        <div className="flex items-center gap-3 text-[#94A3B8] text-[12px]">
          <span className="flex items-center gap-1">👥 {members}명</span>
          <span className="flex items-center gap-1">🕒 {time}</span>
        </div>
        <button className="text-[12px] font-semibold text-[#2563EB]">자세히 보기</button>
      </div>
    </div>
  );
};

const Home: React.FC = () => {
  const navigate = useNavigate();

  return (
    /* 팀원분 MyPage 규격: 430px 고정 및 중앙 정렬 */
    <main className="mx-auto min-h-screen w-full max-w-[430px] bg-[#F8FAFC] pb-[100px] font-sans shadow-2xl relative">
      
      {/* 2. 블루 헤더 섹션 (로고 파일 사용 및 좌측 정렬) */}
      <header className="bg-[#5C7CFF] pt-14 pb-12 px-6 rounded-b-[40px] shadow-lg relative z-10">
        <div className="flex items-center justify-start mb-8 gap-3">
          {/* 이미지 태그로 깔끔하게 로고 삽입 */}
          <img 
            src={logo} 
            alt="MO-YEO Logo" 
            className="w-[35px] h-[35px] shrink-0" 
          />
          <h1 className="text-white text-[28px] font-black tracking-tighter italic leading-none">
            MO-YEO
          </h1>
        </div>
        
        {/* 검색바 */}
        <div className="relative">
          <input
            type="text"
            placeholder="프로젝트, 팀원 검색..."
            className="w-full h-[52px] px-12 rounded-[16px] bg-white shadow-inner focus:outline-none text-[15px] placeholder:text-gray-400"
          />
          <span className="absolute left-4 top-1/2 -translate-y-1/2 text-lg">🔍</span>
        </div>
      </header>

      {/* 3. 퀵 메뉴 (팀원 찾기 / 전체 게시판) */}
      <section className="grid grid-cols-2 gap-4 px-5 -mt-6 relative z-20">
        <button 
          onClick={() => navigate('/members')}
          className="bg-white p-5 rounded-[24px] shadow-[0_8px_20px_rgba(0,0,0,0.06)] flex flex-col items-start border border-[#E2E8F0] transition-all active:scale-95"
        >
          <div className="bg-[#EFF6FF] w-10 h-10 rounded-xl flex items-center justify-center mb-3 text-xl">👥</div>
          <span className="font-bold text-[15px] text-[#1E293B]">팀원 찾기</span>
          <span className="text-[11px] text-[#64748B] mt-0.5">최적의 팀 빌딩</span>
        </button>
        <button 
          onClick={() => navigate('/board')}
          className="bg-white p-5 rounded-[24px] shadow-[0_8px_20px_rgba(0,0,0,0.06)] flex flex-col items-start border border-[#E2E8F0] transition-all active:scale-95"
        >
          <div className="bg-[#F0FDF4] w-10 h-10 rounded-xl flex items-center justify-center mb-3 text-xl">💬</div>
          <span className="font-bold text-[15px] text-[#1E293B]">전체 게시판</span>
          <span className="text-[11px] text-[#64748B] mt-0.5">정보 주고받기</span>
        </button>
      </section>

      {/* 4. 최근 프로젝트 섹션 */}
      <section className="px-5 mt-10">
        <div className="flex justify-between items-center mb-4">
          <div className="flex items-center gap-2">
            <img src={projectIcon} alt="" className="w-5 h-5" />
            <h2 className="font-bold text-[18px] text-[#1E293B]">최근 프로젝트</h2>
          </div>
          <button className="text-[13px] text-[#2563EB] font-bold" onClick={() => navigate('/projects')}>
            전체보기
          </button>
        </div>
        <ProjectCard title="2026 ICT 공모전 팀원 모집" author="김가톨릭" members={5} time="2일 후" tag="Backend" />
        <ProjectCard title="졸업작품 프론트엔드 구인" author="이코딩" members={3} time="5일 후" tag="Frontend" />
      </section>

      {/* 5. 최근 게시글 섹션 (PostPreviewCard 사용) */}
      <section className="px-5 mt-10">
        <div className="flex justify-between items-center mb-4">
          <div className="flex items-center gap-2">
            <img src={postIcon} alt="" className="w-5 h-5" />
            <h2 className="font-bold text-[18px] text-[#1E293B]">실시간 게시글</h2>
          </div>
          <button className="text-[13px] text-[#2563EB] font-bold" onClick={() => navigate('/board')}>
            더보기
          </button>
        </div>
        <div className="flex flex-col gap-3">
          <PostPreviewCard title="같이 시험공부 하실 분 구함!" likeCount={12} commentCount={4} date="03.19 13:30" />
          <PostPreviewCard title="이번 프로젝트 백엔드 스택 추천좀" likeCount={8} commentCount={15} date="03.19 12:45" />
        </div>
      </section>
    </main>
  );
};

export default Home;