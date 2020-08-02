package org.excellent.cancer.algorithms;

/**
 * 在给定数组中，找到与出现次数与值相同的最大数
 */
public interface FindLuckyIntegerInAnArray {

    /**
     * 算法入口
     *
     * @param arr 给定数组
     * @return 结果
     */
    int findLucky(int[] arr);

    /**
     * 返回一个普通解法，将数组置入HashMap中，然后找到最大的
     *
     * @return 普通解法
     */
    static FindLuckyIntegerInAnArray solution() {
        return new Solution();
    }

}