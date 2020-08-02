package org.excellent.cancer.algorithms;

public class Solution implements FourDivisors {

    /**
     * 从头到尾夹，因子等于则叠加
     *
     * @param nums 整数数组
     * @return 因子之后
     */
    @Override
    public int sumFourDivisors(int[] nums) {

        int sum = 0;

        for (int i : nums) {

            int count = 0;
            int result = 0;

            for (int j = 1, k = i; j <= k; j++) {

                k = (int) Math.ceil(i / (j * 1.0));

                if (i % j == 0) {

                    if (j == k) {

                        count++;

                        result += j;

                    } else {

                        count += 2;

                        result += j + k;

                    }

                    if (count > 4) {
                        break;
                    }

                }

            }

            if (count == 4) {
                sum += result;
            }

        }

        return sum;
    }

}
