import java.util.Arrays;

public class Solution {

    private static final int NONE = 0;

    public static int maxEnvelopes(int[][] envelopes) {
/*        Arrays.sort(envelopes, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);


        int currentWidth = envelopes[0][0];
        int count = 0;
        int index = 0;

        PriorityQueue<int[]> queue = new PriorityQueue<>(envelopes.length, comparator(envelopes));

        queue.add(new int[]{index, ++count});

        while (!queue.isEmpty() && index < envelopes.length) {

            if (index < envelopes.length) {

            }

            while (currentWidth == envelopes[index][0]) {
                ++index;
            }



        }*/
        Arrays.sort(envelopes, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
    }

}

class Solution1 {

    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        int len = 0;
        for (int num : nums) {
            int i = Arrays.binarySearch(dp, 0, len, num);
            if (i < 0) {
                i = -(i + 1);
            }
            dp[i] = num;
            if (i == len) {
                len++;
            }
        }
        return len;
    }

    public int maxEnvelopes(int[][] envelopes) {
        // sort on increasing in first dimension and decreasing in second
        Arrays.sort(envelopes, (arr1, arr2) -> {
            if (arr1[0] == arr2[0]) {
                return arr2[1] - arr1[1];
            } else {
                return arr1[0] - arr2[0];
            }
        });
        // extract the second dimension and run LIS
        int[] secondDim = new int[envelopes.length];
        for (int i = 0; i < envelopes.length; ++i) secondDim[i] = envelopes[i][1];
        return lengthOfLIS(secondDim);
    }

    public int findRotateSteps(String ring, String key) {
        int steps = 0;
        for (int i = 0, j = 0; i < key.length(); i++) {
            if (ring.charAt(j) != key.charAt(i)) {
                int clockwise = j, anticlockwise = j;
                do {
                    if (ring.charAt(++clockwise % ring.length()) == key.charAt(i)) {
                        steps++;
                        j = clockwise;
                        break;
                    }

                    if (ring.charAt(anticlockwise = anticlockwise > 0 ? anticlockwise - 1 : ring.length() - 1) == key.charAt(i)) {
                        steps++;
                        j = anticlockwise;
                        break;
                    }
                } while (true);
            }
            steps++;
        }
        return steps;
    }

    public int findMinMoves(int[] machines) {
        int total = 0;
        for (int clothes : machines) {
            total += clothes;
        }

        if (total % machines.length > 0) {
            return -1;
        }

        int per = total / machines.length;
        int moves = 0;

        for (int i = 0; i < machines.length; i++) {
            int needs = machines[i] - per;
            moves = Math.max(moves, Math.abs(needs));
            if (i < machines.length - 1) {
                machines[i + 1] += needs;
            }
        }

        return moves;
    }

    private static final int[] DIFGIT = new int[]{ 1, 3, 7, 15, 31, 63, 127 };

    private static final int[] INTEGER = new int[];

    public int findIntegers(int num) {
        int bits = findBits(num);
        int intergers = INTEGER[bits - 1];

        num -= DIFGIT[bits - 1] + 1;

        if (num > DIFGIT[bits - 2]) {
            num -= DIFGIT[bits - 2] + 1;
        }

        intergers += findIntegers(num);

        return intergers;
    }

    private static int findBits(int num) {
        for (int i = 0; i < DIFGIT.length; i++) {
            if (num <= DIFGIT[i]) {
                return i + 1;
            }
        }
        throw new IllegalArgumentException();
    }

    public int numDecodings(String s) {
        int[] decodings = new int[s.length()];

        char c = s.charAt(0);
        decodings[0] = c == '*' ? 9 : 1;
        for (int i = 1; i < s.length(); i++) {
            c = s.charAt(i);
            if (c == '*') {
                decodings[i] += decodings[i - 1] + 9;
                if (s.charAt(i - 1) == '*') {
                    decodings[i] += decodings[i - 2] + 26;
                } else if (s.charAt(i - 1) == '1') {
                    decodings[i] += decodings[i - 2] + 10;
                } else if (s.charAt(i - 1) == '2') {
                    decodings[i] += decodings[i - 2] + 7;
                }
            } else {
                decodings[i] += 1;
                if (s.charAt(i - 1) == '*') {
                    decodings[i] += decodings[i - 2] + (c - '0') > 6 ? 1 : 2;
                } else if (s.charAt(i - 1) == '1') {
                    decodings[i] += decodings[i - 2] + 1;
                } else if (s.charAt(i - 1) == '2' && (c - '0') > 6) {
                    decodings[i] += decodings[i - 2] + 1;
                }

            }
        }

        return decodings[decodings.length - 1];
    }
}

