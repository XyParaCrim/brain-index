package org.excellent.cancer.algorithms;

import java.util.Arrays;

class Solution implements FindsAllGoodStrings {

    static final int MOD = (10^9) + 7;

    @Override
    public int findGoodStrings(int n, String s1, String s2, String evil) {
        int[] max = value(s2);
        int[] min = value(s1);

        return exclude(max, min, value(evil), n);
    }

    public static int exclude(int[] max, int[] min, int[] exclude, int high) {
        if (high < exclude.length) {
            return 0;
        }


        boolean h = false, l = false;
        for (int i = exclude.length - 1, k = 1; i >= 0; i--,k++) {

            if (h) {
                if (exclude[i] == max[high - k]) {
                    continue;
                }
                if (exclude[i] > max[high - k]) {
                    return exclude(max, min, exclude, high - 1);
                }

                // 相加
                return 0;
            }

            if (exclude[i] == max[high - 1]) {
                h = true;
                continue;
            }

            if (exclude[i] == min[high - 1]) {

                continue;
            }


            if (exclude[i] > max[high - 1] || exclude[i] < min[high - 1]) {
                return exclude(max, min, exclude, high - 1);
            }

            int[] newMin = new int[max.length];
            int[] newMax = new int[min.length];

            for (int z = high - exclude.length, j = z; j < high; j++) {
                newMin[j] = exclude[j - z] + 1;
                if (newMin[j] == 25) {
                    newMin[j + 1]++;
                }
            }
            Arrays.fill(newMin, 0, high - exclude.length - 1, 23);


            return exclude(max, newMin, exclude, high - 1) + exclude(newMax, min, exclude, high - 1);
        }

        // TODO
        throw new RuntimeException();
    }

    private static int hashCode(String source, int i, int j) {
        int code = 0;
        for ( ; i <= j; i++) {
            code = (source.charAt(i) - 'a') + code * 31;
        }
        return code;
    }

    private static int[] value(String s) {
        int[] value = new int[s.length()];

        for (int i = 0; i < s.length(); i++) {
            value[i] = s.charAt(i) - 'a';
        }

        return value;
    }


    private static int sum(int[] max, int[] min) {
        int  sum = 0;

        for (int i = max.length - 1; i >= 0; i--) {
            sum = (sum * 24 + (max[i] - min[i]))%MOD;
        }

        return sum;
    }
}
