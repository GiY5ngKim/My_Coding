import React, { useState } from 'react';
import { apiClient } from '../../api/client';
import closeIcon from "../../assets/close.svg"; // 닫기 아이콘이 있다면 임포트

interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  onRefresh: () => void; // 등록 성공 후 리스트 갱신용
}

const BoardWriteModal: React.FC<ModalProps> = ({ isOpen, onClose, onRefresh }) => {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!title.trim() || !content.trim()) {
      alert("제목과 내용을 모두 입력해주세요.");
      return;
    }

    try {
      setIsSubmitting(true);
      // ✅ 백엔드 API 엔드포인트 (팀원분과 상의한 주소로 변경하세요)
      await apiClient.post('/api/board/write', {
        title: title,
        content: content
      });

      alert("게시글이 성공적으로 등록되었습니다!");
      setTitle(''); // 입력창 초기화
      setContent('');
      onRefresh(); // 부모 컴포넌트의 fetchPosts 실행
      onClose();   // 모달 닫기
    } catch (err) {
      console.error("등록 실패:", err);
      alert("등록 중 오류가 발생했습니다.");
    } finally {
      setIsSubmitting(false);
    }
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-end justify-center bg-black/50 sm:items-center">
      {/* 바텀시트 느낌의 모달 */}
      <div className="w-full max-w-[430px] animate-slide-up rounded-t-[24px] bg-white p-6 shadow-2xl sm:rounded-[24px]">
        <div className="mb-6 flex items-center justify-between">
          <h2 className="text-[20px] font-bold text-[#111827]">새 게시글 작성</h2>
          <button onClick={onClose} className="p-1">
            <img src={closeIcon} alt="닫기" className="h-6 w-6" />
          </button>
        </div>

        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <input
            type="text"
            placeholder="제목을 입력하세요"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="h-[52px] w-full rounded-[14px] border border-[#E2E8F0] px-4 text-[16px] outline-none focus:border-[#5C7CFF]"
          />
          
          <textarea
            placeholder="내용을 입력하세요..."
            rows={8}
            value={content}
            onChange={(e) => setContent(e.target.value)}
            className="w-full resize-none rounded-[14px] border border-[#E2E8F0] p-4 text-[15px] outline-none focus:border-[#5C7CFF]"
          />

          <div className="mt-2 flex gap-3">
            <button
              type="button"
              onClick={onClose}
              className="h-[56px] flex-1 rounded-[16px] bg-[#F1F5F9] font-bold text-[#64748B]"
            >
              취소
            </button>
            <button
              type="submit"
              disabled={isSubmitting}
              className="h-[56px] flex-1 rounded-[16px] bg-[#5C7CFF] font-bold text-white shadow-lg shadow-blue-200 disabled:opacity-50"
            >
              {isSubmitting ? "등록 중..." : "등록하기"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default BoardWriteModal;