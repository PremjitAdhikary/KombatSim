package practice.premjit.patterns.kombatsim.commands.hero;

import java.util.Map;
import java.util.function.Supplier;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.attributes.VariableAttribute;
import practice.premjit.patterns.kombatsim.commands.ActionCommand;
import practice.premjit.patterns.kombatsim.common.Identifiable;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.common.logging.Loggable;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.Move.Recipient;

/**
 * Similar to {@link practice.premjit.patterns.kombatsim.commands.professional.StaminaBasedAction StaminaBasedAction}, 
 * MojoBasedAction can only be executed by {@link practice.premjit.patterns.kombatsim.fighters.Heroes heroes}. This 
 * also has a {@link MojoBasedAction#mojoCost cost} (on mojo, by default 80, but can be set to something else) 
 * associated with it. <p>
 * 
 * Only difference is that the execution can result in any Move (Buff or Damage) as determined by the {@link 
 * MojoBasedAction#moveSupplier moveSupplier} and sent to any {@link MojoBasedAction#recipient recipient}.
 * 
 * @author Premjit Adhikary
 *
 */
public class MojoBasedAction implements ActionCommand, Identifiable {
	AbstractFighter fighter;
	String name;
	Supplier<Move> moveSupplier;
	Recipient recipient;
	double mojoCost;
	int id;
	
	public MojoBasedAction(AbstractFighter fighter, String name, Supplier<Move> moveSupplier) {
		this.fighter = fighter;
		this.name = name;
		this.moveSupplier = moveSupplier;
		this.recipient = Recipient.OPPONENT;
		this.mojoCost = 80;
		id = Randomizer.generateId();
	}

	@Override
	public boolean canBeExecuted() {
		return fighterHasMojo();
	}

	@Override
	public void execute() {
		chargeMojo();
		sendMove();
	}

	public void setMojoCost(double mojoCost) {
		this.mojoCost = mojoCost;
	}
	
	public void setRecipient(Recipient r) {
		this.recipient = r;
	}
	
	public Map<String, String> mapify(Move move) {
		try {
			return KombatLogger.mapBuilder()
					.withName(name)
					.with(((Loggable) move).mapify())
					.build();
		} catch (Exception e) {
			return null;
		}
	}
	
	private boolean fighterHasMojo() {
		return fighter.getAttribute(AttributeType.MOJO)
				.map(a -> (VariableAttribute) a)
				.filter(va -> va.current() > mojoCost)
				.isPresent();
	}
	
	private void chargeMojo() {
		fighter.getAttribute(AttributeType.MOJO).ifPresent(mojo -> 
			((VariableAttribute) mojo).incrementCurrent(-mojoCost));
	}
	
	private void sendMove() {
		Move move = moveSupplier.get();
		KombatLogger.getLogger().log(KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.ACTION, 
				fighter.mapify(), mapify(move));
		fighter.arena().sendMove(move, recipient, this.fighter);
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public int id() {
		return id;
	}

}
