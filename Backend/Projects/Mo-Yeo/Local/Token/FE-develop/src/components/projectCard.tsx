import bookmarkIcon from "../assets/bookmark.svg";

type ProjectCardProps = {
  category: string;
  dDay: string;
  title: string;
  description: string;
  recruitCount: number;
  techStacks: string[];
  writer: string;
  department: string;
  buttonLabel: string;
  onButtonClick?: () => void;
};

export default function ProjectCard({
  category,
  dDay,
  title,
  description,
  recruitCount,
  techStacks,
  writer,
  department,
  buttonLabel,
  onButtonClick,
}: ProjectCardProps) {
  return (
    <div className="bg-white flex flex-col gap-3 p-4 rounded-[14px] shadow-sm border border-[#D0D0D0]">
      <div className="flex items-start justify-between">
        <div className="flex items-center gap-2">
          <button className="text-[10px] text-[#2F6BFF] font-bold bg-[#EFF6FF] px-2 py-1 rounded-lg">
            {category}
          </button>

          <button className="text-[10px] text-[#EF4400] font-bold bg-[#FEF2F2] px-2 py-1 rounded-lg">
            {dDay}
          </button>
        </div>

        <button type="button" className="shrink-0">
          <img
            src={bookmarkIcon}
            alt="스크랩"
            className="h-[15px] w-[15px]"
          />
        </button>
      </div>

      <p className="font-bold text-[16px] text-[#111827] leading-[24px]">
        {title}
      </p>

      <p className="text-[14px] text-[#374151] leading-[22px]">
        {description}
      </p>

      <div>
        <p className="text-[12px] text-[#4B5563]">모집인원: {recruitCount}명</p>
      </div>

      <div className="flex flex-wrap gap-2">
        {techStacks.map((stack) => (
          <button
            key={stack}
            className="text-[10px] text-[#0069A8] bg-[#F0F9FF] px-2 py-1 rounded-lg"
          >
            {stack}
          </button>
        ))}
      </div>

      <div className="border-t border-[#D0D0D0] flex justify-between items-center pt-3">
        <div className="text-[#9D9D9D] text-[12px] leading-[18px]">
          <p>{writer}</p>
          <p>{department}</p>
        </div>

        <button
          className="bg-[#2F6BFF] rounded-lg px-4 py-2 text-[12px] text-white font-bold leading-none shadow-sm cursor-pointer"
          onClick={onButtonClick}
        >
          {buttonLabel}
        </button>
      </div>
    </div>
  );
}