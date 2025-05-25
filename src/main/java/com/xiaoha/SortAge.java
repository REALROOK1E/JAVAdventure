package com.xiaoha;

import java.io.*;

/**
 * @author: ZeKai
 * @date: 2025/5/23
 * @description:
 */
public class SortAge {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String line;
        while ((line = br.readLine()) != null) {
            int n = Integer.parseInt(line);
            if (n == 0) break;

            int[] arr = new int[n];
            String[] tokens = br.readLine().split(" ");
            for (int i = 0; i < n; i++) {
                arr[i] = Integer.parseInt(tokens[i]);
            }

            sort(arr);

            for (int i = 0; i < n; i++) {
                bw.write(arr[i] + " ");
            }
            bw.newLine();
        }

        bw.flush();
        bw.close();
        br.close();
    }

    private static void sort(int[] arr) {

        java.util.Arrays.sort(arr);
    }
}