package org.excellent.cancer.algorithms;

import java.util.stream.IntStream;

/**
 * 动态规划
 *
 * @author XyParaCrim
 */
class MaximumSubarrayDynamicProgramming implements MaximumSubarray {

    @Override
    public int maxSubArray(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] > 0) {
                nums[i] += nums[i - 1];
            }
        }

        //noinspection OptionalGetWithoutIsPresent
        return IntStream.of(nums).max().getAsInt();
    }
}
