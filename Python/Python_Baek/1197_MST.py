 import sys
input = sys.stdin.readline

V, E = map(int, input().split())
edges = []

for _ in range(E):
    a, b, c = map(int, input().split())
    edges.append((c, a, b))

edges.sort()

parent = list(range(V + 1))
rank = [0] * (V + 1)

def find(x):
    while parent[x] != x:
        parent[x] = parent[parent[x]]
        x = parent[x]
    return x

def union(a, b):
    a = find(a)
    b = find(b)
    if a == b:
        return False
    if rank[a] < rank[b]:
        a, b = b, a
    parent[b] = a
    if rank[a] == rank[b]:
        rank[a] += 1
    return True

ans = 0
for cost, a, b in edges:
    if union(a, b):
        ans += cost

print(ans)