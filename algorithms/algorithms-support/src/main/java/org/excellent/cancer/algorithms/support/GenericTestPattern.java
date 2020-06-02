package org.excellent.cancer.algorithms.support;

import java.util.Set;

/**
 * 解析正则字符串测试
 *
 * @param <P> resolved atomic test
 * @param <C> 测试类
 * @param <M> 测试方法
 */
public interface GenericTestPattern<P, C, M> extends TestFilter<C, M> {

    boolean hasIncludedMethodPatterns();

    boolean hasExcludedMethodPatterns();

    boolean hasMethodPatterns();

    boolean isEmpty();

    String getPluginParameterTest();

    Set<P> getIncludedPatterns();

    Set<P> getExcludedPatterns();

}
