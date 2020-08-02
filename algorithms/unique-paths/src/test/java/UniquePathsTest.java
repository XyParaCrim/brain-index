import org.excellent.cancer.algorithms.UniquePaths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class UniquePathsTest {


    @ParameterizedTest
    @CsvFileSource(resources = "/test-case.csv")
    @DisplayName("动态规划")
    public void testSolution(int m, int n, int expected) {
        Assertions.assertEquals(expected, UniquePaths.solution(m, n));
    }


}
