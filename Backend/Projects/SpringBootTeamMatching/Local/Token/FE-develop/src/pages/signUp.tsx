import { useState } from "react";
import FieldLabel from "../components/fieldLabel";
import { useNavigate } from "react-router-dom";
import { PATH } from "../components/path";
import Input from "../components/input";
import Textarea from "../components/textarea";

const fields = [
  {
    id: "nickname",
    title: "이름",
    placeholder: "이름을 입력하세요",
    required: true,
  },
  {
    id: "role",
    title: "직무",
    placeholder: "예: 프론트엔드 개발자",
    required: true,
  },
  {
    id: "email",
    title: "이메일",
    placeholder: "example@email.com",
    required: true,
  },
  {
    id: "githubUrl",
    title: "깃허브 주소",
    placeholder: "https://github.com/username",
    required: false,
  },
  {
    id: "intro",
    title: "자기소개",
    placeholder: "자기소개를 입력하세요",
    required: true,
  },
];

const SignUpPage = () => {
  const [tags, setTags] = useState<string[]>([]);
  const [input, setInput] = useState("");
  const navigate = useNavigate();
  const handleAddTag = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter" && input.trim() !== "") {
      e.preventDefault();

      setTags([...tags, input.trim()]);
      setInput("");
    }
  };
  const handleDelete = (deleteTag: string) => {
    setTags((prev) => prev.filter((tag) => tag !== deleteTag));
  };
  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const data = Object.fromEntries(formData.entries());
    const finalData = {
      ...data,
      tags: tags,
    };

    console.log("최종 데이터:", finalData);
    navigate(PATH.HOME);
  };

  return (
    <>
      <div className="bg-[#F7F8FA] px-5 py-10 flex flex-col gap-5 h-full">
        <div className="flex flex-col gap-2">
          <p className="font-bold text-[24px]">계정 생성</p>
          <p className="font-regular text-[14px]">프로필 정보를 입력해주세요</p>
        </div>
        <form
          className="flex flex-col gap-2"
          id="signUpForm"
          onSubmit={handleSubmit}
        >
          {fields.map((field) => (
            <div key={field.id} className="w-full flex flex-col gap-2">
              <FieldLabel label={field.title} required={field.required} />
              {field.id === "intro" ? (
                <Textarea
                  name={`${field.id}`}
                  placeholder={`${field.placeholder}`}
                />
              ) : (
                <Input
                  name={`${field.id}`}
                  placeholder={`${field.placeholder}`}
                />
              )}
            </div>
          ))}
          <FieldLabel label="사용 가능한 툴 / 기술 스택" required={true} />
          <Input
            value={input}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setInput(e.target.value)
            }
            placeholder="하나씩 입력하고 Enter를 누르세요"
            onKeyDown={handleAddTag}
          />
          <div className="flex gap-2 mb-10 flex-wrap">
            {tags.map((tag) => (
              <p
                key={tag}
                className="text-[#0069A8] bg-[#F0F9FF] px-2 py-1 rounded-lg"
              >
                {tag}
                <button
                  type="button"
                  className="px-1 cursor-pointer"
                  onClick={() => handleDelete(tag)}
                >
                  x
                </button>
              </p>
            ))}
          </div>
        </form>
      </div>
      <div className="fixed bottom-0 w-full max-w-[400px] p-5">
        <button
          type="submit"
          form="signUpForm"
          className="bg-[#2F6BFF] w-full h-13 rounded-lg text-white font-bold cursor-pointer"
        >
          계정 생성 완료
        </button>
      </div>
    </>
  );
};

export default SignUpPage;
