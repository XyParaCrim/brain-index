import org.excellent.cancer.algorithms.RegularExpressionMatching;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegularExpressionMatchingTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/test-case.csv")
    @DisplayName("有限状态机")
    public void testNFA(String match, String regex, boolean expected) {
        assertEquals(expected, RegularExpressionMatching.nfa()
                .isMatch(match, regex));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/test-case.csv")
    @DisplayName("清晰版有限状态机")
    public void testExplicitNFA(String match, String regex, boolean expected) {
        assertEquals(expected, RegularExpressionMatching.nfaOfExplicit()
                .isMatch(match, regex));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/test-case.csv")
    @DisplayName("清晰版动态规划")
    public void testDynamicProgramming(String match, String regex, boolean expected) {
        assertEquals(expected, RegularExpressionMatching.dynamicProgramming()
                .isMatch(match, regex));
    }
}
