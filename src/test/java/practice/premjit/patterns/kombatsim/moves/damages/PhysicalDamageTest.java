package practice.premjit.patterns.kombatsim.moves.damages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

@ExtendWith(MockitoExtension.class)
class PhysicalDamageTest {
    PhysicalDamage damage;
    @Mock AbstractFighter mockFighter;
    
    @BeforeEach
    void init() {
        KombatLogger.getLogger().disableLogging();
        damage = PhysicalDamage.create(d -> d.min(10).max(10));
    }

    @Test
    void testDamageAmount() {
        assertEquals(10, damage.amount());
    }

    @Test
    void testDamagePositiveIncrement() {
        assertEquals(10, damage.amount());
        damage.incrementBy(5);
        assertEquals(15, damage.amount());
    }

    @Test
    void testDamageNegativeIncrement() {
        assertEquals(10, damage.amount());
        damage.incrementBy(-15);
        assertEquals(0, damage.amount());
    }
    
    @Test
    void testIndirect() {
        assertFalse(damage.isIndirect());
        damage.setIndirect();
        assertTrue(damage.isIndirect());
        damage.clearIndirect();
        assertFalse(damage.isIndirect());
    }
    
    @Test
    void testAffect() {
        VariableAttribute life = AttributeUtility.buildLife(30);
        when(mockFighter.getAttribute(AttributeType.LIFE))
            .thenReturn(Optional.of(life));
        
        assertEquals(30, life.current());
        damage.affect(mockFighter);
        assertEquals(20, life.current());
    }
    
    @Test
    void testClone() throws Exception {
        PhysicalDamage clone = (PhysicalDamage) damage.clone();
        assertEquals(damage.damageAmount, clone.damageAmount);
        assertEquals(damage.damageType, clone.damageType);
        assertEquals(damage.indirect, clone.indirect);
        assertNotEquals(damage, clone);
    }

}
