package com.xiaoha;

/**
 * @author: ZeKai
 * @date: 2025/5/26
 * @description:
 **/
import java.util.Scanner;
import java.util.Arrays;

public class frequent{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            int n = scanner.nextInt();
            int q = scanner.nextInt();

            if (n == 0 && q == 0) {
                break;
            }

            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = scanner.nextInt();
            }


            int[] freq = new int[n];
            int[] start_index = new int[n];

            if (n > 0) {
                int currentFreq = 0;
                int currentStart = 0;
                for (int k = 0; k < n; k++) {
                    if (k == 0 || a[k] != a[k - 1]) {
                        // New block starts
                        for (int l = currentStart; l < k; l++) {
                            freq[l] = currentFreq;
                            start_index[l] = currentStart;
                        }
                        currentStart = k;
                        currentFreq = 1;
                    } else {
                        // Same block
                        currentFreq++;
                    }
                }
                // Fill values for the last block
                for (int l = currentStart; l < n; l++) {
                    freq[l] = currentFreq;
                    start_index[l] = currentStart;
                }
            }

            // Process queries
            for (int k = 0; k < q; k++) {
                int i = scanner.nextInt() - 1; // Adjust to 0-based index
                int j = scanner.nextInt() - 1; // Adjust to 0-based index

                int maxFreq = 0;

                // Case 1: a[i] is part of the most frequent value
                // Frequency of a[i] in the range [i, j] is the minimum of its total frequency
                // and the number of elements from i to the end of its block within [i, j].
                // The end index of the block containing a[i] is start_index[i] + freq[i] - 1.
                // The number of elements of a[i] in the range is (end index in range) - i + 1.
                // The end index in range for a[i] is min(j, start_index[i] + freq[i] - 1).
                maxFreq = Math.min(j, start_index[i] + freq[i] - 1) - i + 1;


                // Case 2: a[j] is part of the most frequent value (only if it's a different block than a[i])
                if (start_index[j] != start_index[i]) {
                    // Frequency of a[j] in the range [i, j] is the minimum of its total frequency
                    // and the number of elements from the start of its block within [i, j] to j.
                    // The start index in range for a[j] is max(i, start_index[j]).
                    maxFreq = Math.max(maxFreq, j - Math.max(i, start_index[j]) + 1);
                }


                // Case 3: The most frequent value is entirely contained within (i, j)
                // Find the start index of the block immediately after the block containing a[i].
                int nextBlockStart = start_index[i] + freq[i];

                // If the next block starts within the range [i+1, j-1] (i.e., nextBlockStart <= j and nextBlockStart > i)
                // we need to consider the frequencies of blocks completely within the range (i, j).
                // We can binary search for the start index of the first block whose start index is >= nextBlockStart
                // and whose elements are within j.
                // More simply, we can find the start index of the block containing a[j].
                // Any block starting between nextBlockStart and start_index[j] (exclusive of start_index[j] if a[i] and a[j] are same block)
                // is completely contained if its end index is <= j.

                // Iterate through blocks that start after the block of a[i] and end before or at j.
                int currentBlockIdx = nextBlockStart;
                while (currentBlockIdx <= j && currentBlockIdx < n) {
                    int blockStart = start_index[currentBlockIdx];
                    // If this block is completely within the range (i, j] (or even [i, j]),
                    // we can consider its full frequency.
                    // A block from blockStart to blockStart + freq[currentBlockIdx] - 1
                    // is fully contained in [i, j] if blockStart >= i and blockStart + freq[currentBlockIdx] - 1 <= j.
                    // Since currentBlockIdx >= nextBlockStart > start_index[i] and a[i] <= a[currentBlockIdx],
                    // blockStart >= start_index[i].
                    // We only need to check if the block ends within j.
                    if (blockStart <= j) { // Ensure the block starts within or at the query end
                        int blockEnd = blockStart + freq[currentBlockIdx] - 1;
                        if (blockEnd <= j) { // If the entire block is within the range [i, j]
                            maxFreq = Math.max(maxFreq, freq[currentBlockIdx]);
                        } else { // The block starts within the range but extends beyond j.
                            // This case is already covered by considering a[j] if the block is a[j]'s block.
                            // If it's not a[j]'s block, it means the block of a[j] starts before this one.
                            // We only need to consider the frequency of a[j] up to j.
                            // This part is subtle. If a block is not fully contained, its max frequency within the range
                            // is either limited by the start index i or the end index j.
                            // The frequencies for a[i] and a[j] already cover elements whose blocks are partially in the range.
                            // So we only need to look at blocks fully contained in (i, j).
                            // A block starting at blockStart is fully contained if blockStart >= i and blockEnd <= j.
                            // Our current search starts from currentBlockIdx = nextBlockStart.
                            // Any block starting at currentBlockIdx such that start_index[currentBlockIdx] > i and
                            // start_index[currentBlockIdx] + freq[currentBlockIdx] - 1 <= j is fully contained.
                            // Since we start at nextBlockStart = start_index[i] + freq[i], any block we look at
                            // will have its start index > start_index[i]. Because the array is sorted,
                            // start_index[currentBlockIdx] will be greater than or equal to the start index of the block after a[i]'s block.
                            // We need to check if start_index[currentBlockIdx] > i.
                            if (blockStart > i) {
                                maxFreq = Math.max(maxFreq, freq[currentBlockIdx]);
                            }
                        }
                    }

                    // Move to the start of the next block
                    currentBlockIdx = blockStart + freq[currentBlockIdx];
                }


                System.out.println(maxFreq);
            }
        }

        scanner.close();
    }
}