import { useParams, useNavigate } from "react-router-dom";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import backIcon from "../../assets/back.svg";
import { recruitsApi } from "../../api/recruits";
import { membersApi } from "../../api/member";

const ProjectDetailPage = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const recruitId = Number(id);

  // 현재 사용자 확인용
  const { data: profile } = useQuery({
    queryKey: ['myProfile'],
    queryFn: membersApi.getMyProfile
  });

  const { data: detail, isLoading } = useQuery({
    queryKey: ['recruits', recruitId],
    queryFn: () => recruitsApi.getRecruitDetail(recruitId),
    enabled: !!recruitId
  });

  const applyMutation = useMutation({
    mutationFn: () => recruitsApi.apply(recruitId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['recruits', recruitId] });
      alert("팀 지원이 완료되었습니다!");
    },
    onError: () => {
      alert("지원 처리에 실패했습니다. (마감되었거나 잘못된 요청)");
    }
  });

  const cancelApplyMutation = useMutation({
    mutationFn: () => recruitsApi.cancelApply(recruitId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['recruits', recruitId] });
      alert("지원이 취소되었습니다.");
    }
  });

  if (isLoading) {
    return (
      <div className="flex flex-col min-h-screen bg-[#F9FAFB] max-w-[430px] mx-auto w-full justify-center items-center">
        <p className="text-center text-[#64748B] py-10 font-medium animate-pulse">프로세스 불러오는 중...</p>
      </div>
    );
  }

  if (!detail) {
    return (
      <div className="flex flex-col min-h-screen bg-[#F9FAFB] max-w-[430px] mx-auto w-full items-center justify-center">
        <p className="text-[#64748B] text-[15px] font-medium">프로젝트를 찾을 수 없습니다.</p>
        <button onClick={() => navigate(-1)} className="mt-4 text-[#5C7CFF] font-bold">뒤로 돌아가기</button>
      </div>
    );
  }

  const isAuthor = profile?.memberId === detail.author.memberId;
  const isClosed = detail.status === 'CLOSED';

  // D-Day
  let dDayStr = '';
  if (detail.deadline) {
    const diffTime = new Date(detail.deadline).getTime() - new Date().getTime();
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    if(diffDays > 0) dDayStr = `D-${diffDays}`;
    else if(diffDays === 0) dDayStr = 'D-Day';
    else dDayStr = '기간 마감';
  }

  const handleApplyClick = () => {
    if (detail.appliedByMe) {
      if(confirm('정말 지원을 취소하시겠습니까?')) {
        cancelApplyMutation.mutate();
      }
    } else {
      if(confirm('이 프로젝트에 참여 지원하시겠습니까?')) {
        applyMutation.mutate();
      }
    }
  };

  return (
    <div className="flex flex-col min-h-screen bg-[#F9FAFB] font-sans pb-[100px] max-w-[430px] mx-auto w-full relative shadow-xl">
      {/* 헤더 */}
      <header className="border-b border-[#E5E7EB] bg-white sticky top-0 z-10 w-full">
        <div className="flex flex-col justify-center px-4 pt-4 pb-4">
          <div className="flex items-center">
            <button
              onClick={() => navigate(-1)}
              className="flex h-8 w-8 items-center justify-center -ml-2"
            >
              <img src={backIcon} alt="뒤로가기" className="h-6 w-6" />
            </button>
            <div className="flex flex-1 justify-center">
              <span className="text-[18px] font-bold text-[#1E293B]">프로젝트 상세</span>
            </div>
            <div className="w-8 shrink-0"></div>
          </div>
        </div>
      </header>

      <main className="flex-1 w-full bg-white">
        {/* 상단 섹션 */}
        <div className="px-5 py-6 border-b border-[#F1F5F9]">
          <div className="flex items-center justify-between mb-3">
             <span className="text-[12px] text-[#1447E6] font-bold bg-[#EFF6FF] px-2.5 py-1 rounded-md">
                {detail.category || '기타'}
             </span>
             {dDayStr && (
                <span className="text-[12px] text-[#EA580C] font-bold bg-[#FFF7ED] px-2.5 py-1 rounded-md">
                   {dDayStr}
                </span>
             )}
          </div>
          <h1 className="text-[22px] font-black text-[#1E293B] leading-tight mb-4">
            {detail.title}
          </h1>

          <div className="flex items-center justify-between">
            <div className="flex items-center gap-3">
               <div className="w-10 h-10 rounded-full bg-[#5C7CFF] text-white flex items-center justify-center font-bold text-[16px]">
                  {detail.author.nickname.charAt(0)}
               </div>
               <div className="flex flex-col">
                  <span className="text-[14px] font-bold text-[#1E293B]">{detail.author.nickname}</span>
                  <span className="text-[12px] text-[#64748B] font-medium">{detail.author.departmentName || '학부 미상'}</span>
               </div>
            </div>
            <span className="text-[12px] text-[#94A3B8]">
               {new Date(detail.createdAt).toLocaleDateString()}
            </span>
          </div>
        </div>

        {/* 요약 컨디션 */}
        <div className="px-5 py-6 border-b border-[#F1F5F9] bg-[#F8FAFC]">
          <div className="flex flex-col gap-4">
            <div className="flex justify-between items-center">
              <span className="text-[14px] font-bold text-[#475569]">모집 상태</span>
              <span className={`text-[14px] font-bold ${isClosed ? 'text-[#94A3B8]' : 'text-[#1447E6]'}`}>
                 {isClosed ? '모집 완료' : '모집 중'}
              </span>
            </div>
            <div className="flex justify-between items-center">
              <span className="text-[14px] font-bold text-[#475569]">모집 인원</span>
              <span className="text-[14px] font-bold text-[#1E293B]">
                 {detail.applicantCount} / {detail.totalHeadcount} 명
              </span>
            </div>
            <div className="flex justify-between items-center">
              <span className="text-[14px] font-bold text-[#475569]">마감일</span>
              <span className="text-[14px] font-bold text-[#1E293B]">
                 {detail.deadline ? new Date(detail.deadline).toLocaleDateString() : '미정'}
              </span>
            </div>
          </div>
        </div>

        {/* 세부 내용 */}
        <div className="px-5 py-6">
          <h3 className="text-[16px] font-bold text-[#1E293B] mb-4">기술 스택 및 요구사항</h3>
          <div className="flex gap-2 flex-wrap mb-8">
            {detail.skills && detail.skills.length > 0 ? (
              detail.skills.map((skill, idx) => (
                <span key={idx} className="text-[13px] text-[#1D293D] bg-[#F1F5F9] px-3 py-1.5 rounded-lg font-bold border border-[#E2E8F0]">
                  {skill}
                </span>
              ))
            ) : (
              <span className="text-[13px] text-[#94A3B8]">지정된 기술스택 없음</span>
            )}
          </div>

          <h3 className="text-[16px] font-bold text-[#1E293B] mb-4">상세 내용</h3>
          <p className="text-[14.5px] text-[#334155] leading-relaxed whitespace-pre-line min-h-[100px]">
            {detail.content || '상세 내용이 작성되지 않았습니다.'}
          </p>

          {isAuthor && detail.contactUrl && (
            <div className="mt-8 p-4 bg-[#EFF6FF] rounded-xl border border-[#BFDBFE]">
              <h4 className="text-[13px] font-bold text-[#1D4ED8] mb-1">연락처/오픈채팅 (작성자/승인자 전용)</h4>
              <a href={detail.contactUrl} target="_blank" rel="noreferrer" className="text-[14px] text-[#2563EB] underline break-all">
                {detail.contactUrl}
              </a>
            </div>
          )}
        </div>
      </main>

      {/* 하단 지원하기 픽스 바 */}
      <div className="fixed bottom-0 w-full max-w-[430px] bg-white border-t border-[#E2E8F0] px-5 py-4 shadow-[0_-4px_20px_rgba(0,0,0,0.05)] z-20">
        {isAuthor ? (
           <button 
             onClick={() => alert('지원자 목록 관리 페이지로 이동 (개발예정)')}
             className="w-full h-[52px] rounded-xl bg-[#1E293B] text-white font-bold text-[16px] shadow-lg transition-transform active:scale-[0.98]"
           >
             지원자 관리하기 ({detail.applicantCount}명 참여중)
           </button>
        ) : isClosed && !detail.appliedByMe ? (
           <button 
             disabled
             className="w-full h-[52px] rounded-xl bg-[#F1F5F9] text-[#94A3B8] font-bold text-[16px]"
           >
             모집이 마감되었습니다
           </button>
        ) : (
           <button 
             onClick={handleApplyClick}
             disabled={applyMutation.isPending || cancelApplyMutation.isPending}
             className={`w-full h-[52px] rounded-xl font-bold text-[16px] shadow-md transition-all active:scale-[0.98] ${
               detail.appliedByMe 
                 ? "bg-[#F1F5F9] text-[#64748B] border border-[#E2E8F0] hover:bg-[#E2E8F0]" 
                 : "bg-[#5C7CFF] text-white hover:bg-[#4B6BF5]"
             }`}
           >
             {detail.appliedByMe ? "지원 취소하기" : "이 프로젝트 지원하기 🚀"}
           </button>
        )}
      </div>
    </div>
  );
};

export default ProjectDetailPage;