package practice.premjit.patterns.kombatsim.commands.physical;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.Move.Recipient;
import practice.premjit.patterns.kombatsim.moves.damages.PhysicalDamage;

@ExtendWith(MockitoExtension.class)
class KickTest {
    @Mock ArenaMediator mockArena;
    @Mock AbstractFighter mockFighter;
    @InjectMocks Kick kick;
    
    @BeforeEach
    void init() {
        KombatLogger.getLogger().disableLogging();
    }
    
    @Test
    @DisplayName("when fighter with no strength")
    void testWhenFighterWithNoStrength() {
        lenient().when(mockFighter.getAttribute(AttributeType.STRENGTH)).thenReturn(Optional.empty());
        lenient().when(mockFighter.getAttribute(AttributeType.DEXTERITY))
            .thenReturn(Optional.of(AttributeUtility.buildDexterity(40)));
        assertFalse(kick.canBeExecuted());
    }
    
    @Test
    @DisplayName("when fighter with no dexterity")
    void testWhenFighterWithNoDexterity() {
        lenient().when(mockFighter.getAttribute(AttributeType.STRENGTH))
            .thenReturn(Optional.of(AttributeUtility.buildStrength(40)));
        lenient().when(mockFighter.getAttribute(AttributeType.DEXTERITY)).thenReturn(Optional.empty());
        assertFalse(kick.canBeExecuted());
    }
    
    @Test
    @DisplayName("when fighter with no strength or dexterity")
    void testWhenFighterWithNeitherStrengthNorDexterity() {
        lenient().when(mockFighter.getAttribute(AttributeType.STRENGTH)).thenReturn(Optional.empty());
        lenient().when(mockFighter.getAttribute(AttributeType.DEXTERITY)).thenReturn(Optional.empty());
        assertFalse(kick.canBeExecuted());
    }
    
    @Nested
    @DisplayName("when fighter with more strength")
    class WhenFighterWithMoreStrength{
        double strength = 50;
        double dexterity = 0;
        @Captor ArgumentCaptor<Move> moveArgument;
        
        @BeforeEach
        void init() {
            lenient().when(mockFighter.getAttribute(AttributeType.STRENGTH))
                .thenReturn(Optional.of(AttributeUtility.buildStrength(strength)));
            lenient().when(mockFighter.getAttribute(AttributeType.DEXTERITY))
                .thenReturn(Optional.of(AttributeUtility.buildDexterity(dexterity)));
        }
        
        @Test
        void testCanBeExecuted() {
            assertTrue(kick.canBeExecuted());
        }

        @RepeatedTest(10)
        void testExecute() {
            when(mockFighter.arena()).thenReturn(mockArena);
            kick.execute();
            
            verify(mockArena).sendMove(moveArgument.capture(), eq(Recipient.OPPONENT), eq(mockFighter));
            assertTrue(moveArgument.getValue() instanceof PhysicalDamage);
            
            PhysicalDamage damage = (PhysicalDamage) moveArgument.getValue();
            assertTrue(damage.amount() >= Kick.MIN_DAMAGE);
            assertTrue(damage.amount() <= Kick.MAX_DAMAGE);
        }
    }
    
    @Nested
    @DisplayName("when fighter with more dexterity")
    class WhenFighterWithMoreDexterity{
        double strength = 20;
        double dexterity = 1000;
        @Captor ArgumentCaptor<Move> moveArgument;
        
        @BeforeEach
        void init() {
            lenient().when(mockFighter.getAttribute(AttributeType.STRENGTH))
                .thenReturn(Optional.of(AttributeUtility.buildStrength(strength)));
            lenient().when(mockFighter.getAttribute(AttributeType.DEXTERITY))
                .thenReturn(Optional.of(AttributeUtility.buildDexterity(dexterity)));
        }
        
        @Test
        void testCanBeExecuted() {
            assertTrue(kick.canBeExecuted());
        }

        @RepeatedTest(10)
        void testExecute() {
            when(mockFighter.arena()).thenReturn(mockArena);
            kick.execute();
            
            verify(mockArena).sendMove(moveArgument.capture(), eq(Recipient.OPPONENT), eq(mockFighter));
            assertTrue(moveArgument.getValue() instanceof PhysicalDamage);
            
            PhysicalDamage damage = (PhysicalDamage) moveArgument.getValue();
            assertTrue(damage.amount() >= Kick.MIN_DAMAGE + 6);
            assertTrue(damage.amount() <= Kick.MAX_DAMAGE + 6 + 100);
        }
    }
    
    @Nested
    @DisplayName("when fighter with balanced strength and dexterity")
    class WhenFighterWithBalancedStrengthAndDexterity {
        double strength = 80;
        double dexterity = 30;
        @Captor ArgumentCaptor<Move> moveArgument;
        
        @BeforeEach
        void init() {
            lenient().when(mockFighter.getAttribute(AttributeType.STRENGTH))
                .thenReturn(Optional.of(AttributeUtility.buildStrength(strength)));
            lenient().when(mockFighter.getAttribute(AttributeType.DEXTERITY))
                .thenReturn(Optional.of(AttributeUtility.buildDexterity(dexterity)));
        }
        
        @Test
        void testCanBeExecuted() {
            assertTrue(kick.canBeExecuted());
        }

        @RepeatedTest(10)
        void testExecute() {
            when(mockFighter.arena()).thenReturn(mockArena);
            kick.execute();
            
            verify(mockArena).sendMove(moveArgument.capture(), eq(Recipient.OPPONENT), eq(mockFighter));
            assertTrue(moveArgument.getValue() instanceof PhysicalDamage);
            
            PhysicalDamage damage = (PhysicalDamage) moveArgument.getValue();
            assertTrue(damage.amount() >= Kick.MIN_DAMAGE);
            assertTrue(damage.amount() <= Kick.MAX_DAMAGE + 24 + 10);
        }
    }

}
