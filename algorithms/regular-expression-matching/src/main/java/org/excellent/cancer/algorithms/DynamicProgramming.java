package org.excellent.cancer.algorithms;

/**
 * 使用动态规划实现正则表达式匹配
 */
class DynamicProgramming implements RegularExpressionMatching {

    /**
     * 表示字符匹配情况
     */
    private enum MatchedState {

        MATCHED, UNMATCHED, ASTERISK

    }

    @Override
    public boolean isMatch(String match, String regex) {

        DynamicTable dynamicTable = new DynamicTable(match.length(), regex.length());

        // 初始化长度为0的字符串与正则表达式匹配情况
        for (int i = 1; i < regex.length(); i++) {
            if (regex.charAt(i) == '*') {
                dynamicTable.matchWithEmptyMatch(i);
            }
        }

        // 计算i(0 <= i <= regex.length())长度正则表达式与j(0<= j <= match.length())长度字符串的匹配情况
        for (int i = 0; i < regex.length(); i++) {

            char regexp = regex.charAt(i);

            for (int j = 0; j < match.length(); j++) {

                char current = match.charAt(j);

                switch (matches(current, regexp)) {
                    case MATCHED:
                        dynamicTable.matched(i, j);
                        break;
                    case ASTERISK:
                        if (matchesQuietly(current, regex.charAt(i - 1)) == MatchedState.MATCHED) {
                            dynamicTable.matchedWithAsterisk(i, j);
                        } else {
                            dynamicTable.unmatchWithAsterisk(i, j);
                        }
                        break;
                    default:
                        break;
                }

            }

        }

        return dynamicTable.isMatched();
    }

    /**
     * 判断字符与正则的判断情况
     *
     * @param matchChar 匹配字符
     * @param regexChar 正则字符
     * @return 匹配情况
     */
    public static MatchedState matches(char matchChar, char regexChar) {
        if (regexChar == '*') {
            return MatchedState.ASTERISK;
        }

        return matchChar == regexChar || regexChar == '.' ? MatchedState.MATCHED : MatchedState.UNMATCHED;
    }

    /**
     * 判断字符与正则的判断情况(排除*号的情况)
     *
     * @param matchChar 匹配字符
     * @param regexChar 正则字符
     * @return 匹配情况
     */
    public static MatchedState matchesQuietly(char matchChar, char regexChar) {
        return matchChar == regexChar || regexChar == '.' ? MatchedState.MATCHED : MatchedState.UNMATCHED;
    }


    /**
     * 抽象动态规划的数据记录
     */
    private static class DynamicTable {

        private final boolean[][] table;

        private final int matchLength;

        private final int regexLength;

        private DynamicTable(int matchLength, int regexLength) {
            this.matchLength = matchLength;
            this.regexLength = regexLength;
            this.table = new boolean[regexLength + 1][matchLength + 1];
            // 0长度字符串与0长度正则为可以匹配
            this.table[0][0] = true;
        }

        /**
         * 标识regexIndex位置的正则与matchIndex字符串匹配
         *
         * @param regexIndex 正则字符串索引
         * @param matchIndex 匹配字符串索引
         */
        private void matched(int regexIndex, int matchIndex) {
            mark(regexIndex, matchIndex, isMatched(regexIndex - 1, matchIndex - 1));
        }

        /**
         * 标识regexIndex位置的正则与matchIndex字符串，在遇到*号的情况下匹配
         *
         * @param regexIndex 正则字符串索引
         * @param matchIndex 匹配字符串索引
         */
        private void matchedWithAsterisk(int regexIndex, int matchIndex) {
            assert regexIndex >= 1;

            mark(regexIndex, matchIndex, isMatched(regexIndex - 2, matchIndex - 1) ||
                    isMatched(regexIndex - 2, matchIndex) ||
                    isMatched(regexIndex, matchIndex - 1));
        }

        /**
         * 标识regexIndex位置的正则与matchIndex字符串，在遇到*号的情况下不匹配
         *
         * @param regexIndex 正则字符串索引
         * @param matchIndex 匹配字符串索引
         */
        private void unmatchWithAsterisk(int regexIndex, int matchIndex) {
            assert regexIndex >= 1;

            mark(regexIndex, matchIndex, isMatched(regexIndex - 2, matchIndex));
        }

        /**
         * 在空匹配字符的情况下，标识regexIndex位置为*号
         *
         * @param regexIndex 正则字符串索引
         */
        private void matchWithEmptyMatch(int regexIndex) {
            assert regexIndex >= 1;

            mark(regexIndex, -1, isMatched(regexIndex - 2, -1));
        }

        /**
         * 为二维数组设置值，避免下标出错
         *
         * @param regexIndex 正则字符串索引
         * @param matchIndex 匹配字符串索引
         * @param matched 是否匹配
         */
        private void mark(int regexIndex, int matchIndex, boolean matched) {
            table[regexIndex + 1][matchIndex + 1] = matched;
        }

        /**
         * 返回regexIndex + 1长度正则与matchIndex + 1匹配字符串是否匹配
         *
         * @param regexIndex 正则字符串索引
         * @param matchIndex 匹配字符串索引
         * @return 是否匹配
         */
        private boolean isMatched(int regexIndex, int matchIndex) {
            return table[regexIndex + 1][matchIndex + 1];
        }

        /**
         * 返回regexLength长度正则与matchLength匹配字符串是否匹配
         *
         * @return 是否匹配
         */
        private boolean isMatched() {
            return isMatched(regexLength - 1, matchLength - 1);
        }
    }
}
