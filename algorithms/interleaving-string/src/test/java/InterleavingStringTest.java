import org.excellent.cancer.algorithms.InterleavingString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class InterleavingStringTest {

    @DisplayName("测试动态规划")
    @ParameterizedTest
    @CsvFileSource(resources = "/test-case.csv")
    public void testSolution(String s1, String s2, String s3, boolean expected) {
        Assertions.assertEquals(expected, InterleavingString.solution(s1, s2, s3));
    }

}
