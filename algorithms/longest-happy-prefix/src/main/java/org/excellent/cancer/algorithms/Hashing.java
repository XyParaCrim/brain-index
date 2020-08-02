package org.excellent.cancer.algorithms;

/**
 * 使用字符哈希值计算
 */
public class Hashing implements LongestHappyPrefix {

    @Override
    public String longestPrefix(String s) {
        int length = 0;
        int a = 31;
        for (int i = 0, j = s.length() - 1, k = 0, n = 0, m = 1; i < s.length() - 1; i++,j--) {

            k = k * 31 + s.charAt(i);

            n = n + s.charAt(j) * m;

            m *= a;

            if (k == n) {
                length = i + 1;
            }

        }

        return s.substring(0, length);
    }
}
