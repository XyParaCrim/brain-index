package org.excellent.cancer.algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.IntUnaryOperator;

class BreathFirstSearch implements CheckIfThereIsAValidPathInAGrid {

    enum Direction {
        UP(row -> row - 1, IntUnaryOperator.identity()),
        DOWN(row -> row + 1, IntUnaryOperator.identity()),
        RIGHT(IntUnaryOperator.identity(), column -> column + 1),
        LEFT(IntUnaryOperator.identity(), column -> column - 1);

        final IntUnaryOperator nextRow;

        final IntUnaryOperator nexColumn;

        Direction(IntUnaryOperator nextRow, IntUnaryOperator nexColumn) {
            this.nextRow = nextRow;
            this.nexColumn = nexColumn;
        }
    }

    private static Map<Direction, Map<Integer, Direction>> directionMapMap = new HashMap<>(4);
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

        directionMapMap.put(Direction.UP, up);
        directionMapMap.put(Direction.DOWN, down);
        directionMapMap.put(Direction.RIGHT, right);
        directionMapMap.put(Direction.LEFT, left);

    }

    static class Position {

        final int row;

        final int column;

        final Direction input;

        final Direction output;

        public Position(Direction input, Direction output) {
            this(0, 0, input, output);
        }

        public Position(int row, int column, Direction input, Direction output) {
            this.row = row;
            this.column = column;
            this.input = input;
            this.output = output;
        }

        public int nextRow() {
            return output.nextRow.applyAsInt(row);
        }

        public int nextColumn() {
            return output.nexColumn.applyAsInt(column);
        }
    }

    @Override
    public boolean hasValidPath(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;

        if (rows == 1 && columns == 1) {
            return true;
        }

        PriorityQueue<Position> queue = new PriorityQueue<>(rows * columns);
        boolean[][] walked = new boolean[rows][columns];

        switch (grid[0][0]) {
            case 1:
                queue.add(new Position(Direction.LEFT, Direction.LEFT));
                break;
            case 6:
                queue.add(new Position(Direction.DOWN, Direction.RIGHT));
                break;
            case 2:
                queue.add(new Position(Direction.DOWN, Direction.DOWN));
                break;
            case 3:
                queue.add(new Position(Direction.LEFT, Direction.DOWN));
                break;
            case 4:
                queue.add(new Position(Direction.UP, Direction.RIGHT));
                queue.add(new Position(Direction.LEFT, Direction.DOWN));
        }

        while (!queue.isEmpty()) {

            Position position = queue.poll();

            walked[position.row][position.column] = true;

            int nextRow = position.nextRow();
            int nextColumn = position.nextColumn();

            if (nextColumn >= 0 && nextColumn < columns && nextRow >= 0 && nextRow < rows && !walked[nextRow][nextColumn]) {

                Direction output = directionMapMap.get(position.output).get(grid[nextRow][nextColumn]);

                if (output != null) {

                    if (nextRow == rows - 1 && nextColumn == columns - 1) {
                        return true;
                    }

                    queue.add(new Position(nextRow, nextColumn, position.output, output));

                }

            }


        }

        return false;
    }
}
