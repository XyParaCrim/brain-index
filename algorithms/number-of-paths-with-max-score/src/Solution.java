import java.util.List;

public class Solution {

    final static int MOD = (int) (1e9 + 7);

    final static long SCORE_MASK = (1L << 32) - 1;

    public int[] pathsWithMaxScore(List<String> board) {
        int size = board.size();

        //这里用一个long类型记录信息
        // 1~31位：得分
        // 32~64位：方案数
        long[][] dp = new long[size][size];

        // 方案数：1
        // 得分：0
        dp[0][0] = 1L << 32;

        // 计算达到(i,j)的最大分数和方案数
        for (int i = 0; i < size; i++) {
            String mess = board.get(i);
            for (int j = i == 0 ? 1 : 0; j < size; j++) {
                // 默认为负数
                long ctl = 1L << 63;
                char c = mess.charAt(j);

                if (c != 'X') {

                    // 向左
                    if (j > 0 && dp[i][j - 1] >= 0) {
                        ctl = computeMaxScore(dp[i][j - 1], ctl);
                    }

                    // 向上
                    if (i > 0 && dp[i - 1][j] >= 0) {
                        ctl = computeMaxScore(dp[i - 1][j], ctl);
                    }

                    // 向左上
                    if (i > 0 && j > 0 && dp[i - 1][j - 1] >= 0) {
                        ctl = computeMaxScore(dp[i - 1][j - 1], ctl);
                    }

                    // 如果有最大值则加之
                    if (ctl >= 0 && c != 'S') {
                        ctl += c - '0';
                    }
                }

                dp[i][j] = ctl;
            }
        }

        return dp[size - 1][size - 1] < 0 ?
                new int[] {0, 0} :
                new int[] {(int) dp[size - 1][size -1], (int) (dp[size - 1][size -1] >>> 32)};
    }

    private static long computeMaxScore(long prev, long curr) {
        if (curr >= 0) {
            // 取低32位，即分数
            int currScore = (int) curr;
            int prevScore = (int) prev;

            if (currScore < prevScore) {
                // 有最大值，分数和方案一致
                curr = prev;
            } else if (currScore == prevScore) {
                // 分数一样，则将方案相加，大于1e9+7则取余
                curr = (curr | SCORE_MASK) | sumAndMod(prev, curr);
            }
        } else {
            curr = prev;
        }

        return curr;
    }

    private static long sumAndMod(long a, long b) {
        long sum = (a >>> 32) + (b >>> 32);
        // 因为a和b都必不可能大于MOD
        return (sum > MOD) ? sum : sum - MOD;
    }
}