package org.excellent.cancer.algorithms.support;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class MatchPattern {

    private final String source;

    private final String regexPatternSource;

    private final Pattern regexPattern;

    private final String separator;

    private final String[] tokenized;

    private final boolean isRegexPattern;

    private final boolean isAntPattern;

    private MatchPattern(@NotNull String source, @NotNull String separator) {
        this.isRegexPattern = PatternUtils.isRegexPrefixedPattern(source);
        this.regexPatternSource = this.isRegexPattern ? PatternUtils.trimRegexPrefixedPattern(source) : null;
        this.regexPattern = this.regexPatternSource != null ? Pattern.compile(this.regexPatternSource) : null;
        this.isAntPattern = PatternUtils.isAntPrefixedPattern(source);
        this.source = this.isAntPattern ? PatternUtils.trimAntPrefixedPattern(source) : source;
        this.separator = separator;
        this.tokenized = PatternUtils.tokenizePathToString(this.source, this.separator);
    }

    public boolean matchPath(String path, boolean isCaseSensitive) {
        return isRegexPattern ?
                regexPattern.matcher(path).matches() :
                PatternUtils.matchAntPathPattern(this, path, separator, isCaseSensitive);
    }

    public boolean startWith(String prefix) {
        return source.startsWith(prefix);
    }

    public String[] getTokenizedPathString() {
        return tokenized;
    }
}
