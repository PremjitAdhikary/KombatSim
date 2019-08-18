package practice.premjit.patterns.kombatsim.moves.buffs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.beats.VariableAttributeModifier;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.buffs.AffectAttribute.AffectType;

@ExtendWith(MockitoExtension.class)
class AffectVariableAttributeTest {
	AffectVariableAttribute affectVariableAttribute;
	
	@BeforeEach
	void init() {
		KombatLogger.getLogger().disableLogging();
	}
	
	@Nested
	@DisplayName("when initializing")
	class WhenInit {
		
		@Test
		void permanent() {
			affectVariableAttribute = AffectVariableAttribute.create(b -> b
					.affectAttribute(AttributeType.STAMINA)
					.permanent()
					.percentagePerBeat(10));
			assertEquals(AffectVariableAttribute.TYPE_PERMANENT, affectVariableAttribute.buffType);
			assertEquals(AttributeType.STAMINA, affectVariableAttribute.attributeType);
			assertEquals(AffectType.PERMANENT, affectVariableAttribute.affectType);
			assertEquals(10, affectVariableAttribute.percentagePerBeat);
		}
		
		@Test
		void temporary() {
			affectVariableAttribute = AffectVariableAttribute.create(b -> b
					.affectAttribute(AttributeType.STAMINA)
					.duration(4)
					.percentagePerBeat(10));
			assertEquals(AffectVariableAttribute.TYPE_TEMPORARY, affectVariableAttribute.buffType);
			assertEquals(AttributeType.STAMINA, affectVariableAttribute.attributeType);
			assertEquals(AffectType.TEMPORARY, affectVariableAttribute.affectType);
			assertEquals(4, affectVariableAttribute.duration);
			assertEquals(10, affectVariableAttribute.percentagePerBeat);
		}
		
		@Test
		void testClone() throws Exception {
			affectVariableAttribute = AffectVariableAttribute.create(b -> b
					.affectAttribute(AttributeType.STAMINA)
					.duration(4)
					.percentagePerBeat(10));
			AffectVariableAttribute clone = (AffectVariableAttribute) affectVariableAttribute.clone();
			assertEquals(affectVariableAttribute.buffType, clone.buffType);
			assertEquals(affectVariableAttribute.affectType, clone.affectType);
			assertEquals(affectVariableAttribute.attributeType, clone.attributeType);
			assertEquals(affectVariableAttribute.duration, clone.duration);
			assertEquals(affectVariableAttribute.percentagePerBeat, clone.percentagePerBeat);
			assertNotEquals(affectVariableAttribute, clone);
		}
		
	}
	
	@Nested
	@DisplayName("when affecting")
	class WhenAffect {
		@Mock AbstractFighter mockFighter;
		VariableAttribute mojo;
		@Captor ArgumentCaptor<VariableAttributeModifier> mojoModifierArg;
		
		@BeforeEach
		void init() {
			mojo = AttributeUtility.buildMojo(100, 80);
			when(mockFighter.getAttribute(AttributeType.MOJO)).thenReturn(Optional.of(mojo));
		}
		
		@Test
		void permanent() throws 
			NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
			affectVariableAttribute = AffectVariableAttribute.create(b -> b
					.affectAttribute(AttributeType.MOJO)
					.permanent()
					.percentagePerBeat(10));
			affectVariableAttribute.affect(mockFighter);
			verify(mockFighter).registerObserver(mojoModifierArg.capture());
			VariableAttributeModifier vam = mojoModifierArg.getValue();
			
			Field amountPerBeatField = vam.getClass().getDeclaredField("amountPerBeat");
			amountPerBeatField.setAccessible(true);
			assertEquals(10.0, amountPerBeatField.get(vam));
			
			Field attributeField = vam.getClass().getDeclaredField("attribute");
			attributeField.setAccessible(true);
			assertEquals(mojo, attributeField.get(vam));
			
			Field fighterField = vam.getClass().getDeclaredField("fighter");
			fighterField.setAccessible(true);
			assertEquals(mockFighter, fighterField.get(vam));
		}
		
		@Test
		void temporary() throws 
			NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
			affectVariableAttribute = AffectVariableAttribute.create(b -> b
					.affectAttribute(AttributeType.MOJO)
					.duration(4)
					.percentagePerBeat(10));
			affectVariableAttribute.affect(mockFighter);
			verify(mockFighter).registerObserver(mojoModifierArg.capture());
			VariableAttributeModifier vam = mojoModifierArg.getValue();
			
			Field beatsField = vam.getClass().getDeclaredField("beats");
			beatsField.setAccessible(true);
			assertEquals(4, beatsField.get(vam));
			
			Field amountPerBeatField = vam.getClass().getDeclaredField("amountPerBeat");
			amountPerBeatField.setAccessible(true);
			assertEquals(10.0, amountPerBeatField.get(vam));
			
			Field attributeField = vam.getClass().getDeclaredField("attribute");
			attributeField.setAccessible(true);
			assertEquals(mojo, attributeField.get(vam));
			
			Field fighterField = vam.getClass().getDeclaredField("fighter");
			fighterField.setAccessible(true);
			assertEquals(mockFighter, fighterField.get(vam));
		}
		
	}

}
