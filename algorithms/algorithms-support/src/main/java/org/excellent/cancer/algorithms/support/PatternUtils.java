package org.excellent.cancer.algorithms.support;

import org.jetbrains.annotations.NotNull;

import java.util.StringTokenizer;
import java.util.stream.Stream;

/**
 * 代码笔记
 */
public final class PatternUtils {

    public final static String REGEX_PREFIXED = "%regex[";

    public final static String REGEX_POSTFIX = "]";

    public final static String ANT_PREFIXED = "%ant[";

    public final static String ANT_POSTFIX = REGEX_POSTFIX;

    /**
     * 判断指定的字符串模式是否是正则表达式
     *
     * @param pattern 字符串模式
     * @return 是否是正则表达式
     */
    public static boolean isRegexPrefixedPattern(@NotNull String pattern) {
        return pattern.length() > REGEX_PREFIXED.length() + REGEX_POSTFIX.length() + 1 &&
                pattern.startsWith(REGEX_PREFIXED) &&
                pattern.endsWith(REGEX_POSTFIX);
    }

    /**
     * 剔除表达正则模式的字符
     *
     * @param source 需要剔除的正则模式字符串
     * @return 正则表达式
     */
    public static String trimRegexPrefixedPattern(@NotNull String source) {
        return source.substring(REGEX_PREFIXED.length(), source.length() - REGEX_POSTFIX.length());
    }

    /**
     * 判断指定字符串是否ant模式
     *
     * @param pattern 字符串模式
     * @return 是否是Ant
     */
    public static boolean isAntPrefixedPattern(@NotNull String pattern) {
        return pattern.length() > ANT_PREFIXED.length() + ANT_POSTFIX.length() + 1 &&
                pattern.startsWith(ANT_PREFIXED) &&
                pattern.startsWith(ANT_POSTFIX);
    }

    /**
     * 剔除表达ant模式的字符
     *
     * @param source 需要剔除的ant模式字符串
     * @return ant字符串
     */
    public static String trimAntPrefixedPattern(@NotNull String source) {
        return source.substring(ANT_PREFIXED.length(), source.length() - ANT_POSTFIX.length());
    }

    /**
     * 通过指定路径字符串和分隔符，返回token数组
     *
     * @param path      路径字符串
     * @param separator 分隔符
     * @return token数组
     */
    public static String[] tokenizePathToString(@NotNull String path, @NotNull String separator) {
        Stream.Builder<String> stream = Stream.builder();
        StringTokenizer tokenizer = new StringTokenizer(path, separator);

        while (tokenizer.hasMoreTokens()) {
            stream.add(tokenizer.nextToken());
        }

        return stream.build().toArray(String[]::new);
    }

    public static boolean matchAntPathPattern(MatchPattern matchPattern, String path, String separator, boolean isCaseSensitive) {
        return separatorPatternStartSlashMismatch(matchPattern, path, separator) &&
                matchAntPathPattern(matchPattern.getTokenizedPathString(), tokenizePathToString(path, separator), isCaseSensitive);
    }

    /**
     * 输入模式目录和匹配目录，判断它们是否匹配
     *
     * @param patternDirectories 模式目录
     * @param matchDirectories 匹配目录
     * @param isCaseSensitive 是否区分大小些
     * @return 是否匹配
     */
    public static boolean matchAntPathPattern(String[] patternDirectories, String[] matchDirectories, boolean isCaseSensitive) {
        int fromMatch = 0;
        int fromPattern = 0;
        int toMatch = matchDirectories.length - 1;
        int toPattern = patternDirectories.length - 1;

        String current;

        // 向前依次将pattern目录与match目录进行匹配：
        //   若遇到通配符（**），则退出匹配
        //   若其中任意目录不匹配，则返回false
        while (fromPattern <= toPattern && fromMatch <= toMatch) {
            current = patternDirectories[fromPattern];
            if (current.equals("**")) {
                break;
            }

            if (!match(current, matchDirectories[fromMatch], isCaseSensitive)) {
                return false;
            }

            fromMatch++;
            fromPattern++;
        }

        // match目录已经全部匹配完成，说明需要判断pattern目录是否匹配完全：
        //   若匹配完成，则说明匹配成功
        //   若匹配未完成，则pattern目录剩下的每一个目录都必须是**
        if (fromMatch > toMatch) {
            for (int i = fromPattern; i <= toPattern; i++) {
                if (!patternDirectories[i].equals("**")) {
                    return false;
                }
            }

            return true;
        }

        // pattern目录已经全部匹配成功，说明需要判断match目录未匹配完全：
        //   说明匹配不成功
        if (fromPattern > toPattern) {
            return false;
        }

        // pattern目录和match目录都有剩余的目录还未匹配

        // 向后依次将pattern目录与match目录进行匹配：
        //   若遇到通配符（**），则退出匹配
        //   若其中任意目录不匹配，则返回false
        while (fromPattern <= toPattern && fromMatch <= toMatch) {
            current = patternDirectories[toPattern];
            if (current.equals("**")) {
                break;
            }

            if (!match(current, matchDirectories[toMatch], isCaseSensitive)) {
                return false;
            }

            toPattern--;
            toMatch--;
        }

        // 依然是判断match目录是否匹配完成
        //   若匹配完成，则说明匹配成功
        //   若匹配未完成，则pattern目录剩下的每一个目录都必须是**
        if (fromMatch > toMatch) {
            for (int i = fromPattern; i <= toPattern; i++) {
                if (!patternDirectories[i].equals("**")) {
                    return false;
                }
            }

            return true;
        }

        // match目录依然没有匹配完成，会有以下几种情况

        // pattern目录已经匹配完全，需要返回false
/*        if (fromPattern > toPattern) {
            return false;
        }*/

        // 前后都是通配的pattern目录
        while (fromPattern != toPattern && fromMatch <= toMatch) {
            int wildcardEnd = -1;

            // 寻找下一个不是通配符的目录长度
            for (int i = fromPattern + 1; i <= toPattern; i++) {
                if (patternDirectories[i].equals("**")) {
                    wildcardEnd = i;
                    break;
                }
            }

            // 说明下一个目录为通配符
            if (wildcardEnd == fromPattern + 1) {
                fromPattern++;
            }

            // 处理一段非通配符的目录
            else {
                // fromPattern是通配符，wildcardEnd也是通配符
                int patternLength = wildcardEnd - fromPattern - 1;
                int matchLength = toMatch - fromMatch - 1;
                int foundIndex = -1;
                int j = 0;

                label:
                while (j <= matchLength - patternLength) {

                    for (int k = 0; k < patternLength; k++) {

                        String subPath = patternDirectories[fromPattern + k + 1];
                        String subSource = matchDirectories[fromMatch + j + k];
                        if (!match(subPath, subSource, isCaseSensitive)) {
                            j++;
                            continue label;
                        }
                    }

                    foundIndex = fromMatch + j;
                    break;
                }

                if (foundIndex == -1) {
                    return false;
                }

                fromPattern = wildcardEnd;
                fromMatch = foundIndex + patternLength;

            }

        }

        for (int i = fromPattern; i <= toPattern; i++) {
            if (!patternDirectories[i].equals("**")) {
                return false;
            }
        }

        return true;
    }

    public static boolean match(String pattern, String str, boolean isCaseSensitive) {
        return false;
    }


    public static boolean separatorPatternStartSlashMismatch(@NotNull MatchPattern matchPattern, @NotNull String str, @NotNull String separator) {
        return str.startsWith(separator) != matchPattern.startWith(separator);
    }
}
