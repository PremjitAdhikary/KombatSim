package practice.premjit.patterns.kombatsim.beats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;

@ExtendWith(MockitoExtension.class)
class DexterityBasedReactionObserverTest {
	AbstractFighter mockFighter;
	DexterityBasedReactionObserver dbro;
	
	@BeforeEach
	void init() {
		KombatLogger.getLogger().disableLogging();
		mockFighter = mock(AbstractFighter.class);
		dbro = new DexterityBasedReactionObserver();
		dbro.setFighter(mockFighter);
	}

	@ParameterizedTest
	@MethodSource("provideArgumentsForTestCountFromDexterity")
	void testCountFromDexterity(int input, int expected) {
		when(mockFighter.getAttribute(AttributeType.DEXTERITY))
			.thenReturn(Optional.of(AttributeUtility.buildDexterity(input)));
		assertEquals(expected, dbro.calculateBeatCount());
	}
	
	static Stream<Arguments> provideArgumentsForTestCountFromDexterity() {
		return Stream.of(
				Arguments.of(0, 1000),
				Arguments.of(1, 100),
				Arguments.of(9, 100),
				Arguments.of(10,100),
				Arguments.of(11,100),
				Arguments.of(40, 25),
				Arguments.of(75, 14),
				Arguments.of(85, 12),
				Arguments.of(150, 8),
				Arguments.of(250, 6),
				Arguments.of(450, 4),
				Arguments.of(550, 4),
				Arguments.of(600, 3),
				Arguments.of(1000, 1),
				Arguments.of(1001, 1)
			);
	}

	@Test
	void testForReact() {
		when(mockFighter.getAttribute(AttributeType.DEXTERITY))
			.thenReturn(Optional.of(AttributeUtility.buildDexterity(85)));
		dbro.update();
		assertTrue(dbro.reactEnabled());
		
		dbro.reactSuccessful();
		dbro.reactSuccessful();
		
		for (int i=1; i<dbro.calculateBeatCount(); i++) {
			assertFalse(dbro.reactEnabled());
			dbro.update();
		}
		assertTrue(dbro.reactEnabled());
	}

}
