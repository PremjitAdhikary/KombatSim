package practice.premjit.patterns.kombatsim.beats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;

@ExtendWith(MockitoExtension.class)
class VariableAttributeModifierTest {
	VariableAttributeModifier vam;
	VariableAttribute va;
	
	@BeforeEach
	public void init() {
		KombatLogger.getLogger().disableLogging();
	}
	
	@Nested
	@DisplayName("when positive values are added")
	class WhenPositive {
		
		@BeforeEach
		public void init() {
			va = AttributeUtility.buildStamina(100, 100);
			vam = new VariableAttributeModifier(va, 10);
		}

		@Test
		void testAfterUpdateWithFullStamina() {
			assertEquals(100, va.current(), 0.1);
			vam.update();
			assertEquals(100, va.current(), 0.1);
		}

		@Test
		void testAfterUpdateWithHalfStamina() {
			va.incrementCurrent(-50);
			assertEquals(50, va.current(), 0.1);
			vam.update();
			vam.update();
			assertEquals(70, va.current(), 0.1);
		}
	}
	
	@Nested
	@DisplayName("when negative values are added")
	class WhenNegative {
		
		@BeforeEach
		public void init() {
			va = AttributeUtility.buildStamina(100, 100);
			vam = new VariableAttributeModifier(va, -10);
		}

		@Test
		void testAfterUpdateWithFullStamina() {
			assertEquals(100, va.current(), 0.1);
			vam.update();
			assertEquals(90, va.current(), 0.1);
		}

		@Test
		void testAfterUpdateWithHalfStamina() {
			va.incrementCurrent(-50);
			assertEquals(50, va.current(), 0.1);
			vam.update();
			vam.update();
			assertEquals(30, va.current(), 0.1);
		}

		@Test
		void testAfterUpdateWithNoStamina() {
			va.incrementCurrent(-100);
			assertEquals(0, va.current(), 0.1);
			vam.update();
			assertEquals(0, va.current(), 0.1);
		}
	}
	
	@TestInstance(Lifecycle.PER_CLASS)
	@Nested
	@DisplayName("when temporary modification")
	class WhenTemporaryModification {
		@Mock AbstractFighter mockFighter;
		
		@BeforeEach
		public void init() {
			va = AttributeUtility.buildLife(50);
			when(mockFighter.getAttribute(AttributeType.LIFE)).thenReturn(Optional.of(va));
			vam = new VariableAttributeModifier(mockFighter, AttributeType.LIFE, -5, 3);
		}

		@ParameterizedTest
		@MethodSource("provideArgumentsForTestAfterNBeats")
		void testAfterNBeats(int beats, double life, int times) {
			for (int i=0; i<beats; i++)
				vam.update();
			verify(mockFighter, times(times)).unregisterObserver(any(BeatObserver.class));
			assertEquals(life, va.current(), 0.1);
		}
		
		Stream<Arguments> provideArgumentsForTestAfterNBeats() {
			return Stream.of(
					Arguments.of(0, 50, 0),
					Arguments.of(1, 45, 0),
					Arguments.of(2, 40, 0),
					Arguments.of(3, 35, 1),
					Arguments.of(5, 35, 1)
				);
		}
	}

}
