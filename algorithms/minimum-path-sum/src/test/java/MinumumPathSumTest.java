import org.excellent.cancer.algorithms.MinumumPathSum;
import org.excellent.cancer.algorithms.support.ArgumentConverters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

public class MinumumPathSumTest {


    @ParameterizedTest
    @DisplayName("动态规划")
    @CsvFileSource(resources = "/test-case.csv")
    public void testSolution(@ConvertWith(ArgumentConverters.OfPrimitiveIntMatrix.class) int[][] grid, int expected) {
        Assertions.assertEquals(expected, MinumumPathSum.solution(grid));
    }

}
