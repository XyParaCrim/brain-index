# 最大子序和

- [题目链接](https://leetcode-cn.com/problems/maximum-subarray/)

## 解决方法

`贪心算法`、`动态规划`

###  贪心算法

### 动态规划

设`dp(n)`为包括下标为n的字符串，最大子序和的最大值

#### 代码

```java

class DynamicProgramming {

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


```