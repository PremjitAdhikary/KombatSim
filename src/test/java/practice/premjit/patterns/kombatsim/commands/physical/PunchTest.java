package practice.premjit.patterns.kombatsim.commands.physical;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.Move.Recipient;
import practice.premjit.patterns.kombatsim.moves.damages.PhysicalDamage;

@ExtendWith(MockitoExtension.class)
class PunchTest {
	@Mock ArenaMediator mockArena;
	@Mock AbstractFighter mockFighter;
	@InjectMocks Punch punch;
	
	@BeforeEach
	void init() {
		KombatLogger.getLogger().disableLogging();
	}
	
	@Test
	@DisplayName("when fighter with no strength")
	void testWhenFighterWithNoStrength() {
		when(mockFighter.getAttribute(AttributeType.STRENGTH)).thenReturn(Optional.empty());
		assertFalse(punch.canBeExecuted());
	}
	
	@Nested
	@DisplayName("when fighter with strength")
	class WhenFighterWithStrength {
		double strength = 40;
		@Captor ArgumentCaptor<Move> moveArgument;
		
		@BeforeEach
		void init() {
			when(mockFighter.getAttribute(AttributeType.STRENGTH))
				.thenReturn(Optional.of(AttributeUtility.buildStrength(strength)));
		}
		
		@Test
		void testCanBeExecuted() {
			assertTrue(punch.canBeExecuted());
		}

		@RepeatedTest(10)
		void testExecute() {
			when(mockFighter.arena()).thenReturn(mockArena);
			punch.execute();
			
			verify(mockArena).sendMove(moveArgument.capture(), eq(Recipient.OPPONENT), eq(mockFighter));
			assertTrue(moveArgument.getValue() instanceof PhysicalDamage);
			
			PhysicalDamage damage = (PhysicalDamage) moveArgument.getValue();
			assertTrue(damage.amount() >= Punch.MIN_DAMAGE + (strength*0.25));
			assertTrue(damage.amount() <= Punch.MAX_DAMAGE + (strength*0.25));
		}
		
	}

}
