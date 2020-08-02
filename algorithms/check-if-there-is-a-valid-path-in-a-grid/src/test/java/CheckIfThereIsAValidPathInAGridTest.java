import org.excellent.cancer.algorithms.CheckIfThereIsAValidPathInAGrid;
import org.excellent.cancer.algorithms.support.ArgumentConverters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

public class CheckIfThereIsAValidPathInAGridTest {

    @Tag("清晰版")
    @ParameterizedTest
    @CsvFileSource(resources = "/test-case.csv")
    @DisplayName("深度优先搜索")
    public void bfsTest(
            @ConvertWith(ArgumentConverters.OfPrimitiveIntMatrix.class) int[][] grid,
            boolean expected
    ) {
        Assertions.assertEquals(expected, CheckIfThereIsAValidPathInAGrid.bfs().hasValidPath(grid));
    }

}