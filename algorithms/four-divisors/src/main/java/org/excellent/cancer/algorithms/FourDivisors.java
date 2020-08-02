package org.excellent.cancer.algorithms;

public interface FourDivisors {

    /**
     * 给你一个整数数组 nums，请你返回该数组中恰有四个因数的这些整数的各因数之和
     *
     * @param nums 整数数组
     * @return 因数之和
     */
    int sumFourDivisors(int[] nums);

    /**
     * 常规解法
     *
     * @return 因数之和
     */
    static FourDivisors solution() {
        return new Solution();
    }

}
