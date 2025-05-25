package com.xiaoha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Pickupstick {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> tokens = new ArrayList<>();
        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue; // 跳过空行
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens()) {
                tokens.add(st.nextToken());
            }
        }
        int ptr = 0;
        while (ptr < tokens.size()) {
            int n = Integer.parseInt(tokens.get(ptr++));
            int m = Integer.parseInt(tokens.get(ptr++));
            if (n == 0 && m == 0) break;

            ArrayList<Integer>[] adj = new ArrayList[n + 1];
            for (int i = 1; i <= n; i++) {
                adj[i] = new ArrayList<>();
            }
            int[] inDegree = new int[n + 1];
            for (int i = 0; i < m; i++) {
                int a = Integer.parseInt(tokens.get(ptr++));
                int b = Integer.parseInt(tokens.get(ptr++));
                adj[a].add(b);
                inDegree[b]++;
            }

            ArrayDeque<Integer> queue = new ArrayDeque<>();
            for (int i = 1; i <= n; i++) {
                if (inDegree[i] == 0) queue.add(i);
            }

            List<Integer> result = new ArrayList<>();
            while (!queue.isEmpty()) {
                int u = queue.poll();
                result.add(u);
                for (int v : adj[u]) {
                    inDegree[v]--;
                    if (inDegree[v] == 0) queue.add(v);
                }
            }

            if (result.size() == n) {
                for (int node : result) {
                    System.out.println(node);
                }
            } else {
                System.out.println("IMPOSSIBLE");
            }
        }
    }
}