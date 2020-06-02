package org.excellent.cancer.algorithms.support;

import org.apache.maven.surefire.shared.utils.io.MatchPatterns;
import org.apache.maven.surefire.shared.utils.io.SelectorUtils;

import java.io.File;
import java.util.Objects;
import java.util.regex.Pattern;

import static java.io.File.separatorChar;
import static java.util.regex.Pattern.compile;
import static org.apache.maven.surefire.shared.utils.StringUtils.isBlank;
import static org.apache.maven.surefire.shared.utils.io.SelectorUtils.*;

@SuppressWarnings("unused")
public final class ResolvedSolutionPattern {

    private static final String CLASS_FILE_EXTENSION = ".class";

    private static final String JAVA_FILE_EXTENSION = ".java";

    private static final String WILDCARD_PATH_PREFIX = "**/";

    private static final String WILDCARD_FILENAME_POSTFIX = ".*";

    // member variable

    private final String classPattern;

    private final String methodPattern;

    private final boolean isRegexSolutionClassPattern;

    private final boolean isRegexSolutionMethodPattern;

    private final String description;

    private final ClassMatcher classMatcher = new ClassMatcher();

    private final MethodMatcher methodMatcher = new MethodMatcher();

    public ResolvedSolutionPattern(String classPattern, String methodPattern, boolean isRegex) {
        classPattern = tryTrim(classPattern);
        methodPattern = tryTrim(methodPattern);
        this.description = description(classPattern, methodPattern, isRegex);
        if (isRegex) {
            if (classPattern != null) {
                classPattern = wrapRegex(classPattern);
            }
            if (methodPattern != null) {
                methodPattern = wrapRegex(methodPattern);
            }
        }

        this.classPattern = reformatClassPattern(classPattern, isRegex);
        this.methodPattern = methodPattern;
        this.isRegexSolutionClassPattern = isRegex;
        this.isRegexSolutionMethodPattern = isRegex;
        this.methodMatcher.sanityCheck();
    }

    public String getClassPattern() {
        return classPattern;
    }

    public boolean hasClassSolutionPattern() {
        return classPattern != null;
    }

    public boolean isRegexSolutionClassPattern() {
        return isRegexSolutionClassPattern;
    }

    public boolean isRegexSolutionMethodPattern() {
        return isRegexSolutionMethodPattern;
    }

    public boolean isEmpty() {
        return classPattern == null && methodPattern == null;
    }

    public boolean matchAsInclusive(String solutionClassFile, String methodName) {
        solutionClassFile = tryTrim(solutionClassFile);
        methodName = tryTrim(methodName);

        return isEmpty() || alwaysInclusiveQuietly(solutionClassFile) || match(solutionClassFile, methodName);
    }

    public boolean matchAsExclusive(String solutionClassFile, String methodName) {
        solutionClassFile = tryTrim(solutionClassFile);
        methodName = tryTrim(methodName);

        return !isEmpty() && canMatchExclusive(solutionClassFile, methodName) && match(solutionClassFile, methodName);
    }

    // private methods

    private boolean alwaysInclusiveQuietly(String classFile) {
        return classFile == null && classPattern != null;
    }

    private boolean match(String classFile, String methodName) {
        return matchClass(classFile) && matchMethod(methodName);
    }

    private boolean matchClass(String classFile) {
        return classPattern == null || classMatcher.matchSolutionClassFile(classFile);
    }

    private boolean matchMethod(String methodName) {
        return methodPattern == null || methodName == null || methodMatcher.matchMethodName(methodName);
    }

    private boolean canMatchExclusive(String testClassFile, String methodName) {
        return canMatchExclusiveMethods(testClassFile, methodName)
                || canMatchExclusiveClasses(testClassFile, methodName)
                || canMatchExclusiveAll(testClassFile, methodName);
    }

    private boolean canMatchExclusiveMethods(String testClassFile, String methodName) {
        return testClassFile == null && methodName != null && classPattern == null && methodPattern != null;
    }

    private boolean canMatchExclusiveClasses(String testClassFile, String methodName) {
        return testClassFile != null && methodName == null && classPattern != null && methodPattern == null;
    }

    private boolean canMatchExclusiveAll(String testClassFile, String methodName) {
        return testClassFile != null && methodName != null && (classPattern != null || methodPattern != null);
    }


    // override methods

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResolvedSolutionPattern that = (ResolvedSolutionPattern) o;

