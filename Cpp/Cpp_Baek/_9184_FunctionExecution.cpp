#include <iostream>
using namespace std;

long long dp[21][21][21];
bool vis[21][21][21];

long long w(int a, int b, int c){
    if (a<=0 || b<=0 || c<=0) return 1;
    if (a>20 || b>20 || c>20) return w(20,20,20);
    if (vis[a][b][c]) return dp[a][b][c];
    vis[a][b][c] = true;
    if (a<b && b<c) dp[a][b][c] = w(a,b,c-1) + w(a,b-1,c-1) - w(a,b-1,c);
    else dp[a][b][c] = w(a-1,b,c) + w(a-1,b-1,c) + w(a-1,b,c-1) - w(a-1,b-1,c-1);
    return dp[a][b][c];
}

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    while (true){
        int a,b,c;
        if (!(cin>>a>>b>>c)) break;
        if (a==-1 && b==-1 && c==-1) break;
        cout << "w(" << a << ", " << b << ", " << c << ") = " << w(a,b,c) << '\n';
    }
    return 0;
}

/*sol_2
#include <bits/stdc++.h>
using namespace std;

long long dp[21][21][21];

int clamp20(int x){
    if (x<=0) return 0;      // we'll treat index 0 as "<=0" case
    if (x>20) return 20;
    return x;
}

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    for (int a=0;a<=20;++a)
        for (int b=0;b<=20;++b)
            for (int c=0;c<=20;++c)
                dp[a][b][c] = (a==0 || b==0 || c==0) ? 1 : 0;

    for (int a=1;a<=20;++a){
        for (int b=1;b<=20;++b){
            for (int c=1;c<=20;++c){
                if (a<b && b<c)
                    dp[a][b][c] = dp[a][b][c-1] + dp[a][b-1][c-1] - dp[a][b-1][c];
                else
                    dp[a][b][c] = dp[a-1][b][c] + dp[a-1][b-1][c] + dp[a-1][b][c-1] - dp[a-1][b-1][c-1];
            }
        }
    }

    int a,b,c;
    while (cin>>a>>b>>c){
        if (a==-1 && b==-1 && c==-1) break;
        int A = clamp20(a), B = clamp20(b), C = clamp20(c);
        // If any <=0 -> 1; if any >20 -> use (20,20,20); handled by clamping + base at index 0
        long long ans = (a<=0 || b<=0 || c<=0) ? 1 : dp[A][B][C];
        cout << "w(" << a << ", " << b << ", " << c << ") = " << ans << '\n';
    }
    return 0;
}
*/