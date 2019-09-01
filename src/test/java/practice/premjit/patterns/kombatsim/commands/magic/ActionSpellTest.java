package practice.premjit.patterns.kombatsim.commands.magic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.Mage;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.Move.Recipient;
import practice.premjit.patterns.kombatsim.strategies.magic.SpellBook;

@ExtendWith(MockitoExtension.class)
class ActionSpellTest {
	@Mock Mage mockMage;
	@Mock SpellBook mockBook;
	ActionSpell spell;
	
	@BeforeEach
	void init() {
		KombatLogger.getLogger().disableLogging();
		spell = ActionSpell.create( action -> 
					action.mage(mockMage).name("test").book(mockBook).move(null).cooldown(3) );
	}
	
	@Test
	@DisplayName("spell readiness")
	void testSpellReadiness() {
		assertFalse(spell.isReady()); // ActionSpell starts with a cooldown, so not ready at first
		spell.cooldown.reset();
		assertTrue(spell.isReady());
	}
	
	@Test
	@DisplayName("when spell not ready")
	void testWhenSpellNotReady() {
		assertFalse(spell.canBeExecuted());
	}
	
	@Test
	@DisplayName("when spell ready and mage doesnt have mana")
	void testWhenSpellReadyAndMageNoMana() {
		spell.cooldown.reset();
		assertTrue(spell.isReady());
		when(mockMage.currentMana()).thenReturn(0.0);
		assertFalse(spell.canBeExecuted());
	}
	
	@TestInstance(Lifecycle.PER_CLASS)
	@Nested
	@DisplayName("when mage with mana")
	class WhenMageWithMana {
		@Captor ArgumentCaptor<Move> moveArgument;
		VariableAttribute mana;

		@Test
		void testWhenSpellReady() {
			spell.cooldown.reset();
			assertTrue(spell.isReady());
			when(mockMage.currentMana()).thenReturn(10.0);
			assertTrue(spell.canBeExecuted());
		}

		@Test
		void testWhenSpellExecute() {
			spell.cooldown.reset();
			mana = AttributeUtility.buildMana(30, 25);
			when(mockMage.getAttribute(AttributeType.MANA)).thenReturn(Optional.of(mana));
			when(mockMage.currentMana()).thenReturn(mana.current());
			Supplier<Move> moveSupplier = mock(Supplier.class);
			spell.moveSupplier = moveSupplier;
			Move move = mock(Move.class);
			when(moveSupplier.get()).thenReturn(move);
			ArenaMediator mockArena = mock(ArenaMediator.class);
			when(mockMage.arena()).thenReturn(mockArena);
			
			spell.execute();
			verify(mockArena).sendMove(moveArgument.capture(), eq(Recipient.OPPONENT), eq(mockMage));
			assertEquals(move, moveArgument.getValue());
			assertEquals(24, mana.current());
		}
		
	}

}
