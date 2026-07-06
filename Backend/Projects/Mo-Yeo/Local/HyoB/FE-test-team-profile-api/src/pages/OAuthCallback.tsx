import React, { useEffect } from "react";

const OAuthCallback: React.FC = () => {
  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    let token = params.get("access_token") || params.get("token");

    if (token) {
      token = token.replace(/^(Bearer\s+)+/i, '').replace(/^"|"$/g, '').trim();

      localStorage.setItem("access_token", token);

      console.log("✅ 토큰 저장 완료 (Key: access_token):", token);

      // 홈으로 이동
      window.location.href = "/home";
    } else {
      console.error("❌ URL에 토큰이 없습니다.");
    }
  }, []);

  return (
    <div className="flex h-screen items-center justify-center bg-[#F8FAFC]">
      <div className="text-xl font-bold text-[#5C7CFF]">로그인 처리 중...</div>
    </div>
  );
};

export default OAuthCallback;