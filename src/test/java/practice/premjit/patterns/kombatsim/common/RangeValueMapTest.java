package practice.premjit.patterns.kombatsim.common;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RangeValueMapTest {
    RangeValueMap valueMap;
    
    @BeforeEach
    void init() {
        valueMap = RangeValueMap.builder()
                    .addRangeValue(0.0, 0.0)
                    .addRangeValue(10.0,1.0)
                    .addRangeValue(20.0,2.0)
                    .addRangeValue(30.0,3.0)
                    .addRangeValue(40.0,4.0)
                    .addRangeValue(50.0,5.0)
                    .addRangeValue(60.0,6.0)
                    .addRangeValue(70.0,7.0)
                    .addRangeValue(80.0,8.0)
                    .addRangeValue(90.0,9.0)
                    .build();
    }
    
    @Test
    void testBeyondRange() {
        assertFalse(valueMap.hasValue(-1.0));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> valueMap.getValue(-1.0));
        assertEquals("Key not in Range.", exception.getMessage());
    }
    
    @ParameterizedTest
    @MethodSource("provideArgumentsForTestRangeValueMap")
    void testRangeValueMap(double input, double expected) {
        assertTrue(valueMap.hasValue(input));
        assertEquals(expected, valueMap.getValue(input));
    }
    
    static Stream<Arguments> provideArgumentsForTestRangeValueMap() {
        return Stream.of(
                Arguments.of(0.0, 0.0),
                Arguments.of(9.0, 0.0),
                Arguments.of(10.0, 1.0),
                Arguments.of(15.0, 1.0),
                Arguments.of(19.0, 1.0),
                Arguments.of(19.99, 1.0),
                Arguments.of(20.0, 2.0),
                Arguments.of(20.5, 2.0),
                Arguments.of(80.9, 8.0),
                Arguments.of(90.0, 9.0),
                Arguments.of(1000.0, 9.0)
                );
    }

}
