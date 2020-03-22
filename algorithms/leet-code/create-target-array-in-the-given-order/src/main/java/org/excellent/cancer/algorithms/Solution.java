package org.excellent.cancer.algorithms;

import java.util.LinkedList;
import java.util.stream.IntStream;

/**
 * 通过链表插入实现
 */
class Solution implements CreateTargetArrayInTheGivenOrder {

    @Override
    public int[] createTargetArray(int[] nums, int[] index) {
        return IntStream.range(0, nums.length)
                .collect(LinkedList<Integer>::new, (list, value) -> list.add(index[value], nums[value]), (a, b) -> {})
                .stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

}
