package practice.premjit.patterns.kombatsim.strategies;

import java.util.Optional;

import practice.premjit.patterns.kombatsim.commands.ReactionCommand;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.Move;

/**
 * Strategy Pattern employed to select and execute ReactionCommand
 * 
 * @author Premjit Adhikary
 *
 */
public abstract class AbstractFighterReactionStrategy implements ReactionStrategy {
	protected AbstractFighter fighter;
	
	public AbstractFighterReactionStrategy(AbstractFighter fighter) {
		this.fighter = fighter;
	}

	@Override
	public boolean perform(Optional<Move> move) {
		Optional<ReactionCommand> reaction = selectReaction(move);
		return execute(reaction, move);
	}
	
	protected abstract Optional<ReactionCommand> selectReaction(Optional<Move> move);
	
	protected abstract boolean execute(Optional<ReactionCommand> reaction, Optional<Move> move);

}
