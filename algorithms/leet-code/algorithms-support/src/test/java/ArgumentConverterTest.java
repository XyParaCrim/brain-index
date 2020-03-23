import org.excellent.cancer.algorithms.support.ConverterUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArgumentConverterTest {

    @Nested
    public class String2Matrix {

        @ParameterizedTest
        @CsvSource({
                "'[[], [], []]', 3, 0",
                "'[[1, 2, 3], [1, 2, 3], [1, 2, 3]]', 3, 3"
        })
        @DisplayName("String -> int[][]")
        public void convertMatrixTest(String source, int row, int column) {
            int[][] matrix = ConverterUtils.readPrimitiveIntMatrix(source);

            Assertions.assertEquals(row, matrix.length);
            Assertions.assertEquals(column, matrix[0].length);
        }


        @ParameterizedTest
        @CsvSource({
                "'[[], , [], []]', '数组重复分割'",
                "'[[1, 2, 3], [1,2,3], [1, 2]]', '数组长度不一致'",
                "'[[1, 2, 3] [1,2,3], [1, 2]]', '数组需要\",\"分割'",
                "'[1, 2, 3], [1,2,3]', '数组缺少\"[\"符号包裹'"
        })
        @DisplayName("String -> int[][]: 异常测试")
        public void convertMatrixErrorTest(final String source, String message) {
            Assertions.assertThrows(ArgumentConversionException.class, () -> ConverterUtils.readPrimitiveIntMatrix(source), message);
        }

    }

    @Nested
    public class String2Array {

        /**
         * 随机产生一定长度的整型数组，将其转成字符串，然后比较计算结果
         */
        @RepeatedTest(100)
        @DisplayName("String -> int[]")
        public void convertArrayTest() {
            Random random = new Random();
            for (int i = 1; i < 100; i++) {
                int[] expected = random.ints(i).toArray();

                String source = IntStream.of(expected).
                        mapToObj(String::valueOf).
                        collect(Collectors.joining(","));

                Assertions.assertArrayEquals(expected, ConverterUtils.readPrimitiveIntArrayQuietly(ConverterUtils.wrapArray(source)));
            }
        }

    }

}
