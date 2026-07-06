import React from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '../components/button';

const Onboarding: React.FC = () => {
  const navigate = useNavigate();
  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-[#F8FAFC] font-sans overflow-x-hidden p-6">
      <div className="w-[382px] h-[812px] bg-white rounded-[40px] shadow-2xl border border-[#E5E7EB] px-8 pt-20 pb-10 flex flex-col items-center relative">

        {/* 로고 */}
        <div className="flex flex-col items-center mb-12">
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

        {/* 메인 카피 */}
        <div className="text-center mb-12">
          <h2 className="text-[34px] font-extrabold text-[#1F2937] leading-[1.25] tracking-tight">
            검증된 팀원을<br />
            만나는 가장<br />
            스마트한 방법
          </h2>

          <div className="mt-5 space-y-1">
            <p className="text-[#6A7282] text-[16px] font-medium tracking-tight">
              팀 빌딩부터
            </p>
            <p className="text-[#6A7282] text-[16px] font-medium tracking-tight">
              정보 공유까지
            </p>
          </div>
        </div>

        {/* 기능 카드 */}
        <div className="w-full space-y-4 mb-12">
          <div className="flex items-center p-[16.8px] bg-white rounded-[14px] shadow-sm border border-[#E5E7EB]">
            <div className="w-12 h-12 bg-[#F0F3FF] rounded-[12px] flex items-center justify-center mr-[16.8px] flex-shrink-0">
              <img
                src="/svgs/onboarding/Container2.svg"
                alt="팀 매칭"
                className="w-6 h-6"
              />
            </div>
            <div className="flex flex-col text-left">
              <p className="font-bold text-[#1E293B] text-[16px] leading-[1.1] tracking-tighter">
                팀 매칭
              </p>
              <p className="text-[13px] text-[#6A7282] mt-[2px] leading-[1.2] tracking-tight">
                최적의 팀원 찾기
              </p>
            </div>
          </div>

          <div className="flex items-center p-[16.8px] bg-white rounded-[14px] shadow-sm border border-[#E5E7EB]">
            <div className="w-12 h-12 bg-[#F5F3FF] rounded-[12px] flex items-center justify-center mr-[16.8px] flex-shrink-0">
              <img
                src="/svgs/onboarding/Container3.svg"
                alt="리뷰 문화"
                className="w-6 h-6"
              />
            </div>
            <div className="flex flex-col text-left">
              <p className="font-bold text-[#1E293B] text-[16px] leading-[1.1] tracking-tighter">
                리뷰 문화
              </p>
              <p className="text-[13px] text-[#6A7282] mt-[2px] leading-[1.2] tracking-tight break-keep">
                동료들과 함께 성장하는 커뮤니티
              </p>
            </div>
          </div>
        </div>

        {/* 버튼 영역 */}
        <div className="w-full">
          <Button
            variant="primary"
            size="lg"
            className="h-[64px] w-full rounded-[20px] bg-[#5C7CFF] flex items-center justify-center gap-2 font-bold shadow-lg shadow-blue-200/50 border-none"
            onClick={() => navigate('/login')}
          >

            <span style={{ color: 'white' }}>시작하기</span>
            
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
        </div>

      </div>

      <footer className="mt-8 text-[#6A7282] text-[13px] font-medium text-center">
        © 2026 MO-YEO. All rights reserved.
      </footer>

    </div>
  );
};

export default Onboarding;