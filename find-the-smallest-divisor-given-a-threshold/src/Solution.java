public class Solution {
    public int smallestDivisor(int[] nums, int threshold) {
        long sum = 0;
        for (int value : nums) {
            sum += value;
        }

        long min = (long) Math.ceil(sum * 1.0 / threshold);
        long max = (long) Math.floor(sum * 1.0 / (threshold - nums.length));
        long mid = (min + max) / 2;

        while (max > mid) {
            sum = 0;
            for (int num : nums) {
                sum += (long) Math.ceil(num * 1.0 / mid);
            }

            if (sum <= threshold) {
                max = mid;
            } else {
                min = mid + 1;
            }

            mid = (max + min) / 2;
        }

        return (int) mid;
    }

    public static void main(String[] args) {
//        System.out.println(new Solution().smallestDivisor(new int[]{1, 2, 5, 9}, 6));
//        System.out.println(new Solution().smallestDivisor(new int[]{2, 3, 5, 7, 11}, 11));
//        System.out.println(new Solution().smallestDivisor(new int[]{19}, 5));
//        System.out.println(new Solution().smallestDivisor(new int[]{962551, 933661, 905225, 923035, 990560}, 10));
    }
}
