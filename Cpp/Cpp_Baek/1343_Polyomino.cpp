#include <iostream>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    string s; 
    if (!(cin >> s)) return 0;

    string ans;
    int cnt = 0;

    for (char c : s) {
        if (c == 'X') {
            ++cnt;
        } else { // c == '.'
            if (cnt % 2) { cout << -1; return 0; }
            while (cnt >= 4) { ans += "AAAA"; cnt -= 4; }
            if (cnt == 2) { ans += "BB"; cnt = 0; }
            ans.push_back('.');
        }
    }

    if (cnt % 2) { cout << -1; return 0; }
    while (cnt >= 4) { ans += "AAAA"; cnt -= 4; }
    if (cnt == 2) { ans += "BB"; }

    cout << ans;
    return 0;
}
