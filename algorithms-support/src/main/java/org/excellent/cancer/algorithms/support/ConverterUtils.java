package org.excellent.cancer.algorithms.support;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;

import java.util.LinkedList;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 关于转换器的一些帮助方法
 */
public final class ConverterUtils {

    // 重要的参数

    public static final String ARRAY_SPILT = ",";

    // 参数类型验证

    /**
     * 判断测试方法所表识的参数类型是否于期望的相同
     *
     * @param parameterContext 参数上下文
     * @param expected         期望类型
     */
    public static void requireSpecifiedType(ParameterContext parameterContext, Class<?> expected) {
        Class<?> actual = parameterContext.getParameter().getType();
        if (actual != expected) {
            throw new ArgumentConversionException("Require type: " + expected.getName() + ",  Actual type: " + actual.getName());
        }
    }

    // 字符串转换

    /**
     * 默认输入字符串是按照正确格式输入的数组
     *
     * @param source 输入数组字符串
     * @return 转化后的整数数组
     */
    public static int[] readPrimitiveIntArrayQuietly(String source) {
        return Stream.of(unwrapArray(source).split(ARRAY_SPILT))
                .mapToInt(i -> Integer.parseInt(i.trim()))
                .toArray();
    }

    /**
     * 将字符串转化为字符矩阵，即二维数组
     *
     * @param source 输入数组字符串
     * @return 转化为字符矩阵
     */
    public static char[][] readPrimitiveCharMatrix(String source) {
        int depth = 0; // 表示发现左括号的次数，符号栈的深度
        int i = -1;     // 表示当前遍历的索引
        boolean spilt = false; // 数组之间是否已经逗号分割

        LinkedList<char[]> arrays = new LinkedList<>();

        while (++i < source.length()) {
            // 处理只遇到[符号0～1次的情况，即还未需要编译整数数值
            if (depth < 2) {
                switch (source.charAt(i)) {
                    case ' ': break;
                    case ',':
                        if (spilt && !arrays.isEmpty()) {
                            throwForRepeatSeparator();
                        }
                        spilt = true;
                        break;
                    case '[':
                        depth++;
                        break;
                    case ']':
                        if (depth > 0) {
                            depth--;
                            break;
                        }
                        // 其他情况抛出异常
                    default:
                        throwForParenthesesException();
                }
            }

            // 处理需要读数组的阶段
            else {

                // 当不是数组第一项且没有分隔符的情况
                if (!spilt && !arrays.isEmpty()) {
                    throwForNoneSeparator();
                }

                i = skipSpaceWhite(source, i);

                // 处理无数值的数组
                if (source.charAt(i) == ']') {
                    char[] newArray = new char[0];
                    if (arrays.isEmpty() || arrays.peek().length == newArray.length) {
                        depth--;
                        spilt = false;
                        arrays.add(newArray);

                        continue;
                    }

                    throwForDifferentArrayLength();
                }

                // 正式进入读取数值

                StringBuilder builder = new StringBuilder();

                while (true) {
                    // 跳过空白

                    i = skipSpaceWhite(source, i);

                    // 读出下一个数字
                    char value =  source.substring(i, i = nextInt(source, i)).charAt(0);

                    builder.append(value);

                    i = skipSpaceWhite(source, i);

                    if (i < source.length()) {

                        if (source.charAt(i) == ',') {
                            i++;
                            continue;
                        }

                        if (source.charAt(i) == ']') {
                            depth--;
                            spilt = false;
                            char[] newArray = builder.toString().toCharArray();
                            if (arrays.isEmpty() || arrays.peek().length == newArray.length) {
                                arrays.add(newArray);
                                break;
                            }

                            throwForDifferentArrayLength();
                        }
                    }

                    throwForOthers();
                }


            }
        }

        if (depth != 0) {
            throwForOthers();
        }

        int length = arrays.isEmpty() ? 0 : arrays.peek().length;

        return arrays.toArray(size -> new char[size][length]);
    }

