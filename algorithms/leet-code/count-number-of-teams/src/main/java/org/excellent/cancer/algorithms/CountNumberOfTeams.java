package org.excellent.cancer.algorithms;

/**
 * 计算数组长度为3的局部有序序列个数
 *
 * @author XyParaCrim
 */
public interface CountNumberOfTeams {

    /**
     * 算法入口
     *
     * @param rating 给定数组
     * @return 长度为3的局部有序序列个数
     */
    int numTeams(int[] rating);

    /**
     * 返回一个常规做法，分别计算两侧的大小值
     *
     * @return 常规做法
     */
    static CountNumberOfTeams solution() {
        return new Solution();
    }

    /**
     * 返回一个Leetcode提供的解法
     *
     * @return 返回一个Leetcode提供的解法
     */
    static CountNumberOfTeams fromLeetcode() {
        return new WithTreeArray();
    }

}