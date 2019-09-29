package practice.premjit.patterns.kombatsim.commands.physical;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
class BlockTest {
    @Mock ArenaMediator mockArena;
    @Mock AbstractFighter mockFighter;
    @InjectMocks Block block;
    PhysicalDamage damage;
    
    @BeforeEach
    void init() {
        KombatLogger.getLogger().disableLogging();
        damage = PhysicalDamage.create(d -> d.min(20).max(20));
    }
    
    @Test
    @DisplayName("when no damage")
    void testWhenNoDamage() {
        assertFalse(block.canBeExecuted(Optional.empty()));
    }
    
    @Test
    @DisplayName("when fighter with no strength")
    void testWhenFighterWithNoStrength() {
        when(mockFighter.getAttribute(AttributeType.STRENGTH)).thenReturn(Optional.empty());
        assertFalse(block.canBeExecuted(Optional.of(damage)));
    }
    
    @Nested
    @DisplayName("when fighter with strength")
    class WhenFighterWithStrength {
        double strength = 50;
        
        @BeforeEach
        void init() {
            lenient().when(mockFighter.getAttribute(AttributeType.STRENGTH))
                .thenReturn(Optional.of(AttributeUtility.buildStrength(strength)));
        }
        
        @Test
        void testCanBeExecuted() {
            assertTrue(block.canBeExecuted(Optional.of(damage)));
        }

        @Test
        void testReduceDamage() {
            block.reduceDamage(damage);
            assertEquals(9, damage.amount());
        }

        @Test
        void testExecute() {
            VariableAttribute life = AttributeUtility.buildLife(30);
            assertEquals(30, life.base());
            assertEquals(30, life.current());
            
            lenient().when(mockFighter.getAttribute(AttributeType.LIFE))
                .thenReturn(Optional.of(life));
            
            block.execute(Optional.of(damage));
            assertEquals(30, life.base());
            assertEquals(21, life.current());
        }
        
    }

}
