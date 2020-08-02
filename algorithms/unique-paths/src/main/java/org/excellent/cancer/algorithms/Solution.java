package org.excellent.cancer.algorithms;

class Solution implements UniquePaths {

    @Override
    public int uniquePaths(int m, int n) {
        long[][] dp = new long[m][n];

        dp[0][0] = 1;

        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0];
        }

        for (int i = 1; i < n; i++) {
            dp[0][i] = dp[0][i - 1];
        }

        for (int i = 1; i < m; i++) {

            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        System.out.println(dp[m - 1][n - 1] > Integer.MAX_VALUE);

        return (int) dp[m - 1][n - 1];

    }
}
