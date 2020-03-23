# 检查网格中是否存在有效路径

- [题目链接](https://leetcode-cn.com/problems/check-if-there-is-a-valid-path-in-a-grid/)

## 解决方法

`BFS`

### BFS

#### 思路

经典搜索背公式，其中需要注意：

- 建立一个街道方向表`T(in, street)`：输入街道`street`和进入街道方向`in`，获取是否有出去街道方向`out`

#### 代码
* `T(in, street)`为代码中的`directionTable`
* `Street`代表表格中的一个街道，其中重要的成员变量：
    * `Street.in`：进入街道的方向
    * `Street.out`：离开街道的方向
    * `Street.row`：`grid`的纵坐标
    * `Street.column`：`grid`的横坐标
    
```java
// 经典搜索公式
while (!queue.isEmpty()) {
    Street street = queue.poll();

    // 标记是否已经搜索过
    walked[street.row][street.column] = true;
    // 
    int nextRow = street.nextRow();
    int nextColumn = street.nextColumn();

    // 判断是否出界且是否搜索过
    if (nextColumn >= 0 && nextColumn < columns && nextRow >= 0 && nextRow < rows && !walked[nextRow][nextColumn]) {

        //当前街道的出去方向，即下一个街道的输入方向
        // 获取下一个街道的出去方向
        Direction nextIn = street.out;
        Direction nextOut = directionTable.
                // 支持该入口方向的所有街道
                get(nextIn).
                // 下个一街道是否有出口
                get(grid[nextRow][nextColumn]);

        if (nextOut != null) {
            // 判断是否到达右下角街道
            if (nextRow == rows - 1 && nextColumn == columns - 1) {
                return true;
            }

            queue.add(new Street(nextRow, nextColumn, nextIn, nextOut));

        }
    }
}
return false;
```


