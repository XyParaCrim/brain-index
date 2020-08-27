package org.excellent.cancer.algorithms;

/**
 * 默认实现方法，采用动态规划
 *
 * @author XyParaCrim
 */
class Solution implements LongestPalindromicSubstring {
    @Override
    public String longestPalindromic(String input) {
        if (input.length() <= 1) {
            return input;
        }

        // 对于任意dp[i][j]有字符串i到j是否是回文串
        boolean[][] dp = new boolean[input.length() + 1][input.length() + 1];
        int start, end;
        start = 1;
        end = 1;

        for (int i = 1; i <= input.length(); i++) {

            dp[i][i] = true;

            if (i > 1) {

                if (input.charAt(i - 1) == input.charAt(i - 2)) {
                    dp[i][i - 1] = true;
                    if (end - start < 1) {
                        end = i;
                        start = i - 1;
                    }
                }

                for (int j = i - 2; j > 0; j--) {

                    // 奇数
                    if (input.charAt(i - 1) == input.charAt(j - 1) && dp[i - 1][j + 1]) {

                        dp[i][j] = true;

                        if (i - j > end - start) {
                            end = i;
                            start = j;
                        }

                    }

                }

            }

        }

        return input.substring(start - 1, end);

    }

    public static void main(String[] args) {

        System.out.println(new Solution().longestPalindromic("babad"));
        System.out.println(new Solution().longestPalindromic("bbc"));
        System.out.println(new Solution().longestPalindromic("aaaa"));
    }
}
