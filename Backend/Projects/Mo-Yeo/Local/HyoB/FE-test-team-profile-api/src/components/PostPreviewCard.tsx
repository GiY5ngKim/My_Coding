import heartIcon from "../assets/like.svg";
import commentIcon from "../assets/comment.svg";

type PostPreviewCardProps = {
  title: string;
  likeCount: number;
  commentCount: number;
  date: string;
  author?: string;
};

export default function PostPreviewCard({
  title,
  likeCount,
  commentCount,
  date,
  author,
}: PostPreviewCardProps) {
  return (
    <div
      className="
      w-full
      bg-white
      rounded-[14px]
      border border-gray-100
      p-4
      "
    >
      {/* 제목 + 날짜 */}
      <div className="flex justify-between items-center">
        <span className="text-[15px] text-gray-900">{title}</span>

        <span className="text-xs text-gray-400">{date}</span>
      </div>

      {/* 좋아요 + 댓글 */}
      <div className="flex justify-between items-end">
        {author && <span className="text-[12px] text-[#62748E]">{author}</span>}
        <div className="flex items-center gap-2 mt-3 text-gray-500">
          <div className="flex items-center gap-1">
            <img src={heartIcon} alt="like" className="w-4 h-4" />
            <span className="text-sm">{likeCount}</span>
          </div>

          <div className="flex items-center gap-1">
            <img src={commentIcon} alt="comment" className="w-4 h-4" />
            <span className="text-sm">{commentCount}</span>
          </div>
        </div>
      </div>
    </div>
  );
}
