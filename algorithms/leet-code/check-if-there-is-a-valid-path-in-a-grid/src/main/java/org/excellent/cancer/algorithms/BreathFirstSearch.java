package org.excellent.cancer.algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.IntUnaryOperator;

/**
 * 深度优先算法实现典型搜索题目：边界考虑
 */
class BreathFirstSearch implements CheckIfThereIsAValidPathInAGrid {

    /**
     * 定义不同街道的方向，其中成员变量是为了方便计算坐标
     */
    enum Direction {
        UP(row -> row - 1, IntUnaryOperator.identity()),
        DOWN(row -> row + 1, IntUnaryOperator.identity()),
        RIGHT(IntUnaryOperator.identity(), column -> column + 1),
        LEFT(IntUnaryOperator.identity(), column -> column - 1);

        final IntUnaryOperator nextRow;
        final IntUnaryOperator nexColumn;

        /**
         * @param nextRow 计算在这个方向上的下一个纵坐标
         * @param nexColumn 计算在这个方向上的下一个横坐标
         */
        Direction(IntUnaryOperator nextRow, IntUnaryOperator nexColumn) {
            this.nextRow = nextRow;
            this.nexColumn = nexColumn;
        }
    }

    // 初始化一个方向表：
    //   in方向 -> 支持该输入方向的所有街道 -> 对应街道支持的out方向
    private static Map<Direction, Map<Integer, Direction>> directionTable = new HashMap<>(4);
    static {
        Map<Integer, Direction> up = new HashMap<>(3);
        up.put(2, Direction.UP);
        up.put(3, Direction.LEFT);
        up.put(4, Direction.RIGHT);

        Map<Integer, Direction> down = new HashMap<>(3);
        down.put(2, Direction.DOWN);
        down.put(5, Direction.LEFT);
        down.put(6, Direction.RIGHT);

        Map<Integer, Direction> left = new HashMap<>(3);
        left.put(1, Direction.LEFT);
        left.put(4, Direction.DOWN);
        left.put(6, Direction.UP);

        Map<Integer, Direction> right = new HashMap<>(3);
        right.put(1, Direction.RIGHT);
        right.put(3, Direction.DOWN);
        right.put(5, Direction.UP);

        directionTable.put(Direction.UP, up);
        directionTable.put(Direction.DOWN, down);
        directionTable.put(Direction.RIGHT, right);
        directionTable.put(Direction.LEFT, left);
    }

    /**
     * 搜索的单元节点
     */
    static class Street implements Comparable<Street> {

        /** 纵坐标 */
        final int row;

        /** 横坐标 */
        final int column;

        /** 进入街道方向 */
        final Direction in;

        /** 出去街道方向 */
        final Direction out;

        /** 离远点的距离，用于优先队列排序 */
        final int distance;

        public Street(Direction in, Direction out) {
            this(0, 0, in, out);
        }

        public Street(int row, int column, Direction in, Direction out) {
            this.row = row;
            this.column = column;
            this.in = in;
            this.out = out;
            this.distance = row + column;
        }

        public int nextRow() {
            return out.nextRow.applyAsInt(row);
        }

        public int nextColumn() {
            return out.nexColumn.applyAsInt(column);
        }

        @Override
        public int compareTo(Street o) {
            return distance - o.distance;
        }
    }

    @Override
    public boolean hasValidPath(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;

        if (rows == 1 && columns == 1) {
            return true;
        }

        PriorityQueue<Street> queue = new PriorityQueue<>(rows * columns);
        boolean[][] walked = new boolean[rows][columns];

        // 分析左上街道，支持的方向
        switch (grid[0][0]) {
            case 1:
                queue.add(new Street(Direction.RIGHT, Direction.RIGHT));
                break;
            case 2:
                queue.add(new Street(Direction.DOWN,  Direction.DOWN ));
                break;
            case 3:
                queue.add(new Street(Direction.RIGHT,  Direction.DOWN ));
                break;
            case 4:
                queue.add(new Street(Direction.UP,    Direction.RIGHT));
                queue.add(new Street(Direction.LEFT,  Direction.DOWN ));
                break;
            case 6:
                queue.add(new Street(Direction.DOWN,  Direction.RIGHT));
        }

        // 经典搜索公式
        while (!queue.isEmpty()) {
            Street street = queue.poll();

            // 标记是否已经搜索过
            walked[street.row][street.column] = true;

            int nextRow = street.nextRow();
            int nextColumn = street.nextColumn();

            // 判断是否出界且是否搜索过
            if (nextColumn >= 0 && nextColumn < columns && nextRow >= 0 && nextRow < rows && !walked[nextRow][nextColumn]) {

                //当前街道的出去方向，即下一个街道的输入方向
                // 获取下一个街道的出去方向
                Direction nextIn = street.out;
                Direction nextOut = directionTable.
                        // 支持该入口方向的所有街道
                        get(nextIn).
                        // 下个一街道是否有出口
                        get(grid[nextRow][nextColumn]);

                if (nextOut != null) {

                    if (nextRow == rows - 1 && nextColumn == columns - 1) {
                        return true;
                    }

                    queue.add(new Street(nextRow, nextColumn, nextIn, nextOut));

                }

            }

        }

        return false;
    }
}
