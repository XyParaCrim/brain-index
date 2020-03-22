import org.excellent.cancer.algorithms.FourDivisors;
import org.excellent.cancer.algorithms.support.ArgumentConverters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FourDivisorsTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/test-case.csv")
    @DisplayName("夹bi法")
    public void solutionTest(@ConvertWith(ArgumentConverters.OfPrimitiveIntArray.class) int[] nums, int expected) {
        assertEquals(expected, FourDivisors.solution().sumFourDivisors(nums));
    }

}
