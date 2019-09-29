package practice.premjit.patterns.kombatsim.strategies.physical;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import practice.premjit.patterns.kombatsim.commands.ActionCommand;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;

@ExtendWith(MockitoExtension.class)
class BasicActionStrategyTest {
    @Mock AbstractFighter mockFighter;
    BasicActionStrategy strategy;
    
    @BeforeEach
    void init() {
        KombatLogger.getLogger().disableLogging();
        strategy = new BasicActionStrategy(mockFighter);
    }

    @Test
    void testExecuteAction() {
        strategy.execute(Optional.empty());
    }
    
    @Test
    void testSelectActionWhenNoActions() {
        List<ActionCommand> actions = new ArrayList<>();
        when(mockFighter.allActions()).thenReturn(actions);
        assertFalse(strategy.selectAction().isPresent());
    }
    
    @TestInstance(Lifecycle.PER_CLASS)
    @Nested
    @DisplayName("test with actions")
    class WhenActions {
        ActionCommand mockAction;
        
        @BeforeEach
        void init() {
            mockAction = mock(ActionCommand.class);
            List<ActionCommand> actions = new ArrayList<>();
            actions.add(mockAction);
            when(mockFighter.allActions()).thenReturn(actions);
        }
        
        @Test
        void testSelectActionWhenHasNoExecutableAction() {
            assertFalse(strategy.selectAction().isPresent());
        }
        
        @Test
        void testSelectActionWhenHasExecutableAction() {
            when(mockAction.canBeExecuted()).thenReturn(true);
            assertEquals(mockAction, strategy.selectAction().get());
        }
        
        @Test
        void testPerform() {
            when(mockAction.canBeExecuted()).thenReturn(true);
            strategy.perform();
            verify(mockAction).execute();
        }
        
    }

}
