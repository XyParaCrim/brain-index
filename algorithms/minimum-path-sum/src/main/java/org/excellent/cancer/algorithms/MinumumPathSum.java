package org.excellent.cancer.algorithms;

public interface MinumumPathSum {

    int minPathSum(int[][] grid);

    static int solution(int[][] grid) {
        return new Solution().minPathSum(grid);
    }

}
