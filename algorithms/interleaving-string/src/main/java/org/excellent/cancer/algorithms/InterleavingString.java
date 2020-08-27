package org.excellent.cancer.algorithms;

/**
 * 判断两个字符是为另一个字符的交错字符串
 *
 * @difficulty hard
 * @author XyParaCrim
 */
public interface InterleavingString {

    /**
     * 判断两个字符是为另一个字符的交错字符串
     *
     * @param s1 组成字符串
     * @param s2 组成字符串
     * @param s3 交错字符串
     * @return 判断两个字符是为另一个字符的交错字符串
     */
    boolean isInterleave(String s1, String s2, String s3);

    static boolean solution(String s1, String s2, String s3) {
        return new Solution().isInterleave(s1, s2, s3);
    }

}
