package practice.premjit.patterns.kombatsim.strategies.hero;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import practice.premjit.patterns.kombatsim.commands.AllActions;
import practice.premjit.patterns.kombatsim.commands.physical.AbstractPhysicalActionCommand;
import practice.premjit.patterns.kombatsim.commands.physical.Kick;
import practice.premjit.patterns.kombatsim.commands.physical.Punch;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.Hero;

@ExtendWith(MockitoExtension.class)
class BatmanActionStrategyTest {
	@Mock Hero batman;
	BatmanActionStrategy strategy;
	Punch punch;
	Kick kick;
	@Mock AbstractPhysicalActionCommand mockAction;
	
	@BeforeEach
	void init() {
		KombatLogger.getLogger().disableLogging();
	}
	
	@Nested
	@DisplayName("when batman not equipped")
	class WhenVanillaBatman {
		
		@BeforeEach
		void init() {
			strategy = new BatmanActionStrategy(batman, false);
			when(mockAction.name()).thenReturn(AllActions.KICK.value());
			when(batman.allActions()).thenReturn(Arrays.asList(mockAction));
		}
		
		@Test
		void testSelectAction() {
			assertEquals(mockAction, strategy.selectAction().get());
		}
		
		@Test
		void testPerform() {
			strategy.perform();
			verify(mockAction).execute();
		}
		
	}
	
	@Nested
	@DisplayName("when batman equipped")
	class WhenEquippedBatman {
		
		@BeforeEach
		void init() {
			strategy = new BatmanActionStrategy(batman, true);
			strategy.actionCount = 1;
			when(mockAction.name()).thenReturn(AllActions.PUNCH.value());
			when(batman.allActions()).thenReturn(Arrays.asList(mockAction));
		}
		
		@Test
		void testSelectAction() {
			assertEquals(mockAction, strategy.selectAction().get());
		}
		
		@Test
		void testPerform() {
			strategy.perform();
			verify(mockAction).execute();
		}
		
	}

}
