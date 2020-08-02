import org.excellent.cancer.algorithms.CountNumberOfTeams;
import org.excellent.cancer.algorithms.support.ArgumentConverters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountNumberOfTermsTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/test-case.csv")
    @DisplayName("计算两侧数的大小值")
    public void solutionTest(@ConvertWith(ArgumentConverters.OfPrimitiveIntArray.class) int[] rating, int expected) {
        assertEquals(expected, CountNumberOfTeams.solution().numTeams(rating));
    }
}
