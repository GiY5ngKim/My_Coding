#include <iostream>
#include <vector>
#include <string>
#include <sstream>
#include <limits>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int N; 
    if (!(cin >> N)) return 0;
    cin.ignore(numeric_limits<streamsize>::max(), '\n');

    vector<vector<string>> sen(N);

    for (int i = 0; i < N; ++i) {
        string line; 
        getline(cin, line);

        stringstream ss(line);
        string w;
        while (ss >> w) sen[i].push_back(w);
    }

    string target; 
    getline(cin, target);
    stringstream t(target);

    vector<int> idx(N, 0);

    string w;
    while (t >> w) {
        bool ok = false;
        for (int i = 0; i < N; ++i) {
            if (idx[i] < (int)sen[i].size() && sen[i][idx[i]] == w) {
                ++idx[i];
                ok = true;
                break;
            }
        }
        if(!ok){
            cout << "Impossible\n";
            return 0;
        }
        
    }

    for (int i = 0; i < N; ++i) {
        if (idx[i] != (int)sen[i].size()) {
            cout << "Impossible\n";
            return 0;
        }
    }

    cout << "Possible\n";
    return 0;
}