    /**
     * 将字符串转化为整型矩阵，即二维数组
     *
     * @param source 输入数组字符串
     * @return 转化后的整数矩阵
     */
    public static int[][] readPrimitiveIntMatrix(String source) {
        int depth = 0; // 表示发现左括号的次数，符号栈的深度
        int i = -1;     // 表示当前遍历的索引
        boolean spilt = false; // 数组之间是否已经逗号分割

        LinkedList<int[]> arrays = new LinkedList<>();

        while (++i < source.length()) {
            // 处理只遇到[符号0～1次的情况，即还未需要编译整数数值
            if (depth < 2) {
                switch (source.charAt(i)) {
                    case ' ': break;
                    case ',':
                        if (spilt && !arrays.isEmpty()) {
                            throwForRepeatSeparator();
                        }
                        spilt = true;
                        break;
                    case '[':
                        depth++;
                        break;
                    case ']':
                        if (depth > 0) {
                            depth--;
                            break;
                        }
                    // 其他情况抛出异常
                    default:
                        throwForParenthesesException();
                }
            }

            // 处理需要读数组的阶段
            else {

                // 当不是数组第一项且没有分隔符的情况
                if (!spilt && !arrays.isEmpty()) {
                    throwForNoneSeparator();
                }

                i = skipSpaceWhite(source, i);

                // 处理无数值的数组
                if (source.charAt(i) == ']') {
                    int[] newArray = new int[0];
                    if (arrays.isEmpty() || arrays.peek().length == newArray.length) {
                        depth--;
                        spilt = false;
                        arrays.add(newArray);

                        continue;
                    }

                    throwForDifferentArrayLength();
                }

                // 正式进入读取数值

                IntStream.Builder builder = IntStream.builder();

                while (true) {
                    // 跳过空白

                    i = skipSpaceWhite(source, i);

                    // 读出下一个数字
                    int value = Integer.parseInt(source.substring(i, i = nextInt(source, i)));

                    builder.add(value);

                    i = skipSpaceWhite(source, i);

                    if (i < source.length()) {

                        if (source.charAt(i) == ',') {
                            i++;
                            continue;
                        }

                        if (source.charAt(i) == ']') {
                            depth--;
                            spilt = false;
                            int[] newArray = builder.build().toArray();
                            if (arrays.isEmpty() || arrays.peek().length == newArray.length) {
                                arrays.add(newArray);
                                break;
                            }

                            throwForDifferentArrayLength();
                        }
                    }

                    throwForOthers();
                }


            }
        }

        if (depth != 0) {
            throwForOthers();
        }

        int length = arrays.isEmpty() ? 0 : arrays.peek().length;

        return arrays.toArray(size -> new int[size][length]);
    }

    /**
     * 跳过当前索引的空白
     *
     * @param source 源字符串
     * @param i      当前索引
     * @return 跳过后的索引
     */
    private static int skipSpaceWhite(String source, int i) {
        while (source.charAt(i) == ' ') {
            if (++i == source.length() - 1) {
                throw new ArgumentConversionException("无法转换数组");
            }
        }
        return i;
    }

    /**
     * 读取下一个整数数字
     *
     * @param source 源字符串
     * @param i      当前索引
     * @return 返回下一个数字的结束索引
     */
    @SuppressWarnings("StatementWithEmptyBody")
    private static int nextInt(String source, int i) {
        while (Character.isDigit(source.charAt(i)) && ++i < source.length()) ;

        return i;
    }

    // 格式验证

    /**
     * 剔除包裹数值数据的"["和"]"符号
     *
     * @param wrapped 包裹"["和"]"符号的数组
     * @return 剔除后的字符串
     */
    public static String unwrapArray(String wrapped) throws ArgumentConversionException {
        int first = wrapped.indexOf('[');
        if (first < 0) {
            throw new ArgumentConversionException("数组缺少'['符号包裹");
        }

        int last = wrapped.lastIndexOf(']');
        if (last < 0) {
            throw new ArgumentConversionException("数组缺少'['符号包裹");
        }

        if (first > last) {
            throw new ArgumentConversionException("无法识别数组格式");
        }

        return wrapped.substring(first + 1, last);
    }

    /**
     * 包装数组格式
     *
     * @param unwrapped 未包装的数组字符串
     * @return 包装过后的数组格式字符串
     */
    public static String wrapArray(String unwrapped) {
        return "[" + (unwrapped == null ? "" : unwrapped) + "]";
    }

    // 参数转化异常

    /**
     * 抛出{@link ArgumentConversionException}异常：重复出现分割符号
     */
    private static void throwForRepeatSeparator() {
        throw new ArgumentConversionException("数组重复分割");
    }

    /**
     * 抛出{@link ArgumentConversionException}异常：缺少分隔符
     */
    private static void throwForNoneSeparator() {
        throw new ArgumentConversionException("缺少分隔符");
    }

    /**
     * 抛出{@link ArgumentConversionException}异常：数组括号需要按顺序成对出现
     */
    private static void throwForParenthesesException() {
        throw new ArgumentConversionException("数组括号需要按顺序成对出现");
    }

    /**
     * 抛出{@link ArgumentConversionException}异常：数组长度不一致
     */
    private static void throwForDifferentArrayLength() {
        throw new ArgumentConversionException("数组长度不一致");
    }

    /**
     * 抛出{@link ArgumentConversionException}异常：暂时无法用言语形容的异常
     */
    private static void throwForOthers() {
        throw new ArgumentConversionException("无法转换数组");
    }


}
