正则表达式匹配
======================

- [题目链接](https://leetcode-cn.com/problems/regular-expression-matching/)

# 解决方法

`NFA` `动态规划`

## NFA

非确定有限状态自动机，当且仅当自动机达到停止状态时它才找到匹配状态，与KMP完全类似。

### 思路

**TODO**


### 代码

```java
public boolean isMatch(String s, String p) {
        // 建立状态图
        int[] graph = makeGraph(p);
        int endState = graph.length;

        // 每轮遍历匹配的状态，matchLength记录状态个数
        int[] matchable = new int[endState + 1];
        int matchLength = 1;
        matchable[0] = 0;

        // 下一轮遍历可以匹配的状态，reachLength记录状态个数
        int[] reachable = new int[endState + 1];
        int reachLength = reachable(graph, reachable, matchable, matchLength);

        char current, regexp;
        for (int i = 0; i < s.length(); i++) {
            matchLength = 0;
            current = s.charAt(i);

            // 判断current是否可以匹配状态，若匹配则记录下一个状态至matchable
            for (int j = reachLength - 1; j > -1; j--) {
                if (reachable[j] == endState) continue;
                regexp = p.charAt(reachable[j]);
                if (regexp == current || regexp == '.') {
                    matchable[matchLength++] = reachable[j] + 1;
                }
            }

            reachLength = reachable(graph, reachable, matchable, matchLength);

            // 若下一轮无状态匹配，宣告失败
            if (reachLength == 0) {
                return false;
            }
        }

        // 是否有任意一个状态到达endState
        for (int i = 0; i < reachLength; i++) {
            if (reachable[i] == endState) {
                return true;
            }
        }

        return false;
    }
```

### 运行情况
执行时间：`2 ms`
消耗内存：`35.7 MB`