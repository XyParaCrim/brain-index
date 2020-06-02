package org.excellent.cancer.algorithms.support;

/**
 * 测试筛选器的范型接口
 *
 * @param <C> 测试类
 * @param <M> 测试方法
 */
public interface TestFilter<C, M> {

    boolean shouldRun(C testClass, M methodName);

}
