package practice.premjit.patterns.kombatsim.commands.physical;

import java.util.Map;

import practice.premjit.patterns.kombatsim.commands.ActionCommand;
import practice.premjit.patterns.kombatsim.common.Identifiable;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.Move.Recipient;
import practice.premjit.patterns.kombatsim.moves.damages.PhysicalDamage;

/**
 * Basic Physical Action Command. Holds all the common logic to validate and execute an action.
 * 
 * @author Premjit Adhikary
 *
 */
public abstract class AbstractPhysicalActionCommand implements ActionCommand, Identifiable {
	protected AbstractFighter fighter;
	protected String name;
	protected int id;
	
	public AbstractPhysicalActionCommand(AbstractFighter fighter) {
		this.fighter = fighter;
		id = Randomizer.generateId();
	}

	@Override
	public void execute() {
		PhysicalDamage damage = calculatePhysicalDamage();
		sendPhysicalDamage(damage);
	}
	
	@Override
	public String name() {
		return name;
	}
	
	@Override
	public int id() {
		return id;
	}
	
	protected abstract PhysicalDamage calculatePhysicalDamage();
	
	protected void sendPhysicalDamage(PhysicalDamage damage) {
		KombatLogger.getLogger().log(
				KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.ACTION, fighter.mapify(), mapify(damage));
		this.fighter.arena().sendMove(damage, Recipient.OPPONENT, this.fighter);
	}
	
	
	public Map<String, String> mapify(PhysicalDamage damage) {
		try {
			return KombatLogger.mapBuilder()
					.withName(name)
					.withPartial(damage.mapify())
					.build();
		} catch (Exception e) {
			return null;
		}
	}

}
