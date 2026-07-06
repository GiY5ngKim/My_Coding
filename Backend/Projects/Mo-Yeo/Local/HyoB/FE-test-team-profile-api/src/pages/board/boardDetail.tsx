import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import backIcon from "../../assets/back.svg";
import { apiClient } from '../../api/client'; // ✅ 작성하신 apiClient 임포트

interface PostDetail {
  id: string | number;
  title: string;
  content: string;
  author: string;
  time: string;
  likeCount: number;
  commentCount: number;
}

const BoardDetailPage: React.FC = () => {
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();
  
  // 1. 상태 관리
  const [post, setPost] = useState<PostDetail | null>(null);
  const [isEditing, setIsEditing] = useState(false);
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  // 2. 수정용 로컬 상태
  const [editedTitle, setEditedTitle] = useState("");
  const [editedContent, setEditedContent] = useState("");

  // 3. API: 상세 데이터 가져오기
  const fetchPostDetail = async () => {
    try {
      setIsLoading(true);
      const response = await apiClient.get(`/api/board/${id}`);
      setPost(response.data);
      setEditedTitle(response.data.title);
      setEditedContent(response.data.content);
    } catch (err) {
      console.error("상세 조회 실패:", err);
      alert("게시글을 불러올 수 없습니다.");
      navigate('/board');
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchPostDetail();
  }, [id]);

  // 4. API: 수정 요청
  const handleEditComplete = async () => {
    if (!editedTitle.trim() || !editedContent.trim()) {
      alert("제목과 내용을 모두 입력해주세요.");
      return;
    }

    try {
      await apiClient.put(`/api/board/${id}`, {
        title: editedTitle,
        content: editedContent,
      });
      alert("수정이 완료되었습니다.");
      // 서버 데이터를 다시 불러와 화면 갱신
      fetchPostDetail();
      setIsEditing(false);
    } catch (err) {
      console.error("수정 실패:", err);
      alert("수정 중 오류가 발생했습니다.");
    }
  };

  // 5. API: 삭제 요청
  const handleDelete = async () => {
    if (window.confirm("정말 삭제하시겠습니까?")) {
      try {
        await apiClient.delete(`/api/board/${id}`);
        alert("삭제되었습니다.");
        navigate('/board');
      } catch (err) {
        console.error("삭제 실패:", err);
        alert("삭제 중 오류가 발생했습니다.");
      }
    }
    setIsMenuOpen(false);
  };

  const handleEditStart = () => {
    setIsEditing(true);
    setIsMenuOpen(false);
  };

  const handleEditCancel = () => {
    if (post) {
      setEditedTitle(post.title);
      setEditedContent(post.content);
    }
    setIsEditing(false);
  };

  if (isLoading) return <div className="p-10 text-center font-bold text-[#5C7CFF]">로딩 중...</div>;
  if (!post) return null;

  return (
    <main 
      className="mx-auto min-h-screen w-full max-w-[430px] bg-[#F8FAFC] pb-[100px] font-sans relative text-left"
      onClick={() => isMenuOpen && setIsMenuOpen(false)}
    >
      <header className="border-b border-[#E5E7EB] bg-white sticky top-0 z-30">
        <div className="flex h-[96px] items-end px-[16px] pb-[20px] pt-[40px]">
          <button type="button" onClick={() => navigate(-1)}>
            <img src={backIcon} alt="뒤로가기" className="h-[24px] w-[24px]" />
          </button>
          <div className="flex flex-1 justify-center">
            <span className="text-[20px] font-bold leading-[28px] text-[#000000]">게시판</span>
          </div>
          <div className="w-[24px]" />
        </div>
      </header>

      <section className="px-[16px] pt-[20px]">
        <div className="rounded-[16px] border border-[#E2E8F0] bg-white p-[20px] shadow-sm relative">
          
          <div className="flex items-center justify-between mb-[16px]">
            <div className="flex items-center gap-[10px]">
              <div className="h-[36px] w-[36px] rounded-full bg-[#5C7CFF] flex items-center justify-center text-white text-[14px] font-bold">
                {post.author[0]}
              </div>
              <div className="flex flex-col">
                <span className="text-[15px] font-bold text-[#1E293B]">{post.author}</span>
                <span className="text-[12px] text-[#94A3B8]">{post.time}</span>
              </div>
            </div>
            
            <div className="relative">
              <button 
                className="flex flex-col gap-[3px] p-[10px] hover:bg-gray-50 rounded-full transition-colors"
                onClick={(e) => {
                  e.stopPropagation();
                  setIsMenuOpen(!isMenuOpen);
                }}
              >
                <div className="w-[4px] h-[4px] bg-[#94A3B8] rounded-full"></div>
                <div className="w-[4px] h-[4px] bg-[#94A3B8] rounded-full"></div>
                <div className="w-[4px] h-[4px] bg-[#94A3B8] rounded-full"></div>
              </button>

              {isMenuOpen && (
                <div className="absolute right-0 mt-1 w-[120px] bg-white border border-[#E2E8F0] rounded-[12px] shadow-xl z-40 overflow-hidden text-[14px]">
                  {!isEditing ? (
                    <>
                      <button onClick={handleEditStart} className="w-full px-4 py-3 text-left font-medium text-[#475569] hover:bg-[#F8FAFC] border-b border-[#F1F5F9]">
                        수정하기
                      </button>
                      <button onClick={handleDelete} className="w-full px-4 py-3 text-left font-medium text-[#EF4444] hover:bg-[#FEF2F2]">
                        삭제하기
                      </button>
                    </>
                  ) : (
                    <>
                      <button onClick={handleEditComplete} className="w-full px-4 py-3 text-left font-bold text-[#5C7CFF] hover:bg-[#F8FAFC] border-b border-[#F1F5F9]">
                        수정완료
                      </button>
                      <button onClick={handleEditCancel} className="w-full px-4 py-3 text-left font-medium text-[#64748B] hover:bg-gray-50">
                        취소하기
                      </button>
                    </>
                  )}
                </div>
              )}
            </div>
          </div>

          {!isEditing ? (
            <>
              <h2 className="text-[18px] font-bold text-[#0F172A] mb-[12px] leading-snug">{post.title}</h2>
              <p className="text-[15px] leading-[1.6] text-[#334155] mb-[24px] whitespace-pre-wrap">
                {post.content}
              </p>
            </>
          ) : (
            <div className="flex flex-col gap-3 mb-6">
              <input 
                type="text"
                value={editedTitle}
                onChange={(e) => setEditedTitle(e.target.value)}
                className="w-full text-[16px] font-bold p-3 border border-[#E2E8F0] rounded-[10px] focus:outline-none focus:border-[#5C7CFF] bg-[#F9FAFB]"
                placeholder="제목을 입력하세요"
              />
              <textarea 
                value={editedContent}
                onChange={(e) => setEditedContent(e.target.value)}
                rows={8}
                className="w-full text-[15px] p-3 border border-[#E2E8F0] rounded-[10px] focus:outline-none focus:border-[#5C7CFF] bg-[#F9FAFB] resize-none leading-relaxed"
                placeholder="내용을 입력하세요"
              />
            </div>
          )}

          {!isEditing && (
            <div className="flex items-center justify-between border-t border-[#F1F5F9] pt-[16px]">
              <div className="flex gap-[16px]">
                <button className="flex items-center gap-[6px] text-[13px] font-semibold text-[#64748B]">
                  <span>❤️</span> {post.likeCount}
                </button>
                <button className="flex items-center gap-[6px] text-[13px] font-semibold text-[#64748B]">
                  <span>💬</span> {post.commentCount}
                </button>
              </div>
            </div>
          )}
        </div>
      </section>
    </main>
  );
};

export default BoardDetailPage;