#include <iostream>
#include <vector>
#include <queue>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int V, E; 
    cin >> V >> E;
    int K; 
    cin >> K;

    vector<vector<pair<int,int>>> graph(V + 1);
    graph.reserve(V + 1);

    for (int i = 0; i < E; ++i) {
        int u, v, w; 
        cin >> u >> v >> w;
        graph[u].push_back({v, w});
    }

    const int INF = 1e9;
    vector<int> dist(V + 1, INF);
    dist[K] = 0;

    priority_queue<pair<int,int>, vector<pair<int,int>>, greater<pair<int,int>>> pq;
    pq.push({0, K});

    while (!pq.empty()) {
        auto [d, u] = pq.top(); pq.pop();
        if (d != dist[u]) continue;

        for (auto [v, w] : graph[u]) {
            int nd = d + w;
            if (nd < dist[v]) {
                dist[v] = nd;
                pq.push({nd, v});
            }
        }
    }

    for (int i = 1; i <= V; ++i) {
        if (dist[i] == INF) cout << "INF\n";
        else cout << dist[i] << '\n';
    }
    return 0;
}
