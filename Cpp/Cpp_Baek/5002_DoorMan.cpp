#include <iostream>
#include <string>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int N; 
    string s;
    if (!(cin >> N)) return 0;
    cin >> s;

    int cal = 0, cnt = 0, i = 0;
    auto can = [&](int nextCal){ return abs(nextCal) <= N; };

    while (i < (int)s.size()) {
        char c = s[i];
        int delta = (c == 'M' ?  +1 : -1);
        if (can(cal + delta)) {
            cal += delta;
            ++cnt;
            ++i;
            continue;
        }
        if (i + 1 < (int)s.size() && s[i] != s[i+1]) {
            int delta2 = (s[i+1] == 'M' ? +1 : -1);
            if (can(cal + delta2)) {
                cal += delta2;
                ++cnt;
                swap(s[i], s[i+1]);
                ++i;
                continue;
            }
        }
        break;
    }
    cout << cnt << '\n';
    return 0;
}