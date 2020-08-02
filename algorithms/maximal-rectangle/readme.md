# 最大矩形

[题目链接](https://leetcode-cn.com/problems/maximal-rectangle/)

## 解决方法
 
 `动态规划`
 
### 动态规划

计算出`(x, y)`的最长列或者行，则计算出它们累计的面积

#### 代码

```java
package org.excellent.cancer.algorithms;

class Solution implements MaximalRectangle {

    @Override
    public int maximalRectangle(char[][] matrix) {
        int max = 0;
        int rows = matrix.length;
        if (rows == 0) {
            return 0;
        }

        int columns = matrix[0].length;
        if (columns == 0) {
            return 0;
        }

        int[][] lengths = new int[rows][columns];

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                if (j == 0 && matrix[j][i] == '1') {
                    lengths[j][i] = 1;
                } else if (matrix[j][i] == '1') {
                    lengths[j][i] = lengths[j - 1][i] + 1;
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] == '1') {
                    for (int k = j,  height = lengths[i][k], width = 0; k >= 0 && lengths[i][k] > 0; k--) {
                        height = Math.min(height, lengths[i][k]);
                        max = Math.max(max, height * ++width);
                    }
                }

            }
        }

        return max;
    }
}
```