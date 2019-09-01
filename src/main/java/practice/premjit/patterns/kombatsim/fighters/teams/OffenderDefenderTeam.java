package practice.premjit.patterns.kombatsim.fighters.teams;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.util.Optional;

import practice.premjit.patterns.kombatsim.arenas.ArenaMediator;
import practice.premjit.patterns.kombatsim.beats.BeatObserver;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.fighters.Fighter;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.Move.Recipient;
import practice.premjit.patterns.kombatsim.visitors.FighterVisitor;

public class OffenderDefenderTeam extends AbstractTeam {
	protected AbstractFighter offender;
	protected AbstractFighter defender;

	public OffenderDefenderTeam(ArenaMediator arena) {
		super("OffenderDefenderTeam", arena);
		proxyArena = new OffenderDefenderArena(this);
		id = Randomizer.generateId();
	}
	
	@Override
	protected AbstractFighter currentFighter() {
		return (!defender.isAlive()) ? offender : defender;
	}
	
	public void addOffender(AbstractFighter off) {
		this.offender = off;
		this.offender.arena(proxyArena);
		this.offender.setActionStrategy(new OffenderActionStrategy(this.offender));
		KombatLogger.getLogger().log(
				KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.INFO, 
				this.offender.mapify(), 
				KombatLogger.mapBuilder()
					.withName(TEAMMATE_ADDED)
					.with(AS, "Offender")
					.with(TEAM, this.name)
					.build());
	}
	
	public void addDefender(AbstractFighter def) {
		this.defender = def;
		this.defender.arena(proxyArena);
		this.defender.setActionStrategy(new OffenderActionStrategy(this.defender));
		KombatLogger.getLogger().log(
				KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.INFO, 
				this.defender.mapify(), 
				KombatLogger.mapBuilder()
					.withName(TEAMMATE_ADDED)
					.with(AS, "Defender")
					.with(TEAM, this.name)
					.build());
	}
	
	@Override
	public boolean isAlive() {
		return offender.isAlive() || defender.isAlive();
	}

	@Override
	public void react(Optional<Move> move) {
		currentFighter().react(move);
	}

	@Override
	public void update() {
		offender.update();
		defender.update();
	}

	@Override
	public void registerObserver(BeatObserver observer) {
		offender.registerObserver(observer);
		defender.registerObserver(observer);
	}

	@Override
	public void unregisterObserver(BeatObserver observer) {
		offender.unregisterObserver(observer);
		defender.unregisterObserver(observer);
	}

	@Override
	public void notifyObservers() {
		offender.notifyObservers();
		defender.notifyObservers();
	}

	@Override
	public void accept(FighterVisitor visitor) {
		visitor.visit(defender);
	}
	
	class OffenderActionStrategy extends AbstractTeamActionStrategy {

		public OffenderActionStrategy(AbstractFighter fighter) {
			super(fighter);
		}

		@Override
		protected boolean originalStrategyEnabled() {
			return (fighter == offender && offender.isAlive()) 
					|| (fighter == defender && !offender.isAlive() && defender.isAlive());
		}
		
	}
	
	class OffenderDefenderArena extends ProxyArena {

		OffenderDefenderArena(AbstractFighter team) {
			super(team);
		}

		@Override
		protected void selfMove(Move move, Recipient recipient, Fighter fighter) {
			fighter.react(Optional.ofNullable(move));
		}

		@Override
		protected void allMove(Move move, Recipient recipient, Fighter fighter) {
			offender.react(Optional.ofNullable(moveCloner.clone(move)));
			defender.react(Optional.ofNullable(moveCloner.clone(move)));
			arena.sendMove(move, Recipient.OPPONENT, team);
		}

		@Override
		protected void opponentMove(Move move, Recipient recipient, Fighter fighter) {
			arena.sendMove(move, Recipient.OPPONENT, team);
		}
		
	}


}
