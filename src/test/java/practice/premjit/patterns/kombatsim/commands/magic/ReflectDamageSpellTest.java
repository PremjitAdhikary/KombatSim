package practice.premjit.patterns.kombatsim.commands.magic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.function.DoubleFunction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.AttributeUtility;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.Mage;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.damages.ColdDamage;
import practice.premjit.patterns.kombatsim.moves.damages.FireDamage;
import practice.premjit.patterns.kombatsim.moves.damages.PhysicalDamage;
import practice.premjit.patterns.kombatsim.moves.damages.ShockDamage;
import practice.premjit.patterns.kombatsim.strategies.magic.SpellBook;

@ExtendWith(MockitoExtension.class)
class ReflectDamageSpellTest {
    @Mock Mage mockMage;
    @Mock SpellBook book;
    ReflectDamageSpell spell;
    
    @BeforeEach
    void init() {
        KombatLogger.getLogger().disableLogging();
        spell = ReflectDamageSpell.create( reaction -> 
            reaction.mage(mockMage).name("test").book(book).reflectMoveName("reflectTest")
                .reflectMove(null).cooldown(3).activeDuration(5));
    }
    
    @Test
    @DisplayName("spell readiness")
    void testSpellReadiness() {
        assertTrue(spell.isReady());
    }
    
    @Test
    @DisplayName("when spell ready and spell not active and Mage has no Mana")
    void testWhenSpellReadyAndSpellNotActiveAndMageHasNoMana() {
        assertTrue(spell.isReady());
        assertFalse(spell.isActive());
        when(mockMage.currentMana()).thenReturn(0.0);
        assertFalse(spell.canBeActivated());
    }
    
    @TestInstance(Lifecycle.PER_CLASS)
    @Nested
    @DisplayName("test for spell ready and active")
    class SpellReadyAndSpellActive {
        
        @BeforeEach
        void init() {
            when(mockMage.currentMana()).thenReturn(10.0);
        }
        
        @Test
        @DisplayName("when spell ready and spell not active and Mage has Mana")
        void testWhenSpellReadyAndSpellNotActiveAndMageHasMana() {
            assertTrue(spell.isReady());
            assertFalse(spell.isActive());
            assertTrue(spell.canBeActivated());
        }
        
        @Test
        @DisplayName("When activated")
        void testActivate() {
            assertFalse(spell.canBeExecuted(Optional.empty()));
            
            VariableAttribute mana = AttributeUtility.buildMana(30, 25);
            when(mockMage.getAttribute(AttributeType.MANA)).thenReturn(Optional.of(mana));
            assertTrue(spell.activate());
            
            assertFalse(spell.isReady());
            assertTrue(spell.isActive());
            assertFalse(spell.canBeActivated());
            assertTrue(spell.canBeExecuted(Optional.empty()));
        }
        
        @Test
        @DisplayName("When cooling down")
        void testCooldowns() {
            VariableAttribute mana = AttributeUtility.buildMana(30, 25);
            when(mockMage.getAttribute(AttributeType.MANA)).thenReturn(Optional.of(mana));
            spell.activate();
            
            spell.cooldown.reset();
            
            assertTrue(spell.isReady());
            assertTrue(spell.isActive());
            assertFalse(spell.canBeActivated());
            assertTrue(spell.canBeExecuted(Optional.empty()));
            
            spell.active.reset();
            assertTrue(spell.isReady());
            assertFalse(spell.isActive());
            assertTrue(spell.canBeActivated());
            assertFalse(spell.canBeExecuted(Optional.empty()));
        }
        
    }
    
    @TestInstance(Lifecycle.PER_CLASS)
    @Nested
    @DisplayName("test for spell reactions with just Physical enabled")
    class SpellReactionToPhysical {
        DoubleFunction<Move> moveFunction;
        VariableAttribute life;
        @Captor ArgumentCaptor<Double> moveArgument;
        
        @BeforeEach
        void init() {
            moveFunction = mock(DoubleFunction.class);
            spell = ReflectDamageSpell.create( reaction -> 
                reaction.mage(mockMage).name("test").book(book).reflectMoveName("reflectTest")
                    .reflectMove(moveFunction).cooldown(3).activeDuration(5).reducePhysicalDamage(50));
            spell.active.set();

            life = AttributeUtility.buildLife(100);
            when(mockMage.getAttribute(AttributeType.LIFE)).thenReturn(Optional.of(life));
            lenient().when(mockMage.isAlive()).thenReturn(true);
        }
        
        @Test
        @DisplayName("When react to Physical Damage")
        void testSpellReactionToPhysicalDamage() {
            PhysicalDamage damage = PhysicalDamage.create( d -> d.min(20).max(20) );
            spell.execute(Optional.of(damage));
            assertEquals(90.0, life.current());
            verify(moveFunction).apply(moveArgument.capture());
            assertEquals(10.0, moveArgument.getValue());
        }
        
        @Test
        @DisplayName("When react to Indirect Physical Damage")
        void testSpellReactionToIndirectPhysicalDamage() {
            PhysicalDamage damage = PhysicalDamage.create( d -> d.min(20).max(20).indirect() );
            spell.execute(Optional.of(damage));
            assertEquals(90.0, life.current());
            verify(moveFunction, never()).apply(moveArgument.capture());
        }
        
