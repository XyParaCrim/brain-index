# 不同路径

* [题目链接](https://leetcode-cn.com/problems/unique-paths/)

## 解决方法

`动态规划`

### 动态规划

设`dp[n][m]`为到达`(m, n)`的路径个数，则`(m, n)` = `(m, n - 1)` + `(m- 1, n)`

####代码

```java

class Solution {
    
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

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

        return dp[m - 1][n - 1];
    }
}



```