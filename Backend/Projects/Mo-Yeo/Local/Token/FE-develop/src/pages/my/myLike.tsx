import { useNavigate } from "react-router-dom";
import PostCard from "../../components/PostCard";
import backIcon from "../../assets/back.svg";

export default function MyLike() {
  const navigate = useNavigate();

  const likedPosts = [
    {
      id: 1,
      title: "오늘 도서관가실분",
      content: "5시부터 같이 공부하고 밥도먹어요",
      writer: "꿀범",
      date: "14:31",
      likeCount: 1,
      commentCount: 2,
    },
    {
      id: 2,
      title: "오늘 도서관가실분",
      content:
        "전체 목록에서 보이는 내용은 최대 두 줄로 하고 그 이상은 말줄임표로 보이게 하면 더 깔끔할 것 같아요.",
      writer: "꿀범",
      date: "14:31",
      likeCount: 1,
      commentCount: 2,
    },
    {
      id: 3,
      title: "오늘 도서관가실분",
      content: "좋아요가 없는 경우는 좋아요 생략, 댓글 없는 경우는 댓글 생략",
      writer: "꿀범",
      date: "14:31",
      likeCount: 1,
      commentCount: 0,
    },
  ];

  return (
    <main className="mx-auto min-h-screen w-full max-w-[430px] bg-[#F8FAFC] pb-[88px]">
      <header className="border-b border-[#E5E7EB] bg-white">
        <div className="flex h-[96px] items-end px-[16px] pb-[20px] pt-[40px]">
          <button type="button" onClick={() => navigate(-1)}>
            <img src={backIcon} alt="뒤로가기" className="h-[24px] w-[24px]" />
          </button>

          <div className="flex flex-1 justify-center">
            <span className="text-[20px] font-bold leading-[28px] text-[#000000]">
              나의 좋아요
            </span>
          </div>

          <div className="h-[24px] w-[24px]" />
        </div>
      </header>

      <section className="px-[16px] pt-[16px]">
        <div className="flex flex-col gap-[12px]">
          {likedPosts.map((post) => (
            <PostCard
              key={post.id}
              title={post.title}
              content={post.content}
              author={post.writer}
              time={post.date}
              likeCount={post.likeCount}
              commentCount={post.commentCount}
            />
          ))}
        </div>
      </section>
    </main>
  );
}