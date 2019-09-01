package practice.premjit.patterns.kombatsim.strategies.physical;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

import practice.premjit.patterns.kombatsim.beats.DexterityBasedReactionObserver;
import practice.premjit.patterns.kombatsim.commands.ReactionCommand;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;

@ExtendWith(MockitoExtension.class)
class BasicReactionStrategyTest {
	@Mock AbstractFighter mockFighter;
	@Mock DexterityBasedReactionObserver mockObserver;
	BasicReactionStrategy strategy;
	
	@BeforeEach
	void init() {
		KombatLogger.getLogger().disableLogging();
		strategy = new BasicReactionStrategy(mockFighter);
		strategy.reactionObserver = mockObserver;
	}

	@Test
	void testExecuteAction() {
		assertFalse(strategy.execute(Optional.empty(), Optional.empty()));
	}
	
	@Test
	void testSelectReactionWhenNoReactions() {
		List<ReactionCommand> reactions = new ArrayList<>();
		when(mockFighter.allReactions()).thenReturn(reactions);
		when(mockObserver.reactEnabled()).thenReturn(true);
		assertFalse(strategy.selectReaction(Optional.empty()).isPresent());
		assertFalse(strategy.perform(Optional.empty()));
	}
	
	@TestInstance(Lifecycle.PER_CLASS)
	@Nested
	@DisplayName("test with reactions")
	class WhenReactions {
		ReactionCommand mockReaction;
		List<ReactionCommand> reactions;
		
		@BeforeEach
		void init() {
			mockReaction = mock(ReactionCommand.class);
			reactions = new ArrayList<>();
			reactions.add(mockReaction);
		}
		
		@Test
		void testSelectReactionWhenReactDisabled() {
			when(mockObserver.reactEnabled()).thenReturn(false);
			assertFalse(strategy.selectReaction(Optional.empty()).isPresent());
		}
		
		@Test
		void testSelectReactionWhenHasNoExecutableAction() {
			when(mockObserver.reactEnabled()).thenReturn(true);
			when(mockFighter.allReactions()).thenReturn(reactions);
			assertFalse(strategy.selectReaction(Optional.empty()).isPresent());
		}
		
		@Test
		void testSelectActionWhenHasExecutableReaction() {
			when(mockObserver.reactEnabled()).thenReturn(true);
			when(mockFighter.allReactions()).thenReturn(reactions);
			when(mockReaction.canBeExecuted(any(Optional.class))).thenReturn(true);
			assertTrue(strategy.selectReaction(Optional.empty()).isPresent());
		}
		
		@Test
		void testPerform() {
			when(mockObserver.reactEnabled()).thenReturn(true);
			when(mockFighter.allReactions()).thenReturn(reactions);
			when(mockReaction.canBeExecuted(any(Optional.class))).thenReturn(true);

			assertTrue(strategy.perform(Optional.empty()));
			verify(mockObserver).reactSuccessful();
			verify(mockReaction).execute(any(Optional.class));
		}
		
	}

}
