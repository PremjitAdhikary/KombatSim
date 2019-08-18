package practice.premjit.patterns.kombatsim.fighters.decorators;

import java.util.Map;
import java.util.function.Supplier;

import practice.premjit.patterns.kombatsim.commands.ActionCommand;
import practice.premjit.patterns.kombatsim.common.Identifiable;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.common.logging.Loggable;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.Move.Recipient;

/**
 * Projectiles add additional ActionCommands of type {@link Projectiles.ProjectileCommand ProjectileCommand} that the 
 * Fighter can call upon. The catch is that there is a limit to the number of projectiles available {@link 
 * #projectileCount}. This count is accounted for in {@link Projectiles.ProjectileCommand#canBeExecuted()} <p>
 * 
 * {@link #moveSupplier} supplies the move to inflict the opponent. <p>
 * 
 * @author Premjit Adhikary
 *
 */
public class Projectiles extends AbstractFighterDecorator {
	private String projectileName;
	private int projectileCount;
	private Supplier<Move> moveSupplier;
	private Recipient recipient;

	public Projectiles(AbstractFighter fighter) {
		super(fighter);
		recipient = Recipient.OPPONENT;
	}

	public void setProjectileName(String projectileName) {
		this.projectileName = projectileName;
	}

	public void setProjectileCount(int projectileCount) {
		this.projectileCount = projectileCount;
	}

	public void setMoveSupplier(Supplier<Move> moveSupplier) {
		this.moveSupplier = moveSupplier;
	}

	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}

	@Override
	public void equip() {
		if (isEquipped())
			return;
		theFighter.addAction(new ProjectileCommand());
		equipped();
	}
	
	class ProjectileCommand implements ActionCommand, Identifiable {
		private int id;

		ProjectileCommand() {
			this.id = Randomizer.generateId();
		}

		@Override
		public boolean canBeExecuted() {
			return projectileCount > 0;
		}

		@Override
		public void execute() {
			Move move = calculateMove();
			projectileCount--;
			sendMove(move);
		}

		@Override
		public String name() {
			return projectileName;
		}

		@Override
		public int id() {
			return id;
		}
		
		private Move calculateMove() {
			return moveSupplier.get();
		}
		
		private void sendMove(Move move) {
			KombatLogger.getLogger().log(
					KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.ACTION, theFighter.mapify(), mapify(move));
			theFighter.arena().sendMove(move, recipient, theFighter);
		}
		
		public Map<String, String> mapify(Move move) {
			try {
				return KombatLogger.mapBuilder()
						.withName(projectileName)
						.with("Count", String.valueOf(projectileCount))
						.with(((Loggable) move).mapify())
						.build();
			} catch (Exception e) {
				return null;
			}
		}
		
	}

}
