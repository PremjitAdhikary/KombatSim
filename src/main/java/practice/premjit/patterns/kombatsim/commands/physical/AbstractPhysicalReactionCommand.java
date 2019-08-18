package practice.premjit.patterns.kombatsim.commands.physical;

import java.util.Map;
import java.util.Optional;

import practice.premjit.patterns.kombatsim.commands.ReactionCommand;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.damages.PhysicalDamage;

/**
 * Basic Physical Reaction Command. Holds all the common logic to validate and execute a reaction.
 * 
 * @author Premjit Adhikary
 *
 */
public abstract class AbstractPhysicalReactionCommand implements ReactionCommand {
	protected AbstractFighter fighter;
	protected String name;
	
	public AbstractPhysicalReactionCommand(AbstractFighter fighter) {
		this.fighter = fighter;
	}

	@Override
	public boolean canBeExecuted(Optional<Move> move) {
		return move.isPresent() && move.get() instanceof PhysicalDamage;
	}

	@Override
	public void execute(Optional<Move> move) {
		if (!move.isPresent())
			return;
		reduceDamage((PhysicalDamage) move.get());
		move.get().affect(fighter);
		KombatLogger.getLogger().log(
				KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.REACTION, 
				fighter.mapify(), mapify((PhysicalDamage) move.get()));
	}
	
	public abstract void reduceDamage(PhysicalDamage physicalDamage);
	
	public Map<String, String> mapify(PhysicalDamage damage) {
		try {
			return KombatLogger.mapBuilder()
					.withName(name)
					.with(damage.mapify())
					.build();
		} catch (Exception e) {
			return null;
		}
	}

}