        @Test
        @DisplayName("When react to Physical Damage without limit")
        void testSpellReactionToPhysicalDamageNoLimit() {
            PhysicalDamage damage = PhysicalDamage.create( d -> d.min(140).max(140) );
            spell.execute(Optional.of(damage));
            assertEquals(30.0, life.current());
            verify(moveFunction).apply(moveArgument.capture());
            assertEquals(70.0, moveArgument.getValue());
        }
        
        @Test
        @DisplayName("When react to Physical Damage with limit")
        void testSpellReactionToPhysicalDamageWithLimit() {
            PhysicalDamage damage = PhysicalDamage.create( d -> d.min(140).max(140) );
            spell.limitDamage = 30;
            spell.execute(Optional.of(damage));
            assertEquals(70.0, life.current());
            verify(moveFunction).apply(moveArgument.capture());
            assertEquals(70.0, moveArgument.getValue());
        }
        
        @Test
        @DisplayName("When react to Fire Damage")
        void testSpellReactionToFireDamage() {
            FireDamage damage = FireDamage.create(d -> d.min(50).max(50).damage(10).duration(5));
            spell.execute(Optional.of(damage));
            assertEquals(50.0, life.current());
            verify(moveFunction, never()).apply(moveArgument.capture());
            assertEquals(10.0, damage.burnAmount());
        }
        
        @Test
        @DisplayName("When react to Cold Damage")
        void testSpellReactionToColdDamage() {
            ColdDamage damage = ColdDamage.create(d -> d.min(20).duration(5).deterioration(20));
            spell.execute(Optional.of(damage));
            assertTrue(life.current() > 75.0 && life.current() < 80.0);
            verify(moveFunction, never()).apply(moveArgument.capture());
            assertEquals(20.0, damage.deteriorationPercentage());
        }
        
        @Test
        @DisplayName("When react to Shock Damage")
        void testSpellReactionToShockDamage() {
            ShockDamage damage = ShockDamage.create(d -> d.max(1).duration(5));
            spell.execute(Optional.of(damage));
            assertEquals(99.0, life.current());
            verify(moveFunction, never()).apply(moveArgument.capture());
            assertEquals(5, damage.shockDuration());
        }
        
    }
    
    @TestInstance(Lifecycle.PER_CLASS)
    @Nested
    @DisplayName("test for spell reactions")
    class SpellReaction {
        DoubleFunction<Move> moveFunction;
        VariableAttribute life;
        @Captor ArgumentCaptor<Double> moveArgument;
        
        @BeforeEach
        void init() {
            moveFunction = mock(DoubleFunction.class);
            spell = ReflectDamageSpell.create( reaction -> 
            reaction.mage(mockMage).name("test").book(book).reflectMoveName("reflectTest")
                .reflectMove(moveFunction).cooldown(3).activeDuration(5).reducePhysicalDamage(50)
                .reduceFireDamage(10).reduceColdDamage(5).reduceShockDamage(20));
            spell.active.set();

            life = AttributeUtility.buildLife(100);
            when(mockMage.getAttribute(AttributeType.LIFE)).thenReturn(Optional.of(life));
            lenient().when(mockMage.isAlive()).thenReturn(true);
        }
        
        @Test
        @DisplayName("When react to Physical Damage")
        void testSpellReactionToPhysicalDamage() {
            PhysicalDamage damage = PhysicalDamage.create( d -> d.min(20).max(20) );
            spell.execute(Optional.of(damage));
            assertEquals(90.0, life.current());
            verify(moveFunction).apply(moveArgument.capture());
            assertEquals(10.0, moveArgument.getValue());
        }
        
        @Test
        @DisplayName("When react to Fire Damage")
        void testSpellReactionToFireDamage() {
            FireDamage damage = FireDamage.create(d -> d.min(50).max(50).damage(10).duration(5));
            spell.execute(Optional.of(damage));
            assertEquals(55.0, life.current());
            verify(moveFunction).apply(moveArgument.capture());
            assertEquals(5.0, moveArgument.getValue());
            assertEquals(9.0, damage.burnAmount());
        }
        
        @Test
        @DisplayName("When react to Cold Damage")
        void testSpellReactionToColdDamage() {
            ColdDamage damage = ColdDamage.create(d -> d.min(20).duration(5).deterioration(20));
            spell.execute(Optional.of(damage));
            assertTrue(life.current() > 76.0 && life.current() < 81.0);
            verify(moveFunction).apply(moveArgument.capture());
            assertTrue(moveArgument.getValue() < 1.25);
            assertEquals(19.0, damage.deteriorationPercentage());
        }
        
        @Test
        @DisplayName("When react to Shock Damage")
        void testSpellReactionToShockDamage() {
            ShockDamage damage = ShockDamage.create(d -> d.max(1).duration(5));
            spell.execute(Optional.of(damage));
            assertEquals(99.2, life.current());
            verify(moveFunction).apply(moveArgument.capture());
            assertEquals(0.2, moveArgument.getValue());
            assertEquals(4, damage.shockDuration());
        }
        
    }

}
