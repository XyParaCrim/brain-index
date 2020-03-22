import org.excellent.cancer.algorithms.LongestHappyPrefix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class LongestHappyPrefixTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/test-case.csv")
    @DisplayName("哈希映射")
    public void hashTest(String source, String prefix) {
        Assertions.assertEquals(prefix, LongestHappyPrefix.hashing().longestPrefix(source));
    }

}
