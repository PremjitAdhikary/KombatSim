package practice.premjit.patterns.kombatsim.fighters;

import java.util.Optional;

import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.visitors.FighterVisitor;

public interface Fighter {
	
	double maxLife();
	double currentLife();
	
	void act();
	void react(Optional<Move> move);
	
	void accept(FighterVisitor visitor);

}
