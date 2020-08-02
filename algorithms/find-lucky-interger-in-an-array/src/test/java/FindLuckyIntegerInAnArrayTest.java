import org.excellent.cancer.algorithms.FindLuckyIntegerInAnArray;
import org.excellent.cancer.algorithms.support.ArgumentConverters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindLuckyIntegerInAnArrayTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/test-case.csv")
    @DisplayName("先将数组直入HashMap，然后在筛选出最大的")
    public void solutionText(@ConvertWith(ArgumentConverters.OfPrimitiveIntArray.class) int[] arr, int expected) {
        assertEquals(expected, FindLuckyIntegerInAnArray.solution().findLucky(arr));
    }

}
