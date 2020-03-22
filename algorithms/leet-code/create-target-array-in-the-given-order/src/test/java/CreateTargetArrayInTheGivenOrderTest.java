import org.excellent.cancer.algorithms.CreateTargetArrayInTheGivenOrder;
import org.excellent.cancer.algorithms.support.ArgumentConverters.OfPrimitiveIntArray;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class CreateTargetArrayInTheGivenOrderTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/test-case.csv")
    @DisplayName("Java链表实现")
    public void testSolution(
            @ConvertWith(OfPrimitiveIntArray.class) int[] nums,
            @ConvertWith(OfPrimitiveIntArray.class) int[] index,
            @ConvertWith(OfPrimitiveIntArray.class) int[] expected) {
        assertArrayEquals(expected, CreateTargetArrayInTheGivenOrder.solution().createTargetArray(nums, index));
    }

}
