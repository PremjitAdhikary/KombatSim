package practice.premjit.patterns.kombatsim.moves.damages;

import static practice.premjit.patterns.kombatsim.common.logging.SourcesAndEventsConstants.*;

import java.util.Map;
import java.util.function.Consumer;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.beats.VariableAttributeModifier;
import practice.premjit.patterns.kombatsim.common.Randomizer;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.fighters.AbstractFighter;
import practice.premjit.patterns.kombatsim.visitors.MoveVisitor;

/**
 * Along with the base damage, this adds a burn damage which depletes life over a certain duration. <br><br>
 * 
 * Burn Damage can stack over another burn damage. <br><br>
 * 
 * @author Premjit Adhikary
 *
 */
public class FireDamage extends BaseDamage {
	public static final String TYPE = "Fire";
	protected int burnDuration;
	protected double burnDamage;
	
	private FireDamage(double damage, int burnDuration, double burnDamage) {
		super(damage);
		this.burnDuration = burnDuration;
		this.burnDamage = burnDamage;
		damageType = TYPE;
	}
	
	public double burnAmount() {
		return this.burnDamage;
	}
	
	public void incrementBurnAmountBy(double incr) {
		this.burnDamage += incr;
	}

	@Override
	public void affect(AbstractFighter fighter) {
		super.affect(fighter);
		if (canBurn()) 
			burn(fighter);
	}
	
	private boolean canBurn() {
		return burnDuration > 0;
	}
	
	private void burn(AbstractFighter fighter) {
		VariableAttributeModifier vam = 
				new VariableAttributeModifier(fighter, AttributeType.LIFE, -(burnDamage/burnDuration), burnDuration);
		fighter.registerObserver(vam);
	}

	@Override
	public Map<String, String> mapify() {
		return KombatLogger.mapBuilder()
				.withPartial(super.mapify())
				.with(DURATION, Integer.toString(burnDuration))
				.with(BURN_DAMAGE, Double.toString(burnDamage))
				.buildPartial();
	}

	@Override
	public void accept(MoveVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public static FireDamage create(Consumer<FireDamageBuilder> block) {
		FireDamageBuilder builder = new FireDamageBuilder();
		block.accept(builder);
		return builder.build();
	}
	
	public static class FireDamageBuilder {
		double min;
		double max;
		double damage;
		int duration;
		boolean indirect;
		private FireDamageBuilder() {}
		
		public FireDamageBuilder min(double m) {
			this.min = m;
			return this;
		}
		
		public FireDamageBuilder max(double m) {
			this.max = m;
			return this;
		}
		
		public FireDamageBuilder damage(double d) {
			this.damage = d;
			return this;
		}
		
		public FireDamageBuilder duration(int d) {
			this.duration = d;
			return this;
		}
		
		public FireDamageBuilder indirect() {
			this.indirect = true;
			return this;
		}
		
		private FireDamage build() {
			FireDamage d = new FireDamage(Randomizer.randomDoubleInRange(min, max), duration, damage);
			if (this.indirect) d.setIndirect();
			return d;
		}
	}

}
