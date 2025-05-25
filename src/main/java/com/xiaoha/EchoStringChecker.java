package com.xiaoha;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class EchoStringChecker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        for (int i = 0; i < n; i++) {
            String echoString = scanner.nextLine();
            processEchoString(echoString);
        }

        scanner.close();
    }

    private static void processEchoString(String e) {
        Queue<Integer> s1Indices = new LinkedList<>();
        int s1Ptr = 0;
        boolean consistent = true;

        for (int i = 0; i < e.length(); i++) {
            char currentChar = e.charAt(i);

            if (s1Ptr < s1Indices.size()) {

                int s1CharIndexInE = s1Indices.peek();
                char s1Char = e.charAt(s1CharIndexInE);

                if (currentChar == s1Char) {

                    if (i - s1CharIndexInE > 10) {
                        consistent = false;
                        break;
                    }
                    s1Indices.poll();
                    s1Ptr++;
                } else {s1Indices.offer(i);}

            } else s1Indices.offer(i);

        }

        if (!consistent) {
            System.out.println("Not consistent with the theory");
        } else {
            if (s1Indices.isEmpty()) {
                System.out.println("An echo string with buffer size ten");
            } else {
                System.out.println("Not an echo string, but still consistent with the theory");
            }
        }
    }
}