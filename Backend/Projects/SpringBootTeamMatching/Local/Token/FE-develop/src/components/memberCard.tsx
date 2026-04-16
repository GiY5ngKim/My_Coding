import bookmarkIcon from "../assets/member_bookmark.svg";
import githubIcon from "../assets/github.svg";

type TeamMemberCardProps = {
  name: string;
  role: string;
  description: string;
  techStacks: string[];
  githubLabel?: string;
  githubUrl?: string;
  rating: number;
  profileInitial?: string;
  isBookmarked?: boolean;
  onBookmarkClick?: () => void;
};

export default function TeamMemberCard({
  name,
  role,
  description,
  techStacks,
  githubLabel = "GitHub",
  githubUrl,
  profileInitial,
  isBookmarked = false,
  onBookmarkClick,
}: TeamMemberCardProps) {
  const displayInitial = profileInitial || name.trim().charAt(0) || "?";

  return (
    <article
      className="
        flex w-full max-w-[398px] flex-col rounded-[14px] border
        border-[#E2E8F0] bg-white
        px-[16px] pt-[16px] pb-[16px]
        shadow-[0_1px_3px_rgba(0,0,0,0.10),0_1px_2px_-1px_rgba(0,0,0,0.10)]
      "
    >
      <div className="flex min-h-[40px] items-start justify-between gap-3">
        <div className="flex min-w-0 items-center gap-3">
          <div
            className="
              flex h-10 w-10 shrink-0 items-center justify-center rounded-full
              bg-[#2188FF] text-[14px] font-semibold text-white
            "
          >
            {displayInitial}
          </div>

          <div className="flex min-w-0 flex-col justify-center">
            <span className="truncate text-[14px] font-semibold leading-[20px] text-[#1D293D]">
              {name}
            </span>
            <span className="truncate text-[14px] font-normal leading-[20px] text-[#62748E]">
              {role}
            </span>
          </div>
        </div>

        <button
          type="button"
          onClick={onBookmarkClick}
          aria-label="북마크"
        >
          <img
            src={bookmarkIcon}
            alt="북마크"
            className={`h-[24px] w-[24px] object-contain ${
              isBookmarked ? "opacity-100" : "opacity-70"
            }`}
          />
        </button>
      </div>

      <p className="mt-3 w-full break-words text-[14px] font-normal leading-[20px] text-[#45556C]">
        {description}
      </p>

      <div className="mt-3 flex flex-wrap items-start gap-2">
        {techStacks.map((stack) => (
          <span
            key={stack}
            className="
              inline-flex items-center rounded-[8px]
              bg-[#EFF6FF] px-2 py-1
              text-[12px] font-normal leading-4 text-[#1447E6]
            "
          >
            {stack}
          </span>
        ))}
      </div>

      <div
        className="
          mt-4 flex items-center justify-between
          border-t border-[#F1F5F9]
          pt-3 pb-1
        "
      >
        {githubUrl ? (
          <a
            href={githubUrl}
            target="_blank"
            rel="noreferrer"
            className="flex shrink-0 items-center gap-1 text-[#62748E] hover:opacity-80"
          >
            <img
              src={githubIcon}
              alt="깃허브"
              className="h-4 w-4 object-contain"
            />
            <span className="text-[14px] font-normal leading-[20px]">
              {githubLabel}
            </span>
          </a>
        ) : (
          <div className="flex shrink-0 items-center gap-1 text-[#62748E]">
            <img
              src={githubIcon}
              alt="깃허브"
              className="h-4 w-4 object-contain"
            />
            <span className="text-[14px] font-normal leading-[20px]">
              {githubLabel}
            </span>
          </div>
        )}
      </div>
    </article>
  );
}