package com.xiaoha;

/**
 * @author: ZeKai
 * @date: 2025/5/26
 * @description:
 **/
import java.util.Scanner;

public class walk {

    static int N;
    static int K;
    static int[][] grid;
    static int[][][][] memo;
    static final int INF = Integer.MIN_VALUE / 2;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int caseNum = 1;

        while (true) {
            N = scanner.nextInt();
            K = scanner.nextInt();

            if (N == 0 && K == 0) {
                break;
            }

            grid = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    grid[i][j] = scanner.nextInt();
                }
            }

            memo = new int[N][N][K + 1][3];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    for (int k = 0; k <= K; k++) {
                        for (int d = 0; d < 3; d++) {
                            memo[i][j][k][d] = Integer.MIN_VALUE;
                        }
                    }
                }
            }

            int initialNegCount = (grid[0][0] < 0) ? 1 : 0;

            if (initialNegCount <= K) {
                memo[0][0][initialNegCount][0] = grid[0][0]; // Base case: Value at (0,0) if within neg limit
            }

            memo = new int[N][N][K + 1][3];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    for (int k_val = 0; k_val <= K; k_val++) {
                        for (int d = 0; d < 3; d++) {
                            memo[i][j][k_val][d] = Integer.MIN_VALUE;
                        }
                    }
                }
            }

            int result = solve(0, 0, (grid[0][0] < 0 ? 1 : 0), 0);

            System.out.printf("Case %d: ", caseNum++);

            if (result <= INF / 2) {
                System.out.println("impossible");
            } else {
                System.out.println(result);
            }
        }

        scanner.close();
    }

    private static int solve(int r, int c, int neg, int dir) {
        if (neg > K) return INF;
        if (r == N - 1 && c == N - 1) return grid[r][c];
        if (memo[r][c][neg][dir] != Integer.MIN_VALUE) return memo[r][c][neg][dir]; // Return memoized result

        int max_sum = INF;

        // Move Down
        if (r + 1 < N) {
            int next_neg = neg + (grid[r + 1][c] < 0 ? 1 : 0);
            max_sum = Math.max(max_sum, grid[r][c] + solve(r + 1, c, next_neg, 0)); // After moving down, can move L, R, D from (r+1, c)
        }

        // Right
        if ((dir == 0 || dir == 1) && c + 1 < N) {
            int next_neg = neg + (grid[r][c + 1] < 0 ? 1 : 0);
            max_sum = Math.max(max_sum, grid[r][c] + solve(r, c + 1, next_neg, 1)); // After moving right, can continue R or go D
        }

        //  Left
        if ((dir == 0 || dir == 2) && c - 1 >= 0) {
            int next_neg = neg + (grid[r][c - 1] < 0 ? 1 : 0);
            max_sum = Math.max(max_sum, grid[r][c] + solve(r, c - 1, next_neg, 2)); // After moving left, can continue L or go D
        }

        return memo[r][c][neg][dir] = max_sum;
    }
}