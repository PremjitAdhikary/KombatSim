package practice.premjit.patterns.kombatsim.commands;

import java.util.Optional;

import practice.premjit.patterns.kombatsim.moves.Move;

public interface ReactionCommand {
	
	boolean canBeExecuted(Optional<Move> move);
	
	void execute(Optional<Move> move);

}
