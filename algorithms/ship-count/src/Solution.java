@SuppressWarnings("unused")
class Solution {
    interface Sea {
        boolean hasShips(int[] topRight, int[] bottomLeft);
    }

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
}