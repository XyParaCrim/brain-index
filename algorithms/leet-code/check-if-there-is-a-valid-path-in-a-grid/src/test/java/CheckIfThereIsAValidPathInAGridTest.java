import org.excellent.cancer.algorithms.CheckIfThereIsAValidPathInAGrid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class CheckIfThereIsAValidPathInAGridTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/test-case.csv")
    @DisplayName("深度优先搜索")
    @Disabled
    public void bfsTest(int[][] grid, boolean expected) {
        Assertions.assertEquals(expected, CheckIfThereIsAValidPathInAGrid.bfs().hasValidPath(grid));
    }


}
