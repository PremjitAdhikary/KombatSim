package practice.premjit.patterns.kombatsim.arenas;

import practice.premjit.patterns.kombatsim.fighters.Fighter;
import practice.premjit.patterns.kombatsim.moves.Move;

public interface ArenaMediator {
	
	void sendMove(Move move, Move.Recipient recipient, Fighter fighter);
	
	void addFighter(Fighter fighter);

}
