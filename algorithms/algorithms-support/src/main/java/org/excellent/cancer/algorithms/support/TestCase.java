package org.excellent.cancer.algorithms.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表识测试用例的解析方式
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestCase {

    /**
     * 算法输入参数类型，程序会按照类型格式读取数据
     *
     * @return 算法输入参数类型
     */
    Class<?>[] value();

    /**
     * 扫描所有测试用例文件，用于读取
     *
     * @return 测试用例文件路径
     */
    String[] casePath() default {};

}
