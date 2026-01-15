#include <iostream>
#include <vector>
#include <queue>
using namespace std;

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int N, L;
    cin >> N >> L;

    vector<vector<int>> graph(N + 1);
    vector<int> visited(N + 1, 0);

    for(int i = 0; i < L; i++){
        int a, b;
        cin >> a >> b;
        graph[a].push_back(b);
        graph[b].push_back(a);
    }

    queue<int> q;
    q.push(1);
    visited[1] = 1;

    while(!q.empty()){
        int cur = q.front();
        q.pop();
        for(int nxt : graph[cur]){
            if(!visited[nxt]){
                visited[nxt] = 1;
                q.push(nxt);
            }
        }
    }

    int cnt = 0;
    for(int i = 2; i <= N; i++){
        if(visited[i]) cnt++;
    }

    cout << cnt;
    return 0;
}