package practice.premjit.patterns.kombatsim.commands.physical;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.damages.PhysicalDamage;

@ExtendWith(MockitoExtension.class)
class EvadeTest {
	@Mock ArenaMediator mockArena;
	@Mock AbstractFighter mockFighter;
	@InjectMocks Evade evade;
	PhysicalDamage damage;
	
	@BeforeEach
	void init() {
		KombatLogger.getLogger().disableLogging();
		damage = PhysicalDamage.create(d -> d.min(20).max(20));
	}
	
	@Test
	@DisplayName("when no damage")
	void testWhenNoDamage() {
		assertFalse(evade.canBeExecuted(Optional.empty()));
	}
	
	@Test
	@DisplayName("when fighter with no dexterity")
	void testWhenFighterWithNoDexterity() {
		when(mockFighter.getAttribute(AttributeType.DEXTERITY)).thenReturn(Optional.empty());
		assertFalse(evade.canBeExecuted(Optional.of(damage)));
	}
	
	@Nested
	@DisplayName("when fighter with very less dexterity")
	class WhenFighterWithLessDexterity {
		double dexterity = 0;
		
		@BeforeEach
		void init() {
			lenient().when(mockFighter.getAttribute(AttributeType.DEXTERITY))
				.thenReturn(Optional.of(AttributeUtility.buildStrength(dexterity)));
		}
		
		@Test
		void testCanBeExecuted() {
			assertTrue(evade.canBeExecuted(Optional.of(damage)));
		}

		@Test
		void testReduceDamage() {
			evade.reduceDamage(damage);
			assertEquals(20, damage.amount());
		}

		@Test
		void testExecute() {
			VariableAttribute life = AttributeUtility.buildLife(30);
			assertEquals(30, life.base());
			assertEquals(30, life.current());
			
			lenient().when(mockFighter.getAttribute(AttributeType.LIFE))
				.thenReturn(Optional.of(life));
			
			evade.execute(Optional.of(damage));
			assertEquals(30, life.base());
			assertEquals(10, life.current());
		}
		
	}
	
	@Nested
	@DisplayName("when fighter with very high dexterity")
	class WhenFighterWithHighDexterity {
		double dexterity = 2000;
		
		@BeforeEach
		void init() {
			lenient().when(mockFighter.getAttribute(AttributeType.DEXTERITY))
				.thenReturn(Optional.of(AttributeUtility.buildStrength(dexterity)));
		}
		
		@Test
		void testCanBeExecuted() {
			assertTrue(evade.canBeExecuted(Optional.of(damage)));
		}

		@Test
		void testReduceDamage() {
			evade.reduceDamage(damage);
			assertEquals(0, damage.amount());
		}

		@Test
		void testExecute() {
			VariableAttribute life = AttributeUtility.buildLife(30);
			assertEquals(30, life.base());
			assertEquals(30, life.current());
			
			lenient().when(mockFighter.getAttribute(AttributeType.LIFE))
				.thenReturn(Optional.of(life));
			
			evade.execute(Optional.of(damage));
			assertEquals(30, life.base());
			assertEquals(30, life.current());
		}
		
	}

}
