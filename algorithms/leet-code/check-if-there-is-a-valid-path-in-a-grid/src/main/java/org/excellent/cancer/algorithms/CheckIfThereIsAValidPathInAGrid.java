package org.excellent.cancer.algorithms;


public interface CheckIfThereIsAValidPathInAGrid {

    boolean hasValidPath(int[][] grid);

    /**
     * @return 深度优先算法
     */
    static CheckIfThereIsAValidPathInAGrid bfs() {
        return new BreathFirstSearch();
    }
}
