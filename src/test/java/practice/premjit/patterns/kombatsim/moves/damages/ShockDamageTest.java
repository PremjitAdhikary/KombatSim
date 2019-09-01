package practice.premjit.patterns.kombatsim.moves.damages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.strategies.ActionStrategy;
import practice.premjit.patterns.kombatsim.strategies.ReactionStrategy;

@ExtendWith(MockitoExtension.class)
class ShockDamageTest {
	ShockDamage damage;
	VariableAttribute life;
	@Mock AbstractFighter mockFighter;
	@Mock ActionStrategy actionStretegy;
	@Mock ReactionStrategy reactionStretegy;
	
	@BeforeEach
	void init() {
		KombatLogger.getLogger().disableLogging();
		damage = ShockDamage.create(d -> d.max(10).duration(5));
		life = AttributeUtility.buildLife(100);
	}
	
	@Test
	void testShockDamage() {
		double d = damage.amount();
		assertTrue(d <= 10);
		assertEquals(5, damage.shockDuration());
		damage.incrementShockDurationBy(4);
		assertEquals(9, damage.shockDuration());
	}
	
	@Test
	void testAffectOfDamage() {
		when(mockFighter.getAttribute(AttributeType.LIFE)).thenReturn(Optional.of(life));
		
		assertEquals(100, life.current());
		damage.affect(mockFighter);
		assertTrue(90 <= life.current());
	}
	
	@Test
	void testAffectOfShock() {
		when(mockFighter.getAttribute(AttributeType.LIFE)).thenReturn(Optional.of(life));
		doCallRealMethod().when(mockFighter).getActionStrategy();
		doCallRealMethod().when(mockFighter).setActionStrategy(any(ActionStrategy.class));
		doCallRealMethod().when(mockFighter).getReactionStrategy();
		doCallRealMethod().when(mockFighter).setReactionStrategy(any(ReactionStrategy.class));
		
		mockFighter.setActionStrategy(actionStretegy);
		mockFighter.setReactionStrategy(reactionStretegy);
		
		verify(actionStretegy, never()).perform();
		verify(reactionStretegy, never()).perform(any(Optional.class));
		
		mockFighter.getActionStrategy().perform();
		assertEquals(actionStretegy, mockFighter.getActionStrategy());
		verify(actionStretegy, times(1)).perform();
		
		mockFighter.getReactionStrategy().perform(Optional.empty());
		assertEquals(reactionStretegy, mockFighter.getReactionStrategy());
		verify(reactionStretegy, times(1)).perform(any(Optional.class));
		
		damage.affect(mockFighter);
		
		mockFighter.getActionStrategy().perform();
		assertNotEquals(actionStretegy, mockFighter.getActionStrategy());
		verify(actionStretegy, times(1)).perform();
		
		mockFighter.getReactionStrategy().perform(Optional.empty());
		assertNotEquals(reactionStretegy, mockFighter.getReactionStrategy());
		verify(reactionStretegy, times(1)).perform(any(Optional.class));
		
		damage.reset();
		mockFighter.getActionStrategy().perform();
		assertNotEquals(actionStretegy, mockFighter.getActionStrategy());
		verify(actionStretegy, times(2)).perform();
		
		mockFighter.getReactionStrategy().perform(Optional.empty());
		assertNotEquals(reactionStretegy, mockFighter.getReactionStrategy());
		verify(reactionStretegy, times(2)).perform(any(Optional.class));
	}
	
	@Test
	void testClone() throws Exception {
		ShockDamage clone = (ShockDamage) damage.clone();
		assertEquals(damage.damageAmount, clone.damageAmount);
		assertEquals(damage.damageType, clone.damageType);
		assertEquals(damage.indirect, clone.indirect);
		assertEquals(damage.shockDuration, clone.shockDuration);
		assertNotEquals(damage, clone);
		assertNull(clone.fighter);
		assertNull(clone.timer);
	}

}
