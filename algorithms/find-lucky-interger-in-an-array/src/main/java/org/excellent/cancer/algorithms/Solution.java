package org.excellent.cancer.algorithms;

import java.util.HashMap;
import java.util.Map;

/**
 * 在数组中找到出现次数与值相等的最大数
 */
class Solution implements FindLuckyIntegerInAnArray {

    /**
     *  先将数组按数字和出现次数更新到HashMap中，然后找出最大的
     *
     * @param arr 输入数组
     * @return 出现次数与值相等的最大数
     */
    @Override
    public int findLucky(int[] arr) {
        Map<Integer, Integer> arrMap = new HashMap<>(arr.length);
        for (int i : arr) {
            arrMap.put(i, arrMap.getOrDefault(i, 0) + 1);
        }

        return arrMap.
                keySet().
                stream().
                filter(i -> i.equals(arrMap.get(i))).
                mapToInt(Integer::intValue).
                max().
                orElse(-1);
    }
}
