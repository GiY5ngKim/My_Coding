import axios from 'axios';

// 환경 변수 설정 (개발(.env.development) 또는 프로덕션(.env.production)에 따라 다름)
// 프록시를 사용하는 경우 (예: 로컬 개발), 직접 /api 와 같이 상대경로를 사용하거나 VITE_API_BASE_URL 빈 문자열 가능
const baseURL = import.meta.env.VITE_API_BASE_URL || '/';

export const apiClient = axios.create({
  baseURL: baseURL,
  // timeout: 5000,
  withCredentials: true, // CORS 쿠키 전송 시 필요
});

// 요청 인터셉터 추가: 로컬 스토리지에 토큰이 있다면 모든 API 요청의 헤더에 포함
apiClient.interceptors.request.use(
  (config) => {
    // 토큰 저장 방식에 맞게 가져오기 로직 수정 가능 (localStorage, sessionStorage, 혹은 쿠키)
    const token = localStorage.getItem('access_token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 응답 인터셉터 처리 예시 (토큰 만료 등에 따른 처리 등 추가 가능)
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    // 예: 401 Unauthorized 시 로그인 페이지 이동 등 공통 에러 처리 로직
    if (error.response?.status === 401) {
      // 로컬 스토리지 비우기 및 경로 리다이렉션 등의 처리 가능
      console.warn("Unauthorized API call. Token maybe invalid.");
    }
    return Promise.reject(error);
  }
);
