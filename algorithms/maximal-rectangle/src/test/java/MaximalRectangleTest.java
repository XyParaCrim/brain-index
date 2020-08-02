import org.excellent.cancer.algorithms.MaximalRectangle;
import org.excellent.cancer.algorithms.support.ArgumentConverters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

public class MaximalRectangleTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/test-case.csv")
    @DisplayName("动态规划")
    public void testSolution(@ConvertWith(ArgumentConverters.OfCharMaxtrix.class) char[][] matrix, int expected) {
        Assertions.assertEquals(expected, MaximalRectangle.solution(matrix));
    }

}
