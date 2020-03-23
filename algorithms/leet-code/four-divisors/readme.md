# 四因数

[题目链接](https://leetcode-cn.com/problems/four-divisors/)

## 解决方法

`暴力夹逼法`

### 思路

不做任何预处理，遍历整个数值，多于4因子的放弃，刚好4因子的累加

#### 代码
```java
public class Solution {
    public int sumFourDivisors(int[] nums) {
        // 因子之和
        int sum = 0;
        for (int i : nums) {
            int count = 0;
            int result = 0;

            // 遍历整个数值
            for (int j = 1, k = i; j <= k; j++) {
                // 缩小范围
                k = (int) Math.ceil(i / (j * 1.0));
                
                // j为因子，需要区分因子是否相等
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
```