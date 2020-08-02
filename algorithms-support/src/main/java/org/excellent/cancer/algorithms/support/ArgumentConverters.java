package org.excellent.cancer.algorithms.support;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

import static org.excellent.cancer.algorithms.support.ConverterUtils.readPrimitiveIntArrayQuietly;
import static org.excellent.cancer.algorithms.support.ConverterUtils.requireSpecifiedType;

/**
 * CVS参数转换器
 *
 * @author XyParaCrim
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
                throw new ArgumentConversionException("无法将字符串转换成数字", e);
            }
        }
    }

    /**
     * 将字符串转换为int矩阵
     */
    public static class OfPrimitiveIntMatrix implements ArgumentConverter {

        @Override
        public Object convert(Object o, ParameterContext parameterContext) throws ArgumentConversionException {
            try {
                requireSpecifiedType(parameterContext, int[][].class);

                return ConverterUtils.readPrimitiveIntMatrix(String.valueOf(o));
            } catch (ArgumentConversionException e) {
                throw e;
            } catch (Exception e) {
                throw new ArgumentConversionException("无法转换成矩阵", e);
            }
        }

    }

    public static class OfStringMatrix implements ArgumentConverter {

        @Override
        public Object convert(Object o, ParameterContext parameterContext) throws ArgumentConversionException {
            try {
                requireSpecifiedType(parameterContext, int[][].class);

                return ConverterUtils.readPrimitiveIntMatrix(String.valueOf(o));
            } catch (ArgumentConversionException e) {
                throw e;
            } catch (Exception e) {
                throw new ArgumentConversionException("无法转换成字符串矩阵", e);
            }
        }
    }

    public static class OfCharMaxtrix implements ArgumentConverter {

        @Override
        public Object convert(Object o, ParameterContext parameterContext) throws ArgumentConversionException {
            try {
                requireSpecifiedType(parameterContext, char[][].class);

                return ConverterUtils.readPrimitiveCharMatrix(String.valueOf(o));
            } catch (ArgumentConversionException e) {
                throw e;
            } catch (Exception e) {
                throw new ArgumentConversionException("无法转换成字符串矩阵", e);
            }
        }
    }
}
