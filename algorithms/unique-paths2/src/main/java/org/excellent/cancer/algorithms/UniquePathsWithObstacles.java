package org.excellent.cancer.algorithms;

public interface UniquePathsWithObstacles {

    int uniquePathsWithObstacles(int[][] obstacleGrid);

    static int solution(int[][] obstacleGrid) {
        return new Solution().uniquePathsWithObstacles(obstacleGrid);
    }

}
