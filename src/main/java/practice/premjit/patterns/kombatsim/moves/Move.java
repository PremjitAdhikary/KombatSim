package practice.premjit.patterns.kombatsim.moves;

import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.visitors.MoveVisitor;

/**
 * Just a marker
 * 
 * @author Premjit Adhikary
 *
 */
public interface Move {
	
	enum Recipient {
		SELF,
		OPPONENT,
		ALL
	}
	
	void affect(AbstractFighter fighter);
	
	void accept(MoveVisitor visitor);

}
