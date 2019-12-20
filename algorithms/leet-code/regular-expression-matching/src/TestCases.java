import nfa.Solution;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final public class TestCases {
    static final List<Consumer<BiPredicate<String, String>>> TEST_CASES = Stream.of(
            createCase("aa", "a", false),
            createCase("ab", ".*", true),
            createCase("aab", "c*a*b", true),
            createCase("mississippi", "mis*is*p*.", false),
            createCase("absbebuuuiobe", "a.*be", true),
            createCase("aaa", "a*a", true),
            createCase("aaa","aaaa", false),
            createCase("aaa","ab*a*c*a", true)
    ).collect(Collectors.toList());


    public static Consumer<BiPredicate<String, String>> createCase(final String str, final String express, final boolean result) {
        return isMatch -> {
            assert result == isMatch.test(str, express);
        };
    }

    public static void main(String[] args) {
        Solution nfa = new Solution();
        TEST_CASES.forEach(test -> test.accept(nfa::isMatch));
    }
}
