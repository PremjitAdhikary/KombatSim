package practice.premjit.patterns.kombatsim.moves.buffs;

import static org.junit.jupiter.api.Assertions.*;
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
import practice.premjit.patterns.kombatsim.fighters.Mage;

@ExtendWith(MockitoExtension.class)
class AttributeStealTest {
    AttributeSteal attributeSteal;
    @Mock AbstractFighter mockFighter;
    @Mock Mage mage;

    @Captor ArgumentCaptor<VariableAttributeModifier> lifeModifierArg;
    VariableAttribute life;
    @Captor ArgumentCaptor<VariableAttributeModifier> manaModifierArg;
    VariableAttribute mana;
    
    @BeforeEach
    void init() {
        KombatLogger.getLogger().disableLogging();
        attributeSteal = new AttributeSteal(mage, 4, 10, AttributeType.LIFE, AttributeType.MANA);
    }
    
    @Test
    void testAttributeSteal() {
        assertEquals(AttributeSteal.TYPE, attributeSteal.buffType);
        assertEquals(mage, attributeSteal.caster);
        assertEquals(4, attributeSteal.duration);
        assertEquals(10, attributeSteal.percentagePerBeat);
        assertEquals(AttributeType.LIFE, attributeSteal.steal);
        assertEquals(AttributeType.MANA, attributeSteal.restore);
    }
    
    @Test
    void testAffect() throws 
            NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        life = AttributeUtility.buildLife(100);
        mana = AttributeUtility.buildMana(100, 90);
        when(mockFighter.getAttribute(AttributeType.LIFE)).thenReturn(Optional.of(life));
        when(mage.getAttribute(AttributeType.MANA)).thenReturn(Optional.of(mana));
        
        attributeSteal.affect(mockFighter);
        
        // verify steal component
        verify(mockFighter).registerObserver(lifeModifierArg.capture());
        VariableAttributeModifier vaml = lifeModifierArg.getValue();
        
        Field beatsField = vaml.getClass().getDeclaredField("beats");
        beatsField.setAccessible(true);
        assertEquals(4, beatsField.get(vaml));
        
        Field amountPerBeatField = vaml.getClass().getDeclaredField("amountPerBeat");
        amountPerBeatField.setAccessible(true);
        assertEquals(-10.0, amountPerBeatField.get(vaml));
        
        Field attributeField = vaml.getClass().getDeclaredField("attribute");
        attributeField.setAccessible(true);
        assertEquals(life, attributeField.get(vaml));
        
        Field fighterField = vaml.getClass().getDeclaredField("fighter");
        fighterField.setAccessible(true);
        assertEquals(mockFighter, fighterField.get(vaml));
        
        // verify restore component
        verify(mage).registerObserver(manaModifierArg.capture());
        VariableAttributeModifier vamm = manaModifierArg.getValue();
        
        beatsField = vamm.getClass().getDeclaredField("beats");
        beatsField.setAccessible(true);
        assertEquals(4, beatsField.get(vamm));
        
        amountPerBeatField = vamm.getClass().getDeclaredField("amountPerBeat");
        amountPerBeatField.setAccessible(true);
        assertEquals(10.0, amountPerBeatField.get(vamm));
        
        attributeField = vamm.getClass().getDeclaredField("attribute");
        attributeField.setAccessible(true);
        assertEquals(mana, attributeField.get(vamm));
        
        fighterField = vamm.getClass().getDeclaredField("fighter");
        fighterField.setAccessible(true);
        assertEquals(mage, fighterField.get(vamm));
    }
    
    @Test
    void testClone() throws Exception {
        AttributeSteal clone = (AttributeSteal) attributeSteal.clone();
        assertEquals(attributeSteal.buffType, clone.buffType);
        assertEquals(attributeSteal.caster, clone.caster);
        assertEquals(attributeSteal.duration, clone.duration);
        assertEquals(attributeSteal.percentagePerBeat, clone.percentagePerBeat);
        assertEquals(attributeSteal.restore, clone.restore);
        assertEquals(attributeSteal.steal, clone.steal);
        assertNotEquals(attributeSteal, clone);
    }

}
