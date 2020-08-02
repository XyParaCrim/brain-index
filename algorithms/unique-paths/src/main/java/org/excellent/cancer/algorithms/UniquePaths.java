package org.excellent.cancer.algorithms;

public interface UniquePaths {

    int uniquePaths(int m, int n);

    static int solution(int m, int n) {
        return new Solution().uniquePaths(m, n);
    }

}
