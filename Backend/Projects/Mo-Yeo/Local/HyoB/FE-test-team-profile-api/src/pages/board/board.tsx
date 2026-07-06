import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import PostCard from '../../components/PostCard'; 
import backIcon from "../../assets/back.svg";
import plusIcon from "../../assets/PlusButton.svg"; 
import { apiClient } from '../../api/client'; 
// ✅ 방금 만든 모달 임포트
import BoardWriteModal from './BoardWriteModal';

interface Post {
  id: number;
  title: string;
  content: string;
  author: string;
  time: string;
  likeCount: number;
  commentCount: number;
}

const Board: React.FC = () => {
  const navigate = useNavigate();
  
  const [posts, setPosts] = useState<Post[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  
  // ✅ 모달 오픈 상태 관리
  const [isModalOpen, setIsModalOpen] = useState(false);

  const fetchPosts = async () => {
    try {
      setIsLoading(true);
      const response = await apiClient.get('/api/board/list'); 
      setPosts(response.data);
    } catch (err: any) {
      console.error("게시글 불러오기 실패:", err);
      setError("게시글을 불러오는 중 문제가 발생했습니다.");
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchPosts();
  }, []);

  return (
    <main className="mx-auto min-h-screen w-full max-w-[430px] bg-[#F8FAFC] pb-[88px] font-sans relative">
      <header className="border-b border-[#E5E7EB] bg-white sticky top-0 z-30">
        <div className="flex h-[96px] items-end px-[16px] pb-[20px] pt-[40px]">
          <button type="button" onClick={() => navigate(-1)}>
            <img src={backIcon} alt="뒤로가기" className="h-[24px] w-[24px]" />
          </button>
          <div className="flex flex-1 justify-center">
            <span className="text-[20px] font-bold leading-[28px] text-[#000000]">게시판</span>
          </div>
          {/* ✅ 클릭 시 navigate 대신 모달 오픈! */}
          <button 
            type="button" 
            onClick={() => setIsModalOpen(true)}
            className="flex h-[25px] w-[25px] shrink-0 items-center justify-end transition-transform active:scale-90"
          >
            <img src={plusIcon} alt="글쓰기" className="h-[24px] w-[24px]" />
          </button>
        </div>
      </header>

      <section className="px-[16px] pt-[16px]">
        <div className="relative mb-[16px]">
          <input 
            type="text" 
            placeholder="🔍 게시글 검색..." 
            className="w-full h-[48px] pl-12 pr-4 bg-white border border-[#E2E8F0] rounded-[14px] text-[15px] focus:outline-none focus:border-[#2563EB] shadow-sm transition-colors"
          />
        </div>

        <div className="flex flex-col gap-[12px]">
          {isLoading ? (
            <div className="flex flex-col gap-3">
              {[1, 2, 3].map((n) => (
                <div key={n} className="w-full h-[120px] bg-white rounded-[20px] animate-pulse border border-gray-100" />
              ))}
            </div>
          ) : error ? (
            <div className="py-20 text-center text-gray-500">{error}</div>
          ) : posts.length === 0 ? (
            <div className="py-20 text-center text-gray-400 font-medium">등록된 게시글이 없습니다.</div>
          ) : (
            posts.map((post) => (
              <div 
                key={post.id} 
                onClick={() => navigate(`/board/${post.id}`)}
                className="cursor-pointer transition-transform active:scale-[0.98]"
              >
                <PostCard {...post} />
              </div>
            ))
          )}
        </div>
      </section>

      {/* ✅ 모달 컴포넌트 배치 */}
      <BoardWriteModal 
        isOpen={isModalOpen} 
        onClose={() => setIsModalOpen(false)} 
        onRefresh={fetchPosts} 
      />
    </main>
  );
};

export default Board;