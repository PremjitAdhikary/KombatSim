package practice.premjit.patterns.kombatsim.moves.damages;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import practice.premjit.patterns.kombatsim.attributes.Attribute;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;

@ExtendWith(MockitoExtension.class)
class DarkDamageTest {
	DarkDamage damage;
	VariableAttribute life;
	Attribute strength;
	@Mock AbstractFighter mockFighter;
	
	@BeforeEach
	void init() {
		KombatLogger.getLogger().disableLogging();
		damage = new DarkDamage(15, 10);
		life = AttributeUtility.buildLife(100);
		strength = AttributeUtility.buildStrength(50);
	}
	
	@Test
	void testDarkDamage() {
		assertEquals(15, damage.amount());
		assertEquals(10, damage.deteriorationPercentage);
	}
	
	@Test
	void testAffectOfDamage() {
		when(mockFighter.getAttribute(AttributeType.LIFE)).thenReturn(Optional.of(life));
		
		assertEquals(100, life.current());
		damage.affect(mockFighter);
		assertEquals(85, life.current());
	}
	
	@Test
	void testAffectOfDark() {
		when(mockFighter.getAttribute(AttributeType.LIFE)).thenReturn(Optional.of(life));
		when(mockFighter.getPrimaryAttribute()).thenReturn(Optional.of(strength));
		
		assertEquals(50, strength.base());
		assertEquals(50, strength.net());
		
		damage.affect(mockFighter);
		assertEquals(50, strength.base());
		assertEquals(45, strength.net());
	}
	
	@Test
	void testClone() throws Exception {
		DarkDamage clone = (DarkDamage) damage.clone();
		assertEquals(damage.damageAmount, clone.damageAmount);
		assertEquals(damage.damageType, clone.damageType);
		assertEquals(damage.indirect, clone.indirect);
		assertEquals(damage.deteriorationPercentage, clone.deteriorationPercentage);
		assertNotEquals(damage, clone);
	}

}
