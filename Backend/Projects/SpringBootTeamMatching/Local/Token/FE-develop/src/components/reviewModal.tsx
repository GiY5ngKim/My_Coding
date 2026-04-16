import { useEffect, useMemo, useState } from "react";
import closeIcon from "../assets/close.svg";

type ReviewMember = {
  id: number;
  name: string;
  role: string;
};

type ReviewValue = {
  memberId: number;
  rating: number;
  reviewText: string;
};

type ReviewModalProps = {
  isOpen: boolean;
  onClose: () => void;
  projectTitle: string;
  members: ReviewMember[];
  onSubmit?: (reviews: ReviewValue[]) => void;
};

export default function reviewModal({
  isOpen,
  onClose,
  projectTitle,
  members,
  onSubmit,
}: ReviewModalProps) {
  const [currentStep, setCurrentStep] = useState(0);
  const [reviewMap, setReviewMap] = useState<Record<number, ReviewValue>>({});

  useEffect(() => {
    if (!isOpen) return;

    const initialMap: Record<number, ReviewValue> = {};
    members.forEach((member) => {
      initialMap[member.id] = {
        memberId: member.id,
        rating: 0,
        reviewText: "",
      };
    });

    setCurrentStep(0);
    setReviewMap(initialMap);
  }, [isOpen, members]);

  const totalStep = members.length;
  const currentMember = members[currentStep];

  const currentReview = useMemo(() => {
    if (!currentMember) {
      return {
        memberId: 0,
        rating: 0,
        reviewText: "",
      };
    }

    return (
      reviewMap[currentMember.id] ?? {
        memberId: currentMember.id,
        rating: 0,
        reviewText: "",
      }
    );
  }, [currentMember, reviewMap]);

  if (!isOpen || !currentMember) return null;

  const isLastStep = currentStep === totalStep - 1;
  const isNextDisabled =
    currentReview.rating === 0 || currentReview.reviewText.trim().length === 0;

  const handleChangeRating = (rating: number) => {
    setReviewMap((prev) => ({
      ...prev,
      [currentMember.id]: {
        ...prev[currentMember.id],
        memberId: currentMember.id,
        rating,
      },
    }));
  };

  const handleChangeReviewText = (text: string) => {
    setReviewMap((prev) => ({
      ...prev,
      [currentMember.id]: {
        ...prev[currentMember.id],
        memberId: currentMember.id,
        reviewText: text,
      },
    }));
  };

  const handleNext = () => {
    if (isNextDisabled) return;

    if (isLastStep) {
      const result = members.map((member) => reviewMap[member.id]);
      onSubmit?.(result);
      onClose();
      return;
    }

    setCurrentStep((prev) => prev + 1);
  };

  const handlePrev = () => {
    if (currentStep === 0) return;
    setCurrentStep((prev) => prev - 1);
  };

  return (
    <div
      className="fixed inset-y-0 left-1/2 z-[100] w-full max-w-[430px] -translate-x-1/2 bg-black/45"
      onClick={onClose}
    >
      <div className="flex min-h-screen items-center justify-center px-[16px] py-[24px]">
        <div
          className="w-full overflow-hidden rounded-[24px] bg-white shadow-[0_12px_40px_rgba(15,23,42,0.18)]"
          onClick={(e) => e.stopPropagation()}
        >
          <div className="flex max-h-[calc(100vh-48px)] flex-col">
            <div className="border-b border-[#E5E7EB] px-[20px] pb-[18px] pt-[18px]">
              <div className="flex items-start justify-between gap-[12px]">
                <div className="min-w-0">
                  <h2 className="text-[18px] font-bold leading-[28px] text-[#0F172A]">
                    팀원 리뷰 작성
                  </h2>
                  <p className="mt-[6px] truncate text-[14px] leading-[20px] text-[#64748B]">
                    {projectTitle}
                  </p>
                </div>

                <button
                  type="button"
                  onClick={onClose}
                  className="flex h-[28px] w-[28px] items-center justify-center"
                >
                  <img src={closeIcon} alt="닫기" className="h-[20px] w-[20px]" />
                </button>
              </div>

              <div className="mt-[12px]">
                <div className="flex items-center gap-[8px]">
                  {members.map((_, index) => (
                    <div
                      key={index}
                      className="h-[6px] flex-1 overflow-hidden rounded-full bg-[#E2E8F0]"
                    >
                      <div
                        className={`h-full rounded-full ${
                          index <= currentStep ? "bg-[#2F6BFF]" : "bg-transparent"
                        }`}
                      />
                    </div>
                  ))}
                </div>

                <div className="mt-[8px] text-center text-[14px] leading-[20px] text-[#94A3B8]">
                  {currentStep + 1} / {totalStep}
                </div>
              </div>
            </div>

            <div className="overflow-y-auto px-[20px] py-[20px]">
              <div className="flex items-center gap-[14px]">
                <div className="flex h-[60px] w-[60px] shrink-0 items-center justify-center rounded-full bg-[linear-gradient(135deg,#2F6BFF_0%,#0EA5E9_100%)] text-[28px] font-bold text-white shadow-[0_8px_20px_rgba(47,107,255,0.28)]">
                  {currentMember.name.charAt(0)}
                </div>

                <div>
                  <div className="text-[18px] font-bold leading-[26px] text-[#111827]">
                    {currentMember.name}
                  </div>
                  <div className="text-[14px] leading-[20px] text-[#64748B]">
                    {currentMember.role}
                  </div>
                </div>
              </div>

              <div className="mt-[28px]">
                <label className="text-[16px] font-bold leading-[24px] text-[#111827]">
                  별점 평가 <span className="text-[#EF4444]">*</span>
                </label>

                <div className="mt-[16px] flex items-center justify-between">
                  {Array.from({ length: 5 }).map((_, index) => {
                    const starValue = index + 1;
                    const isFilled = starValue <= currentReview.rating;

                    return (
                      <button
                        key={starValue}
                        type="button"
                        onClick={() => handleChangeRating(starValue)}
                        className="flex h-[44px] w-[44px] items-center justify-center"
                      >
                        <svg
                          viewBox="0 0 24 24"
                          className={`h-[36px] w-[36px] ${
                            isFilled
                              ? "fill-[#2F6BFF] stroke-[#2F6BFF]"
                              : "fill-none stroke-[#B6C2D1]"
                          }`}
                          strokeWidth="1.8"
                        >
                          <path d="M12 3.8l2.52 5.1 5.63.82-4.08 3.98.96 5.61L12 16.64 6.97 19.31l.96-5.61-4.08-3.98 5.63-.82L12 3.8z" />
                        </svg>
                      </button>
                    );
                  })}
                </div>
              </div>

              <div className="mt-[28px]">
                <label className="text-[16px] font-bold leading-[24px] text-[#111827]">
                  추가 의견 <span className="text-[#EF4444]">*</span>
                </label>

                <textarea
                  value={currentReview.reviewText}
                  onChange={(e) => handleChangeReviewText(e.target.value)}
                  placeholder="함께 작업하면서 느낀 점을 자유롭게 작성해주세요."
                  className="mt-[12px] h-[108px] w-full resize-none rounded-[14px] border border-[#D7DFEA] px-[16px] py-[14px] text-[14px] leading-[20px] text-[#111827] placeholder:text-[#9CA3AF] focus:outline-none"
                />
              </div>
            </div>

            <div className="border-t border-[#E5E7EB] px-[20px] py-[16px]">
              <div className="flex gap-[10px]">
                <button
                  type="button"
                  onClick={handlePrev}
                  disabled={currentStep === 0}
                  className={`h-[48px] flex-1 rounded-[14px] text-[16px] font-bold ${
                    currentStep === 0
                      ? "bg-[#F1F5F9] text-[#94A3B8]"
                      : "border border-[#D7DFEA] bg-white text-[#334155]"
                  }`}
                >
                  이전
                </button>

                <button
                  type="button"
                  onClick={handleNext}
                  disabled={isNextDisabled}
                  className={`h-[48px] flex-1 rounded-[14px] text-[16px] font-bold ${
                    isNextDisabled
                      ? "bg-[#E2E8F0] text-[#94A3B8]"
                      : "bg-[#2F6BFF] text-white"
                  }`}
                >
                  {isLastStep ? "제출하기" : "다음"}
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}