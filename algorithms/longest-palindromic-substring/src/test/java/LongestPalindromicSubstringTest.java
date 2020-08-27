import org.excellent.cancer.algorithms.LongestPalindromicSubstring;
import org.excellent.cancer.algorithms.support.ArgumentConverters;
import org.excellent.cancer.algorithms.support.ExcellentAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

public class LongestPalindromicSubstringTest {


    @DisplayName("测试动态规划")
    @ParameterizedTest
    @CsvFileSource(resources = "/test-case.csv")
    public void testSolution(String input, @ConvertWith(ArgumentConverters.OfStringArray.class) String[] expected) {
        ExcellentAssertions.assertMatchAny(expected, LongestPalindromicSubstring.solution(input));
    }

}
