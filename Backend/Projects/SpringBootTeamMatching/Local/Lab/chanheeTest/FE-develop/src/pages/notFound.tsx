import React from 'react';
import { useNavigate } from 'react-router-dom';
import fourZeroFourIcon from "../assets/404.svg"; 
import faceIcon from "../assets/faceIcon.svg";

const NotFoundPage: React.FC = () => {
  const navigate = useNavigate();

  return (
    <main className="mx-auto min-h-screen w-full max-w-[430px] bg-[#F8FAFC] font-sans flex flex-col items-center justify-center px-[24px] shadow-2xl relative">
      
      <div className="mb-6">
        <img 
          src={fourZeroFourIcon} 
          alt="404 Error" 
          className="w-[200px] h-auto"
        />
      </div>
      <div className="text-center mb-12">
        <h1 className="text-[26px] font-bold text-[#1E293B] mb-3 tracking-tight">
          페이지를 찾을 수 없습니다
        </h1>
        <p className="text-[14px] text-[#64748B] leading-relaxed">
          요청하신 페이지가 존재하지 않거나<br />
          이동되었을 수 있습니다
        </p>
      </div>
      <div className="mb-16">
        <div className="w-[160px] h-[160px] bg-[#EFF6FF] rounded-full flex items-center justify-center">
          <img 
            src={faceIcon} 
            alt="Neutral Face" 
            className="w-[80px] h-auto" 
          />
        </div>
      </div>

      {/* 5. 버튼 섹션 */}
      <div className="w-full flex flex-col gap-3 mb-12">
        <button 
          onClick={() => navigate('/home')}
          className="w-full h-[56px] bg-[#5C7CFF] text-white rounded-[14px] font-bold flex items-center justify-center gap-2 shadow-lg shadow-blue-100 transition-all active:scale-[0.98]"
        >
          <span className="text-lg">🏠</span>
          홈으로 가기
        </button>

        <button 
          onClick={() => navigate(-1)}
          className="w-full h-[56px] bg-white text-[#5C7CFF] border border-[#E2E8F0] rounded-[14px] font-bold flex items-center justify-center gap-2 transition-all active:scale-[0.98]"
        >
          <span className="text-lg">←</span>
          이전 페이지로
        </button>
      </div>

      <footer className="text-[12px] text-[#94A3B8] font-medium text-center">
        문제가 지속되면 관리자에게 문의해주세요
      </footer>
    </main>
  );
};

export default NotFoundPage;