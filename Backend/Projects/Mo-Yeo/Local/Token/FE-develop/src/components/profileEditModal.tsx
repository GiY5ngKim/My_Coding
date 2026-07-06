import { useEffect, useState } from "react";
import closeIcon from "../assets/close.svg";

type ProfileForm = {
  name: string;
  role: string;
  email: string;
  bio: string;
  techStacks: string[];
};

type ProfileEditModalProps = {
  isOpen: boolean;
  onClose: () => void;
  initialProfile: ProfileForm;
  onSave: (profile: ProfileForm) => void;
};

export default function profileEditModal({
  isOpen,
  onClose,
  initialProfile,
  onSave,
}: ProfileEditModalProps) {
  const [form, setForm] = useState({
    name: "",
    role: "",
    email: "",
    bio: "",
    techStacksText: "",
  });

  useEffect(() => {
    if (!isOpen) return;

    setForm({
      name: initialProfile.name,
      role: initialProfile.role,
      email: initialProfile.email,
      bio: initialProfile.bio,
      techStacksText: initialProfile.techStacks.join(", "),
    });
  }, [isOpen, initialProfile]);

  if (!isOpen) return null;

  const handleSave = () => {
    onSave({
      name: form.name.trim(),
      role: form.role.trim(),
      email: form.email.trim(),
      bio: form.bio.trim(),
      techStacks: form.techStacksText
        .split(",")
        .map((item) => item.trim())
        .filter(Boolean),
    });
    onClose();
  };

  return (
    <div
      className="fixed inset-y-0 left-1/2 z-[110] w-full max-w-[430px] -translate-x-1/2 bg-black/45"
      onClick={onClose}
    >
      <div className="flex min-h-screen items-center justify-center px-[16px] py-[24px]">
        <div
          className="w-full overflow-hidden rounded-[24px] bg-white shadow-[0_12px_40px_rgba(15,23,42,0.18)]"
          onClick={(e) => e.stopPropagation()}
        >
          <div className="border-b border-[#E5E7EB] px-[20px] py-[18px]">
            <div className="flex items-center justify-between">
              <h2 className="text-[18px] font-bold leading-[28px] text-[#111827]">
                프로필 수정
              </h2>

              <button
                type="button"
                onClick={onClose}
                className="flex h-[28px] w-[28px] items-center justify-center"
              >
                <img src={closeIcon} alt="닫기" className="h-[20px] w-[20px]" />
              </button>
            </div>
          </div>

          <div className="max-h-[calc(100vh-160px)] overflow-y-auto px-[20px] py-[20px]">
            <div className="flex flex-col gap-[18px]">
              <div className="flex flex-col gap-[8px]">
                <label className="text-[14px] font-semibold text-[#111827]">
                  이름
                </label>
                <input
                  value={form.name}
                  onChange={(e) =>
                    setForm((prev) => ({ ...prev, name: e.target.value }))
                  }
                  className="h-[46px] rounded-[12px] border border-[#D7DFEA] px-[14px] text-[14px] focus:outline-none"
                />
              </div>

              <div className="flex flex-col gap-[8px]">
                <label className="text-[14px] font-semibold text-[#111827]">
                  역할
                </label>
                <input
                  value={form.role}
                  onChange={(e) =>
                    setForm((prev) => ({ ...prev, role: e.target.value }))
                  }
                  className="h-[46px] rounded-[12px] border border-[#D7DFEA] px-[14px] text-[14px] focus:outline-none"
                />
              </div>

              <div className="flex flex-col gap-[8px]">
                <label className="text-[14px] font-semibold text-[#111827]">
                  이메일
                </label>
                <input
                  value={form.email}
                  onChange={(e) =>
                    setForm((prev) => ({ ...prev, email: e.target.value }))
                  }
                  className="h-[46px] rounded-[12px] border border-[#D7DFEA] px-[14px] text-[14px] focus:outline-none"
                />
              </div>

              <div className="flex flex-col gap-[8px]">
                <label className="text-[14px] font-semibold text-[#111827]">
                  소개
                </label>
                <textarea
                  value={form.bio}
                  onChange={(e) =>
                    setForm((prev) => ({ ...prev, bio: e.target.value }))
                  }
                  className="h-[100px] resize-none rounded-[12px] border border-[#D7DFEA] px-[14px] py-[12px] text-[14px] focus:outline-none"
                />
              </div>

              <div className="flex flex-col gap-[8px]">
                <label className="text-[14px] font-semibold text-[#111827]">
                  기술 스택
                </label>
                <input
                  value={form.techStacksText}
                  onChange={(e) =>
                    setForm((prev) => ({
                      ...prev,
                      techStacksText: e.target.value,
                    }))
                  }
                  placeholder="React, TypeScript, Node.js"
                  className="h-[46px] rounded-[12px] border border-[#D7DFEA] px-[14px] text-[14px] focus:outline-none"
                />
              </div>
            </div>
          </div>

          <div className="border-t border-[#E5E7EB] px-[20px] py-[16px]">
            <div className="flex gap-[10px]">
              <button
                type="button"
                onClick={onClose}
                className="h-[48px] flex-1 rounded-[14px] border border-[#D7DFEA] bg-white text-[16px] font-bold text-[#334155]"
              >
                취소
              </button>

              <button
                type="button"
                onClick={handleSave}
                className="h-[48px] flex-1 rounded-[14px] bg-[#2F6BFF] text-[16px] font-bold text-white"
              >
                저장
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}