package practice.premjit.patterns.kombatsim.strategies.physical;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import practice.premjit.patterns.kombatsim.beats.DexterityBasedReactionObserver;
import practice.premjit.patterns.kombatsim.commands.ReactionCommand;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.strategies.AbstractFighterReactionStrategy;

/**
 * Most Basic ReactionStrategy... Select an ReactionCommand at random
 * 
 * @author Premjit Adhikary
 *
 */
public class BasicReactionStrategy extends AbstractFighterReactionStrategy {
	protected DexterityBasedReactionObserver reactionObserver;

	public BasicReactionStrategy(AbstractFighter fighter) {
		super(fighter);
		reactionObserver = new DexterityBasedReactionObserver();
		reactionObserver.setFighter(this.fighter);
		this.fighter.registerObserver(reactionObserver);
	}

	@Override
	protected Optional<ReactionCommand> selectReaction(Optional<Move> move) {
		if (!reactionObserver.reactEnabled()) {
			return Optional.empty();
		}
		
		List<ReactionCommand> allEnabledReactions = fighter.allReactions()
				.stream()
				.filter( a -> a.canBeExecuted(move) )
				.collect(Collectors.toList());
		if (allEnabledReactions.isEmpty())
			return Optional.empty(); 
	
		int selectedReaction = Randomizer.randomInteger(allEnabledReactions.size()-1);
		return Optional.of(allEnabledReactions.get(selectedReaction));
	}

	@Override
	protected boolean execute(Optional<ReactionCommand> reaction, Optional<Move> move) {
		if (!reaction.isPresent()) 
			return false;
		
		reactionObserver.reactSuccessful();
		reaction.get().execute(move);
		return true;
	}

}
