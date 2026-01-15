#include <bits/stdc++.h> //웬만한 라이브러리 한 번에 입력(설치 필요)

#include <iostream> // 표준 입출력 헤더파일(cin, cout...)
#include <string> // string 타입 사용시 필요
#include <cstring> // strchr, strcmp 등 문자열 함수 사용시 필요
#include <cstdio> // printf, scanf 사용시 필요(C 표준 입출력 라이브러리)
#include <cmath> // pow 등 수학 계산 사용시 필요(제곱은 **이 더 나음)
#include <algorithm> // 정렬, 값 탐색, 수정/변형, 조건검사/비교
#include <vector> // vector 자료형 사용시 필요
#include <set> // set 자료형 사용시 필요
#include <map> // map 자료형 사용시 필요
#include <queue> // queue, priority_queue 자료형 사용시 필요
#include <stack> // stack 자료형 사용시 필요
#include <iomanip> // setw, setfill(), setprecision() 사용시 필요
#include <sstream> // ostringstream 사용시 필요

using namespace std; // 안쓰면  std::cin 이런 식으로 써야함

"n번째 소수점까지 출력"
cout << fixed << setprecision(1) << cal; // cal을 소수 1번째 자리까지 출력
cout << fixed;
cout.precision(1);
cout << ans; // 좌측과같이 코딩 가능

"C++ 표준 입출력과 C 입출력 동기화 OFF"
ios::sync_with_stdio(false); // cin cout만 쓰면 C stream 필요 X -> 해제시킴으로써 C stream 의 불필요 연산 제거
// 사용 시 scanf, printf 사용 불가

"cin cout 독립화"
cin.tie(nullptr); // cout이 출력 버퍼 비우지 않아도 cin 입력 가능 -> 백준은 출력값만 보기에 입출력 꼬여도 상관 X

"콘솔창 유지하여 출력값 확인"
system("pause");

"리스트 생성과 출력 동시에 가능"
cout << "FFFFFFDCBAA"[n/10];

"C 표준 입출력, #include <cstdio> 필요"
scanf("%d %d", &A, &B); // 주소값 넣어줘야함
printf("%d\n", A + B);

"한 줄에 여러개 출력 #include <string> 필요, 횟수 크면 비효율"
cout << string(c, "*") << "\n"; // string 클래스

"리스트 생성 및 초기화"
int arr[201] = {0,}; // 전부 0
int arr[201] = {2}; // 첫번째 원소는 2, 나머지는 0

"str 문자 연산"
c = c - "a" + "A" // c++에서 char는 정수형 타입

"전역/지역 값 직접 접근(수정)"
for (char &c : s) // &를 붙이면 원본 수정, 없으면 복사

"문자, 문자열 출력"
"\\" // 역슬래시 출력, "\"" // 큰따옴표 출력

"출력 종료 시점 설정"
if(cin.eof()) break; // 입력이 끝날 시 종료, 입력 읽기를 먼저 실행한 뒤 사용해야함
while(cin >> a >> b) // 입력이 끝날 시 종료

"공백이 있는 문자열 받기"
cin >> // 공백은 취급하지 않음
while(getline(cin,s)) // 공백도 포함하여 입력을 저장

"함수 생성"
int sum(int a, int b){ //출력값 형태로 그냥 생성하면 됌

"줄바꿈"
cout << a << "\n"; // 줄바꿈 출력
cout << a << endl; // 줄바꿈 후 출력 버퍼 비우기

"문자열 뒤집기"
string rev = word;
reverse(rev.begin(), rev.end()); // return이 아닌 원본 뒤집기

string word.rbegin(), word.rend()); // 뒤집은 문자열 생성

"삼항연산자 -> 참/거짓 시 출력"
cout << (possible ? "Possible" : "Impossible") << "\n";

