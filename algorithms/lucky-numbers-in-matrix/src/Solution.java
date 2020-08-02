import java.util.LinkedList;
import java.util.List;

/**
 * 找出矩阵中同时满足下列两个条件的元素：
 * 在同一行的所有元素中最小
 * 在同一列的所有元素中最大
 */
public class Solution {

    /**
     * 前提条件：
     * m == mat.length
     * n == mat[i].length
     * 1 <= n, m <= 50
     * 1 <= matrix[i][j] <= 10^5
     *
     * @param matrix 一个整数矩阵
     * @return 返回满足条件元素的队列
     */
    public List<Integer> luckyNumbers(int[][] matrix) {

        int rows = matrix.length;
        int columns = matrix[0].length;


        int[] maxValue = new int[columns]; // 记录一列中最大值元素索引
        int[] minValue = new int[rows];    // 记录一行中最小值元素索引

        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < columns; j++) {
                // 第j列：第i行第j列是否大于第maxValue[j]第j列元素的值（是否大于之前记录的最大值）
                if (matrix[i][j] > matrix[maxValue[j]][j]) {
                    maxValue[j] = i;
                }

                // 第i行：将第i行的最小值元素更新到minValue中
                if (matrix[i][j] < matrix[i][minValue[i]]) {
                    minValue[i] = j;
                }
            }
        }

        // 第i列：判断第i列的最大值元素所在的行数（k = maxValue[i]
        //       第k行的最小值索引（minValue[k]）是否是第i列的
        List<Integer> result = new LinkedList<>();
        for (int i = 0; i < columns; i++) {
            if (minValue[maxValue[i]] == i) {
                result.add(matrix[maxValue[i]][i]);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        // answer：15
        new Solution().luckyNumbers(new int[][]{
                {3, 7, 8},
                {9, 11, 13},
                {15, 16, 17}
        });

        // answer: 12
        new Solution().luckyNumbers(new int[][]{
                {1,10,4,2},
                {9,3,8,7},
                {15,16,17,12}
        });
    }
}
