# 最小路径和

[题目链接](https://leetcode-cn.com/problems/minimum-path-sum/)

## 解决方法

`动态规划`

### 动态规划

设`dp[n][m]`为到达`(m, n)`的路径个数，则`(m, n)` = Min(`(m, n - 1)` + `(m- 1, n)`)

####代码

```java
class Solution  {

    public int minPathSum(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        int[][] dp = new int[rows][columns];

        dp[0][0] = grid[0][0];

        for (int i = 1; i < rows; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        for (int i = 1; i < columns; i++) {
            dp[0][i] = dp[0][i - 1] + grid[0][i];
        }

        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                dp[i][j] = grid[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        return dp[rows - 1][columns - 1];
    }
}
```