#include <iostream>
#include <vector>
using namespace std;

int N;
long long K;
vector<long long> A;
vector<long long> tmp;
long long cnt = 0;
long long ans = -1;

void merge_sort(int left, int right){
    if(left >= right) return;
    int mid = (left + right) / 2;
    merge_sort(left, mid);
    merge_sort(mid + 1, right);

    int i = left;
    int j = mid + 1;
    int t = left;

    while(i <= mid && j <= right){
        if(A[i] <= A[j]) tmp[t++] = A[i++];
        else tmp[t++] = A[j++];
    }
    while(i <= mid) tmp[t++] = A[i++];
    while(j <= right) tmp[t++] = A[j++];

    for(int k = left; k <= right; k++){
        A[k] = tmp[k];
        if(++cnt == K) ans = A[k];
    }
}

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cin >> N >> K;
    A.resize(N);
    tmp.resize(N);

    for(int i = 0; i < N; i++) cin >> A[i];

    merge_sort(0, N - 1);

    cout << ans << '\n';
    return 0;
}
