#include <iostream>
#include <vector>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int N, M; 
    if (!(cin >> N >> M)) return 0;

    vector<vector<int>> sw(N);
    vector<int> cnt(M + 1, 0);

    for (int i = 0; i < N; ++i) {
        int k; cin >> k;
        sw[i].resize(k);
        for (int j = 0; j < k; ++j) {
            int x; cin >> x;
            sw[i][j] = x;
            ++cnt[x];
        }
    }

    for (int i = 0; i < N; ++i) {
        bool ok = true;
        for (int lam : sw[i]) {
            if (cnt[lam] == 1) {
                ok = false;
                break;
            }
        }
        if (ok) { cout << 1 << '\n'; return 0; }
    }
    cout << 0 << '\n';
    return 0;
}