package org.excellent.cancer.algorithms;

public interface MaximumSubarray {

    int maxSubArray(int[] nums);


    static int solution(int[] nums) {
        return new MaximumSubarrayDynamicProgramming().maxSubArray(nums);
    }

}
