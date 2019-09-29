package practice.premjit.patterns.kombatsim.moves.damages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

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
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.Mage;
import practice.premjit.patterns.kombatsim.fighters.Professional;
import practice.premjit.patterns.kombatsim.visitors.FighterVisitor;

@ExtendWith(MockitoExtension.class)
class ColdDamageTest {
    ColdDamage damage;
    VariableAttribute life;
    
    @BeforeEach
    void init() {
        KombatLogger.getLogger().disableLogging();
        damage = ColdDamage.create(d -> d.min(10).duration(5).deterioration(8));
    }
    
    @Test
    void testColdDamage() {
        double d = damage.amount();
        assertTrue(d >= 10 && d <= 15);
        assertEquals(5, damage.coldDuration);
        assertEquals(8, damage.deteriorationPercentage());
        damage.incrementDeteriorationPercentageBy(4);
        assertEquals(12, damage.deteriorationPercentage());
    }
    
    @Test
    void testClone() throws Exception {
        ColdDamage clone = (ColdDamage) damage.clone();
        assertEquals(damage.damageAmount, clone.damageAmount);
        assertEquals(damage.damageType, clone.damageType);
        assertEquals(damage.indirect, clone.indirect);
        assertEquals(damage.deteriorationPercentage, clone.deteriorationPercentage);
        assertEquals(damage.coldDuration, clone.coldDuration);
        assertNotEquals(damage, clone);
        assertNull(clone.fighter);
        assertNull(clone.timer);
    }
    
    @Nested
    @DisplayName("when fighter is a professional")
    class WhenProfessional {
        @Mock Professional pro;
        Attribute dexterity;
        
        @BeforeEach
        void init() {
            life = AttributeUtility.buildLife(100);
            dexterity = AttributeUtility.buildDexterity(50);
            lenient().when(pro.getAttribute(AttributeType.LIFE)).thenReturn(Optional.of(life));
            lenient().when(pro.getAttribute(AttributeType.DEXTERITY)).thenReturn(Optional.of(dexterity));
        }
        
        @Test
        void testAffectOnLife() {
            assertEquals(100, life.current());
            damage.affect(pro);
            assertTrue(life.current() <= 90);
        }
        
        @Test
        void testAffectOnDexterity() {
            doCallRealMethod().when(pro).accept(any(FighterVisitor.class));
            
            assertEquals(50, dexterity.net());
            damage.affect(pro); // calls set()
            assertEquals(46, dexterity.net());
            
            damage.reset(); // simulating duration over
            assertEquals(50, dexterity.net());
        }
        
        @Test
        void testClone() throws Exception {
            ColdDamage clone = (ColdDamage) damage.clone();
            Professional pro2 = mock(Professional.class);
            lenient().when(pro2.getAttribute(AttributeType.LIFE)).thenReturn(Optional.of(life));
            lenient().when(pro2.getAttribute(AttributeType.DEXTERITY)).thenReturn(Optional.of(dexterity));
            damage.affect(pro);
            clone.affect(pro2);
            assertNotEquals(damage.fighter, clone.fighter);
            assertNotEquals(damage.timer, clone.timer);
        }
        
    }
    
    @Nested
    @DisplayName("when fighter is a mage")
    class WhenMage {
        @Mock Mage mage;
        VariableAttribute mana;
        
        @BeforeEach
        void init() {
            life = AttributeUtility.buildLife(100);
            mana = AttributeUtility.buildMana(50, 40);
            lenient().when(mage.getAttribute(AttributeType.LIFE)).thenReturn(Optional.of(life));
            lenient().when(mage.getAttribute(AttributeType.MANA)).thenReturn(Optional.of(mana));
        }
        
        @Test
        void testAffectOnLife() {
            assertEquals(100, life.current());
            damage.affect(mage);
            assertTrue(life.current() <= 90);
        }
        
        @Test
        void testAffectOnMana() {
            doCallRealMethod().when(mage).accept(any(FighterVisitor.class));
            
            assertEquals(50, mana.net());
            assertEquals(40, mana.current());
            damage.affect(mage); // calls set()
            assertEquals(45, mana.net());
            assertEquals(35, mana.current());
            
            damage.reset(); // simulating duration over
            assertEquals(50, mana.net());
            assertEquals(40, mana.current());
        }
        
    }

}
