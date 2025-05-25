package com.xiaoha;

import java.util.*;
import java.io.*;

public class RangeQuery {
    private static class Query implements Comparable<Query> {
        int left;
        int right;
        int index;

        Query(int left, int right, int index) {
            this.left = left;
            this.right = right;
            this.index = index;
        }

        @Override
        public int compareTo(Query other) {
            int blockSize = 320;
            if (this.left / blockSize != other.left / blockSize) {
                return Integer.compare(this.left, other.left);
            }
            return Integer.compare(this.right, other.right);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        while (true) {
            String[] firstLine = reader.readLine().split(" ");
            int n = Integer.parseInt(firstLine[0]);
            int q = Integer.parseInt(firstLine[1]);

            if (n == 0) {
                break;
            }

            int[] sequence = new int[n];
            int[] frequencyCount = new int[200001];
            int[] result = new int[q];

            String[] sequenceInput = reader.readLine().split(" ");
            for (int i = 0; i < n; i++) {
                sequence[i] = Integer.parseInt(sequenceInput[i]) + 100000; // Normalize to non-negative
            }

            List<Query> queries = new ArrayList<>();
            for (int i = 0; i < q; i++) {
                String[] queryInput = reader.readLine().split(" ");
                int left = Integer.parseInt(queryInput[0]) - 1;
                int right = Integer.parseInt(queryInput[1]) - 1;
                queries.add(new Query(left, right, i));
            }

            Collections.sort(queries);

            int currentLeft = 0, currentRight = -1;
            int maxFrequency = 0;

            for (Query query : queries) {
                while (currentLeft < query.left) {
                    removeElement(sequence[currentLeft++], frequencyCount, maxFrequency);
                }
                while (currentLeft > query.left) {
                    addElement(sequence[--currentLeft], frequencyCount, maxFrequency);
                }
                while (currentRight < query.right) {
                    addElement(sequence[++currentRight], frequencyCount, maxFrequency);
                }
                while (currentRight > query.right) {
                    removeElement(sequence[currentRight--], frequencyCount, maxFrequency);
                }

                result[query.index] = maxFrequency;
            }

            for (int i = 0; i < q; i++) {
                writer.write(result[i] + "\n");
            }
        }

        writer.flush();
        writer.close();
        reader.close();
    }

    private static void addElement(int value, int[] frequencyCount, int maxFrequency) {
        frequencyCount[value]++;
        if (frequencyCount[value] > maxFrequency) {
            maxFrequency = frequencyCount[value];
        }
    }

    private static void removeElement(int value, int[] frequencyCount, int maxFrequency) {
        frequencyCount[value]--;
        if (frequencyCount[value] < maxFrequency) {
            maxFrequency = Arrays.stream(frequencyCount).max().getAsInt();
        }
    }
}