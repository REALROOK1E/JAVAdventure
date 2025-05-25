package com.xiaoha;

/**
 * @author: ZeKai
 * @date: 2025/5/24
 * @description:
 **/
import java.util.Scanner;

public class prime {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt(); 
        for (int test = 0; test < t; test++) {
            int n = sc.nextInt(); 
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt(); 
            }
            
            boolean found = false;
            int minlen = Integer.MAX_VALUE;
            int startIdx = -1;
            for (int len = 2; len <= n; len++) { 
                
                for (int i = 0; i + len <= n; i++) {
                    int sum = 0;
                    for (int j = 0; j < len; j++) {
                        sum += arr[i + j];
                    }
                    
                    if (isPrime(sum)) { 
                        if (len < minlen) {
                            minlen = len;
                            startIdx = i;
                            found = true;//update length and start index， if prime was found
                        }
                        
                    }
                }
                if (found) break; 
            }
            
            if (found) {
                System.out.print("Shortest primed subsequence is length " + minlen + ":");
                for (int i = 0; i < minlen; i++) {
                    System.out.print(" " + arr[startIdx + i]);
                }
                System.out.println();
            } else {
                System.out.println("This sequence is anti-primed.");
            }
        }
        sc.close();
    }


    static boolean isPrime(int num) {
        if (num < 2) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false;
        for (int i = 3; i * i <= num; i += 2) {
            if (num % i == 0) return false;
        }
        return true;
    }
}