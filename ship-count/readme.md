矩形内船只数
======================

- [题目链接](https://leetcode-cn.com/problems/number-of-ships-in-a-rectangle/solution/gen-ju-ti-mu-shu-ju-pan-duan-jie-ti-fang-fa-by-xyp/)

# 解决方法

`二分法`

## 二分法

### 思路
 因为题目出现`400`次调用API，且坐标系长度为`1000`，基本可以判断出log的关系。
 
 - 判断`topRight`和`bottomLeft`区域是否有船，若没有返回`0`
 - 判断`topRight`和`bottomLeft`区域是否是一个点，若是返回`1`
 - 判断`topRight`和`bottomLeft`区域里是x轴范围大还是y轴范围大，选择区域更大的切分
 - 若x轴范围大，左右切分；若y轴范围大，上下切分，返回两个切分区域的船只数
 
### 代码
 
    public int countShips(Sea sea, int[] topRight, int[] bottomLeft) {
            int half;
            // 这个区域是否游船，若没有返回0
            return sea.hasShips(topRight, bottomLeft) ?
                    // 这个区域是否是一个点，若是返回1
                    topRight[0] != bottomLeft[0] || topRight[1] != bottomLeft[1] ?
                            // 判断x轴范围大还是y轴范围大
                            topRight[0] - bottomLeft[0] > topRight[1] - bottomLeft[1] ?
                                    // 分成左右两块
                                    countShips(sea, new int[]{half = (topRight[0] + bottomLeft[0]) >> 1, topRight[1]}, bottomLeft) +
                                            countShips(sea, topRight, new int[]{half + 1, bottomLeft[1]}) :
                                    // 分成上下两块
                                    countShips(sea, new int[]{topRight[0], half = (topRight[1] + bottomLeft[1]) >> 1}, bottomLeft) +
                                            countShips(sea, topRight, new int[]{bottomLeft[0], half + 1}) :
                            1 :
                    0;
    }
    
### 运行情况
执行用时：`1 ms`

