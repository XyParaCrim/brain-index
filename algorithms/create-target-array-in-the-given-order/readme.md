# 按既定顺序创建目标数组

[题目链接](https://leetcode-cn.com/problems/create-target-array-in-the-given-order/)

## 解决方法
`链表`

### 链表

#### 思路

此题插入数组的行为和链表插入的行为一致，只是`LinkedList`不是`RandomAccess`，选择插入的位置需要消耗一些时间

##### 代码
```java
public class Solution {
    public int[] createTargetArray(int[] nums, int[] index) {
        return IntStream.range(0, nums.length)
                .collect(LinkedList<Integer>::new, (list, value) -> list.add(index[value], nums[value]), (a, b) -> {})
                .stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }
}
```

