package practice.premjit.patterns.kombatsim.commands.magic;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.DoubleFunction;
import java.util.function.Function;

import practice.premjit.patterns.kombatsim.attributes.AttributeType;
import practice.premjit.patterns.kombatsim.common.logging.KombatLogger;
import practice.premjit.patterns.kombatsim.common.logging.Loggable;
import practice.premjit.patterns.kombatsim.fighters.Mage;
import practice.premjit.patterns.kombatsim.moves.Move;
import practice.premjit.patterns.kombatsim.moves.Move.Recipient;
import practice.premjit.patterns.kombatsim.moves.buffs.AffectAttribute;
import practice.premjit.patterns.kombatsim.moves.buffs.AffectVariableAttribute;
import practice.premjit.patterns.kombatsim.moves.buffs.AttributeSteal;
import practice.premjit.patterns.kombatsim.moves.buffs.Buff;
import practice.premjit.patterns.kombatsim.moves.damages.BaseDamage;
import practice.premjit.patterns.kombatsim.moves.damages.ColdDamage;
import practice.premjit.patterns.kombatsim.moves.damages.FireDamage;
import practice.premjit.patterns.kombatsim.moves.damages.PhysicalDamage;
import practice.premjit.patterns.kombatsim.moves.damages.ShockDamage;
import practice.premjit.patterns.kombatsim.strategies.magic.SpellBook;
import practice.premjit.patterns.kombatsim.visitors.MoveVisitor;

/**
 * The basic idea of ReflectDamageSpell is that it absorbs some part and reflects other part of the damage inflicted 
 * to the mage. <p>
 * 
 * It can be set up to reduce specific percentage of damage for each type of damage using the following apis.<p><ul>
 * <li>{@link #reducePhysicalDamage(double)}
 * <li>{@link #reduceFireDamage(double)}
 * <li>{@link #reduceColdDamage(double)}
 * <li>{@link #reduceShockDamage(double)}
 * </ul>
 * 
 * {@link ReflectDamageSpell.DamageReducer reduceDamage} using Visitor pattern determines the type of damage and 
 * reduces damage accordingly.
 * 
 * {@link ReflectDamageSpell#moveFunction moveFunction} returns the damage to the attacker. This is a {@link Function} 
 * which accepts the reduced damage from {@link ReflectDamageSpell.DamageReducer#reduced reduced} and can be set up 
 * to build up on it. <p>
 * 
 * Along with that we have {@link #limitDamage} which limits the amount of damage that the mage is inflicted with. 
 * This can be setup with {@link #setLimitDamage(double)}
 * 
 * @author Premjit Adhikary
 *
 */
public class ReflectDamageSpell extends AbstractReactionSpell {
	protected DamageReducer reduceDamage;
	protected DoubleFunction<Move> moveFunction;
	protected Recipient recipient;
	protected String reflectiveName;
	protected double limitDamage;
	protected Map<String, Double> damageReduction;

	public ReflectDamageSpell(Mage mage, String name, SpellBook book, int cooldownDuration, 
			int activeDuration, String reflectiveName) {
		super(mage, name, book, cooldownDuration, activeDuration);
		recipient = Recipient.OPPONENT;
		reduceDamage = new DamageReducer();
		this.reflectiveName = reflectiveName;
		limitDamage = 0;
		damageReduction = new HashMap<>(4);
	}

	@Override
	public void execute(Optional<Move> move) {
		if (!move.isPresent())
			return;
		reduceDamage(move.get());
		affectMage(move.get());
		if (damageReflectable())
			reflectDamage();
	}

	private void reduceDamage(Move move) {
		reduceDamage.reduced = 0;
		reduceDamage.indirectDamage = false;
		move.accept(reduceDamage);
	}

	private void affectMage(Move move) {
		move.affect(mage);
		KombatLogger.getLogger().log(
				KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.REACTION, 
				mage.mapify(), mapify(move, name));
	}

	private boolean damageReflectable() {
		return !reduceDamage.indirectDamage && reduceDamage.reduced > 0 && moveFunction != null;
	}

