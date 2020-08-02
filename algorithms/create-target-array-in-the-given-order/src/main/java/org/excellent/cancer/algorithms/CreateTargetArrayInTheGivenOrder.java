package org.excellent.cancer.algorithms;

public interface CreateTargetArrayInTheGivenOrder {

    /**
     * 按照给你的索引顺序，重新将数值排序，返回新数值
     *
     * @param nums 数值
     * @param index 数值顺序
     * @return 调好顺序的数值
     */
    int[] createTargetArray(int[] nums, int[] index);

    /**
     * Java原生链表实现
     *
     * @return 返回默认实现
     */
    static CreateTargetArrayInTheGivenOrder solution() {
        return new Solution();
    }
}
