import React from 'react';
import Button from '../components/button';
import { useNavigate } from 'react-router-dom';

const Login: React.FC = () => {
    const navigate = useNavigate();
  return (

    <div className="flex flex-col items-center justify-center min-h-screen bg-[#F8FAFC] font-sans overflow-x-hidden p-6">
      <div className="w-[382px] h-[812px] bg-white rounded-[40px] shadow-2xl border border-[#E5E7EB] px-8 pt-20 pb-10 flex flex-col items-center relative">

        {/* 3. 로고 섹션 */}
        <div className="flex flex-col items-center mb-16">
          <div className="w-[72px] h-[72px] bg-[#5C7CFF] rounded-[24px] flex items-center justify-center mb-5 shadow-lg shadow-blue-100/60">
            <img
              src="/svgs/onboarding/Container.svg"
              alt="MO-YEO Logo"
              className="w-10 h-10 object-contain"
            />
          </div>

          <h1 className="text-[32px] font-black text-black tracking-tighter italic">
            MO-YEO
          </h1>

          <p className="text-[14px] text-black font-semibold mt-1 tracking-tight">
            가톨릭대학교 학생 전용
          </p>
        </div>

        {/* 4. 메인 카피 섹션 */}
        <div className="text-center mb-16">
          <h2 className="text-[34px] font-extrabold text-[#1F2937] leading-[1.25] tracking-tight">
            검증된 팀원을<br />
            만나는 가장<br />
            스마트한 방법
          </h2>

          <div className="mt-6 space-y-1">
            <p className="text-[#6A7282] text-[16px] font-medium tracking-tight">
              기술 스택 기반 팀 빌딩부터
            </p>
            <p className="text-[#6A7282] text-[16px] font-medium tracking-tight">
              코드 리뷰까지
            </p>
          </div>
        </div>

        <div className="mt-auto w-full flex flex-col items-center">
          <Button
            variant="primary"
            size="lg"
            className="h-[64px] w-full rounded-[20px] bg-[#5C7CFF] flex items-center justify-center gap-2 font-bold shadow-lg shadow-blue-200/50 border-none transition-all active:scale-[0.98]"
            onClick={() => navigate('/home')}
          >
            <span style={{ color: 'white' }}>구글 로그인</span>
            <svg
              width="20"
              height="20"
              viewBox="0 0 24 24"
              fill="none"
              stroke="white"
              strokeWidth="3"
              strokeLinecap="round"
              strokeLinejoin="round"
            >
              <path d="M5 12h14M12 5l7 7-7 7" />
            </svg>
          </Button>

          <p className="mt-6 text-[#6A7282] text-[12px] font-medium">
            가톨릭대학교 학생 전용 서비스입니다.
          </p>
        </div>
      </div>
      <footer className="mt-6 text-[#6A7282] text-[12px] font-medium text-center opacity-60">
        © 2026 MO-YEO. All rights reserved.
      </footer>

    </div>
  );
};

export default Login;