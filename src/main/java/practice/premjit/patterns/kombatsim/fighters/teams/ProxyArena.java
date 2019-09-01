package practice.premjit.patterns.kombatsim.fighters.teams;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.fighters.Fighter;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.Move.Recipient;
import practice.premjit.patterns.kombatsim.visitors.MoveCloner;

public abstract class ProxyArena implements ArenaMediator {
	AbstractFighter team;
	protected MoveCloner moveCloner;
	
	ProxyArena(AbstractFighter team) {
		this.team = team;
		moveCloner = new MoveCloner();
	}

	@Override
	public void sendMove(Move move, Recipient recipient, Fighter fighter) {
		switch(recipient) {
		case SELF:
			selfMove(move, recipient, fighter);
			break;
		case ALL:
			allMove(move, recipient, fighter);
			break;
		case OPPONENT:
			opponentMove(move, recipient, fighter);
			break;
		}
	}
	
	protected abstract void selfMove(Move move, Recipient recipient, Fighter fighter);
	
	protected abstract void allMove(Move move, Recipient recipient, Fighter fighter);
	
	protected abstract void opponentMove(Move move, Recipient recipient, Fighter fighter);

	@Override
	public void addFighter(Fighter fighter) {
		// do nothing
	}

}
