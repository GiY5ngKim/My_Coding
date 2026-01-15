import sys
input = sys.stdin.readline

n, m = map(int, input().split())
parent = list(range(n + 1))
rank = [0] * (n + 1)

def find(x):
    while parent[x] != x:
        parent[x] = parent[parent[x]]
        x = parent[x]
    return x

def union(a, b):
    a = find(a)
    b = find(b)
    if a == b:
        return
    if rank[a] < rank[b]:
        a, b = b, a
    parent[b] = a
    if rank[a] == rank[b]:
        rank[a] += 1

out = []
for _ in range(m):
    line = input().split()
    op, a, b = map(int, line)
    if op == 0:
        union(a, b)
    else:
        out.append("YES\n" if find(a) == find(b) else "NO\n")

sys.stdout.write("".join(out))