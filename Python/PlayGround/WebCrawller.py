import requests
from bs4 import BeautifulSoup
import time

def crawl_tistory_post(url, output_filename):
    """
    특정 티스토리 블로그 포스트의 본문 텍스트를 크롤링하여 파일로 저장하는 함수
    """
    # 1. 서버에 접근 권한을 얻기 위해 사람(브라우저)인 것처럼 User-Agent 설정
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36'
    }

    print(f"[{url}] 크롤링 시작...")
    
    try:
        # 2. 웹페이지 데이터 가져오기
        response = requests.get(url, headers=headers)
        response.raise_for_status() # 웹페이지를 못 불러오면 에러 발생
        
        # 3. BeautifulSoup으로 HTML 분석하기
        soup = BeautifulSoup(response.text, 'html.parser')
        
        # 4. 티스토리 본문 영역 찾기 
        # (티스토리 스킨에 따라 class 이름이 다를 수 있음. 대표적인 2가지)
        content_area = soup.find('div', class_='tt_article_useless_p_margin') 
        if not content_area:
            content_area = soup.find('div', class_='article_view')
            
        if not content_area:
            print("본문 영역을 찾을 수 없습니다. 블로그 스킨 구조가 다를 수 있습니다.")
            return

        # 5. 본문 안의 모든 문단(<p> 태그) 추출하기
        paragraphs = content_area.find_all('p')
        
        # 6. 추출한 텍스트를 파일로 저장하기
        with open(output_filename, 'w', encoding='utf-8') as f:
            for p in paragraphs:
                text = p.get_text().strip()
                if text: # 빈 줄이 아니면 쓰기
                    f.write(text + '\n')
                    
        print(f"성공적으로 크롤링하여 [{output_filename}]에 저장했습니다!")

    except Exception as e:
        print(f"크롤링 중 에러가 발생했습니다: {e}")

if __name__ == "__main__":
    # 필요한 라이브러리 설치 안내: pip install requests beautifulsoup4
    
    # 크롤링할 타겟 URL (질문자님이 주신 링크)
    target_url = "https://chobopark.tistory.com/495"
    
    # 저장할 파일 이름
    save_file = "2024_3회.txt"
    
    # 크롤링 실행
    crawl_tistory_post(target_url, save_file)