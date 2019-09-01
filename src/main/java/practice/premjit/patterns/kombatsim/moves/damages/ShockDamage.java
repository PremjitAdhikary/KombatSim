package practice.premjit.patterns.kombatsim.moves.damages;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import practice.premjit.patterns.kombatsim.beats.BeatObserver;
import practice.premjit.patterns.kombatsim.beats.FlipFlopObserver;
import practice.premjit.patterns.kombatsim.common.FlipFlopable;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.strategies.ActionStrategy;
import practice.premjit.patterns.kombatsim.strategies.ReactionStrategy;
import practice.premjit.patterns.kombatsim.visitors.MoveVisitor;

/**
 * Along with base damage, this renders the opponent powerless to act or react for a certain duration. <br><br>
 * 
 * This it does by wrapping the original strategies of the opponent under a flag. This flag is disabled for the 
 * duration of the shock and then its enabled. <br><br>
 * 
 * In case there are overlapping Shock Damage, the shock duration lasts till the last shock damage. So no stacking of 
 * damages here. <br><br>  
 * 
 * @author Premjit Adhikary
 *
 */
public class ShockDamage extends BaseDamage implements FlipFlopable, Cloneable {
	public static final String TYPE = "Shock";
	protected int shockDuration;
	protected AbstractFighter fighter;
	protected ActionStrategy originalActionPerformer;
	protected ReactionStrategy originalReactionPerformer;
	BeatObserver timer;
	private boolean blocked;

	private ShockDamage(double damageAmount, int shockDuration) {
		super(damageAmount);
		this.shockDuration = shockDuration;
		this.damageType = TYPE;
	}
	
	public int shockDuration() {
		return this.shockDuration;
	}
	
	public void incrementShockDurationBy(int incr) {
		this.shockDuration += incr;
	}

	@Override
	public void affect(AbstractFighter fighter) {
		super.affect(fighter);
		this.fighter = fighter;
		shockFighter();
	}
	
	private void shockFighter() {
		this.originalActionPerformer = fighter.getActionStrategy();
		this.originalReactionPerformer = fighter.getReactionStrategy();
		timer = new FlipFlopObserver(this.shockDuration, this.fighter, this);
	}

	@Override
	public Map<String, String> mapify() {
		return KombatLogger.mapBuilder()
				.withPartial(super.mapify())
				.with(DURATION, Integer.toString(shockDuration))
				.buildPartial();
	}

	@Override
	public void set() {
		this.fighter.setActionStrategy(shockedActionStrategy());
		this.fighter.setReactionStrategy(shockedReactionStrategy());
		blocked = true;
	}

	@Override
	public void reset() {
		blocked = false;
	}

	@Override
	public void accept(MoveVisitor visitor) {
		visitor.visit(this);
	}
	
	private ActionStrategy shockedActionStrategy() {
		return () -> Optional.ofNullable(blocked ? null : originalActionPerformer)
						.ifPresent(ActionStrategy::perform);
	}
	
	private ReactionStrategy shockedReactionStrategy() {
		return move -> !blocked && originalReactionPerformer.perform(move);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public static ShockDamage create(Consumer<ShockDamageBuilder> block) {
		ShockDamageBuilder builder = new ShockDamageBuilder();
		block.accept(builder);
		return builder.build();
	}
	
	public static class ShockDamageBuilder {
		double max;
		int duration;
		boolean indirect;
		private ShockDamageBuilder() {}
		
		public ShockDamageBuilder max(double m) {
			max = m;
			return this;
		}
		
		public ShockDamageBuilder duration(int d) {
			duration = d;
			return this;
		}
		
		public ShockDamageBuilder indirect() {
			indirect = true;
			return this;
		}
		
		private ShockDamage build() {
			ShockDamage d = new ShockDamage(Randomizer.randomDoubleInRange(1, max), duration);
			if (indirect) d.setIndirect();
			return d;
		}
	}

}
