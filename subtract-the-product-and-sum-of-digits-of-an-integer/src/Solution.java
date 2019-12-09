public class Solution {
    public int subtractProductAndSum(int n) {
        int product = 1;
        int sum = 0;

        do {
            int i = n%10;
            product *= i;
            sum += i;
        } while ((n = n/10) > 0);

        return product - sum;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().subtractProductAndSum(4421));
        System.out.println(new Solution().subtractProductAndSum(234));
    }
}
