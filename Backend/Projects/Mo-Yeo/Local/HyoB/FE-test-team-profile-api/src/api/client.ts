import axios from 'axios';

const baseURL = 'http://3.37.55.120.nip.io:8080';

export const apiClient = axios.create({
  baseURL: baseURL,
  withCredentials: false, 
});

apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('access_token'); 
    
    if (token) {
      const cleanToken = token.replace(/^(Bearer\s+)+/i, '').replace(/^"|"$/g, '').trim();
      config.headers.set('Authorization', `Bearer ${cleanToken}`);
    }
    
    
    return config;
  },
  (error) => Promise.reject(error)
);

apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      console.warn("인증 실패: 401 Unauthorized 에러가 발생했습니다.");
      console.warn("백엔드 응답 상세:", error.response?.data);
      console.warn("현재 전송된 헤더:", error.config?.headers);
      
    }
    return Promise.reject(error);
  }
);
