package practice.premjit.patterns.kombatsim.commands.hero;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.function.Supplier;
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
import org.mockito.InjectMocks;
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

@ExtendWith(MockitoExtension.class)
class MojoBasedActionTest {
	@Mock AbstractFighter mockFighter;
	@InjectMocks MojoBasedAction mojoAction;
	
	@BeforeEach
	void init() {
		KombatLogger.getLogger().disableLogging();
	}
	
	@Test
	@DisplayName("when fighter with no mojo")
	void testWhenFighterWithNoMojo() {
		when(mockFighter.getAttribute(AttributeType.MOJO)).thenReturn(Optional.empty());
		assertFalse(mojoAction.canBeExecuted());
	}
	
	@TestInstance(Lifecycle.PER_CLASS)
	@Nested
	@DisplayName("when fighter with mojo")
	class WhenFighterWithMojo {
		@Captor ArgumentCaptor<Move> moveArgument;
		VariableAttribute mojo;

		@ParameterizedTest
		@MethodSource("provideArgumentsForTestCanBeExecuted")
		void testCanBeExecuted(int input, boolean expected) {
			mojo = AttributeUtility.buildMojo(100, input);
			when(mockFighter.getAttribute(AttributeType.MOJO)).thenReturn(Optional.of(mojo));
			assertEquals(expected, mojoAction.canBeExecuted());
		}
		
		Stream<Arguments> provideArgumentsForTestCanBeExecuted() {
			return Stream.of(
					Arguments.of(35, false),
					Arguments.of(85, true)
				);
		}

		@ParameterizedTest
		@MethodSource("provideArgumentsForTestSetMojoCost")
		void testSetMojoCost(int inputCost, boolean expected) {
			mojo = AttributeUtility.buildMojo(100, 50);
			when(mockFighter.getAttribute(AttributeType.MOJO)).thenReturn(Optional.of(mojo));
			mojoAction.mojoCost = inputCost;
			assertEquals(expected, mojoAction.canBeExecuted());
		}
		
		Stream<Arguments> provideArgumentsForTestSetMojoCost() {
			return Stream.of(
					Arguments.of(85, false),
					Arguments.of(35, true)
				);
		}

		@ParameterizedTest
		@MethodSource("provideArgumentsForTestExecute")
		void testExecute(double mojoCost, Recipient recipient) {
			mojo = AttributeUtility.buildMojo(100, 90);
			ArenaMediator mockArena = mock(ArenaMediator.class);
			Supplier<Move> moveSupplier = mock(Supplier.class);
			mojoAction.moveSupplier = moveSupplier;
			Move move = mock(Move.class);
			when(moveSupplier.get()).thenReturn(move);
			when(mockFighter.getAttribute(AttributeType.MOJO)).thenReturn(Optional.of(mojo));
			when(mockFighter.arena()).thenReturn(mockArena);
			
			mojoAction.mojoCost = mojoCost;
			mojoAction.recipient = recipient;
			mojoAction.execute();
			verify(mockArena).sendMove(moveArgument.capture(), eq(recipient), eq(mockFighter));
			assertEquals(move, moveArgument.getValue());
			assertEquals(90-mojoCost, mojo.current());
		}
		
		Stream<Arguments> provideArgumentsForTestExecute() {
			return Stream.of(
					Arguments.of(40, Recipient.SELF),
					Arguments.of(60, Recipient.ALL),
					Arguments.of(50, Recipient.OPPONENT)
				);
		}
	}

}
