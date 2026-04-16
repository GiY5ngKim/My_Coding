import React from 'react';
import { useNavigate } from 'react-router-dom';
// 팀원분이 만든 공통 컴포넌트 및 에셋
import PostCard from '../../components/PostCard'; 
import backIcon from "../../assets/back.svg";
// 1. 새로운 플러스 버튼 아이콘 임포트
import plusIcon from "../../assets/PlusButton.svg"; 

const Board: React.FC = () => {
  const navigate = useNavigate();

  const posts = [
    { id: 1, title: "오늘 도서관가실분", content: "5시부터 같이 공부하고 밥도먹어요 좋지👍", author: "꿀범", time: "14:31", likeCount: 5, commentCount: 2 },
    { id: 2, title: "쑤기님이랑 도서관가실분", content: "5시부터 같이 공부하고 밥도먹어요 전체 목록에서 보이는 내용은 최대 두줄로 해요...", author: "꿀범", time: "14:31", likeCount: 0, commentCount: 2 },
    { id: 3, title: "코드 리뷰좀...", content: 'System.out.println("Moyeo");', author: "꿀범", time: "14:31", likeCount: 1, commentCount: 2 },
    { id: 4, title: "코드 리뷰좀...", content: 'System.out.println("Moyeo");', author: "꿀범", time: "14:31", likeCount: 1, commentCount: 2 },
    { id: 5, title: "코드 리뷰좀...", content: 'System.out.println("Moyeo");', author: "꿀범", time: "14:31", likeCount: 1, commentCount: 2 },
    
  ];

  return (
    /* 마이페이지 규격(430px) 및 배경색 통일 */
    <main className="mx-auto min-h-screen w-full max-w-[430px] bg-[#F8FAFC] pb-[88px] font-sans">
      
      {/* 헤더: 마이페이지 h-[96px] 및 flex 구조와 100% 일치 */}
      <header className="border-b border-[#E5E7EB] bg-white sticky top-0 z-30">
        <div className="flex h-[96px] items-end px-[16px] pb-[20px] pt-[40px]">
          <button type="button" onClick={() => navigate(-1)}>
            <img src={backIcon} alt="뒤로가기" className="h-[24px] w-[24px]" />
          </button>

          <div className="flex flex-1 justify-center">
            <span className="text-[20px] font-bold leading-[28px] text-[#000000]">
              게시판
            </span>
          </div>

          {/* 2. 우측 글쓰기 버튼: PlussButton.svg 적용 */}
          {/* 정렬 유지를 위해 w-[36px] 박스 크기는 그대로 유지합니다. */}
          <button 
            type="button" 
            onClick={() => navigate('/board/write')}
            className="flex h-[25px] w-[25px] shrink-0 items-center justify-end transition-transform active:scale-90"
          >
            <img src={plusIcon} alt="글쓰기" className="h-[24px] w-[24px]" />
          </button>
        </div>
      </header>

      {/* 섹션 패딩 및 검색창 (마이페이지 스타일) */}
      <section className="px-[16px] pt-[16px]">
        <div className="relative mb-[16px]">
          <input 
            type="text" 
            placeholder="🔍 게시글 검색..." 
            className="w-full h-[48px] pl-12 pr-4 bg-white border border-[#E2E8F0] rounded-[14px] text-[15px] focus:outline-none focus:border-[#2563EB] shadow-sm transition-colors"
          />
        </div>

        {/* 게시글 리스트 */}
        <div className="flex flex-col gap-[12px]">
          {posts.map((post) => (
            <div 
              key={post.id} 
              onClick={() => navigate(`/board/${post.id}`)}
              className="cursor-pointer transition-transform active:scale-[0.98]"
            >
              <PostCard 
                title={post.title}
                content={post.content}
                author={post.author}
                time={post.time}
                likeCount={post.likeCount}
                commentCount={post.commentCount}
              />
            </div>
          ))}
        </div>
      </section>
    </main>
  );
};

export default Board;