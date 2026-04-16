package boj_2525_oven_timer;

// java.io 패키지에 들어있는 클래스들을 한꺼번에 사용하겠다는 뜻이다.
// 여기서는 주로 BufferedReader, InputStreamReader를 사용해서 빠른 입력을 받기 위해 import 한다.
import java.io.*;

// java.util 패키지에 들어있는 유틸 클래스들을 한꺼번에 사용하겠다는 뜻이다.
// 여기서는 입력 문자열을 공백 기준으로 나누기 위한 StringTokenizer를 사용한다.
import java.util.*;

// 백준에서 자바 코드는 클래스 이름이 반드시 Main이어야 한다.
// 파일 이름도 보통 Main.java로 제출된다.
public class Main {

    // 자바 프로그램이 실행되면 가장 먼저 시작되는 메서드이다.
    // public: 어디서든 접근 가능
    // static: 객체를 만들지 않아도 실행 가능
    // void: 반환값 없음
    // throws Exception: 실행 중 발생할 수 있는 예외를 직접 처리하지 않고 넘기겠다는 뜻
    public static void main(String[] args) throws Exception {

        // BufferedReader는 입력을 빠르게 받기 위한 클래스다.
        // Scanner보다 훨씬 빠르기 때문에 백준에서는 자주 사용한다.
        // new InputStreamReader(System.in)은 키보드 입력(표준 입력)을 문자 단위로 읽게 해준다.
        // 즉, 전체적으로 "빠르게 한 줄씩 입력받기 위한 객체"를 만드는 코드다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 첫 번째 줄 입력을 읽는다.
        // 문제에서 첫 줄은 "현재 시 현재 분" 형태로 들어온다. 예: 14 30
        // StringTokenizer는 문자열을 공백 기준으로 잘라서 토큰 단위로 나눠준다.
        StringTokenizer st = new StringTokenizer(br.readLine());

        // st.nextToken()은 첫 번째 토큰을 꺼낸다.
        // 예를 들어 "14 30"이면 첫 번째는 "14"다.
        // Integer.parseInt(...)는 문자열 "14"를 정수 14로 바꾼다.
        // 그래서 H에는 현재 시(hour)가 저장된다.
        int H = Integer.parseInt(st.nextToken());

        // st.nextToken()을 한 번 더 호출해서 두 번째 토큰을 가져온다.
        // 예를 들어 "14 30"이면 두 번째는 "30"이다.
        // 이를 정수로 바꿔서 M에 저장한다.
        // 즉, 현재 분(minute)을 저장하는 줄이다.
        int M = Integer.parseInt(st.nextToken());

        // 두 번째 줄 입력을 읽는다.
        // 문제에서 이 줄은 조리 시간 T(분 단위)이다. 예: 20
        // br.readLine()으로 한 줄 전체를 문자열로 읽고,
        // Integer.parseInt(...)로 정수로 바꿔서 T에 저장한다.
        int T = Integer.parseInt(br.readLine());

        // 현재 시간을 "총 몇 분인지"로 바꾼다.
        // 예를 들어 14시 30분이면 14 * 60 + 30 = 870분이다.
        // 여기에 조리 시간 T를 더하면
        // "오늘 0시 기준으로 몇 분이 지난 시각인지"를 한 번에 계산할 수 있다.
        int total = H * 60 + M + T;

        // total / 60 은 총 몇 시간인지 구하는 연산이다.
        // 하지만 24시간을 넘어갈 수 있으므로 % 24를 해줘야 한다.
        // 예를 들어 25시가 되면 실제 시각은 1시여야 하므로,
        // (total / 60) % 24 를 통해 0~23 범위의 시로 맞춰준다.
        // 문자열 연결로 "시 분" 형태를 만들기 위해 중간에 " " 공백 문자열을 넣었다.
        // System.out.println(...)은 출력 후 줄바꿈까지 해준다.
        System.out.println((total / 60) % 24 + " " + total % 60);

        // total % 60 은 총 분에서 "남는 분"을 의미한다.
        // 예를 들어 total이 890이면
        // 890 / 60 = 14시간, 890 % 60 = 50분이므로 결과는 14시 50분이다.
    }
}