	private void reflectDamage() {
		Move reflect = moveFunction.apply(reduceDamage.reduced);
		if (reflect != null) {
			KombatLogger.getLogger().log(
					KombatLogger.LEVEL.HIGH, KombatLogger.EVENT_TYPE.ACTION, 
					mage.mapify(), mapify(reflect, reflectiveName));
			mage.arena().sendMove(reflect, recipient, mage);
		}
	}
	
	public void setMoveFunction(DoubleFunction<Move> moveFunction) {
		this.moveFunction = moveFunction;
	}

	public void setRecipient(Recipient r) {
		this.recipient = r;
	}

	public void setLimitDamage(double limitDamage) {
		this.limitDamage = limitDamage;
	}

	@Override
	protected void onActivate() {
		// do nothing
	}
	
	public void reducePhysicalDamage(double percentage) {
		damageReduction.put(PhysicalDamage.TYPE, percentage);
	}
	
	public void reduceFireDamage(double percentage) {
		damageReduction.put(FireDamage.TYPE, percentage);
	}
	
	public void reduceColdDamage(double percentage) {
		damageReduction.put(ColdDamage.TYPE, percentage);
	}
	
	public void reduceShockDamage(double percentage) {
		damageReduction.put(ShockDamage.TYPE, percentage);
	}
	
	public Map<String, String> mapify(Move move, String name) {
		try {
			return KombatLogger.mapBuilder()
					.withName(name)
					.with(((Loggable) move).mapify())
					.build();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * DamageReducer implements Visitor pattern to determine the move, then reduce damage accordingly based on set up 
	 * percentages.
	 * 
	 * @author Premjit Adhikary
	 *
	 */
	class DamageReducer implements MoveVisitor {
		double reduced;
		boolean indirectDamage;
		
		double reduced() {
			return reduced;
		}

		@Override
		public void visit(Move m) {
			// do nothing
		}

		@Override
		public void visit(PhysicalDamage p) {
			if (!reduceBaseDamage(p, PhysicalDamage.TYPE))
				return;
			
			limitDamage(p);
		}

		@Override
		public void visit(FireDamage f) {
			if (!reduceBaseDamage(f, FireDamage.TYPE))
				return;
			
			limitDamage(f);
			
			double reduceBurn = f.burnAmount() * damageReduction.get(FireDamage.TYPE) / 100;
			f.incrementBurnAmountBy(-reduceBurn);
		}

		@Override
		public void visit(ColdDamage c) {
			if (!reduceBaseDamage(c, ColdDamage.TYPE))
				return;
			limitDamage(c);
			
			double reduceDetoriationPercentage = 
					c.deteriorationPercentage() * damageReduction.get(ColdDamage.TYPE) / 100;
			c.incrementDeteriorationPercentageBy(-reduceDetoriationPercentage);
		}

		@Override
		public void visit(ShockDamage s) {
			if (!reduceBaseDamage(s, ShockDamage.TYPE))
				return;
			limitDamage(s);
			
			int reduceShockDuration = 
					(int) Math.floor(s.shockDuration() * damageReduction.get(ShockDamage.TYPE) / 100);
			s.incrementShockDurationBy(-reduceShockDuration);
		}

		@Override
		public void visit(Buff b) {
			// do nothing
			reduced = 0;
		}

		@Override
		public void visit(AffectAttribute aa) {
			// do nothing
			reduced = 0;
		}

		@Override
		public void visit(AffectVariableAttribute ave) {
			// do nothing
			reduced = 0;
		}

		@Override
		public void visit(AttributeSteal as) {
			// do nothing
			reduced = 0;
		}
		
		private boolean reduceBaseDamage(BaseDamage b, String type) {
			indirectDamage = b.isIndirect();
			if (!damageReduction.containsKey(type))
				return false;
			
			reduced = b.amount() * damageReduction.get(type) / 100;
			b.incrementBy(-reduced);
			return true;
		}
		
		private void limitDamage(BaseDamage b) {
			if (limitDamage == 0 || !mage.getAttribute(AttributeType.LIFE).isPresent())
				return;
			mage.getAttribute(AttributeType.LIFE).ifPresent(life -> {
				double damage = life.base() * limitDamage / 100;
				if (b.amount() > damage) {
					b.incrementBy(-(b.amount()-damage));
				}
			});
		}
		
	}

}
