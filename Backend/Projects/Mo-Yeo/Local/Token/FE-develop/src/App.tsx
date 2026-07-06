import { useEffect } from "react";
import { RouterProvider } from "react-router-dom";
import { router } from "./routes";

function App() {
  useEffect(() => {
    const url = new URL(window.location.href);
    const token = url.searchParams.get("token");

    if (token) {
      // 발급받은 토큰을 로컬스토리지에 저장
      localStorage.setItem("access_token", token);
      
      // 보안을 위해 URL 상의 토큰 파라미터를 흔적 없이 즉시 지움 (새로고침 안됨)
      window.history.replaceState({}, document.title, window.location.pathname);
    }
  }, []);

  return <RouterProvider router={router} />;
}

export default App;
