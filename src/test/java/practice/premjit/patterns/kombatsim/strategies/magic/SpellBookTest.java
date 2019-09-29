package practice.premjit.patterns.kombatsim.strategies.magic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.commands.magic.AbstractReactionSpell;
import practice.premjit.patterns.kombatsim.commands.magic.ActionSpell;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.Mage;

@ExtendWith(MockitoExtension.class)
class SpellBookTest {
    SpellBook book;
    @Mock Mage mockMage;
    @Mock ArenaMediator mockArena;
    
    @BeforeEach
    void init() {
        KombatLogger.getLogger().disableLogging();
        book = new SpellBook(mockMage);
    }

    @Test
    @DisplayName("Spells added")
    void testSpellsAdded() {
        assertEquals(0, book.actionSpellsReady.size());
        ActionSpell mockAction = mock(ActionSpell.class);
        book.returnSpell(mockAction);
        when(mockAction.canBeExecuted()).thenReturn(true);
        assertEquals(1, book.actionSpellsReady.size());
        assertTrue(book.getActionSpell().isPresent());

        assertEquals(0, book.reactionSpellsReady.size());
        AbstractReactionSpell mockReaction = mock(AbstractReactionSpell.class);
        book.returnSpell(mockReaction);
        assertEquals(1, book.reactionSpellsReady.size());
    }
    
    @TestInstance(Lifecycle.PER_CLASS)
    @Nested
    @DisplayName("when multiple action spells")
    class WhenMultipleActionSpells {
        ActionSpell mockAction1, mockAction2, mockAction3;
        Map<String, ActionSpell> actionMap;
        
        @BeforeEach
        void init() {
            mockAction1 = mock(ActionSpell.class);
            mockAction2 = mock(ActionSpell.class);
            mockAction3 = mock(ActionSpell.class);
            book.borrowSpell(mockAction1);
            book.borrowSpell(mockAction2);
            book.borrowSpell(mockAction3);
            lenient().when(mockAction1.name()).thenReturn("mock1");
            lenient().when(mockAction2.name()).thenReturn("mock2");
            lenient().when(mockAction3.name()).thenReturn("mock3");
            actionMap = new HashMap<>();
            actionMap.put("mock1", mockAction1);
            actionMap.put("mock2", mockAction2);
            actionMap.put("mock3", mockAction3);
        }

        @ParameterizedTest
        @MethodSource("provideArgumentsForTestActionAvailibility")
        @DisplayName("check availibility")
        void testActionAvailibility(List<String> actionsReturned, int expectedCooldown, int expectedReady, 
                List<String> actionsCooldown) {
            assertEquals(0, book.actionSpellsReady.size());
            assertEquals(3, book.actionSpellsCooldown.size());
            
            for (String spellName : actionsReturned)
                book.returnSpell(actionMap.get(spellName));
            
            assertEquals(expectedCooldown, book.actionSpellsCooldown.size());
            assertIterableEquals(actionsCooldown, 
                    book.actionSpellsCooldown
                        .stream()
                        .map(ActionSpell::name)
                        .collect(Collectors.toList()));
            
            assertEquals(expectedReady, book.actionSpellsReady.size());
            assertIterableEquals(actionsReturned, 
                    book.actionSpellsReady
                        .stream()
                        .map(ActionSpell::name)
                        .collect(Collectors.toList()));
        }
        
        Stream<Arguments> provideArgumentsForTestActionAvailibility() {
            return Stream.of(
                    Arguments.of(Arrays.asList(), 3, 0, Arrays.asList("mock1", "mock2", "mock3")),
                    Arguments.of(Arrays.asList("mock1"), 2, 1, Arrays.asList("mock2", "mock3")),
                    Arguments.of(Arrays.asList("mock1", "mock2"), 1, 2, Arrays.asList("mock3")),
                    Arguments.of(Arrays.asList("mock1", "mock2", "mock3"), 0, 3, Arrays.asList())
            );
        }
        
        @Test
        @DisplayName("check get action spell")
        void testGetActionSpell() {
            assertFalse(book.getActionSpell().isPresent());
            book.returnSpell(mockAction1);
            book.returnSpell(mockAction2);
            when(mockAction1.canBeExecuted()).thenReturn(false);
            when(mockAction2.canBeExecuted()).thenReturn(false);
            assertFalse(book.getActionSpell().isPresent());
            when(mockAction1.canBeExecuted()).thenReturn(true);
            assertTrue(book.getActionSpell().isPresent());
        }
        
        @Test
        void testPerform() {
            book.returnSpell(mockAction1);
            book.setActionChance(0);
            book.perform();
            verify(mockAction1, never()).execute();
            book.setActionChance(100);
            when(mockAction1.canBeExecuted()).thenReturn(true);
            book.perform();
            verify(mockAction1).execute();
        }
        
    }
    
