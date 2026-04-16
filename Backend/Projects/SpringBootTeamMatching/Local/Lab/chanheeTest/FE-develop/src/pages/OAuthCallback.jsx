import { useEffect } from "react";

export default function OAuthCallback() {
  useEffect(() => {
    // URL에서 token 꺼내기
    const params = new URLSearchParams(window.location.search);
    const token = params.get("token");

    if (token) {
      // 👉 localStorage 저장
      localStorage.setItem("access_token", token);

      // 👉 저장 확인 로그
      console.log("토큰 저장 완료:", token);

      // 👉 홈으로 이동
      window.location.href = "/home";    } else {
      console.error("토큰 없음");
    }
  }, []);

  return <div>로그인 처리 중…</div>;
}