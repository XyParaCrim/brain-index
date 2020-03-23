正则表达式匹配
======================

- [题目链接](https://leetcode-cn.com/problems/regular-expression-matching/)

# 解决方法

`NFA` `动态规划`

## NFA

非确定有限状态自动机，当且仅当自动机达到停止状态时它才找到匹配状态，与KMP完全类似。

### 思路

1. 构造一个状态机：输入正则表达式其中一个字符，可以得到输入字符可以到达的其他字符
2. 获取可到状态：输入已达状态字符，获取可到达的所有字符
    - 若无可到达状态，说明无法匹配
3. 匹配判断：按顺序取匹配字符串的字符与所有可到达正则字符匹配
    - 若匹配成功，将下一个状态置于已达状态
4. 重复步骤2，直至匹配字符串匹配完全
5. 匹配完全：若已达状态中，有任意一个状态到达结束状态，说明匹配成功


### 局限

若匹配一个字符，产生过多的可达状态，则需要逐一遍历匹配，造成过多消耗


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

## 动态规划
将`n`长度的字符串与`m`长度的正则表达式的匹配情况，全部计算出来，最终得到是否匹配。其中：
- `n`: ```1 <= n <= StringLength```
- `m`: ```1 <= m <= RegexLength```

### 思路
1. 构造匹配情况表：`T(m, n)`
2. 依次枚举`m`和`n`所有情况：
    1. `*`: `T(m, n)` = `match(m - 1, n)` ? { `T(m - 2, n - 1)` || `T(m, n - 1)` || `T(m - 2, n)` } : `T(m - 2, n)`
    2. 非`*`: `T(m, n)` = `match(m, n)` | `T(m - 1, n - 1)`
3. 返回结果`T(RegexLength, StringLength)`


-------
其中：`Match(m, n)`表示，`m`位置正则是否与`n`位置字符匹配

### 局限

动态规划需要稳定枚举出所有情况，才能通过推到得出其他情况

### 代码
```java
public boolean isMatch(String match, String regex) {

        DynamicTable dynamicTable = new DynamicTable(match.length(), regex.length());

        // 初始化长度为0的字符串与正则表达式匹配情况
        for (int i = 1; i < regex.length(); i++) {
            if (regex.charAt(i) == '*') {
                dynamicTable.matchWithEmptyMatch(i);
            }
        }

        // 计算i(0 <= i <= regex.length())长度正则表达式与j(0<= j <= match.length())长度字符串的匹配情况
        for (int i = 0; i < regex.length(); i++) {

            char regexp = regex.charAt(i);

            for (int j = 0; j < match.length(); j++) {

                char current = match.charAt(j);

                switch (matches(current, regexp)) {
                    case MATCHED:
                        dynamicTable.matched(i, j);
                        break;
                    case ASTERISK:
                        if (matchesQuietly(current, regex.charAt(i - 1)) == MatchedState.MATCHED) {
                            dynamicTable.matchedWithAsterisk(i, j);
                        } else {
                            dynamicTable.unmatchWithAsterisk(i, j);
                        }
                        break;
                    default:
                        break;
                }

            }

        }

        return dynamicTable.isMatched();
    }
```