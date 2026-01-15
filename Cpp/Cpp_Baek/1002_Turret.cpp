#include <iostream>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;

    while (T--) {
        long long x1, y1, r1, x2, y2, r2;
        cin >> x1 >> y1 >> r1 >> x2 >> y2 >> r2;

        long long dx = x1 - x2;
        long long dy = y1 - y2;
        long long dist = dx * dx + dy * dy;
        long long diff = (r1 - r2) * (r1 - r2);
        long long sum = (r1 + r2) * (r1 + r2);

        if (dist == 0 && r1 == r2) cout << -1 << "\n";
        else if (dist > sum) cout << 0 << "\n";
        else if (dist < diff) cout << 0 << "\n";
        else if (dist == sum || dist == diff) cout << 1 << "\n";
        else cout << 2 << "\n";
    }
    return 0;
}