package org.excellent.cancer.algorithms.support;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * CVS参数转换器
 */
public final class ArgumentConverters {

    /**
     * 将字符串转换为int数组
     */
    public static class OfPrimitiveIntArray implements ArgumentConverter {

        @Override
        public Object convert(Object o, ParameterContext parameterContext) throws ArgumentConversionException {
            try {
                requireSpecifiedType(parameterContext, int[].class);

                return readPrimitiveIntArrayQuietly(String.valueOf(o));
            } catch (NumberFormatException e) {
                throw new ArgumentConversionException("其他错误", e);
            }
        }
    }

/*    public static class OfPrimitiveIntMatrix implements ArgumentConverter {

        @Override
        public Object convert(Object o, ParameterContext parameterContext) throws ArgumentConversionException {
            try {
                requireSpecifiedType(parameterContext, int[][].class);

                String source = trimBracket(String.valueOf(o));

            }
        }
    }*/


    // 帮助方法

    private static void requireSpecifiedType(ParameterContext parameterContext, Class<?> expected) throws ArgumentConversionException {
        if (parameterContext.getParameter().getType() != expected) {
            throw  new ArgumentConversionException("只支持" + expected.getName() +"转换: " + parameterContext.getParameter().getType().getName());
        }
    }

    private static int[] readPrimitiveIntArrayQuietly(String source) throws ArgumentConversionException {
        source = trimBracket(source);

        return Arrays.stream(source.split(","))
                .mapToInt(s -> Integer.parseInt(s.trim()))
                .toArray();
    }

    private static Stream<IntStream> readPrimitiveIntArray(String source) throws ArgumentConversionException {

        return null;

        // TODO 读矩阵

/*        int i = 0;

        while (i < source.length()) {
            if (source.charAt(i++) != '[') {
                throw new ArgumentConversionException("数组缺少'['符号包裹");
            }

            IntStream intStream = IntStream.of();


            String value = "";
            while (true) {

                if (i == source.length()) {
                    throw new ArgumentConversionException("数组缺少']'符号包裹");
                }

                char a = source.charAt(i) {

                }
                if (source.charAt()) {

                }

            }

        }

        if (source.charAt(i++) != '[') {
            throw new ArgumentConversionException("数组缺少'['符号包裹");
        }

        IntStream intStream = IntStream.of();

        while (true) {



        }*/
    }

    /**
     * 将字符串分别剔除前后的[]
     */
    private static String trimBracket(String source) {
        source = source.trim();

        if (source.charAt(0) != '[') {
            throw new ArgumentConversionException("数组缺少'['符号包裹");
        }

        if (source.charAt(source.length() - 1) != ']') {
            throw new ArgumentConversionException("数组缺少']'符号包裹");
        }

        return source.substring(1, source.length() - 1).trim();
    }

}
