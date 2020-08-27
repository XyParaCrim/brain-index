package org.excellent.cancer.algorithms;

/**
 * 从一个字符中获取最长回文串
 *
 * @difficulty medium
 * @author XyParaCrim
 */
public interface LongestPalindromicSubstring {

    /**
     * 返回最长回文子串
     *
     * @param input 输入字符串
     * @return 返回最长回文子串
     */
    String longestPalindromic(String input);

    /**
     * 默认解决情况
     *
     * @param input 输入字符串
     * @return 返回最长回文子串
     */
    static String solution(String input) {
        return new Solution().longestPalindromic(input);
    }

}
