import React, { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import backIcon from "../../assets/back.svg";
// 더보기 아이콘이 없다면 아래 코드로 대체해서 사용하세요.
// import moreIcon from "../../assets/more.svg"; 

const BoardDetailPage: React.FC = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  
  // 1. 상태 관리 추가
  const [isEditing, setIsEditing] = useState(false); // 수정 모드 여부
  const [isMenuOpen, setIsMenuOpen] = useState(false); // 더보기 메뉴 오픈 여부
  
  // 임시 데이터 (실제로는 useQuery의 data)
  const [post, setPost] = useState({
    id: id,
    title: "오늘 도서관가실분",
    content: "5시부터 같이 공부하고 밥도먹어요 좋지👍",
    author: "최노드",
    time: "02.10 14:31",
    likeCount: 5,
    commentCount: 2,
  });

  // 2. 수정용 로컬 상태 (임시 데이터를 복사)
  const [editedTitle, setEditedTitle] = useState(post.title);
  const [editedContent, setEditedContent] = useState(post.content);

  // 메뉴 핸들러
  const handleEditStart = () => {
    setIsEditing(true); // 수정 모드 진입
    setIsMenuOpen(false);
  };

  const handleEditCancel = () => {
    // 취소 시 입력했던 내용 초기화
    setEditedTitle(post.title);
    setEditedContent(post.content);
    setIsEditing(false);
    setIsMenuOpen(false);
  };

  const handleEditComplete = () => {
    // 실제로는 여기서 API 업데이트 호출 예정
    setPost({
      ...post,
      title: editedTitle,
      content: editedContent,
    });
    alert("수정이 완료되었습니다.");
    setIsEditing(false);
    setIsMenuOpen(false);
  };

  const handleDelete = () => {
    if (window.confirm("정말 삭제하시겠습니까?")) {
      alert("삭제되었습니다.");
      navigate('/board');
    }
    setIsMenuOpen(false);
  };

  return (
    <main 
      className="mx-auto min-h-screen w-full max-w-[430px] bg-[#F8FAFC] pb-[100px] font-sans relative text-left"
      onClick={() => isMenuOpen && setIsMenuOpen(false)} // 외부 클릭 시 메뉴 닫기
    >
      {/* 헤더: 규격 유지 */}
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

      {/* 게시글 영역 */}
      <section className="px-[16px] pt-[20px]">
        <div className="rounded-[16px] border border-[#E2E8F0] bg-white p-[20px] shadow-sm relative">
          
          {/* 유저 프로필 & 더보기 메뉴 */}
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
            
            {/* 더보기 버튼 (CSS로 구현) */}
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

              {/* 드롭다운 메뉴: 모드에 따라 내용 변경 */}
              {isMenuOpen && (
                <div className="absolute right-0 mt-1 w-[120px] bg-white border border-[#E2E8F0] rounded-[12px] shadow-xl z-40 overflow-hidden text-[14px]">
                  {!isEditing ? (
                    // 일반 모드 메뉴
                    <>
                      <button onClick={handleEditStart} className="w-full px-4 py-3 text-left font-medium text-[#475569] hover:bg-[#F8FAFC] border-b border-[#F1F5F9]">
                        수정하기
                      </button>
                      <button onClick={handleDelete} className="w-full px-4 py-3 text-left font-medium text-[#EF4444] hover:bg-[#FEF2F2]">
                        삭제하기
                      </button>
                    </>
                  ) : (
                    // 수정 모드 메뉴
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

          {/* 컨텐츠 영역: 모드에 따라 UI 전환 */}
          {!isEditing ? (
            // 일반 보기 모드
            <>
              <h2 className="text-[18px] font-bold text-[#0F172A] mb-[12px] leading-snug">{post.title}</h2>
              <p className="text-[15px] leading-[1.6] text-[#334155] mb-[24px] whitespace-pre-wrap">
                {post.content}
              </p>
            </>
          ) : (
            // ✨ 수정 모드 UI (Input/Textarea)
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

          {/* 하단 좋아요/댓글/스크랩 (수정 모드에서는 숨김 처리 권장) */}
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