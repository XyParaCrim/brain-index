package org.excellent.cancer.algorithms.support;

import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.Objects;

/**
 * 添加更多的断言
 *
 * @author XyParaCrim
 */
public final class ExcellentAssertions {

    /**
     * 匹配数组内任意一项匹配
     *
     * @param array 期望数组
     * @param actual 实际值
     * @param <T> 类型
     */
    public static <T> void assertMatchAny(T[] array, T actual) {

        for (T expected : array) {

            if (Objects.equals(expected, actual)) {

                Assertions.assertTrue(true, "Match any: " + expected);
                return;
            }

        }

        Assertions.fail("Expected " + Arrays.toString(array) + ", but " + actual);

    }

}
