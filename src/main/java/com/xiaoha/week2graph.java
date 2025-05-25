package com.xiaoha;

// 这个代码是我写的，别太在意格式哈
import java.io.*;
import java.util.*;

/*
队列初始化：
使用队列 q 进行广度优先搜索。
将起始顶点 start 加入队列，并将其染成颜色 0。
遍历邻接顶点：
对于队列中的每个顶点 u，遍历其邻接顶点 vtx。
如果邻接顶点未染色（color[vtx] == -1），将其染成与 u 不同的颜色（1 - color[u]），并加入队列。
如果邻接顶点已经染色且与 u 颜色相同，说明图不是二分图，返回 false。
返回结果：
如果所有顶点都能成功染色且满足二分图的条件，返回 true。
代码总结
功能：检测图是否为二分图，并计算每个连通分量的染色结果。
核心逻辑：通过广度优先搜索（BFS）对图进行二分染色。
输入输出：输入图的顶点数、边数和边的连接关系，输出计算结果或 -1（如果图不是二分图）。
 */
public class week2graph {
    static List<Integer>[] graph;
    static int[] color;
    static int v, e;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        while (T-- > 0) {
            String[] ve = br.readLine().trim().split("\\s+");
            v = Integer.parseInt(ve[0]);
            e = Integer.parseInt(ve[1]);
            graph = new ArrayList[v];
            for (int i = 0; i < v; i++) graph[i] = new ArrayList<>();
            for (int i = 0; i < e; i++) {
                String[] ft = br.readLine().trim().split("\\s+");
                int f = Integer.parseInt(ft[0]);
                int t = Integer.parseInt(ft[1]);
                graph[f].add(t);
                graph[t].add(f);
            }
            color = new int[v];
            Arrays.fill(color, -1);
            int res = 0;
            boolean ok = true;
            for (int i = 0; i < v; i++) {
                if (color[i] == -1) {
                    int[] cnt = new int[2];
                    if (!bfs(i, cnt)) {
                        ok = false;
                        break;
                    }
                    if (cnt[0] + cnt[1] == 1) res += 1;
                    else res += Math.min(cnt[0], cnt[1]);
                }
            }
            System.out.println(ok ? res : -1);
        }
    }

    static boolean bfs(int start, int[] cnt) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(start);
        color[start] = 0;
        cnt[0]++;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int vtx : graph[u]) {
                if (color[vtx] == -1) {
                    color[vtx] = 1 - color[u];
                    cnt[color[vtx]]++;
                    q.offer(vtx);
                } else if (color[vtx] == color[u]) {
                    return false;
                }
            }
        }
        return true;
    }
}