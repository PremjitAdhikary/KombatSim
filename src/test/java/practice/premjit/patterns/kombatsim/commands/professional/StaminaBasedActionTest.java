package practice.premjit.patterns.kombatsim.commands.professional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.Move.Recipient;
import practice.premjit.patterns.kombatsim.moves.damages.PhysicalDamage;

@ExtendWith(MockitoExtension.class)
class StaminaBasedActionTest {
	@Mock AbstractFighter mockFighter;
	StaminaBasedAction staminaAction;
	
	@BeforeEach
	void init() {
		KombatLogger.getLogger().disableLogging();
	}
	
	@Test
	@DisplayName("when fighter with no stamina")
	void testWhenFighterWithNoStamina() {
		when(mockFighter.getAttribute(AttributeType.STAMINA)).thenReturn(Optional.empty());
		staminaAction = new StaminaBasedAction(mockFighter, "test");
		assertFalse(staminaAction.canBeExecuted());
	}
	
	@TestInstance(Lifecycle.PER_CLASS)
	@Nested
	@DisplayName("when fighter with stamina")
	class WhenFighterWithStamina {
		@Captor ArgumentCaptor<Move> moveArgument;
		VariableAttribute stamina;

		@ParameterizedTest
		@MethodSource("provideArgumentsForTestCanBeExecuted")
		void testCanBeExecuted(int input, boolean expected) {
			stamina = AttributeUtility.buildStamina(100, input);
			when(mockFighter.getAttribute(AttributeType.STAMINA)).thenReturn(Optional.of(stamina));
			staminaAction = new StaminaBasedAction(mockFighter, "test");
			assertEquals(expected, staminaAction.canBeExecuted());
		}
		
		Stream<Arguments> provideArgumentsForTestCanBeExecuted() {
			return Stream.of(
					Arguments.of(35, false),
					Arguments.of(45, true)
				);
		}

		@ParameterizedTest
		@MethodSource("provideArgumentsForTestExecute")
		void testExecute(int input, double expectedDamage, double expectedStamina) {
			ArenaMediator mockArena = mock(ArenaMediator.class);
			stamina = AttributeUtility.buildStamina(100, input);
			when(mockFighter.getAttribute(AttributeType.STAMINA)).thenReturn(Optional.of(stamina));
			when(mockFighter.arena()).thenReturn(mockArena);
			
			staminaAction = new StaminaBasedAction(mockFighter, "test");
			staminaAction.execute();
			verify(mockArena).sendMove(moveArgument.capture(), eq(Recipient.OPPONENT), eq(mockFighter));
			assertTrue(moveArgument.getValue() instanceof PhysicalDamage);
			
			PhysicalDamage damage = (PhysicalDamage) moveArgument.getValue();
			assertTrue(damage.amount() >= expectedDamage);
			assertTrue(damage.amount() <= 10 + expectedDamage);
			assertEquals(expectedStamina, stamina.current());
		}
		
		Stream<Arguments> provideArgumentsForTestExecute() {
			return Stream.of(
					Arguments.of(45, 40, 5),
					Arguments.of(70, 60, 10),
					Arguments.of(95, 80, 15)
				);
		}
		
	}

}
