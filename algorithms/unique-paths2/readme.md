# 不同路径2

* [题目链接](https://leetcode-cn.com/problems/unique-paths-ii/)

## 解决方法

`动态规划`

### 动态规划

设`dp[n][m]`为到达`(m, n)`的路径个数，则`(m, n)` = `(m, n - 1)` + `(m- 1, n)`
> 若为障碍则为0
####代码

```java

class Solution {
    
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int rows = obstacleGrid.length;
        int columns = obstacleGrid[0].length;

        int[][] dp = new int[rows][columns];

        dp[0][0] = obstacleGrid[0][0] == 1 ? 0 : 1;

        for (int i = 1; i < rows; i++) {
            dp[i][0] = obstacleGrid[i][0] == 0 ? dp[i - 1][0] : 0;
        }

        for (int i = 1; i < columns; i++) {
            dp[0][i] = obstacleGrid[0][i] == 0 ? dp[0][i - 1] : 0;
        }

        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                dp[i][j] = obstacleGrid[i][j] == 0 ? dp[i - 1][j] + dp[i][j - 1] : 0;
            }
        }

        return dp[rows - 1][columns - 1];
    }
}



```