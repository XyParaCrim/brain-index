package org.excellent.cancer.algorithms;

public interface LongestHappyPrefix {

    /**
     * 「快乐前缀」是在原字符串中既是 非空 前缀也是后缀（不包括原字符串自身）的字符串
     *
     * @param s 输入字符串
     * @return 最长快乐字符串
     */
    String longestPrefix(String s);

    /**
     * @return 返回使用哈希映射实现的方法
     */
    static LongestHappyPrefix hashing() {
        return new Hashing();
    }
}
