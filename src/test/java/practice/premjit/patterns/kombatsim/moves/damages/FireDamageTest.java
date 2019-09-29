package practice.premjit.patterns.kombatsim.moves.damages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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

@ExtendWith(MockitoExtension.class)
class FireDamageTest {
    @Mock AbstractFighter mockFighter;
    FireDamage damage;
    @Captor ArgumentCaptor<VariableAttributeModifier> lifeModifierArg;
    VariableAttribute life;
    
    @BeforeEach
    void init() {
        KombatLogger.getLogger().disableLogging();
        damage = FireDamage.create(d -> d.min(10).max(10).duration(2).damage(8));
        life = AttributeUtility.buildLife(100);
    }
    
    @Test
    void testFireDamage() {
        assertEquals(10, damage.amount());
        assertEquals(8, damage.burnAmount());
        damage.incrementBurnAmountBy(-4);
        assertEquals(4, damage.burnAmount());
    }
    
    @Test
    void testAffectOfDamage() {
        when(mockFighter.getAttribute(AttributeType.LIFE)).thenReturn(Optional.of(life));
        
        assertEquals(100, life.current());
        damage.affect(mockFighter);
        assertEquals(90, life.current());
    }
    
    @Test
    void testAffectOfBurn() throws 
        NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        
        when(mockFighter.getAttribute(AttributeType.LIFE)).thenReturn(Optional.of(life));
        
        damage.affect(mockFighter);
        
        verify(mockFighter).registerObserver(lifeModifierArg.capture());
        VariableAttributeModifier vam = lifeModifierArg.getValue();
        
        Field beatsField = vam.getClass().getDeclaredField("beats");
        beatsField.setAccessible(true);
        assertEquals(2, beatsField.get(vam));
        
        Field amountPerBeatField = vam.getClass().getDeclaredField("amountPerBeat");
        amountPerBeatField.setAccessible(true);
        assertEquals(-4.0, amountPerBeatField.get(vam));
        
        Field attributeField = vam.getClass().getDeclaredField("attribute");
        attributeField.setAccessible(true);
        assertEquals(life, attributeField.get(vam));
        
        Field fighterField = vam.getClass().getDeclaredField("fighter");
        fighterField.setAccessible(true);
        assertEquals(mockFighter, fighterField.get(vam));
    }
    
    @Test
    void testClone() throws Exception {
        FireDamage clone = (FireDamage) damage.clone();
        assertEquals(damage.damageAmount, clone.damageAmount);
        assertEquals(damage.damageType, clone.damageType);
        assertEquals(damage.indirect, clone.indirect);
        assertEquals(damage.burnDamage, clone.burnDamage);
        assertEquals(damage.burnDuration, clone.burnDuration);
        assertNotEquals(damage, clone);
    }

}
