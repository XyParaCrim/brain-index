import org.excellent.cancer.algorithms.MaximumSubarray;
import org.excellent.cancer.algorithms.support.ArgumentConverters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaximumSubarrayTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/test-case.csv")
    @DisplayName("动态规划")
    public void test(@ConvertWith(ArgumentConverters.OfPrimitiveIntArray.class) int[] nums,
                     int expected) {
        assertEquals(expected, MaximumSubarray.solution(nums));
    }


}
