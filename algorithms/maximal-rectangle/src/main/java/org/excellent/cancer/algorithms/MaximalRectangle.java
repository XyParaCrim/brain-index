package org.excellent.cancer.algorithms;

public interface MaximalRectangle {

    int maximalRectangle(char[][] matrix);

    static int solution(char[][] matrix) {
        return new Solution().maximalRectangle(matrix);
    }

}