        return Objects.equals(classPattern, that.classPattern) && Objects.equals(methodPattern, that.methodPattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classPattern, methodPattern);
    }

    @Override
    public String toString() {
        return isEmpty() ? "" : description;
    }

    // static methods

    private static String tryTrim(String str) {
        return str == null || (str = str.trim()).isEmpty() ? null : str;
    }

    private static String description(String clazz, String method, boolean isRegex) {
        String description;
        if (clazz == null && method == null) {
            description = null;
        } else if (clazz == null) {
            description = "#" + method;
        } else if (method == null) {
            description = clazz;
        } else {
            description = clazz + "#" + method;
        }

        return isRegex && description != null ? wrapRegex(description) : description;
    }

    @SuppressWarnings("ConstantConditions")
    private static String reformatClassPattern(String s, boolean isRegex) {
        if (s != null && !isRegex) {
            String path = convertToPath(s);
            path = fromFullyQualifiedClass(path);
            if (path != null && !path.startsWith(WILDCARD_PATH_PREFIX)) {
                path = WILDCARD_PATH_PREFIX + path;
            }
            return path;
        } else {
            return s;
        }
    }

    private static String convertToPath(String className) {
        if (isBlank(className)) {
            return null;
        } else {
            if (className.endsWith(JAVA_FILE_EXTENSION)) {
                className = className.substring(0, className.length() - JAVA_FILE_EXTENSION.length())
                        + CLASS_FILE_EXTENSION;
            }
            return className;
        }
    }

    static String fromFullyQualifiedClass(String cls) {
        if (cls.endsWith(CLASS_FILE_EXTENSION)) {
            String className = cls.substring(0, cls.length() - CLASS_FILE_EXTENSION.length());
            return className.replace('.', '/') + CLASS_FILE_EXTENSION;
        } else if (!cls.contains("/")) {
            if (cls.endsWith(WILDCARD_FILENAME_POSTFIX)) {
                String clsName = cls.substring(0, cls.length() - WILDCARD_FILENAME_POSTFIX.length());
                return clsName.contains(".") ? clsName.replace('.', '/') + WILDCARD_FILENAME_POSTFIX : cls;
            } else {
                return cls.replace('.', '/');
            }
        } else {
            return cls;
        }
    }

    static String wrapRegex(String unwrapped) {
        return SelectorUtils.REGEX_HANDLER_PREFIX + unwrapped + SelectorUtils.PATTERN_HANDLER_SUFFIX;
    }

    private static void checkIllegalCharacters(String... expressions) {
        for (String expression : expressions) {
            if (expression.contains("#")) {
                throw new IllegalArgumentException("Extra '#' in regex: " + expression);
            }
        }
    }

    private static void throwSanityError(IllegalArgumentException e) {
        throw new IllegalArgumentException("%regex[] usage rule violation, valid regex rules:\n"
                + " * <classNameRegex>#<methodNameRegex> - "
                + "where both regex can be individually evaluated as a regex\n"
                + " * you may use at most 1 '#' to in one regex filter. "
                + e.getLocalizedMessage(), e);
    }

    private final class ClassMatcher {

        private volatile MatchPatterns cache;

        private MatchPatterns of(String... sources) {
            if (cache == null) {
                try {
                    checkIllegalCharacters(sources);
                    cache = MatchPatterns.from(sources);
                } catch (IllegalArgumentException e) {
                    throwSanityError(e);
                }
            }
            return cache;
        }

        boolean matchSolutionClassFile(String classFile) {
            return ResolvedSolutionPattern.this.isRegexSolutionClassPattern() ?
                    matchClassRegexPattern(classFile) :
                    matchClassPattern(classFile);
        }

        private boolean matchClassRegexPattern(String classFile) {
            String file = File.separatorChar == '/' ? classFile : classFile.replace('/', File.separatorChar);
            return of(classPattern).matches(file, true);
        }

        private boolean matchClassPattern(String classFile) {
            String classPattern = ResolvedSolutionPattern.this.classPattern;
            if (separatorChar != '/') {
                classFile = classFile.replace('/', separatorChar);
                classPattern = classPattern.replace('/', separatorChar);
            }

            if (classPattern.endsWith(WILDCARD_FILENAME_POSTFIX) || classPattern.endsWith(CLASS_FILE_EXTENSION)) {
                return of(classPattern).matches(classFile, true);
            } else {
                String[] classPatterns = {classPattern + CLASS_FILE_EXTENSION, classPattern};
                return of(classPatterns).matches(classFile, true);
            }
        }
    }

    private final class MethodMatcher {

        private volatile Pattern cache;

        void sanityCheck() {
            if (ResolvedSolutionPattern.this.isRegexSolutionMethodPattern() && ResolvedSolutionPattern.this.hasClassSolutionPattern()) {
                try {
                    checkIllegalCharacters(ResolvedSolutionPattern.this.methodPattern);
                    fetchCache();
                } catch (IllegalArgumentException e) {
                    throwSanityError(e);
                }
            }
        }

        private void fetchCache() {
            if (cache == null) {
                int from = REGEX_HANDLER_PREFIX.length();
                int to = ResolvedSolutionPattern.this.methodPattern.length() - PATTERN_HANDLER_SUFFIX.length();
                String pattern = ResolvedSolutionPattern.this.methodPattern.substring(from, to);
                cache = compile(pattern);
            }
        }

        boolean matchMethodName(String methodName) {
            if (ResolvedSolutionPattern.this.isRegexSolutionMethodPattern()) {
                fetchCache();
                return cache.matcher(methodName).matches();
            } else {
                return matchPath(ResolvedSolutionPattern.this.methodPattern, methodName);
            }
        }
    }
}