    @TestInstance(Lifecycle.PER_CLASS)
    @Nested
    @DisplayName("when multiple reaction spells")
    class WhenMultipleReactionSpells {
        AbstractReactionSpell mockReaction1, mockReaction2, mockReaction3;
        Map<String, AbstractReactionSpell> reactionMap;
        
        @BeforeEach
        void init() {
            mockReaction1 = mock(AbstractReactionSpell.class);
            mockReaction2 = mock(AbstractReactionSpell.class);
            mockReaction3 = mock(AbstractReactionSpell.class);
            book.borrowSpell(mockReaction1);
            book.borrowSpell(mockReaction2);
            book.borrowSpell(mockReaction3);
            lenient().when(mockReaction1.name()).thenReturn("mock1");
            lenient().when(mockReaction2.name()).thenReturn("mock2");
            lenient().when(mockReaction3.name()).thenReturn("mock3");
            reactionMap = new HashMap<>();
            reactionMap.put("mock1", mockReaction1);
            reactionMap.put("mock2", mockReaction2);
            reactionMap.put("mock3", mockReaction3);
        }

        @ParameterizedTest
        @MethodSource("provideArgumentsForTestReactionAvailibility")
        @DisplayName("check availibility")
        void testActionAvailibility(List<String> reactionsReturned, int expectedCooldown, int expectedReady, 
                List<String> reactionsCooldown) {
            assertEquals(0, book.reactionSpellsReady.size());
            assertEquals(3, book.reactionSpellsCooldown.size());
            
            for (String spellName : reactionsReturned)
                book.returnSpell(reactionMap.get(spellName));
            
            assertEquals(expectedCooldown, book.reactionSpellsCooldown.size());
            assertIterableEquals(reactionsCooldown, 
                    book.reactionSpellsCooldown
                        .stream()
                        .map(AbstractReactionSpell::name)
                        .collect(Collectors.toList()));
            
            assertEquals(expectedReady, book.reactionSpellsReady.size());
            assertIterableEquals(reactionsReturned, 
                    book.reactionSpellsReady
                        .stream()
                        .map(AbstractReactionSpell::name)
                        .collect(Collectors.toList()));
        }
        
        Stream<Arguments> provideArgumentsForTestReactionAvailibility() {
            return Stream.of(
                    Arguments.of(Arrays.asList(), 3, 0, Arrays.asList("mock1", "mock2", "mock3")),
                    Arguments.of(Arrays.asList("mock1"), 2, 1, Arrays.asList("mock2", "mock3")),
                    Arguments.of(Arrays.asList("mock1", "mock2"), 1, 2, Arrays.asList("mock3")),
                    Arguments.of(Arrays.asList("mock1", "mock2", "mock3"), 0, 3, Arrays.asList())
            );
        }
        
        @Test
        @DisplayName("check get reaction spell when no ready spells")
        void testGetActionSpellWhenNoReactionSpellsReady() {
            assertTrue(book.reactionSpellsReady.isEmpty());
            assertFalse(book.getReactionSpell().isPresent());
        }
        
        @Test
        @DisplayName("check get reaction spell when one spell active")
        void testGetActionSpellWhenAReactionActive() {
            book.returnSpell(mockReaction1);
            book.returnSpell(mockReaction2);
            assertFalse(book.reactionSpellsReady.isEmpty());
            when(mockMage.allReactions()).thenReturn(Arrays.asList(mockReaction1, mockReaction2, mockReaction3));
            when(mockReaction1.isActive()).thenReturn(false);
            when(mockReaction2.isActive()).thenReturn(false);
            when(mockReaction3.isActive()).thenReturn(true);
            assertFalse(book.getReactionSpell().isPresent());
        }
        
        @Test
        @DisplayName("check get reaction spell when no spell active")
        void testGetActionSpellWhenNoReactionActive() {
            book.returnSpell(mockReaction1);
            book.returnSpell(mockReaction2);
            assertFalse(book.reactionSpellsReady.isEmpty());
            when(mockMage.allReactions()).thenReturn(Arrays.asList(mockReaction1, mockReaction2, mockReaction3));
            when(mockReaction1.isActive()).thenReturn(false);
            when(mockReaction2.isActive()).thenReturn(false);
            when(mockReaction3.isActive()).thenReturn(false);
            assertTrue(book.getReactionSpell().isPresent());
        }
        
        @Test
        void testPerform() {
            when(mockMage.allReactions()).thenReturn(Arrays.asList(mockReaction1, mockReaction2, mockReaction3));
            book.returnSpell(mockReaction1);
            when(mockReaction1.isActive()).thenReturn(false);
            when(mockReaction2.isActive()).thenReturn(false);
            when(mockReaction3.isActive()).thenReturn(false);
            assertFalse(book.perform(Optional.empty()));
            verify(mockReaction1).activate();
            when(mockReaction1.isActive()).thenReturn(true);
            assertTrue(book.perform(Optional.empty()));
            verify(mockReaction1).execute(any(Optional.class));
        }
        
    }

}