"반복문"
for (char c : a) count[c - "a"]++; // str a에 있는 각 문자 개수만큼 반복
while (n--) { //n번 반복

"줄 단위로 읽기"
while (getline(cin, line)) //입력 끝날 때 까지 줄 단위로 line에 저장
-> True, False 값을 가짐

"문자열 같은지 비교"
if (s.compare(i, tl, t) == 0) // string 필요
s의 i번째부터 tl만큼 잘랐을 때 t랑 같으면 0 반환

"문자열 삭제"
s.erase(3, 3) // 3번 인덱스부터 3개 삭제

"검색(algorithm 필요)"
if (find(v.begin(), v.end(), "a") != v.end()) { //std::find, 못찾으면 v.end() 반환
size_t pos = s.find("a") // string::find, 몾찾으면 string::npos
if (s.find("666") != string::npos) //string::find 대신 -1 쓰면 백준은 틀림(Why?)

"정렬(algorithm 필요)"
sort(sides, sides + 3); // side부터 뒤 2개(총 3개) 오름차순 정렬
sort(v.begin(), v.end(), greater<int>()); // 내림차순 정렬
sort(v.rbegin(), v.rend()) // 내림차순 정렬
sort의 세번째 인자에 사용자 정의 함수 삽입하여 사용 가능
-> sort(first,last,comp(a,b)) 에서 

"n번 반복된 문자열"
string res = string(cnt, "1"); // "1"을 cnt만큼 반복한 문자열 생성
string underscore(cur * 4, '_'); // 이렇게도 사용 가능

"문자열 -> 수 변환"
long long num = stoll(N); // 문자열 N을 long long으로 변환

"size_t"
부호없는 정수 타입 -> 음수가 될 수 없는 값에 사용
find()의 반환 타입이 size_t, size_t의 최대값이 npos

"1LL"
int형 계산 중 overflow가 발생하는 지점에서 임시로 long long 타입으로 계산

"for (auto &s : v)"
for문에서 v의 원소에 따라 값 타임 자동 할당

"벡터.resize(n)" // // 벡터 크기 n으로 변경
"set자료형은 검색이 빠르다 -> if(s.count(x))로 x가 있는지 확인 가능"
"ostringstream" // 여러 문자열을 << 연산자로 이어붙여서 저장 
                //string 을 +연산자로 이어붙이는 것보다 상황에 따라 더 효율적
"substr(a, b)" // a번째 인덱스부터 b개 문자열 반환
"setw(n)" // 출력할 문자열의 최소 너비를 n으로 설정
"setfill(c)" // 너비 n보다 짧은 문자열 앞을 문자 c로 채움
"if (strchr("HPY", c)) hap++;" // c가 "HPY"에 포함되어 있으면 hap 증가

"문자열 한 번에 출력" (#include <sstream> 필요)
ostringstream out;
out << "Hello" << "\n" << "World" << "\n"; // 여러 문자열을 << 연산자로 이어붙여서 저장
cout << out.str(); // str()로 한 번에 출력

"set배열 vector 변환" => set은 트리기반이라 몇 번째 원소 인지 알 수 없음
vector<int> all(uniq.begin(), uniq.end()); // set uniq의 원소를 모두 포함하는 vector all 생성
int rank = lower_bound(all.begin(), all.end(), target) - all.begin() + 1; // all에서 target의 위치(몇 번째 원소인지) 찾기
-> lower_bound는 이터레이터(포인터 개념)이므로 벡터의 시작 위치를 빼줘야 정수형 위치가 됨

"stringstream" => 문자열을 단어 단위로 쪼개서 읽을 때 사용
string line; 
    getline(cin, line);
    stringstream ss(line); // line 문자열을 단어 단위로 쪼개기 위해 stringstream에 삽입
    string w;
    while (ss >> w) sen[i].push_back(w); // 공백 포함 전체 문장을 stringstream에 넣고 >> 연산자로 단어 단위로 읽음
    cin.ignore(numeric_limits<streamsize>::max(), '\n'); // string stream 이전에 cin으로 값 입력받을 때 남아있는 개행문자 제거