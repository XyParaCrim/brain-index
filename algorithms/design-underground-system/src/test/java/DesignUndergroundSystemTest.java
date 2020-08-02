import org.excellent.cancer.algorithms.DesignUndergroundSystem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DesignUndergroundSystemTest {

    @Test
    @DisplayName("Case1: 两个HashMap：一个以游客id为键，一个以往返两地")
    public void testSolutionForCaseOne() {
        DesignUndergroundSystem undergroundSystem = DesignUndergroundSystem.solution();

        undergroundSystem.checkIn(45, "Leyton", 3);
        undergroundSystem.checkIn(32, "Paradise", 8);
        undergroundSystem.checkIn(27, "Leyton", 10);
        undergroundSystem.checkOut(45, "Waterloo", 15);
        undergroundSystem.checkOut(27, "Waterloo", 20);
        undergroundSystem.checkOut(32, "Cambridge", 22);

        assertEquals(14.0, undergroundSystem.getAverageTime("Paradise", "Cambridge"));
        assertEquals(11.0, undergroundSystem.getAverageTime("Leyton", "Waterloo"));
        undergroundSystem.checkIn(10, "Leyton", 24);

        assertEquals(11.0, undergroundSystem.getAverageTime("Leyton", "Waterloo"));

        undergroundSystem.checkOut(10, "Waterloo", 38);

        assertEquals(12.0, undergroundSystem.getAverageTime("Leyton", "Waterloo"));

    }

    @Test
    @DisplayName("Case2: 两个HashMap：一个以游客id为键，一个以往返两地")
    public void testSolutionForCaseTwo() {
        DesignUndergroundSystem undergroundSystem = DesignUndergroundSystem.solution();

        undergroundSystem.checkIn(10, "Leyton", 3);
        undergroundSystem.checkOut(10, "Paradise", 8);

        assertEquals(5.0, undergroundSystem.getAverageTime("Leyton", "Paradise"));
        undergroundSystem.checkIn(5, "Leyton", 10);
        undergroundSystem.checkOut(5, "Paradise", 16);
        assertEquals(5.5, undergroundSystem.getAverageTime("Leyton", "Paradise"));
        undergroundSystem.checkIn(2, "Leyton", 21);
        undergroundSystem.checkOut(2, "Paradise", 30);
        assertEquals(6.666666666666667, undergroundSystem.getAverageTime("Leyton", "Paradise"));

    }



}
