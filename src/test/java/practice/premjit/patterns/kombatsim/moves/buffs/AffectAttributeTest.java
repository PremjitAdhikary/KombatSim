package practice.premjit.patterns.kombatsim.moves.buffs;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import practice.premjit.patterns.kombatsim.attributes.Attribute;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.buffs.AffectAttribute.AffectType;

@ExtendWith(MockitoExtension.class)
class AffectAttributeTest {
	AffectAttribute affectAttribute;
	@Mock AbstractFighter mockFighter;
	Attribute strength;
	
	@BeforeEach
	void init() {
		KombatLogger.getLogger().disableLogging();
	}
	
	@Nested
	@DisplayName("when initializing")
	class WhenInit {
		
		@Test
		void justAffectAndAttributeType() {
			affectAttribute = AffectAttribute.create(b -> b
					.affectAttribute(AttributeType.STAMINA)
					.permanent());
			assertFalse(affectAttribute.affectPrimary);
			assertEquals(AffectType.PERMANENT, affectAttribute.affectType);
			assertEquals(AttributeType.STAMINA, affectAttribute.attributeType);
			assertNull(affectAttribute.addend);
			assertNull(affectAttribute.multiplicand);
			assertNull(affectAttribute.duration);
		}
		
	}
	
	@Nested
	@DisplayName("when permanent")
	class WhenPermanent {
		
		@BeforeEach
		void init() {
			strength = AttributeUtility.buildStrength(100);
			affectAttribute = AffectAttribute.create(b -> b
					.affectAttribute(AttributeType.STRENGTH)
					.permanent()
					.addend(10.0)
					.multiplicand(-0.5));
		}
		
		@Test
		void testAffectAttribute() {
			assertEquals(AffectAttribute.TYPE_PERMANENT, affectAttribute.buffType);
			assertFalse(affectAttribute.affectPrimary);
			assertEquals(AffectType.PERMANENT, affectAttribute.affectType);
			assertEquals(AttributeType.STRENGTH, affectAttribute.attributeType);
			assertEquals(10.0,  affectAttribute.addend);
			assertEquals(-0.5, affectAttribute.multiplicand);
			assertNull(affectAttribute.duration);
		}
		
		@Test
		void testAffectOf() {
			when(mockFighter.getAttribute(AttributeType.STRENGTH)).thenReturn(Optional.of(strength));
			assertEquals(100, strength.net());
			
			affectAttribute.affect(mockFighter); // calls set()
			assertEquals(60, strength.net());
			
			affectAttribute.reset();
			assertEquals(60, strength.net());
		}
		
	}
	
	@Nested
	@DisplayName("when temporary")
	class WhenTemporary {
		
		@BeforeEach
		void init() {
			strength = AttributeUtility.buildStrength(100);
			affectAttribute = AffectAttribute.create(b -> b
					.affectPrimary()
					.duration(4)
					.addend(10.0)
					.multiplicand(-0.5));
		}
		
		@Test
		void testAffectAttribute() {
			assertEquals(AffectAttribute.TYPE_TEMPORARY ,affectAttribute.buffType);
			assertTrue(affectAttribute.affectPrimary);
			assertEquals(AffectType.TEMPORARY, affectAttribute.affectType);
			assertEquals(10.0,  affectAttribute.addend);
			assertEquals(-0.5, affectAttribute.multiplicand);
			assertEquals(4, affectAttribute.duration);
		}
		
		@Test
		void testAffectOf() {
			when(mockFighter.getPrimaryAttribute()).thenReturn(Optional.of(strength));
			assertEquals(100, strength.net());
			
			affectAttribute.affect(mockFighter);
			assertEquals(60, strength.net());
			
			affectAttribute.reset();
			assertEquals(100, strength.net());
		}
		
		@Test
		void testClone() throws CloneNotSupportedException {
			AffectAttribute clone = (AffectAttribute) affectAttribute.clone();
			assertEquals(affectAttribute.buffType, clone.buffType);
			assertEquals(affectAttribute.affectPrimary, clone.affectPrimary);
			assertEquals(affectAttribute.affectType, clone.affectType);
			assertEquals(affectAttribute.addend, clone.addend);
			assertEquals(affectAttribute.multiplicand, clone.multiplicand);
			assertEquals(affectAttribute.duration, clone.duration);
			assertNotEquals(affectAttribute, clone);
			clone.addend -= 5;
			assertNotEquals(affectAttribute.addend, clone.addend);
		}
		
	}

}
