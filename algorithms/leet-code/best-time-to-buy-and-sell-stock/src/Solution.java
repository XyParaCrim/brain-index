public class Solution {

    private static final int MAX_PRICE = Integer.MAX_VALUE;

    private static final int MIN_PRICE = Integer.MIN_VALUE;

    private static final int INI_PRICE = 0;

    /**
     * 表示支持的最多交易次数
     */
    public enum TraceTimes {
        ONCE,
        DOUBLE,
        MORE
    }

    public static int maxProfit(int[] prices, TraceTimes times) {
        if (times == null) {
            throw new IllegalArgumentException();
        }

        switch (times) {
            case ONCE: {
                int hold = MAX_PRICE;
                int sold = INI_PRICE;
                for (int price : prices) {
                    sold = Math.max(sold, price - hold);
                    hold = Math.min(hold, price);
                }
                return sold;
            }
            case DOUBLE: {
                int[] profits = new int[prices.length];

                int firstSold = INI_PRICE;
                int firstHold = MAX_PRICE;

                int secondSold = INI_PRICE;
                int secondRest = INI_PRICE;

                int maxProfit = 0;

                for (int first = 0, second = prices.length - 1; first < prices.length; first++, second--) {
                    firstSold = Math.max(firstSold, prices[first] - firstHold);
                    firstHold = Math.min(firstHold, prices[first]);
                    maxProfit = Math.max(maxProfit, profits[first] += firstSold);

                    secondSold = Math.max(secondSold, secondRest - prices[second]);
                    secondRest = Math.max(secondRest, prices[second]);
                    if (second > 0) {
                        maxProfit = Math.max(maxProfit, profits[second - 1] += secondSold);
                    }
                }

                return maxProfit;
            }
            case MORE: {
                int hold = MAX_PRICE;
                int sold = INI_PRICE;
                int rest;
                for (int price : prices) {
                    rest = Math.max(INI_PRICE, price - hold);
                    sold += Math.max(INI_PRICE, rest);
                    hold = rest > 0 ? price : Math.min(hold, price);
                }
                return sold;
            }
            default:
                throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        // 测试最多交易一次
        verifyResults(maxProfit(new int[]{7, 1, 5, 3, 6, 4}, TraceTimes.ONCE), 5);
        verifyResults(maxProfit(new int[]{1, 2, 3, 4, 5}, TraceTimes.ONCE), 4);
        verifyResults(maxProfit(new int[]{7, 6, 4, 3, 1}, TraceTimes.ONCE), 0);

        // 测试最多交易二次
        verifyResults(maxProfit(new int[]{3, 3, 5, 0, 0, 3, 1, 4}, TraceTimes.DOUBLE), 6);
        verifyResults(maxProfit(new int[]{1, 2, 3, 4, 5}, TraceTimes.DOUBLE), 4);
        verifyResults(maxProfit(new int[]{7, 6, 4, 3, 1}, TraceTimes.DOUBLE), 0);

        // 测试随意交易
        verifyResults(maxProfit(new int[]{7, 1, 5, 3, 6, 4}, TraceTimes.MORE), 7);
        verifyResults(maxProfit(new int[]{1, 2, 3, 4, 5}, TraceTimes.MORE), 4);
        verifyResults(maxProfit(new int[]{7, 6, 4, 3, 1}, TraceTimes.MORE), 0);
    }

    private static void verifyResults(int actual, int expected) {
        if (actual != expected) {
            new Error("expected " + expected + " but " + actual).printStackTrace();
        }
    }
}
