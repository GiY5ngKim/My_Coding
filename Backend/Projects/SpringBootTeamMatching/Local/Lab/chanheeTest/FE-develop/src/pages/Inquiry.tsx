import { useState } from "react";
import { useNavigate } from "react-router-dom";
import backIcon from "../assets/back.svg"; // 기존 경로 유지

export default function InquiryPage() {
  const navigate = useNavigate();
  const [inquiryType, setInquiryType] = useState("");

  const types = ["기능 문의", "버그 신고", "개선 제안", "계정 문제", "신고하기", "기타"];

  return (
    <main className="mx-auto min-h-screen w-full max-w-[430px] bg-[#F8FAFC]">
      {/* Header */}
      <header className="border-b-[0.8px] border-[#E5E7EB] bg-white">
        <div className="flex h-[96px] items-end px-[16px] pb-[20px] pt-[40px]">
          <button type="button" onClick={() => navigate(-1)}>
            <img src={backIcon} alt="뒤로가기" className="h-[24px] w-[24px]" />
          </button>
          <div className="flex flex-1 justify-center">
            <span className="text-[20px] font-bold text-[#1E293B]">문의하기</span>
          </div>
          <div className="h-[24px] w-[24px]" />
        </div>
      </header>

      <div className="flex flex-col gap-[20px] px-[20px] py-[24px]">
        {/* Support Center Card */}
        <section className="rounded-[12px] bg-[#5B76F2] p-[20px] text-white shadow-sm">
          <h2 className="mb-[12px] text-[18px] font-bold">MO-YEO 지원센터</h2>
          <div className="flex flex-col gap-[6px] text-[14px] opacity-90">
            <p className="flex items-center gap-[8px]">📧 support@codemate.catholic.ac.kr</p>
            <p className="flex items-center gap-[8px]">📞 010-1234-5678</p>
            <p className="flex items-center gap-[8px]">🕒 평일 09:00 - 18:00 운영</p>
          </div>
        </section>

        {/* Inquiry Form */}
        <section className="flex flex-col gap-[24px] rounded-[16px] border border-[#E2E8F0] bg-white p-[20px]">
          {/* Type Selection */}
          <div>
            <label className="mb-[12px] block text-[15px] font-bold text-[#334155]">문의 유형</label>
            <div className="grid grid-cols-2 gap-[8px]">
              {types.map((type) => (
                <button
                  key={type}
                  onClick={() => setInquiryType(type)}
                  className={`h-[48px] rounded-[8px] border text-[14px] transition-all ${
                    inquiryType === type
                      ? "border-[#5B76F2] bg-[#F0F4FF] font-bold text-[#5B76F2]"
                      : "border-[#E2E8F0] bg-[#F8FAFC] text-[#64748B]"
                  }`}
                >
                  {type}
                </button>
              ))}
            </div>
          </div>

          {/* Title Input */}
          <div>
            <label className="mb-[12px] block text-[15px] font-bold text-[#334155]">제목</label>
            <input
              type="text"
              placeholder="제목을 입력해주세요"
              className="w-full rounded-[8px] border border-[#E2E8F0] bg-[#F8FAFC] px-[16px] py-[14px] text-[14px] outline-none focus:border-[#5B76F2]"
            />
          </div>

          {/* Content Input */}
          <div>
            <label className="mb-[12px] block text-[15px] font-bold text-[#334155]">문의 내용</label>
            <div className="relative">
              <textarea
                placeholder="문의하실 내용을 입력해주세요&#10;&#10;• 발생한 문제나 상황을 구체적으로 설명해주세요&#10;• 관련 스크린샷이 있다면 함께 첨부해주세요"
                className="h-[200px] w-full resize-none rounded-[8px] border border-[#E2E8F0] bg-[#F8FAFC] px-[16px] py-[14px] text-[14px] outline-none focus:border-[#5B76F2]"
              />
              <span className="absolute bottom-[12px] right-[16px] text-[12px] text-[#94A3B8]">0 / 1000자</span>
            </div>
          </div>

          {/* User Info */}
          <div className="rounded-[8px] bg-[#F8FAFC] p-[16px]">
            <div className="flex justify-between mb-[8px]">
              <span className="text-[14px] text-[#64748B]">이름</span>
              <span className="text-[14px] font-medium text-[#1E293B]">김가톨릭</span>
            </div>
            <div className="flex justify-between">
              <span className="text-[14px] text-[#64748B]">이메일</span>
              <span className="text-[14px] font-medium text-[#1E293B]">name@catholic.ac.kr</span>
            </div>
            <p className="mt-[12px] text-[11px] text-[#94A3B8]">답변은 등록하신 이메일로 발송됩니다.</p>
          </div>

          {/* Submit Button */}
          <button
            type="submit"
            className="h-[56px] w-full rounded-[12px] bg-[#5B76F2] text-[16px] font-bold text-white shadow-lg active:scale-[0.98] transition-transform"
          >
            문의하기
          </button>
        </section>
      </div>
    </main>
  );
}