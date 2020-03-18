package org.excellent.cancer.algorithms;

/**
 * 提供算法不同解法
 */
public interface RegularExpressionMatching {

    /**
     * 字符串与正则表达式是否匹配
     *
     * @param match 需要匹配的字符串
     * @param regex 正则表达式
     * @return 是否匹配
     */
    boolean isMatch(String match, String regex);

    // 所有实现的方法

    /**
     * 有限状态机
     *
     * @return 正则表达式匹配算法实现
     */
    static RegularExpressionMatching nfa() {
        return new NFA();
    }

    /**
     * 现实清晰的有限状态机
     *
     * @return 正则表达式匹配算法实现
     */
    static RegularExpressionMatching nfaOfExplicit() {
        return new ExplicitNFA();
    }

}
