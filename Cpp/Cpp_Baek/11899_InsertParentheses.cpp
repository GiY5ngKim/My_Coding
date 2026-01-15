#include <iostream>
using namespace std;
int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    string S;
    cin >> S;

    int rst = 0, start = 0, end = 0;
    for(char c : S){
        if(c == ')' && rst == 0) start++;
        else if(c == '(') rst++;
        else if(c == ')') rst--;
    }
    cout << start + rst;
    return 0;
}