import java.util.*;

public class Solution {

    static Map<Integer, Integer> INDEX = new HashMap<>(1024);

    static {
        index(1, 1);
        index(1, 2);
        index(1, 3);
        index(2, 1);
        index(2, 2);
        index(2, 3);
        index(3, 1);
        index(3, 2);
        index(3, 3);
    }

    static void index(int rows, int columns) {
        int low = (rows - 1) * 3 + columns;
        LinkedList<Integer> queue = new LinkedList<>();
        INDEX.put(low, 0);
        queue.add(0);

        while (queue.size() > 0) {
            int state = queue.pop();
            int count = INDEX.get((state << 4) | low);

            for (int i = 0; i < rows; i++) {
                for (int j = 0, next; j < columns; j++) {
                    next = state;

                    next ^= 1 << (i * 3 + j);

                    if (j > 0) {
                        next ^= 1 << (i * 3 + j - 1);
                    }

                    if (j < columns - 1) {
                        next ^= 1 << (i * 3 + j + 1);
                    }

                    if (i > 0) {
                        next ^= 1 << ((i - 1) * 3 + j);
                    }

                    if (i < rows - 1) {
                        next ^= 1 << ((i + 1) * 3 + j);
                    }

                    if (!INDEX.containsKey((next << 4) | low)) {
                        queue.add(next);
                        INDEX.put((next << 4) | low, count + 1);
                    }
                }
            }
        }
    }


    public int minFlips(int[][] mat) {
        int low = (mat.length - 1) * 3 +  mat[0].length;
        int high = 0;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                high |= mat[i][j] << (i * 3 + j);
            }
        }

        return INDEX.getOrDefault((high << 4) | low, -1);
    }

    public static void main(String[] args) {
        System.out.println(new Solution().minFlips(
                new int[][]{
                        {0, 0}, {0, 1}
                }
        ));

        System.out.println(new Solution().minFlips(
                new int[][]{
                        {0}
                }
        ));

        System.out.println(new Solution().minFlips(
                new int[][]{
                        {1,1,1},{1,0,1},{0,0,0}
                }
        ));

        System.out.println(new Solution().minFlips(
                new int[][]{
                        {1,0,0},{1,0,0}
                }
        ));
    }
}
